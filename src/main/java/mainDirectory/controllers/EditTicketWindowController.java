package mainDirectory.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainDirectory.dialogs.Dialogs;
import mainDirectory.modelFX.PersonFX;
import mainDirectory.modelFX.StatusFX;
import mainDirectory.modelFX.TicketPlanningModel;
import mainDirectory.utils.Exceptions.ApplicationException;

public class EditTicketWindowController {


    @FXML
    private Label idNumberLabel;

    @FXML
    private TextField editTicketMatNumber;

    @FXML
    private TextField editTicketMatDesc;

    @FXML
    private TextField editTicketProject;

    @FXML
    private TextArea editTicketNotes;

    @FXML
    private ComboBox<PersonFX> editTicketPlanComboBox;

    @FXML
    private ComboBox<PersonFX> editTicketPurComboBox;

    @FXML
    private ComboBox<PersonFX> editTicketScmComboBox;

    @FXML
    private Button saveChangesButton;

    @FXML
    private Button saveAndExitButton;

    @FXML
    private ComboBox<StatusFX> editTicketStatusComboBox;

    public TicketPlanningModel ticketPlanningModel;

    private PlanningWindowController planningWindowController;


    @FXML
    private void initialize() {
        ticketPlanningModel = new TicketPlanningModel();
        try {
            ticketPlanningModel.innitStaticLists();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

        bindings();


    }

    public void bindings() {
        this.editTicketStatusComboBox.setItems(this.ticketPlanningModel.getStatusFXObservableList());
        this.editTicketScmComboBox.setItems(this.ticketPlanningModel.getScMFXList());
        this.editTicketPurComboBox.setItems(this.ticketPlanningModel.getPurFXList());
        this.editTicketPlanComboBox.setItems(this.ticketPlanningModel.getPlanningFXList());
        this.idNumberLabel.setText(this.ticketPlanningModel.getTicketFXObjectProperty().idPropertyProperty().getValue().toString());
        this.editTicketMatNumber.textProperty().bindBidirectional(this.ticketPlanningModel.getTicketFXObjectProperty().materialNamePropertyProperty());
        this.editTicketMatDesc.textProperty().bindBidirectional(this.ticketPlanningModel.getTicketFXObjectProperty().materialDescriptionPropertyProperty());
        this.editTicketProject.textProperty().bindBidirectional(this.ticketPlanningModel.getTicketFXObjectProperty().projectPropertyProperty());
        this.editTicketNotes.textProperty().bindBidirectional(this.ticketPlanningModel.getTicketFXObjectProperty().notesPropertyProperty());
        this.editTicketPlanComboBox.valueProperty().bindBidirectional(this.ticketPlanningModel.getTicketFXObjectProperty().plannerFXPropertyProperty());
        this.editTicketPurComboBox.valueProperty().bindBidirectional(this.ticketPlanningModel.getTicketFXObjectProperty().buyerFXPropertyProperty());
        this.editTicketScmComboBox.valueProperty().bindBidirectional(this.ticketPlanningModel.getTicketFXObjectProperty().scmerFXPropertyProperty());
        this.editTicketStatusComboBox.valueProperty().bindBidirectional(this.ticketPlanningModel.getTicketFXObjectProperty().statusPropertyProperty());

    }


    @FXML
        void PurComboBoxOnAction() {
        this.ticketPlanningModel.getTicketFXObjectProperty().setBuyerFXProperty(this.editTicketPurComboBox.getSelectionModel().getSelectedItem());

    }

    @FXML
    void planComboBoxOnAction() {
        this.ticketPlanningModel.getTicketFXObjectProperty().setPlannerFXProperty(this.editTicketPlanComboBox.getSelectionModel().getSelectedItem());

    }

    @FXML
    void saveAndExitButtonOnClick() {


    }

    @FXML
    void saveChangesButtonOnClick() {
        try {
            this.ticketPlanningModel.saveTicketInDB();
        } catch (ApplicationException e) {
            Dialogs.alertMessage(e.getMessage());
        }
        this.ticketPlanningModel.filterByPlanner();
        Stage stage =(Stage) this.saveChangesButton.getScene().getWindow();
        stage.close();




    }

    @FXML
    void scmComboBoxOnAction() {
        this.ticketPlanningModel.getTicketFXObjectProperty().setScmerFXProperty(this.editTicketScmComboBox.getSelectionModel().getSelectedItem());

    }
    @FXML
     void statusComboBoxOnSelection() {
        this.ticketPlanningModel.getTicketFXObjectProperty().setStatusProperty(this.editTicketStatusComboBox.getSelectionModel().getSelectedItem());
    }

    public TicketPlanningModel getTicketPlanningModel() {
        return ticketPlanningModel;
    }

    public void setTicketPlanningModel(TicketPlanningModel ticketPlanningModel) {
        this.ticketPlanningModel = ticketPlanningModel;
    }

    public PlanningWindowController getPlanningWindowController() {
        return planningWindowController;
    }

    public void setPlanningWindowController(PlanningWindowController planningWindowController) {
        this.planningWindowController = planningWindowController;
    }
}