package com.temp.view;

import com.temp.Main;
import com.temp.builder.ExcelBuilder;
import com.temp.helper.TruckHelper;
import com.temp.model.Temperature;
import com.temp.model.Truck;
import com.temp.widget.MaskField;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.Arrays;


public class TemperatureController {

    @FXML
    private TableView<Temperature> tempTable;
    @FXML
    private TableColumn<Temperature, String> tempColumn;


    @FXML
    private DatePicker dateFromField;
    @FXML
    private DatePicker dateToField;
    @FXML
    private MaskField timeFromField;
    @FXML
    private MaskField timeToField;
    @FXML
    private TextField tempOneField;
    @FXML
    private TextField tempTwoField;
    @FXML
    private TextField diffField;
    @FXML
    private ComboBox<Truck> registeryField;
    @FXML
    private TextField fileNumber;
    @FXML
    private CheckBox secondTempCheckBox;
    @FXML
    private Label tempTwoLabel;

    private Main mainApp;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        tempColumn.setCellValueFactory(cellData -> cellData.getValue().getId());

        tempTable.getSelectionModel().selectedItemProperty().addListener((
                (observable, oldValue, newValue) -> {
                    //zapisujemy dane z pol do poprzedniej temp
                    if (oldValue != null)
                        saveFromFields(oldValue);

                    //ustaw dane z temperatury do pol
                    if (newValue != null)
                        saveToFields(newValue);

                    //jesli nowa wartosc nie jest pierwsza, zamknij pola
                    disableFields(newValue);
                }
        ));

        tempTwoField.setDisable(true);
        tempTwoLabel.setDisable(true);

       TruckHelper.getTruckList().forEach(e -> registeryField.getItems().add(e));
       registeryField.setConverter(new StringConverter<Truck>() {
           @Override
           public String toString(Truck object) {
               return object.getRejestracja();
           }

           @Override
           public Truck fromString(String string) {
               return null;
           }
       });
    }

    private void disableFields(Temperature newValue) {
        boolean disable = mainApp.getTemperatures().indexOf(newValue) != 0;
        dateFromField.setDisable(disable);
        timeFromField.setDisable(disable);
    }

    private void saveToFields(Temperature newValue) {
        dateFromField.setValue(newValue.getDateFrom());
        dateToField.setValue(newValue.getDateTo());
        timeFromField.setPlainText(newValue.getTimeFrom() != null ? newValue.getTimeFrom() : "");
        timeToField.setPlainText(newValue.getTimeTo() != null ? newValue.getTimeTo() : "");
        tempOneField.setText(String.valueOf(newValue.getTemp()));
        tempTwoField.setText(String.valueOf(newValue.getTemp2()));
        diffField.setText(String.valueOf(newValue.getDiff()));
        secondTempCheckBox.setSelected(newValue.hasSecondProperty().getValue());

        secondTempAction();
    }

    private void saveFromFields(Temperature oldValue) {
        oldValue.setDateFrom(dateFromField.getValue());
        oldValue.setDateTo(dateToField.getValue());
        oldValue.setTimeFrom(timeFromField.getPlainText());
        oldValue.setTimeTo(timeToField.getPlainText());
        oldValue.setTemp(Float.valueOf(tempOneField.getText()));
        if (tempTwoField.getText() != null)
            oldValue.setTemp2(Float.valueOf(tempTwoField.getText()));
        oldValue.setDiff(Float.valueOf(diffField.getText()));
        oldValue.setHasSecond(secondTempCheckBox.selectedProperty().getValue());
        oldValue.setRegistery(registeryField.getValue().getRejestracja());
        oldValue.setFileNumber(fileNumber.getText());
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMain(Main mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        mainApp.getTemperatures().add(new Temperature());
        tempTable.setItems(mainApp.getTemperatures());
        tempTable.getSelectionModel().select(0);
    }


    @FXML
    private void addTemperature() {
        mainApp.getTemperatures().add(new Temperature());
    }

    @FXML
    private void deleteTemperature() {
        mainApp.getTemperatures().remove(tempTable.getSelectionModel().getSelectedItem());
        int i = 1;
        for (Temperature temperature : mainApp.getTemperatures()) {
            temperature.setId("Temperatura " + i++);
        }
        Temperature.number = i-1;
    }

    @FXML
    private void createTemperature() {

        saveFromFields(tempTable.getSelectionModel().getSelectedItem());//zapisujemy aktualne wartosci dla pewnosci

        //TODO : walidacja pol?
        Exception ex = null;

        ExcelBuilder excelBuilder = new ExcelBuilder();
        boolean error = false;
        try {
            String name = registeryField.getValue().getRejestracja();
            if(fileNumber.getText() != null && !fileNumber.getText().equals("")) {
                name += " " + fileNumber.getText();
            }
            excelBuilder.buildExcel(mainApp.getTemperatures(), name , registeryField.getValue().getRejestracja());
        } catch (Exception e) {
            error = true;
            ex= e;
            e.printStackTrace();
        }

        if(error) {
            String blad = Arrays.stream(ex.getStackTrace()).map(a -> a.toString()).reduce((s, s2) -> {return s + "\n" +s2;}).get();
            Alert alert = new Alert(Alert.AlertType.ERROR.CONFIRMATION, "Pojawił się błąd "+ blad , ButtonType.OK);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Temperatura stworzona pod nazwa : " + registeryField.getValue().getRejestracja() + ".xlsx", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void secondTempAction() {
        tempTwoField.setDisable(!secondTempCheckBox.selectedProperty().getValue());
        tempTwoLabel.setDisable(!secondTempCheckBox.selectedProperty().getValue());
    }

    @FXML
    private void changeDateFrom() {
        LocalDate accDate = dateFromField.getValue();
        if (accDate != null && dateToField.getValue() == null)
            dateToField.setValue(accDate);
    }

    /**
     * <MaskField fx:id="timeFromField" GridPane.columnIndex="1" GridPane.rowIndex="1" mask="DD:DD"/>
     * <MaskField fx:id="timeToField" GridPane.columnIndex="1" GridPane.rowIndex="3" mask="DD:DD"/>
     */
}
