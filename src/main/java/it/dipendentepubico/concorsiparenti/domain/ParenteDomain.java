package it.dipendentepubico.concorsiparenti.domain;

import it.dipendentepubico.concorsiparenti.jpa.entity.AnagraficaEntity;
import it.dipendentepubico.concorsiparenti.jpa.entity.GradoParentelaEntity;


public class ParenteDomain {
    private AnagraficaDomain anagrafica;
    private GradoParentelaDomain gradoParentela;

    public ParenteDomain() {
    }

    public ParenteDomain(AnagraficaDomain anagrafica, GradoParentelaDomain gradoParentela) {
        this.anagrafica = anagrafica;
        this.gradoParentela = gradoParentela;
    }

    /**
     * DTO per HQL
     * @param anagraficaEntity
     * @param gradoParentela
     */
    public ParenteDomain(AnagraficaEntity anagraficaEntity, GradoParentelaEntity gradoParentela){
        this.anagrafica = new AnagraficaDomain(anagraficaEntity.getId(), anagraficaEntity.getNome(), anagraficaEntity.getCognome(), anagraficaEntity.getCodiceFiscale());
        this.gradoParentela = new GradoParentelaDomain(gradoParentela.getDescrizione());
    }

    public AnagraficaDomain getAnagrafica() {
        return anagrafica;
    }

    public void setAnagrafica(AnagraficaDomain anagrafica) {
        this.anagrafica = anagrafica;
    }

    public GradoParentelaDomain getGradoParentela() {
        return gradoParentela;
    }

    public void setGradoParentela(GradoParentelaDomain gradoParentela) {
        this.gradoParentela = gradoParentela;
    }
}
