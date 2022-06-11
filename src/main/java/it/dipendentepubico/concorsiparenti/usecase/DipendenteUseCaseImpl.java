package it.dipendentepubico.concorsiparenti.usecase;

import it.dipendentepubico.concorsiparenti.domain.*;
import it.dipendentepubico.concorsiparenti.domain.reqsponse.DipendenteInsertDomainRequest;
import it.dipendentepubico.concorsiparenti.domain.reqsponse.DipendenteInsertUpdateDomainResponse;
import it.dipendentepubico.concorsiparenti.domain.reqsponse.DipendenteUpdateDomainRequest;
import it.dipendentepubico.concorsiparenti.jpa.entity.AnagraficaEntity;
import it.dipendentepubico.concorsiparenti.jpa.entity.CategoriaEntity;
import it.dipendentepubico.concorsiparenti.jpa.entity.DipendenteEntity;
import it.dipendentepubico.concorsiparenti.jpa.entity.EnteEntity;
import it.dipendentepubico.concorsiparenti.jpa.repository.AnagraficaRepository;
import it.dipendentepubico.concorsiparenti.jpa.repository.CategoriaRepository;
import it.dipendentepubico.concorsiparenti.jpa.repository.DipendenteRepository;
import it.dipendentepubico.concorsiparenti.jpa.repository.EnteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.dipendentepubico.concorsiparenti.usecase.exporter.TableSearchExporter;

import java.io.OutputStream;
import java.util.*;

@Service
public class DipendenteUseCaseImpl extends AbstractUseCaseImpl implements DipendenteUseCase {
    @Autowired
    private DipendenteRepository dipendenteRepository;
    @Autowired
    private AnagraficaRepository anagraficaRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private EnteRepository enteRepository;

    @Override
    public JpaSpecificationExecutor getRepository() {
        return dipendenteRepository;
    }

    @Override
    @Transactional
    public DipendenteInsertUpdateDomainResponse insert(DipendenteInsertDomainRequest dipinsert) {
        // mappo campi corrispondenti
        DipendenteEntity dip = mapper.map(dipinsert, DipendenteEntity.class);
        Optional<AnagraficaEntity> anagraficaRepositoryById = anagraficaRepository.findById(dipinsert.getIdAnagrafica());
        Optional<CategoriaEntity> categoriaRepositoryById = categoriaRepository.findById(dipinsert.getIdCategoria());
        Optional<EnteEntity> enteRepositoryById = enteRepository.findById(dipinsert.getIdEnte());
        if(!anagraficaRepositoryById.isPresent() || !categoriaRepositoryById.isPresent() || !enteRepositoryById.isPresent()) {
            throw new NoSuchElementException();
        }

        dip.setAnagrafica(anagraficaRepositoryById.get());
        dip.setCategoria(categoriaRepositoryById.get());
        dip.setEnte(enteRepositoryById.get());

        DipendenteEntity dipendente = dipendenteRepository.save(dip);

        return mapper.map(dipendente, DipendenteInsertUpdateDomainResponse.class);

    }

    @Override
    @Transactional
    public DipendenteInsertUpdateDomainResponse update(DipendenteUpdateDomainRequest dipupdate) {
        DipendenteEntity dip = dipendenteRepository.getById(dipupdate.getId());
        // mappo campi corrispondenti tranne anagrafica
        // prima i campi descrittivi
        mapper.map(dipupdate, dip);
        // poi i campi id
        dip.setCategoria( categoriaRepository.getById(dipupdate.getIdCategoria()) );
        dip.setEnte( enteRepository.getById(dipupdate.getIdEnte()) );

        DipendenteEntity dipendente = dipendenteRepository.save(dip);

        return mapper.map(dipendente, DipendenteInsertUpdateDomainResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public DipendenteDomain getDipendenteById(Integer dipendenteId) {
        return mapper.map(dipendenteRepository.getById(dipendenteId), DipendenteDomain.class);
    }

    @Override
    public void delete(Integer dipendenteId) {
        dipendenteRepository.deleteById(dipendenteId);
    }

    @Override
    public void writeDipendenteListToFormat(Constants.EXPORT_TYPE exportType, String sortField, String sortDirection, String search, OutputStream outputStream) {
        // ottengo i dati
        List<DipendenteEntity> enteList = retrieveEntityFilteredList(sortField, sortDirection, search);
        TableSearchExporter<DipendenteEntity> exporter = new TableSearchExporter<>(
                () -> {
                    List<String> headerList = new ArrayList<>(2);
                    headerList.add("Dipendente ID");
                    headerList.add("Nome");
                    headerList.add("Cognome");
                    headerList.add("Codice Fiscale");
                    headerList.add("Data Inizio");
                    headerList.add("Data Fine");
                    headerList.add("Categoria");
                    return headerList;
                },
                entity -> {
                    List arr = new ArrayList(2);
                    arr.add(entity.getId());
                    arr.add(entity.getAnagrafica().getNome());
                    arr.add(entity.getAnagrafica().getCognome());
                    arr.add(entity.getAnagrafica().getCodiceFiscale());
                    arr.add(entity.getDataInizio());
                    arr.add(entity.getDataFine());
                    arr.add(entity.getCategoria().getDescrizione());
                    return arr;
                });
        switch (exportType) {
            case CSV:
                exporter.exportCSV(enteList, outputStream);
                break;
            case XLSX:
                exporter.exportXLSX(enteList, outputStream);
                break;

        }
    }

    @Override
    public DipendenteConParentiDomain findDipendenteConParenti(String codiceFiscale, Date data) {
        return dipendenteRepository.findDipendenteConParenti(codiceFiscale,data);
    }
}
