package com.temp.builder;

import com.temp.mapper.TemperatureMapper;
import com.temp.model.TempExcel;
import com.temp.model.Temperature;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TextBuilder {

    public static final String DAS_COUNTDOWN_ON_CONTROLLER_OFF = "DAS Countdown ON/Controller Off";
    private List<TempExcel> temps;
    private List<String> excelRows = new ArrayList<>();
    private LocalDateTime accTime;
    private float accTemp;

    private static final String SHUTDOWN_DISEL = "\"Shutdown, Disel, Cycle Sentry\"";
    private static final String NORMAL_LINE = "\"Modulation, Disel, Continuous\"";

    public TextBuilder(ObservableList<Temperature> list) {
        temps = list.stream().map(TemperatureMapper::temperatureToTempExcel).collect(Collectors.toList());

    }

    public List<String> buildText() throws Exception {
        createWholeTextForExcel();
        return excelRows;
    }

    /**
     * Build text
     */
    private void createTypicalRow(TempExcel tempExcel, int minutes,  boolean beginning, String text) {
        //actualize temp
        StringBuilder sb = new StringBuilder(getTempTime(minutes, tempExcel));
        sb.append(Helper.randTemp(accTemp, tempExcel.getDiff()));
        sb.append(" , ");

        //drugi czujnik
        if(tempExcel.isHasSecond()) {
            sb.append(Helper.randTemp(tempExcel.getTemp2(), tempExcel.getDiff()));
        } else {
            sb.append(Helper.randTemp(accTemp, tempExcel.getDiff()));
        }
        sb.append(" , ");

        //jesli poczatek to olac temp
        if(beginning) {
            sb.append("----");
        } else {
            sb.append(accTemp);
        }

        sb.append(", ");
        sb.append(text);
        sb.append(",");

        excelRows.add(sb.toString());
    }

    private void actualizeTemp(TempExcel tempExcel) {
        double diff = accTemp - tempExcel.getTemp();
        if(diff > 0) {
            accTemp = Math.max(tempExcel.getTemp(), accTemp-1);
        } else if(diff != 0) {
            accTemp = Math.min(tempExcel.getTemp(), accTemp+1);
        }
    }

    private String getTempTime(int plusMinutes, TempExcel tempExcel) {
        //jesli zmienił się dzień, dodaj na chama zmiane dnia!!!
        LocalDateTime newDate = accTime.plusMinutes(plusMinutes);
        if(newDate.getDayOfYear() != accTime.getDayOfYear()) {
            if(!tempExcel.isHasSecond()) {
                excelRows.add(accTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "  00:00  , SETPT:       " + tempExcel.getTemp()); //wiersz z zarami i SETPT
                excelRows.add(accTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "  00:00  , ,," + tempExcel.getTemp() + ",,"); //wiersz z zarami i SETPT
            } else {
                excelRows.add(accTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "  00:00  , SETPT:       " + tempExcel.getTemp() + " , " + tempExcel.getTemp2()); //wiersz z zarami i SETPT
                excelRows.add(accTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "  00:00  , ,," + tempExcel.getTemp() + ",," + tempExcel.getTemp2() + ",,"); //wiersz z zarami i SETPT
            }
        }

        //dodaj czas
        accTime = newDate;

        StringBuilder sb = new StringBuilder(accTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        sb.append("  ");
        sb.append(accTime.toLocalTime().toString());
        sb.append("  , ");

        return sb.toString();
    }

    private void createWholeTextForExcel() {
        createBeginning(temps.get(0));
        int i = 0;
        for (TempExcel temp : temps) {
            if(i != 0) {
                //inna temp, dodaj wpis o temp!
                accTime = LocalDateTime.of(temps.get(i-1).getDateTo(), temps.get(i-1).getTimeTo());
                //jesli jest kilka temp, to setpt jest tylko 1!!!
                excelRows.add(getTempTime(0, temp) + "SETPT:       " + temp.getTemp());
            }
            createTextFromTemp(temp);
            i++;
        }
        createEnding(temps.get(temps.size()-1));
    }

    private void createBeginning(TempExcel tempExcel) {
        accTemp = tempExcel.getTemp();

        //zacznijmy czas
        accTime = LocalDateTime.of(tempExcel.getDateFrom(), tempExcel.getTimeFrom());

        //Tworzymy poczatek z pierwszej temp
        //PowerUp
        excelRows.add(getTempTime(0, tempExcel) + "Power up");

        //setpt
        if(!tempExcel.isHasSecond())
            excelRows.add(getTempTime(1, tempExcel) + "SETPT:       " + tempExcel.getTemp());
        else
            excelRows.add(getTempTime(1, tempExcel) + "SETPT:       " + tempExcel.getTemp()+ " , " + tempExcel.getTemp2());

        //shutdown
        createTypicalRow(tempExcel, 30, true, SHUTDOWN_DISEL);

        for(int i = 0; i < 4; i++) {
            createTypicalRow(tempExcel, 30, true, NORMAL_LINE);
        }
    }

    private void createTextFromTemp(TempExcel tempExcel) {
        LocalDateTime until = LocalDateTime.of(tempExcel.getDateTo(), tempExcel.getTimeTo());

        int defrostCount = Helper.getRand(4, 8);

        //dopoki nie nadszedl koniec
        while(until.isAfter(accTime)) {
            actualizeTemp(tempExcel);
            if(defrostCount == 0) {
                defrostMe(tempExcel);
                defrostCount = Helper.getRand(4, 8);

                continue;
            }

            createTypicalRow(tempExcel, 30, false, NORMAL_LINE);
            defrostCount--;
        }

    }

    private void defrostMe(TempExcel tempExcel) {
        //Defrost line
        StringBuilder sb = new StringBuilder(getTempTime(Helper.getRand(5,25), tempExcel));
        sb.append("Timed Defrost Entered");
        excelRows.add(sb.toString());

        //normal line
        createTypicalRow(tempExcel, Helper.getRand(3,6), false, NORMAL_LINE);

        //After Defrost line
        sb = new StringBuilder(getTempTime(0, tempExcel));
        sb.append("Defrost State Exited");
        excelRows.add(sb.toString());

    }

    private void createEnding(TempExcel tempExcel) {
        createTypicalRow(tempExcel, 30, false, DAS_COUNTDOWN_ON_CONTROLLER_OFF);

        //last two lines
        StringBuilder sb = new StringBuilder(getTempTime(30, tempExcel));
        sb.append(",----    ,----  ,,DAS Countdown ON/Controller Off,-----");
        excelRows.add(sb.toString());

        sb = new StringBuilder(getTempTime(30, tempExcel));
        sb.append(",----    ,----  ,,DAS Conservative ON/Controller Off,-----");
        excelRows.add(sb.toString());

        excelRows.add("                                      , End of data");

        excelRows.stream().forEach(System.out::println);
    }


}
