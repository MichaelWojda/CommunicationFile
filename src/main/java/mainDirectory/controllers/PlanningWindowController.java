package mainDirectory.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import mainDirectory.modelFX.AddTicketForPlanningModel;
import mainDirectory.modelFX.PersonFX;

import java.io.IOException;

public class PlanningWindowController {

    @FXML
    private TextField materialNameField;

    @FXML
    private TextField materialDescField;

    @FXML
    private TextField projectNameField;

    @FXML
    private TextArea notesField;

    @FXML
    private ComboBox<PersonFX> scmComboBox;

    @FXML
    private ComboBox<PersonFX> purComboBox;

    @FXML
    private ComboBox<PersonFX> choosePlanAuthorBox;

    private AddTicketForPlanningModel addTicketForPlanningModel;

    @FXML
    private void initialize(){
        addTicketForPlanningModel = new AddTicketForPlanningModel();
        addTicketForPlanningModel.innit();
        this.choosePlanAuthorBox.setItems(addTicketForPlanningModel.getPlanningFXList());
        this.purComboBox.setItems(addTicketForPlanningModel.getPurFXList());
        this.scmComboBox.setItems(addTicketForPlanningModel.getScMFXList());
        this.materialNameField.textProperty().bind(addTicketForPlanningModel.getTicketFXObjectProperty().materialNamePropertyProperty());
        this.materialDescField.textProperty().bind(addTicketForPlanningModel.getTicketFXObjectProperty().materialDescriptionPropertyProperty());
        this.notesField.textProperty().bind(addTicketForPlanningModel.getTicketFXObjectProperty().notesPropertyProperty());
        this.projectNameField.textProperty().bind(addTicketForPlanningModel.getTicketFXObjectProperty().projectPropertyProperty());




    }

    @FXML
    void onSelectionAuthorBox() {
        this.addTicketForPlanningModel.setAuthorFXObjectProperty(this.choosePlanAuthorBox.getSelectionModel().getSelectedItem());

    }
    @FXML
    void purBoxOnSelection() {
        this.addTicketForPlanningModel.setPurFXObjectProperty(this.purComboBox.getSelectionModel().getSelectedItem());
    }

    @FXML
    void scmBoxOnSelection() {
        this.addTicketForPlanningModel.setScmFXObjectProperty(this.scmComboBox.getSelectionModel().getSelectedItem());

    }

    @FXML
    void statusBoxOnSelection() {

    }

}
