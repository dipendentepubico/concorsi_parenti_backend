package it.dipendentepubico.concorsiparenti.jpa.entity;

public enum EStatoOpenData {
    NON_ALLINEATO("N"),
    IMPORTAZIONE_IN_CORSO("W"),
    ALLINEATO("A");

    private String code;

    EStatoOpenData(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
