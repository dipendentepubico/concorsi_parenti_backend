package it.dipendentepubico.concorsiparenti.jpa.repository;

import it.dipendentepubico.concorsiparenti.jpa.entity.SettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepository extends JpaRepository<SettingsEntity, String> {
}
