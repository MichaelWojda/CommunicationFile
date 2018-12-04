package mainDirectory.controllers;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import mainDirectory.modelFX.PersonModel;

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
    ListProperty<String> list;


    @FXML
    public void initialize(){
        personModel = new PersonModel();
        personModel.innit();
        initComboBox();


        this.nameField.textProperty().bind(personModel.getPersonFXSimpleObjectProperty().nameProperty());
        this.surnameField.textProperty().bind(personModel.getPersonFXSimpleObjectProperty().surnameProperty());
        this.deptComboBox.itemsProperty().bindBidirectional(list);




    }

    private void initComboBox() {
        ArrayList<String> listOfDepts = new ArrayList<>();
        listOfDepts.add("Zaopatrzenie");
        listOfDepts.add("SCM");
        listOfDepts.add("Planowanie");
        departaments= FXCollections.observableArrayList(listOfDepts);
        list = new SimpleListProperty<String>();
        list.set(departaments);
    }


    @FXML
    void addPersonOnClick() {
        this.personModel.savePersonInDB();


    }

}
