package it.dipendentepubico.concorsiparenti.rest.model;

public class Ente extends CaptchaForm {
    private Integer id;
    private String descrizione;
    private String codiceFiscale;
    private String codiceIPA;
    /**
     * Considero l'ente allineato con l'IPA solo se it.dipendentepubico.concorsiparenti.jpa.entity.EStatoOpenData.ALLINEATO
     * <br/>
     * In tutti gli altri casi è false
     */
    private boolean allineato;

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

    public String getCodiceIPA() {
        return codiceIPA;
    }

    public void setCodiceIPA(String codiceIPA) {
        this.codiceIPA = codiceIPA;
    }

    public boolean isAllineato() {
        return allineato;
    }

    public void setAllineato(boolean allineato) {
        this.allineato = allineato;
    }
}
