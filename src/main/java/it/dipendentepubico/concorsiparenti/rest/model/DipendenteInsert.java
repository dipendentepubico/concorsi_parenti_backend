package it.dipendentepubico.concorsiparenti.rest.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class DipendenteInsert {
    @NotNull
    private Date dataInizio;
    private Date dataFine;
    @NotNull
    private Integer idCategoria;
    @NotNull
    private Integer idAnagrafica;
    @NotEmpty
    private String link;

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
