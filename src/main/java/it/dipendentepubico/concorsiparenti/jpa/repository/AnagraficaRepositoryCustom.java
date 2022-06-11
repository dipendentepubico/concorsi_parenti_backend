package it.dipendentepubico.concorsiparenti.jpa.repository;

import it.dipendentepubico.concorsiparenti.jpa.entity.AnagraficaEntity;
import it.dipendentepubico.concorsiparenti.domain.AnagraficaConDipendentiParentiDomain;
import it.dipendentepubico.concorsiparenti.domain.ParenteDomain;

import java.util.List;

public interface AnagraficaRepositoryCustom {
    List<AnagraficaConDipendentiParentiDomain> findIdoneiConDipendentiParenti(Integer concorsoId);
    List<ParenteDomain> findDipendentiParentiByIdoneo(Integer concorsoId, Integer anagraficaIdoneoId);
    List<AnagraficaEntity> searchAutocomplete(String searchLike);
}
