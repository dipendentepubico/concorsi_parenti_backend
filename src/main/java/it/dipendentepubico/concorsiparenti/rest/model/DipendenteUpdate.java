package it.dipendentepubico.concorsiparenti.rest.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class DipendenteUpdate {
    @NotNull
    private Integer id;
    @NotNull
    private Date dataInizio;
    private Date dataFine;
    @NotNull
    private Integer idCategoria;
    // questo non può essere modificato. serve solo per mostrare a video ma non effettuo update
    private String nominativo;
    @NotEmpty
    private String link;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getNominativo() {
        return nominativo;
    }

    public void setNominativo(String nominativo) {
        this.nominativo = nominativo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
