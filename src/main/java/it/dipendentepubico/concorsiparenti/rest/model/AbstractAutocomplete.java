package it.dipendentepubico.concorsiparenti.rest.model;

public abstract class AbstractAutocomplete {
    private Integer id;
    private String descrizione;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
