package it.dipendentepubico.concorsiparenti.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import it.dipendentepubico.concorsiparenti.jpa.entity.GraduatoriaFinaleEntity;

public interface GraduatoriaRepository extends JpaRepository<GraduatoriaFinaleEntity, Integer> {
    GraduatoriaFinaleEntity findGraduatoriaFinaleEntityByConcorsoId(Integer concorsoId);
}
