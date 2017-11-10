package com.temp.mapper;

import com.temp.model.TempExcel;
import com.temp.model.Temperature;

import java.time.LocalTime;

public class TemperatureMapper {

    public static TempExcel temperatureToTempExcel(Temperature temperature) {

        TempExcel tempExcel = new TempExcel();

        tempExcel.setDateFrom(temperature.getDateFrom());
        if(temperature.getTimeFrom().length() == 4) {
            tempExcel.setTimeFrom(getTime(temperature.getTimeFrom()));
        }

        tempExcel.setDateTo(temperature.getDateTo());
        tempExcel.setTimeTo(getTime(temperature.getTimeTo()));

        tempExcel.setTemp(temperature.getTemp());
        tempExcel.setTemp2(temperature.getTemp2());
        tempExcel.setDiff(temperature.getDiff());

        tempExcel.setRegestiry(temperature.getRegistery());
        tempExcel.setType(temperature.getType());

        tempExcel.setHasSecond(temperature.isHasSecond());

        return tempExcel;
    }

    private static LocalTime getTime(String time) {
        return LocalTime.of(getHour(time), getMinute(time));
    }

    private static int getMinute(String timeFrom) {
        if(timeFrom.charAt(2) != 0) {
            return Integer.valueOf(timeFrom.substring(2));
        }
        return Integer.valueOf(timeFrom.substring(3));
    }

    private static int getHour(String timeFrom) {
        if(timeFrom.charAt(0) != 0) {
            return Integer.valueOf(timeFrom.substring(0, 2));
        }
        return Integer.valueOf(timeFrom.substring(1, 2));
    }

}
