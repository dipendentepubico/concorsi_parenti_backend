package it.dipendentepubico.concorsiparenti.usecase.exporter;

import it.dipendentepubico.concorsiparenti.domain.Constants;
import it.dipendentepubico.concorsiparenti.jpa.entity.EntityInterface;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Interfaccia implementata per ogni {@link EntityInterface} per ogni {@link Constants.EXPORT_TYPE}
 */
public interface IExporter {
    void export(OutputStream outputStream) throws IOException;
}
