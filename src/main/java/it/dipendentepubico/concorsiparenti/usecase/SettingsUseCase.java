package it.dipendentepubico.concorsiparenti.usecase;

public interface SettingsUseCase {
    boolean isAlignRunning();
    void setAlignRunning();
    void setAlignStopped();
}
