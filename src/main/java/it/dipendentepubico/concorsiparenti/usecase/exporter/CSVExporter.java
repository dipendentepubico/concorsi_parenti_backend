package it.dipendentepubico.concorsiparenti.usecase.exporter;

import it.dipendentepubico.concorsiparenti.jpa.entity.EntityInterface;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Classe base per esportazione di lista di entity in file csv.
 * @param <E>   Il tipo dell'entity ottenuta da db che tipizza la lista di oggetti da esportare in csv
 */
public class CSVExporter<E extends EntityInterface> implements IExporter {
    private List<E> entityList;
    protected CSVPrinter csvPrinter;
    private GenerateHeader generateHeader;
    private GenerateRow<E> generateRow;

    public CSVExporter(List<E> entityList, GenerateHeader generateHeader, GenerateRow<E> generateRow) {
        this.entityList = entityList;
        this.generateHeader = generateHeader;
        this.generateRow = generateRow;
    }

    private void writeHeaderLine() throws IOException {
        csvPrinter.printRecord(generateHeader.getArrayOfHeaderCells());
    }

    private void writeDataLines() throws IOException {
        for (E entity : entityList) {
                csvPrinter.printRecord(generateRow.getArrayOfCells(entity));
        }

    }

    public void export(OutputStream outputStream) throws IOException{
        csvPrinter = new CSVPrinter(new OutputStreamWriter(outputStream), CSVFormat.DEFAULT);
        writeHeaderLine();
        writeDataLines();
        csvPrinter.close();
        outputStream.close();
    }

}
