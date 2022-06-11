package it.dipendentepubico.concorsiparenti.jpa.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="SETTINGS")
public class SettingsEntity implements Serializable, EntityInterface {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="CODICE")
    private String codice;

    @Column(name="VALORE")
    private String valore;

    @Column(name="DESCRIZIONE")
    private String descrizione;

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
