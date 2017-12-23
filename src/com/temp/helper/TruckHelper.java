package com.temp.helper;

import com.temp.model.Truck;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class TruckHelper {

    private static List<Truck> naczepy;

    public static List<Truck> getTruckList() {
        if(naczepy == null) {
            naczepy = new ArrayList<>();
            Reader in = null;
            try {
                in = new FileReader("naczepy.csv");
                Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().withDelimiter(';').parse(in);
                for (CSVRecord record : records) {
                    Truck truck = new Truck();
                    truck.setRejestracja(record.get("Rejestracja"));
                    truck.setTkUnitId(record.get("TkUnitId").substring(1));
                    truck.setRevision(record.get("Revision"));
                    truck.setAgregat(record.get("Agregat"));

                    naczepy.add(truck);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return naczepy;
    }

    public static Truck getByName(String name) {
        if(naczepy == null)
            getTruckList();

        for (Truck truck : naczepy) {
            if(truck.getRejestracja().equals(name)) {
                return truck;
            }
        }

        return null;
    }

}
