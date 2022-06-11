package it.dipendentepubico.concorsiparenti.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler" })
@Entity
@Table(name="PARENTELA")
public class ParentelaEntity implements Serializable, EntityInterface {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="PARENTELA_ID_GENERATOR", sequenceName="PARENTELA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="PARENTELA_ID_GENERATOR")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "anagrafica_from_id")
    private AnagraficaEntity anagraficaFrom;

    @OneToOne
    @JoinColumn(name = "anagrafica_to_id")
    private AnagraficaEntity anagraficaTo;


    @OneToOne
    @JoinColumn(name = "parentela_id")
    private GradoParentelaEntity parentela;


    public ParentelaEntity(){
        // Costruttore vuoto per JPA
    }

    public AnagraficaEntity getAnagraficaFrom() {
        return anagraficaFrom;
    }

    public AnagraficaEntity getAnagraficaTo() {
        return anagraficaTo;
    }

    public GradoParentelaEntity getParentela() {
        return parentela;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAnagraficaFrom(AnagraficaEntity anagraficaFrom) {
        this.anagraficaFrom = anagraficaFrom;
    }

    public void setAnagraficaTo(AnagraficaEntity anagraficaTo) {
        this.anagraficaTo = anagraficaTo;
    }

    public void setParentela(GradoParentelaEntity parentela) {
        this.parentela = parentela;
    }
}
