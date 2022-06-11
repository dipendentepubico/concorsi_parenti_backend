package it.dipendentepubico.concorsiparenti.domain.reqsponse;

import java.util.Date;

public class DipendenteInsertDomainRequest {
    private Integer idEnte;
    private Date dataInizio;
    private Date dataFine;
    private Integer idCategoria;
    private Integer idAnagrafica;
    private String link;

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

    public Integer getIdAnagrafica() {
        return idAnagrafica;
    }

    public void setIdAnagrafica(Integer idAnagrafica) {
        this.idAnagrafica = idAnagrafica;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
