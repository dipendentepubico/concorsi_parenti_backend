package it.dipendentepubico.concorsiparenti.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import it.dipendentepubico.concorsiparenti.jpa.entity.CategoriaEntity;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Integer> {
}
