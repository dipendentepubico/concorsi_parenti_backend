package it.dipendentepubico.concorsiparenti.usecase.importer;

import it.dipendentepubico.concorsiparenti.jpa.entity.EStatoOpenData;
import it.dipendentepubico.concorsiparenti.jpa.entity.EnteEntity;
import it.dipendentepubico.concorsiparenti.jpa.repository.EnteRepository;
import it.dipendentepubico.concorsiparenti.spring.websocket.StompAlignConfiguration;
import it.dipendentepubico.concorsiparenti.usecase.SettingsUseCase;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

@Component
@Scope("prototype")
public class EnteCSVImportThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(EnteCSVImportThread.class);

    @Autowired
    private SettingsUseCase settingsUseCase;

    @Autowired
    private EnteRepository enteRepository;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;
    @Autowired
    private StompAlignConfiguration stompAlignConfiguration;

    private String filename;

    private final Set<String> enti = Collections.synchronizedSet(new HashSet<>());

    private enum Headers {
        _id, Codice_IPA, Denominazione_ente, Codice_fiscale_ente, Codice_uni_uo, Codice_uni_aoo, Codice_uni_uo_padre, Descrizione_uo, Data_istituzione, Nome_responsabile, Cognome_responsabile, Mail_responsabile, Telefono_responsabile, Codice_comune_ISTAT, Codice_catastale_comune, CAP, Indirizzo, Telefono, Fax, Mail1, Tipo_Mail1, Mail2, Tipo_Mail2, Mail3, Tipo_Mail3, Data_aggiornamento, Url
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
//    @Transactional non funziona con un thread quindi creo manualmente la transazione
    public void run() {
        logger.info("Avvio thread allineamento");
        // prima transazione: setto align in progress = true
        // notifico via stomp ai client
        final TransactionTemplate t1 = new TransactionTemplate(platformTransactionManager);
        Boolean execute1 = t1.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                // aggiornamento in corso? esco
                if(settingsUseCase.isAlignRunning()){
                    logger.info("Allineamento in corso: esco e non proseguo allineamento");
                    return false;
                }
                // setto l'applicativo readonly impostando @BlockOnAlign sui controller e notificando via websocket
                settingsUseCase.setAlignRunning();
                stompAlignConfiguration.notifyAlignToClients(true);
                logger.info("Flag allineamento in corso settato");
                return true;
            }
        }
        );
        // seconda transazione: insert/update enti
        if(execute1) {
            final TransactionTemplate t2 = new TransactionTemplate(platformTransactionManager);
            t2.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    long startTime = System.nanoTime();

                    Reader in = null;
                    try {
                        logger.info("Avvio transazione allineamento");
                        // setto tutti gli enti a "in lavorazione"
                        enteRepository.setInCorso();
                        in = new FileReader(filename);
                        CSVParser parse = CSVFormat.DEFAULT.withHeader(Headers.class).withFirstRecordAsHeader().parse(in);
                        // più righe si riferiscono a stesso ente ma diverso ufficio

                        boolean newcode = true;

                        if(newcode) {

                            StreamSupport.stream(parse.spliterator(), true)
                                    .filter(skippaProcessati())
                                    .forEach(record -> {
                                        String codiceIpa = processaEntity(record);
                                        synchronized (enti) {
                                            enti.add(codiceIpa);
                                        }
                                    });
                            logger.info("Processati {} enti", enti.size());
                        }else {
                            Set<String> enti = new HashSet<>();
                            for (CSVRecord record : parse) {
                                String codiceIpa = record.get(Headers.Codice_IPA);
                                logger.debug("Letta riga con codice IPA " + codiceIpa);
                                if (!enti.contains(codiceIpa)) {
                                    processaEntityOld(record);
                                    enti.add(codiceIpa);
                                }
                            }
                            logger.info("Processati {} enti", enti.size());
                        }
                        logger.info("Setto le restanti entity a NON_ALLINEATO");
                        enteRepository.setNonAllineato();

                        logger.info("Transazione allineamento terminata");
                    } catch (IOException e) {
                        logger.error("Errore IO nel parse del csv", e);
                    }finally {
                        try {
                            if(in!=null) in.close();
                        } catch (IOException e) {
                            logger.error("Non riesco a chiudere il csv", e);
                        }
                    }
                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime);
                    logger.info("Tempo di esecuzione {} secondi", duration/1000000);
                }
            });


            // terza transazione: setto align in progress = false
            // notifico via stomp ai client
            final TransactionTemplate t3 = new TransactionTemplate(platformTransactionManager);
            t3.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    // setto applicativo su write
                    settingsUseCase.setAlignStopped();

                    stompAlignConfiguration.notifyAlignToClients(false);
                    logger.info("Flag allineamento in corso settato a false");
                }
            });

        }
        logger.info("Fine thread allineamento");
    }

    private void processaEntityOld(CSVRecord record) {
        // aggiorno la entity e la imposto allineata
        String codiceIpa = record.get(Headers.Codice_IPA);
        logger.debug("Processo nuova entity");
        EnteEntity e = enteRepository.findByCodiceIPA(codiceIpa);
        if (e == null) {
            e = new EnteEntity();
            e.setCodiceIPA(codiceIpa);
        }
        e.setCodiceFiscale(record.get(Headers.Codice_fiscale_ente));
        e.setDescrizione(record.get(Headers.Denominazione_ente));
        e.setStatoOpenData(EStatoOpenData.ALLINEATO);
        enteRepository.save(e);

    }

    private String processaEntity(CSVRecord record) {
        // aggiorno la entity e la imposto allineata
        String codiceIpa = record.get(Headers.Codice_IPA);
        logger.debug("Processo nuova entity {} con thread {}/{} {}", codiceIpa, ForkJoinPool.commonPool().getActiveThreadCount(),
                ForkJoinPool.commonPool().getParallelism(), Thread.currentThread().getName() );

        EnteEntity e = enteRepository.findByCodiceIPA(codiceIpa);
        if (e == null) {
            e = new EnteEntity();
            e.setCodiceIPA(codiceIpa);
        }
        e.setCodiceFiscale(record.get(Headers.Codice_fiscale_ente));
        e.setDescrizione(record.get(Headers.Denominazione_ente));
        e.setStatoOpenData(EStatoOpenData.ALLINEATO);
        enteRepository.save(e);
        return codiceIpa;
    }

    private Predicate<CSVRecord> skippaProcessati() {
        return record -> {
            synchronized (enti) {
                String codiceIpa = record.get(Headers.Codice_IPA);
                logger.debug("Processo nuova entity {} con thread {}/{} {}", codiceIpa, ForkJoinPool.commonPool().getActiveThreadCount(),
                        ForkJoinPool.commonPool().getParallelism(), Thread.currentThread().getName() );
                return !enti.contains(codiceIpa);
            }
        };
    }
}
