package it.dipendentepubico.concorsiparenti.usecase.exporter;

import it.dipendentepubico.concorsiparenti.jpa.entity.EntityInterface;

import java.util.List;

@FunctionalInterface
public interface GenerateRow<E extends EntityInterface> {
    /**
     * Dato l'oggetto in input ritorna la lista dei valori Object da inserire nelle celle della riga corrente.
     * L'ordine deve corrispondere a quello di {@link GenerateHeader#getArrayOfHeaderCells()}.
     * @param entity        Tipo della classe figlio
     * @return              Lista di valori di diversi tipi utilizzati per popolare le celle della riga di questa entity
     */
    List getArrayOfCells(E entity);
}
