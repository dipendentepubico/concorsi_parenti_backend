package it.dipendentepubico.concorsiparenti.domain;

import it.dipendentepubico.concorsiparenti.rest.model.Anagrafica;

import java.util.List;

public class DipendenteConParentiDomain {
    private AnagraficaDomain anagrafica;
    private EnteDomain ente;
    private List<ParenteDomain> parenti;

    public DipendenteConParentiDomain(AnagraficaDomain anagrafica, List<ParenteDomain> parenti) {
        this.anagrafica = anagrafica;
        this.parenti = parenti;
    }

    public AnagraficaDomain getAnagrafica() {
        return anagrafica;
    }

    public void setAnagrafica(AnagraficaDomain anagrafica) {
        this.anagrafica = anagrafica;
    }

    public EnteDomain getEnte() {
        return ente;
    }

    public void setEnte(EnteDomain ente) {
        this.ente = ente;
    }

    public List<ParenteDomain> getParenti() {
        return parenti;
    }

    public void setParenti(List<ParenteDomain> parenti) {
        this.parenti = parenti;
    }
}
