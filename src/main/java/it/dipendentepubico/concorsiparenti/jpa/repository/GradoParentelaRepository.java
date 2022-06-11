package it.dipendentepubico.concorsiparenti.jpa.repository;


import it.dipendentepubico.concorsiparenti.jpa.entity.GradoParentelaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradoParentelaRepository extends JpaRepository<GradoParentelaEntity, Integer> {
    GradoParentelaEntity findGradoParentelaEntityByDescrizione(String descrizione);
}
