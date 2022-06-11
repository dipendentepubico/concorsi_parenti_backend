package it.dipendentepubico.concorsiparenti.usecase;

import it.dipendentepubico.concorsiparenti.domain.*;
import it.dipendentepubico.concorsiparenti.domain.reqsponse.DipendenteInsertDomainRequest;
import it.dipendentepubico.concorsiparenti.domain.reqsponse.DipendenteInsertUpdateDomainResponse;
import it.dipendentepubico.concorsiparenti.domain.reqsponse.DipendenteUpdateDomainRequest;


import java.io.OutputStream;
import java.util.Date;

public interface DipendenteUseCase {
    DipendenteInsertUpdateDomainResponse insert(DipendenteInsertDomainRequest dipinsert);

    DipendenteInsertUpdateDomainResponse update(DipendenteUpdateDomainRequest dipupdate);

    DipendenteDomain getDipendenteById(Integer dipendenteId);

    void delete(Integer dipendenteId);

    void writeDipendenteListToFormat(Constants.EXPORT_TYPE exportType, String sortField, String sortDirection, String search, OutputStream outputStream);

    DipendenteConParentiDomain findDipendenteConParenti(String codiceFiscale, Date data);
}
