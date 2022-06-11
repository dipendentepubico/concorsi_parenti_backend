package it.dipendentepubico.concorsiparenti.jpa.auth.repository;

import java.util.Optional;

import it.dipendentepubico.concorsiparenti.jpa.auth.entity.RoleEnumEntity;
import it.dipendentepubico.concorsiparenti.jpa.auth.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(RoleEnumEntity name);
}