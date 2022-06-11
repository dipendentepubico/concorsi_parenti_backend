package it.dipendentepubico.concorsiparenti.rest.align.model;

public class AlignStatusMessage {
    private boolean alignRunning;

    /**
     * Necessario per serializzazione JSON
     */
    public AlignStatusMessage() {
    }

    public AlignStatusMessage(boolean alignRunning) {
        this.alignRunning = alignRunning;
    }

    public boolean isAlignRunning() {
        return alignRunning;
    }

    public void setAlignRunning(boolean alignRunning) {
        this.alignRunning = alignRunning;
    }
}
