package it.dipendentepubico.concorsiparenti.domain;

public class GradoParentelaDomain {

    private String descrizione;

    public GradoParentelaDomain() {
    }

    public GradoParentelaDomain(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
