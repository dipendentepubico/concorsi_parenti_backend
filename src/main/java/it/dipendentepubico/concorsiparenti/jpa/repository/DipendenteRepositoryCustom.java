package it.dipendentepubico.concorsiparenti.jpa.repository;

import it.dipendentepubico.concorsiparenti.domain.DipendenteConParentiDomain;

import java.util.Date;

public interface DipendenteRepositoryCustom {
    DipendenteConParentiDomain findDipendenteConParenti(String codiceFiscale, Date data);
}
