package it.dipendentepubico.concorsiparenti.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler" })
@Entity
@Table(name="ANAGRAFICA")
public class AnagraficaEntity implements Serializable, EntityInterface {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="ANAGRAFICA_ID_GENERATOR", sequenceName="ANAGRAFICA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="ANAGRAFICA_ID_GENERATOR")
    private Integer id;

    @Column(name="CODICE_FISCALE", unique=true, nullable = false)
    private String codiceFiscale;

    @Column(name="NOME", nullable = false)
    private String nome;

    @Column(name="COGNOME", nullable = false)
    private String cognome;

    public AnagraficaEntity(){
        // Costruttore vuoto per JPA
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
}
