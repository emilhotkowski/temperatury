package com.temp.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class TempExcel {

    private LocalDate dateFrom;
    private LocalTime timeFrom;

    private LocalDate dateTo;
    private LocalTime timeTo;

    private float temp;
    private float temp2;
    private float diff;

    private String regestiry;
    private String fileNumber;

    private boolean hasSecond;

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }


    public LocalTime getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(LocalTime timeFrom) {
        this.timeFrom = timeFrom;
    }

    public LocalTime getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(LocalTime timeTo) {
        this.timeTo = timeTo;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }


    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getTemp2() {
        return temp2;
    }

    public void setTemp2(float temp2) {
        this.temp2 = temp2;
    }

    public float getDiff() {
        return diff;
    }

    public void setDiff(float diff) {
        this.diff = diff;
    }

    public String getRegestiry() {
        return regestiry;
    }

    public void setRegestiry(String regestiry) {
        this.regestiry = regestiry;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public boolean isHasSecond() {
        return hasSecond;
    }

    public void setHasSecond(boolean hasSecond) {
        this.hasSecond = hasSecond;
    }

    @Override
    public String toString() {
        return "TempExcel{" +
                "dateFrom=" + dateFrom +
                ", timeFrom=" + timeFrom +
                ", dateTo=" + dateTo +
                ", timeTo=" + timeTo +
                ", temp=" + temp +
                ", temp2=" + temp2 +
                ", diff=" + diff +
                ", regestiry='" + regestiry + '\'' +
                ", hasSecond=" + hasSecond +
                '}';
    }
}
