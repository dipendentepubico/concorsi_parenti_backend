package it.dipendentepubico.concorsiparenti.usecase.exporter;

import it.dipendentepubico.concorsiparenti.jpa.entity.EntityInterface;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Classe base per esportazione di lista di entity in file xlsx.
 * @param <E>   Il tipo dell'entity ottenuta da db che tipizza la lista di oggetti da esportare in Excel
 */
public class ExcelExporter<E extends EntityInterface> implements IExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<E> entityList;
    private String sheetName;
    private GenerateHeader generateHeader;
    private GenerateRow<E> generateRow;

    /**
     * Stile generico per le celle dati
     */
    private CellStyle styleData;
    /**
     * Stile per le celle dati contenenti date
     */
    private CellStyle styleDataDate;


    public ExcelExporter(List<E> entityList, String sheetName, GenerateHeader generateHeader, GenerateRow<E> generateRow) {
        this.entityList = entityList;
        this.workbook = new XSSFWorkbook();
        this.sheetName = sheetName;
        this.generateHeader = generateHeader;
        this.generateRow = generateRow;

        styleData = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        styleData.setFont(font);

        styleDataDate = workbook.createCellStyle();
        font.setFontHeight(14);
        styleDataDate.setFont(font);
        // formato data locale
        styleDataDate.setDataFormat((short)14);


    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet(this.sheetName);

        Row row = sheet.createRow(0);
        // header ha stile diverso
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        int columnCount = 0;
        for (String headerValue : generateHeader.getArrayOfHeaderCells() ){
            createCell(row, columnCount++, headerValue).setCellStyle(style);
        }

    }


    private void writeDataLines() {
        int rowCount = 1;

        for (E entity : entityList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            // ciclo sui valori dell'entity per questa riga e creo le relative celle
            for(Object cellValue : generateRow.getArrayOfCells(entity)){
                createCell(row, columnCount++, cellValue);
            }
        }
    }


    /**
     * Dato il valore, in base al tipo, valorizza la cella e il suo stile
     * @param row           la riga corrente
     * @param columnCount   l'indice della colonna
     * @param value         il valore della cella
     */
    protected Cell createCell(Row row, int columnCount, Object value) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        CellStyle style = styleData;
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else if (value instanceof String) {
            cell.setCellValue((String) value);
        }else if (value instanceof Timestamp){
            cell.setCellValue(new Date(((Timestamp) value).getTime()));
            style=styleDataDate;
        }else if (value instanceof Date){
            cell.setCellValue((Date) value);
            style=styleDataDate;
        }
        else if (value != null){
            // qui finiscono le date e tutti gli altri oggetti non null per i quali spero sia implementato il toString
            cell.setCellValue(value.toString());
        }
        cell.setCellStyle(style);
        return cell;
    }

    public void export(OutputStream outputStream) throws IOException {
        writeHeaderLine();
        writeDataLines();

        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }

}
