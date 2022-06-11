package it.dipendentepubico.concorsiparenti.domain;

import java.util.List;


public class AnagraficaConDipendentiParentiDomain {
    private AnagraficaDomain anagrafica;
    private List<ParenteDomain> parenteList;

    public AnagraficaConDipendentiParentiDomain(AnagraficaDomain anagrafica, List<ParenteDomain> parenteDomainList) {
        this.anagrafica = anagrafica;
        this.parenteList = parenteDomainList;
    }

    public AnagraficaDomain getAnagrafica() {
        return anagrafica;
    }

    public void setAnagrafica(AnagraficaDomain anagrafica) {
        this.anagrafica = anagrafica;
    }

    public List<ParenteDomain> getParenteList() {
        return parenteList;
    }

    public void setParenteList(List<ParenteDomain> parenteDomainList) {
        this.parenteList = parenteDomainList;
    }
}
