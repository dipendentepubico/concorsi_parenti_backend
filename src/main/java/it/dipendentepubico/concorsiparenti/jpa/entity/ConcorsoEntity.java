package it.dipendentepubico.concorsiparenti.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler" })
@Entity
@Table(name="CONCORSO")
public class ConcorsoEntity implements Serializable, EntityInterface {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="CONCORSO_ID_GENERATOR", sequenceName="CONCORSO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="CONCORSO_ID_GENERATOR")
    private Integer id;

    @Column(name="GAZZETTA_UFFICIALE_ANNO", nullable = false)
    private Integer guAnno;

    @Column(name="GAZZETTA_UFFICIALE_NUMERO", nullable = false)
    private Integer guNumero;

    @OneToOne
    @JoinColumn(name = "ENTE_ID", nullable = false)
    private EnteEntity ente;

    @OneToOne
    @JoinColumn(name = "CATEGORIA_ID", nullable = false)
    private CategoriaEntity categoria;

    public ConcorsoEntity(){
        // Costruttore vuoto per JPA
    }

    public CategoriaEntity getCategoria() {
        return categoria;
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

    public Integer getGuAnno() {
        return guAnno;
    }

    public void setGuAnno(Integer guAnno) {
        this.guAnno = guAnno;
    }

    public Integer getGuNumero() {
        return guNumero;
    }

    public void setGuNumero(Integer guNumero) {
        this.guNumero = guNumero;
    }

    public void setEnte(EnteEntity ente) {
        this.ente = ente;
    }

    public void setCategoria(CategoriaEntity categoria) {
        this.categoria = categoria;
    }
}
