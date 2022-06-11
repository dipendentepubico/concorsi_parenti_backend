package it.dipendentepubico.concorsiparenti.jpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler" })
@Entity
@Table(name="GRADUATORIA_ANAGRAFICA")
public class GraduatoriaAnagraficaEntity implements Serializable, EntityInterface {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="GRADANAG_ID_GENERATOR", sequenceName="GRADANAG_SEQ", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="GRADANAG_ID_GENERATOR")
    private Integer id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "GRADUATORIA_ID", nullable = false)
    private GraduatoriaFinaleEntity graduatoria;


    @OneToOne
    @JoinColumn(name = "ANAGRAFICA_ID", nullable = false)
    private AnagraficaEntity anagrafica;

    @Column(name="POSIZIONE")
    private Integer posizione;

    @Column(name="VINCITORE")
    private Boolean vincitore;

    public GraduatoriaAnagraficaEntity(){
        // Costruttore vuoto per JPA
    }

    public GraduatoriaFinaleEntity getGraduatoria() {
        return graduatoria;
    }

    public AnagraficaEntity getAnagrafica() {
        return anagrafica;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setGraduatoria(GraduatoriaFinaleEntity graduatoria) {
        this.graduatoria = graduatoria;
    }

    public void setAnagrafica(AnagraficaEntity anagrafica) {
        this.anagrafica = anagrafica;
    }

    public Integer getPosizione() {
        return posizione;
    }

    public void setPosizione(Integer posizione) {
        this.posizione = posizione;
    }

    public Boolean getVincitore() {
        return vincitore;
    }

    public void setVincitore(Boolean vincitore) {
        this.vincitore = vincitore;
    }
}
