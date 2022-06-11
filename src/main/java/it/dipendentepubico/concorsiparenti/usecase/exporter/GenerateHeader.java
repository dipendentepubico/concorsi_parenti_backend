package it.dipendentepubico.concorsiparenti.usecase.exporter;

import it.dipendentepubico.concorsiparenti.jpa.entity.EntityInterface;

import java.util.List;

@FunctionalInterface
public interface GenerateHeader {
    /**
     * Per lo specifico oggetto ritorna la lista dei valori stringhe per popolare l'header.
     * L'ordine deve corrispondere a quello di {@link GenerateRow#getArrayOfCells(EntityInterface)}.
     * @return      Lista di valori da inserire nelle celle dell'header
     */
    List<String> getArrayOfHeaderCells();
}
