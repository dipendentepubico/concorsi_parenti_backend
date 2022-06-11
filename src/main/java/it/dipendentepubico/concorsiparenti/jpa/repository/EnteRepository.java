package it.dipendentepubico.concorsiparenti.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import it.dipendentepubico.concorsiparenti.jpa.entity.EnteEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EnteRepository extends JpaRepository<EnteEntity, Integer>, JpaSpecificationExecutor<EnteEntity> {
    @Modifying
    @Query(value = "update EnteEntity e set e.statoOpenData =  'W' ")
    void setInCorso();

    EnteEntity findByCodiceFiscale(String codiceFiscale);
    EnteEntity findByCodiceIPA(String codiceIpa);

    @Modifying
    @Query(value = "update EnteEntity e " +
            " set e.statoOpenData =  'N' " +
            " where e.statoOpenData =  'W' ")
    void setNonAllineato();


}
