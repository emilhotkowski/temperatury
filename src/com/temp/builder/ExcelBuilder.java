package com.temp.builder;

import com.temp.mapper.TemperatureMapper;
import com.temp.model.TempExcel;
import com.temp.model.Temperature;
import javafx.collections.ObservableList;
import javafx.scene.control.Cell;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExcelBuilder {

    private List<String> excelRows;

    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet spreadsheet = workbook.createSheet("Strona 1");
    XSSFFont font = workbook.createFont();
    XSSFCellStyle style = workbook.createCellStyle();

    List<Double> colSizes = Arrays.asList(2.15, 1.29, 0.17, 1.04 , 0.17, 1.01, 0.20, 1.17, 1.79, 2.04, 2.15, 2.15, 1.79);

    List<Double> rowHight = Arrays.asList(0.53, 0.56, 0.26, 0.56, 0.29, 0.56, 0.56, 0.53, 0.53, 0.53, 0.53);

    public void buildExcel(ObservableList<Temperature> list, String name) throws Exception {
        getText(list);
        configureSpreadsheet();
        createWholeExcel();
        saveExcel(name);
    }

    private void configureSpreadsheet() {
        //TODO: marginesy ?

        //col sizes
        int i = 0;
        for(double colSize : colSizes) {
            spreadsheet.setColumnWidth(i++, Helper.calcSize(colSize));
        }

        //font
        //Create a new font and alter it.
        font.setFontHeightInPoints((short) 11);
        font.setFontName("Calibri");
        style.setFont(font);

    }


    private void getText(ObservableList<Temperature> list) throws Exception {
        TextBuilder textBuilder = new TextBuilder(list);
        excelRows = textBuilder.buildText();
    }

    /**
     * Build excel
     */
    private void createWholeExcel() {
        //zrob jakies konfiguracje spreadsheet'a etc

        int accLine = 0;
        int saved = 0;
        while(saved != excelRows.size()) {
            if(accLine % 50 < 10) {
                //tutaj robimy poczatki etc
                createAdditionalLine(accLine);
            } else {
                XSSFRow row = spreadsheet.createRow(accLine);
                XSSFCell cell = row.createCell(0);
                cell.setCellStyle(style);
                cell.setCellValue(excelRows.get(saved++));
            }

            accLine++;
        }
    }

    private void createAdditionalLine(int accLine) {

        XSSFRow row = spreadsheet.createRow(accLine);
        row.setHeightInPoints(Helper.calcSizePoints(rowHight.get(accLine%50)));

    }

    private void saveExcel(String name) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(name+ ".xlsx"));
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
