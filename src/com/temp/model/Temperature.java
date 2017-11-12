package com.temp.model;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.Date;

public class Temperature {

    static public Integer number = 0;

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
        type = new SimpleObjectProperty<TempType>();
        hasSecond = new SimpleBooleanProperty();

    }

    private ObjectProperty<LocalDate> dateFrom;
    private StringProperty timeFrom;
    private ObjectProperty<LocalDate> dateTo;
    private StringProperty timeTo;
    private FloatProperty temp;
    private FloatProperty temp2;
    private FloatProperty diff;
    private StringProperty registery;
    private ObjectProperty<TempType> type;
    private StringProperty id;
    private BooleanProperty hasSecond;

    public LocalDate getDateFrom() {
        return dateFrom.get();
    }

    public ObjectProperty<LocalDate> dateFromProperty() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom.set(dateFrom);
    }

    public String getTimeFrom() {
        return timeFrom.get();
    }

    public StringProperty timeFromProperty() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom.set(timeFrom);
    }

    public LocalDate getDateTo() {
        return dateTo.get();
    }

    public ObjectProperty<LocalDate> dateToProperty() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo.set(dateTo);
    }

    public String getTimeTo() {
        return timeTo.get();
    }

    public StringProperty timeToProperty() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo.set(timeTo);
    }

    public float getTemp() {
        return temp.get();
    }

    public FloatProperty tempProperty() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp.set(temp);
    }

    public float getDiff() {
        return diff.get();
    }

    public FloatProperty diffProperty() {
        return diff;
    }

    public void setDiff(float diff) {
        this.diff.set(diff);
    }

    public String getRegistery() {
        return registery.get();
    }

    public StringProperty registeryProperty() {
        return registery;
    }

    public void setRegistery(String registery) {
        this.registery.set(registery);
    }

    public TempType getType() {
        return type.get();
    }

    public ObjectProperty<TempType> typeProperty() {
        return type;
    }

    public void setType(TempType type) {
        this.type.set(type);
    }

    public float getTemp2() {
        return temp2.get();
    }

    public FloatProperty temp2Property() {
        return temp2;
    }

    public void setTemp2(float temp2) {
        this.temp2.set(temp2);
    }

    public StringProperty getId() {
        return id;
    }

    public boolean isHasSecond() {
        return hasSecond.get();
    }

    public BooleanProperty hasSecondProperty() {
        return hasSecond;
    }

    public void setHasSecond(boolean hasSecond) {
        this.hasSecond.set(hasSecond);
    }

    public void setId(String id) {
        this.id.set(id);
    }
}
