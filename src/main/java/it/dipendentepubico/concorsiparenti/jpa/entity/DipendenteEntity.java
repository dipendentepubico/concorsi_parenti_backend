package it.dipendentepubico.concorsiparenti.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler" })
@Entity
@Table(name="DIPENDENTE")
public class DipendenteEntity implements Serializable, EntityInterface {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="DIPENDENTE_ID_GENERATOR", sequenceName="DIPENDENTE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="DIPENDENTE_ID_GENERATOR")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "ENTE_ID", nullable = false)
    private EnteEntity ente;

    @OneToOne
    @JoinColumn(name = "ANAGRAFICA_ID", nullable = false)
    private AnagraficaEntity anagrafica;

    @Column(name="DATA_INIZIO", nullable = false)
    private Date dataInizio;

    @Column(name="DATA_FINE")
    private Date dataFine;

    @Column(name="LINK", nullable = false)
    private String link;

    @OneToOne
    @JoinColumn(name = "CATEGORIA_ID")
    private CategoriaEntity categoria;

    public DipendenteEntity(){
       // Costruttore vuoto per JPA
    }

    public AnagraficaEntity getAnagrafica() {
        return anagrafica;
    }


    public EnteEntity getEnte() {
        return ente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEnte(EnteEntity ente) {
        this.ente = ente;
    }

    public void setAnagrafica(AnagraficaEntity anagrafica) {
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

    public CategoriaEntity getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEntity categoria) {
        this.categoria = categoria;
    }


}
