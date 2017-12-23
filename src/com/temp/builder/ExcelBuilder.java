package com.temp.builder;

import com.temp.model.Temperature;
import com.temp.model.Truck;
import javafx.collections.ObservableList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExcelBuilder {

    private static int IMG_WIDTH, IMG_HEIGHT;

    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet spreadsheet = workbook.createSheet("Strona 1");
    XSSFFont font = workbook.createFont();
    XSSFFont font2 = workbook.createFont();
    XSSFCellStyle calibri11 = workbook.createCellStyle();
    XSSFCellStyle calibri12 = workbook.createCellStyle();
    List<Double> colSizes = Arrays.asList(2.15, 1.29, 0.17, 1.04, 0.17, 1.01, 0.20, 1.17, 1.79, 2.04, 2.15, 2.15, 1.79);
    List<Double> rowHight = Arrays.asList(0.53, 0.56, 0.26, 0.56, 0.29, 0.56, 0.56, 0.53, 0.53, 0.53, 0.53);
    private List<String> excelRows;
    private List<Truck> naczepy;
    private Truck accTruck;
    int pictureureIdx;

    public void buildExcel(ObservableList<Temperature> list, String name, String registery) throws Exception {
        getCSVData(registery);
        getText(list);
        configureSpreadsheet();
        createWholeExcel();
        saveExcel(name);
    }

    private void getCSVData(String registery) {
        naczepy = new ArrayList<>();
        Reader in = null;
        try {
            in = new FileReader("naczepy.csv");
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().withDelimiter(';').parse(in);
            for (CSVRecord record : records) {
                Truck truck = new Truck();
                truck.setRejestracja(record.get("Rejestracja"));
                truck.setTkUnitId(record.get("TkUnitId"));
                truck.setRevision(record.get("Revision"));
                truck.setAgregat(record.get("Agregat"));

                naczepy.add(truck);

                if (registery.equals(truck.getRejestracja())) {
                    accTruck = truck;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (accTruck != null) {
            try {
                if(accTruck.getAgregat().equals("thermo king")) {
                    IMG_HEIGHT = 90;
                    IMG_WIDTH = 227;
                } else {
                    //rowHight = Arrays.asList(0.56, 0.60, 0.29, 0.60, 0.32, 0.60, 0.60, 0.56, 0.56, 0.56, 0.56);
                    IMG_HEIGHT = 91;
                    IMG_WIDTH = 243;
                }
                BufferedImage originalImage = ImageIO.read(new File("resources/" + accTruck.getAgregat() + ".png"));
                int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

                BufferedImage resized = resizeImage(originalImage, type);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write( resized, "png", baos );
                baos.flush();
                byte[] imageInByte = baos.toByteArray();
                baos.close();

                pictureureIdx = workbook.addPicture(imageInByte, Workbook.PICTURE_TYPE_PNG);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type){
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }

    private void configureSpreadsheet() {
        //col sizes
        int i = 0;
        for (double colSize : colSizes) {
            spreadsheet.setColumnWidth(i++, Helper.calcSize(colSize));
        }

        //font
        //Create a new font and alter it.
        font.setFontHeightInPoints((short) 11);
        font.setFontName("Calibri");
        calibri11.setFont(font);

        font2.setFontHeightInPoints((short) 12);
        font2.setFontName("Calibri");
        calibri12.setFont(font2);

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
        while (saved != excelRows.size()) {
            if (accLine % 50 < 10) {
                //tutaj robimy poczatki etc
                createAdditionalLine(accLine);
            } else {
                XSSFRow row = spreadsheet.createRow(accLine);
                XSSFCell cell = row.createCell(0);
                cell.setCellStyle(calibri11);
                cell.setCellValue(excelRows.get(saved++));
            }

            accLine++;
        }
    }

    private void createAdditionalLine(int accLine) {

        XSSFRow row = spreadsheet.createRow(accLine);
        row.setHeightInPoints(Helper.calcSizePoints(rowHight.get(accLine % 50)));

        if (accTruck != null) {
            addTruckLine(row, accLine % 50, accLine);
        }

    }

    private void addTruckLine(XSSFRow row, int i, int full) {
        if (i == 1) {
            XSSFCell cell = row.createCell(8);
            if(accTruck.getAgregat().equals("Carrier")) {
                cell.setCellStyle(calibri11);
            }
            cell.setCellValue("Device:DAS");

            XSSFCell cell2 = row.createCell(10);
            cell2.setCellStyle(calibri12);
            cell2.setCellValue("Logger ID:");

            XSSFCell cell3 = row.createCell(11);
            cell3.setCellStyle(calibri12);
            cell3.setCellValue(accTruck.getRejestracja());
        }

        if (i == 3) {
            XSSFCell cell = row.createCell(8);
            cell.setCellStyle(calibri12);
            cell.setCellValue("TK Unit ID:" + accTruck.getTkUnitId());
        }

        if (i == 5) {
            XSSFCell cell2 = row.createCell(8);
            cell2.setCellStyle(calibri12);
            cell2.setCellValue("Revision: " + accTruck.getRevision());

            XSSFCell cell3 = row.createCell(10);
            cell3.setCellStyle(calibri12);
            cell3.setCellValue("Temperature: C  Pressure: BAR");
        }

        //dodanie bordera
        if (i == 0) {

            XSSFCell cellImage = row.createCell(0);
            addImage(full);

            XSSFCellStyle borderStyle = workbook.createCellStyle();

            borderStyle.setBorderLeft(BorderStyle.THIN);
            borderStyle.setBorderTop(BorderStyle.THIN);


            row.getCell(0).setCellStyle(borderStyle);

            for (int j = 1; j < 12; j++) {
                XSSFCellStyle top = workbook.createCellStyle();
                top.setBorderTop(BorderStyle.THIN);

                XSSFCell cell = row.createCell(j);
                cell.setCellStyle(top);
            }

            XSSFCellStyle right = workbook.createCellStyle();
            right.setBorderRight(BorderStyle.THIN);
            right.setBorderTop(BorderStyle.THIN);
            XSSFCell cell = row.createCell(12);
            cell.setCellStyle(right);

        } else if (i < 6) {
            XSSFCellStyle borderStyle = workbook.createCellStyle();

            borderStyle.setFont(font2);
            borderStyle.setBorderLeft(BorderStyle.THIN);
            XSSFCell cell = row.createCell(0);
            cell.setCellStyle(borderStyle);
            if (i == 5)
                cell.setCellValue("               Sensor  Averaging");

            XSSFCellStyle right = workbook.createCellStyle();
            right.setBorderRight(BorderStyle.THIN);
            cell = row.createCell(12);
            cell.setCellStyle(right);
        } else if (i == 6) {
            XSSFCellStyle borderStyle = workbook.createCellStyle();

            borderStyle.setBorderLeft(BorderStyle.THIN);
            borderStyle.setBorderBottom(BorderStyle.THIN);
            XSSFCell cell = row.createCell(0);
            cell.setCellStyle(borderStyle);

            for (int j = 1; j < 12; j++) {
                XSSFCellStyle top = workbook.createCellStyle();
                top.setBorderBottom(BorderStyle.THIN);

                cell = row.createCell(j);
                cell.setCellStyle(top);
            }

            XSSFCellStyle right = workbook.createCellStyle();
            right.setBorderRight(BorderStyle.THIN);
            right.setBorderBottom(BorderStyle.THIN);
            cell = row.createCell(12);
            cell.setCellStyle(right);
        }
    }

    private void addImage(int full) {
        //Returns an object that handles instantiating concrete classes
        XSSFCreationHelper helper = workbook.getCreationHelper();
        //Creates the top-level drawing patriarch.
        Drawing drawing = spreadsheet.createDrawingPatriarch();

        //Create an anchor that is attached to the worksheet
        ClientAnchor anchor = helper.createClientAnchor();

        //create an anchor with upper left cell _and_ bottom right cell
        //anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
        anchor.setCol1(0); //Column B
        anchor.setRow1(full); //Row 3

        Picture picture = drawing.createPicture(anchor, pictureureIdx);
        picture.resize();
    }

    private void saveExcel(String name) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File( "temperatury/" + name + ".xlsx"));
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
