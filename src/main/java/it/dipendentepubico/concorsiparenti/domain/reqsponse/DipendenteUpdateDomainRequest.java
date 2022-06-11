package it.dipendentepubico.concorsiparenti.domain.reqsponse;

import java.util.Date;

public class DipendenteUpdateDomainRequest {
    private Integer id;
    private Integer idEnte;
    private Date dataInizio;
    private Date dataFine;
    private Integer idCategoria;
    private String link;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(Integer idEnte) {
        this.idEnte = idEnte;
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

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
