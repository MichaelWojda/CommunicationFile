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
    private void initialize() throws IOException {
        addTicketForPlanningModel = new AddTicketForPlanningModel();
        addTicketForPlanningModel.innit();
        this.choosePlanAuthorBox.setItems(addTicketForPlanningModel.getPlanningFXList());

    }

    @FXML
    void onSelectionAuthorBox() {

    }
    @FXML
    void purBoxOnSelection() {

    }

    @FXML
    void scmBoxOnSelection() {

    }

    @FXML
    void statusBoxOnSelection() {

    }

}
