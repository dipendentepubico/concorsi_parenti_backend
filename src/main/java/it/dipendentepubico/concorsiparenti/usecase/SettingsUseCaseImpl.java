package it.dipendentepubico.concorsiparenti.usecase;

import it.dipendentepubico.concorsiparenti.domain.Constants;
import it.dipendentepubico.concorsiparenti.jpa.entity.SettingsEntity;
import it.dipendentepubico.concorsiparenti.jpa.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SettingsUseCaseImpl implements SettingsUseCase{
    @Autowired
    private SettingsRepository settingsRepository;

    @Override
    public boolean isAlignRunning() {
        Optional<SettingsEntity> byId = settingsRepository.findById(Constants.ESettingsCodes.ALLINEAMENTO_IN_CORSO.getCode());
        return byId.isPresent() && byId.get().getValore().equals(Constants.ESettingsAllineamentoValues.SI.getCode());
    }

    @Override
    public void setAlignRunning() {
        Optional<SettingsEntity> byId = settingsRepository.findById(Constants.ESettingsCodes.ALLINEAMENTO_IN_CORSO.getCode());
        if (byId.isPresent()) byId.get().setValore(Constants.ESettingsAllineamentoValues.SI.getCode());
    }

    @Override
    @Transactional
    public void setAlignStopped() {
        Optional<SettingsEntity> byId = settingsRepository.findById(Constants.ESettingsCodes.ALLINEAMENTO_IN_CORSO.getCode());
        if (byId.isPresent()) byId.get().setValore(Constants.ESettingsAllineamentoValues.NO.getCode());
    }
}
