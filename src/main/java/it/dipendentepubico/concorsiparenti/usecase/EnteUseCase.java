package it.dipendentepubico.concorsiparenti.usecase;

import it.dipendentepubico.concorsiparenti.domain.Constants;
import it.dipendentepubico.concorsiparenti.domain.EnteDomain;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.concurrent.Future;

public interface EnteUseCase extends AbstractUseCase{
    void writeEnteListToFormat(Constants.EXPORT_TYPE format, String sortField, String sortDirection, String search, OutputStream outputStream);
    EnteDomain insertEnte(EnteDomain toInsert);

    EnteDomain updateEnte(EnteDomain toUpdate);

    Future allineaOpenData(String filename);

    EnteDomain retrieveEnte(Integer enteId);

    void uploadAndAlign(MultipartFile file);
}
