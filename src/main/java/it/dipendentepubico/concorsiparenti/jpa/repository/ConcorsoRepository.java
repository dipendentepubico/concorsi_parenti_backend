package it.dipendentepubico.concorsiparenti.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import it.dipendentepubico.concorsiparenti.jpa.entity.ConcorsoEntity;

public interface ConcorsoRepository extends JpaRepository<ConcorsoEntity, Integer>, JpaSpecificationExecutor<ConcorsoEntity> {
}
