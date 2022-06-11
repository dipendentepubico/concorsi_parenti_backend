package it.dipendentepubico.concorsiparenti.rest.model;

import java.time.LocalDate;

public class Dipendente {
    private Integer id;
    private Ente ente;
    private Anagrafica anagrafica;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private String link;
    private Categoria categoria;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Ente getEnte() {
        return ente;
    }

    public void setEnte(Ente ente) {
        this.ente = ente;
    }

    public Anagrafica getAnagrafica() {
        return anagrafica;
    }

    public void setAnagrafica(Anagrafica anagrafica) {
        this.anagrafica = anagrafica;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
