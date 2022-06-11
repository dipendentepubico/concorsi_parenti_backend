package it.dipendentepubico.concorsiparenti.jpa.repository;

import it.dipendentepubico.concorsiparenti.jpa.entity.DipendenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DipendenteRepository extends JpaRepository<DipendenteEntity, Integer>, DipendenteRepositoryCustom, JpaSpecificationExecutor<DipendenteEntity> {

}
