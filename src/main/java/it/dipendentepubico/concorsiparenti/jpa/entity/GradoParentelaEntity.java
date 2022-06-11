package it.dipendentepubico.concorsiparenti.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler" })
@Entity
@Table(name="GRADO_PARENTELA")
public class GradoParentelaEntity implements Serializable, EntityInterface {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="GRADO_PARENTELA_ID_GENERATOR", sequenceName="GRADO_PARENTELA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="GRADO_PARENTELA_ID_GENERATOR")
    private Integer id;

    @Column(name="DESCRIZIONE", nullable = false, unique = true)
    private String descrizione;

    public GradoParentelaEntity() {
        // Costruttore vuoto per JPA
    }

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
