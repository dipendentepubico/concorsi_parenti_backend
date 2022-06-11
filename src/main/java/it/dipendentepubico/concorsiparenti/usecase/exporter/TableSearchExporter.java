package it.dipendentepubico.concorsiparenti.usecase.exporter;

import it.dipendentepubico.concorsiparenti.jpa.entity.EntityInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class TableSearchExporter<E extends EntityInterface> implements IFacadeExporter<E> {

    private static final Logger logger = LoggerFactory.getLogger(TableSearchExporter.class);

    private GenerateHeader generateHeader;
    private GenerateRow<E> generateRow;

    public TableSearchExporter(GenerateHeader generateHeader, GenerateRow<E> generateRow) {
        this.generateHeader = generateHeader;
        this.generateRow = generateRow;
    }

    @Override
    public void exportCSV(List<E> entityList, OutputStream outputStream) {
        CSVExporter<E> exporter = new CSVExporter<E>(entityList,
                this.generateHeader,
                this.generateRow);
        try {
            exporter.export(outputStream);
        } catch (IOException e) {
            logger.error("Errore esportando in csv", e);
        }
    }

    @Override
    public void exportXLSX(List<E> entityList, OutputStream outputStream) {
        ExcelExporter<E> exporter = new ExcelExporter<E>(entityList, "Table Export",
                this.generateHeader,
                this.generateRow);
        try {
            exporter.export(outputStream);
        } catch (IOException e) {
            logger.error("Errore esportando in xlsx", e);
        }
    }



}
