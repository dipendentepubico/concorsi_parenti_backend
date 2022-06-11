package it.dipendentepubico.concorsiparenti.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler" })
@Entity
@Table(name="GRADUATORIA_FINALE")
public class GraduatoriaFinaleEntity implements Serializable, EntityInterface {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="GRADUATORIA_ID_GENERATOR", sequenceName="GRADUATORIA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="GRADUATORIA_ID_GENERATOR")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "CONCORSO_ID", nullable = false, unique = true)
    private ConcorsoEntity concorso;

    @Column(name="DATA")
    private Date data;

    @Column(name="LINK")
    private String link;

    @JsonManagedReference
    @OneToMany(mappedBy = "graduatoria")
    private List<GraduatoriaAnagraficaEntity> graduatoriaAnagraficaEntityList;


    public GraduatoriaFinaleEntity(){
        // Costruttore vuoto per JPA
    }

    public ConcorsoEntity getConcorso() {
        return concorso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setConcorso(ConcorsoEntity concorso) {
        this.concorso = concorso;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<GraduatoriaAnagraficaEntity> getGraduatoriaAnagraficaEntityList() {
        return graduatoriaAnagraficaEntityList;
    }

    public void setGraduatoriaAnagraficaEntityList(List<GraduatoriaAnagraficaEntity> graduatoriaAnagraficaEntityList) {
        this.graduatoriaAnagraficaEntityList = graduatoriaAnagraficaEntityList;
    }
}
