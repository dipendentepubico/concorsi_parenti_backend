package it.dipendentepubico.concorsiparenti.domain;

import java.util.Date;

public class DipendenteDomain implements DomainInterface {
    private Integer id;
    private EnteDomain ente;
    private AnagraficaDomain anagrafica;
    private Date dataInizio;
    private Date dataFine;
    private String link;
    private CategoriaDomain categoria;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EnteDomain getEnte() {
        return ente;
    }

    public void setEnte(EnteDomain ente) {
        this.ente = ente;
    }

    public AnagraficaDomain getAnagrafica() {
        return anagrafica;
    }

    public void setAnagrafica(AnagraficaDomain anagrafica) {
        this.anagrafica = anagrafica;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public CategoriaDomain getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDomain categoria) {
        this.categoria = categoria;
    }
}
