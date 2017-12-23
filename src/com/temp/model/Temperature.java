package com.temp.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Temperature {

    static Integer number = 0;
    private ObjectProperty<LocalDate> dateFrom;
    private StringProperty timeFrom;
    private ObjectProperty<LocalDate> dateTo;
    private StringProperty timeTo;
    private FloatProperty temp;
    private FloatProperty temp2;
    private FloatProperty diff;
    private StringProperty registery;
    private StringProperty fileNumber;
    private StringProperty id;
    private BooleanProperty hasSecond;
    public Temperature() {
        number++;
        id = new SimpleStringProperty();
        id.set("Temperatura " + number);

        dateFrom = new SimpleObjectProperty<>();
        dateTo = new SimpleObjectProperty<>();
        timeFrom = new SimpleStringProperty();
        timeTo = new SimpleStringProperty();
        temp = new SimpleFloatProperty();
        temp2 = new SimpleFloatProperty();
        diff = new SimpleFloatProperty();
        registery = new SimpleStringProperty();
        fileNumber = new SimpleStringProperty();
        hasSecond = new SimpleBooleanProperty();

    }

    public LocalDate getDateFrom() {
        return dateFrom.get();
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom.set(dateFrom);
    }

    public ObjectProperty<LocalDate> dateFromProperty() {
        return dateFrom;
    }

    public String getTimeFrom() {
        return timeFrom.get();
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom.set(timeFrom);
    }

    public StringProperty timeFromProperty() {
        return timeFrom;
    }

    public LocalDate getDateTo() {
        return dateTo.get();
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo.set(dateTo);
    }

    public ObjectProperty<LocalDate> dateToProperty() {
        return dateTo;
    }

    public String getTimeTo() {
        return timeTo.get();
    }

    public void setTimeTo(String timeTo) {
        this.timeTo.set(timeTo);
    }

    public StringProperty timeToProperty() {
        return timeTo;
    }

    public float getTemp() {
        return temp.get();
    }

    public void setTemp(float temp) {
        this.temp.set(temp);
    }

    public FloatProperty tempProperty() {
        return temp;
    }

    public float getDiff() {
        return diff.get();
    }

    public void setDiff(float diff) {
        this.diff.set(diff);
    }

    public FloatProperty diffProperty() {
        return diff;
    }

    public String getRegistery() {
        return registery.get();
    }

    public void setRegistery(String registery) {
        this.registery.set(registery);
    }

    public StringProperty registeryProperty() {
        return registery;
    }

    public String getFileNumber() {
        return fileNumber.get();
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber.set(fileNumber);
    }

    public StringProperty fileNumberProperty() {
        return fileNumber;
    }

    public float getTemp2() {
        return temp2.get();
    }

    public void setTemp2(float temp2) {
        this.temp2.set(temp2);
    }

    public FloatProperty temp2Property() {
        return temp2;
    }

    public StringProperty getId() {
        return id;
    }

    public boolean isHasSecond() {
        return hasSecond.get();
    }

    public void setHasSecond(boolean hasSecond) {
        this.hasSecond.set(hasSecond);
    }

    public BooleanProperty hasSecondProperty() {
        return hasSecond;
    }
}
