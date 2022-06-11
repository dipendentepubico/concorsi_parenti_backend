package it.dipendentepubico.concorsiparenti.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler" })
@Entity
@Table(name="ENTE")
public class EnteEntity implements Serializable, EntityInterface {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="ENTE_ID_GENERATOR", sequenceName="ENTE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="ENTE_ID_GENERATOR")
    private Integer id;

    @Column(name="DESCRIZIONE", nullable = false)
    @NotEmpty
    private String descrizione;

    @Column(name="CODICE_FISCALE", nullable = true)
    private String codiceFiscale;

    /**
     * Codice univoco nell'Indice Pubblica Amministrazione https://indicepa.gov.it/ipa-portale/
     * Chiave sul db IPA
     */
    @Column(name="CODICE_IPA", nullable = true, unique = true)
    private String codiceIPA;

    @Column(name="STATO_OPENDATA")
    private EStatoOpenData statoOpenData = EStatoOpenData.NON_ALLINEATO;

    public EnteEntity(){
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

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public EStatoOpenData getStatoOpenData() {
        return statoOpenData;
    }

    public void setStatoOpenData(EStatoOpenData statoOpenData) {
        this.statoOpenData = statoOpenData;
    }

    public String getCodiceIPA() {
        return codiceIPA;
    }

    public void setCodiceIPA(String codiceIPA) {
        this.codiceIPA = codiceIPA;
    }
}
