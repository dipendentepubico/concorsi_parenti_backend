package it.dipendentepubico.concorsiparenti.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import it.dipendentepubico.concorsiparenti.jpa.entity.AnagraficaEntity;
import it.dipendentepubico.concorsiparenti.jpa.entity.EnteEntity;

import java.util.Date;
import java.util.List;

public interface AnagraficaRepository extends JpaRepository<AnagraficaEntity, Integer>, JpaSpecificationExecutor<EnteEntity>, AnagraficaRepositoryCustom {
    @Query("select d.anagrafica from DipendenteEntity d " +
            " where d.ente.id = :enteId " +
            " and (d.dataFine is null or d.dataFine >= :data) and d.dataInizio <= :data " +
            " order by d.anagrafica.cognome, d.anagrafica.nome ")
    List<AnagraficaEntity> findDipendentiParentiEnte(Integer enteId, Date data);
}
