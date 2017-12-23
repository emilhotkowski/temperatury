package com.temp.model;

public class Truck {

    private String rejestracja;
    private String tkUnitId;
    private String revision;
    private String agregat;

    public String getRejestracja() {
        return rejestracja;
    }

    public void setRejestracja(String rejestracja) {
        this.rejestracja = rejestracja;
    }

    public String getTkUnitId() {
        return tkUnitId;
    }

    public void setTkUnitId(String tkUnitId) {
        this.tkUnitId = tkUnitId;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getAgregat() {
        return agregat;
    }

    public void setAgregat(String agregat) {
        this.agregat = agregat;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "rejestracja='" + rejestracja + '\'' +
                ", tkUnitId='" + tkUnitId + '\'' +
                ", revision='" + revision + '\'' +
                ", agregat='" + agregat + '\'' +
                '}';
    }
}
