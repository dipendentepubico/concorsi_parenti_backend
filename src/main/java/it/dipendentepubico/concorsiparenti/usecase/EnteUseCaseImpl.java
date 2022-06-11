package it.dipendentepubico.concorsiparenti.usecase;

import it.dipendentepubico.concorsiparenti.domain.Constants;
import it.dipendentepubico.concorsiparenti.domain.EnteDomain;
import it.dipendentepubico.concorsiparenti.jpa.entity.EStatoOpenData;
import it.dipendentepubico.concorsiparenti.jpa.entity.EnteEntity;
import it.dipendentepubico.concorsiparenti.jpa.repository.EnteRepository;
import it.dipendentepubico.concorsiparenti.usecase.exporter.TableSearchExporter;
import it.dipendentepubico.concorsiparenti.usecase.importer.EnteCSVImportThread;
import it.dipendentepubico.concorsiparenti.usecase.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class EnteUseCaseImpl extends AbstractUseCaseImpl implements EnteUseCase {
    private static final Logger logger = LoggerFactory.getLogger(EnteUseCaseImpl.class);

    @Autowired
    ApplicationContext context;

    @Autowired
    private StorageService storageService;

    @Autowired
    private EnteRepository enteRepository;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public JpaSpecificationExecutor getRepository() {
        return enteRepository;
    }

    @Override
    public void writeEnteListToFormat(Constants.EXPORT_TYPE format, String sortField, String sortDirection, String search, OutputStream outputStream) {
        List<EnteEntity> enteList = retrieveEntityFilteredList(sortField, sortDirection, search);
        TableSearchExporter<EnteEntity> exporter = new TableSearchExporter<>(
                () -> {
                    List<String> headerList = new ArrayList<>(2);
                    headerList.add("Ente ID");
                    headerList.add("Descrizione");
                    return headerList;
                },
                entity -> {
                    List arr = new ArrayList(2);
                    arr.add(entity.getId());
                    arr.add(entity.getDescrizione());
                    return arr;
                });
        switch (format) {
            case CSV:
                exporter.exportCSV(enteList, outputStream);
                break;
            case XLSX:
                exporter.exportXLSX(enteList, outputStream);
                break;

        }
    }

    @Override
    public EnteDomain insertEnte(EnteDomain toInsert) {
        EnteEntity e = mapper.map(toInsert, EnteEntity.class);
        e.setStatoOpenData(EStatoOpenData.NON_ALLINEATO);
        return mapper.map(enteRepository.save(e), EnteDomain.class);
    }

    @Override
    public EnteDomain updateEnte(EnteDomain toUpdate) {
        EnteEntity e = mapper.map(toUpdate, EnteEntity.class);
        e.setStatoOpenData(EStatoOpenData.NON_ALLINEATO);
        return mapper.map(enteRepository.save(e), EnteDomain.class);
    }


    @Override
    public Future allineaOpenData(String filename) {
        EnteCSVImportThread thread = context.getBean(EnteCSVImportThread.class);
        thread.setFilename(filename);
        Future<?> future = taskExecutor.submit(thread);
        return future;
    }

    @Override
    public void uploadAndAlign(MultipartFile file) {
        Path path = storageService.store(file);
        allineaOpenData(path.toFile().getAbsolutePath());
    }

    @Override
    public EnteDomain retrieveEnte(Integer enteId) {
        return mapper.map(enteRepository.getById(enteId), EnteDomain.class);
    }

}
