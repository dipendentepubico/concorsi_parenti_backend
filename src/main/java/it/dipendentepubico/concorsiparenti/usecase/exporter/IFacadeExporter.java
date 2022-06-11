package it.dipendentepubico.concorsiparenti.usecase.exporter;

import it.dipendentepubico.concorsiparenti.jpa.entity.EntityInterface;

import java.io.OutputStream;
import java.util.List;


/**
 * Interfaccia implementata per ogni facade di {@link EntityInterface}.
 * Ogni metodo richiama l'{@link IExporter} dello specifico formato per questa {@link EntityInterface}.
 * @param <E>
 */
public interface IFacadeExporter<E extends EntityInterface> {
    void exportCSV(List<E> entityList, OutputStream outputStream);
    void exportXLSX(List<E> entityList, OutputStream outputStream);
}
