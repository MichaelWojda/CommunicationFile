package mainDirectory.controllers;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import mainDirectory.modelFX.PersonModel;

import java.io.IOException;
import java.util.ArrayList;

public class AdminWindowController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private ComboBox<String> deptComboBox;

    @FXML
    private Button addPersonButton;

    private PersonModel personModel;

    ObservableList<String> departaments;


    @FXML
    public void initialize(){
        personModel = new PersonModel();
        personModel.innit();
        initComboBox();


        this.nameField.textProperty().bindBidirectional(personModel.getPersonFXSimpleObjectProperty().nameProperty());
        this.surnameField.textProperty().bindBidirectional(personModel.getPersonFXSimpleObjectProperty().surnameProperty());
        this.deptComboBox.getItems().setAll(departaments);




    }

    private void initComboBox() {
        ArrayList<String> listOfDepts = new ArrayList<>();
        listOfDepts.add("Zaopatrzenie");
        listOfDepts.add("SCM");
        listOfDepts.add("Planowanie");
        departaments= FXCollections.observableArrayList(listOfDepts);

    }


    @FXML
    void addPersonOnClick() {
            try {
            this.personModel.savePersonInDB();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void onSelection() {
        this.personModel.getPersonFXSimpleObjectProperty().setDepartament(this.deptComboBox.getSelectionModel().selectedItemProperty().toString());
    }
}
