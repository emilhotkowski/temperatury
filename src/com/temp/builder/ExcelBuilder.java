package com.temp.builder;

import com.temp.mapper.TemperatureMapper;
import com.temp.model.TempExcel;
import com.temp.model.Temperature;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

public class ExcelBuilder {

    private List<TempExcel> temps;
    private List<String> excelRows;

    public ExcelBuilder(ObservableList<Temperature> list) {
        temps = list.stream().map(TemperatureMapper::temperatureToTempExcel).collect(Collectors.toList());
    }

    public void buildExcel() throws Exception {
        createWholeTextForExcel();
        createWholeExcel();
    }

    /**
     * Build text
     */
    private void createWholeTextForExcel() {
        temps.forEach(this::createTextFromTemp);
    }

    private void createTextFromTemp(TempExcel tempExcel) {
        System.out.print("ELO");
    }

    /**
     * Build excel
     */
    private void createWholeExcel() {
    }

}
