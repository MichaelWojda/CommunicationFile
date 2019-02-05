package mainDirectory.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import mainDirectory.modelFX.TicketPlanningModel;

public class ViewTicketWindowController {

    @FXML
    private TextField idText;

    @FXML
    private TextField authorText;

    @FXML
    private TextField matText;

    @FXML
    private TextField descText;

    @FXML
    private TextField projectText;

    @FXML
    private TextArea notesText;

    @FXML
    private TextField planText;

    @FXML
    private TextField purText;

    @FXML
    private TextField scmText;

    @FXML
    private TextField statusText;

    @FXML
    private TextField dataText;

    @FXML
    private TextField activeText;


    TicketPlanningModel ticketPlanningModel;

    @FXML
    public void initialize(){
        ticketPlanningModel= new TicketPlanningModel();
        bindings();


    }

    public void bindings() {
       this.matText.textProperty().bindBidirectional((this.ticketPlanningModel.getTicketHistoryObject().materialNamePropertyProperty()));
       this.idText.textProperty().bind(this.ticketPlanningModel.getTicketHistoryObject().id_ticketPropertyProperty().asString());
       this.authorText.setText(this.ticketPlanningModel.getTicketHistoryObject().getAuthorFXProperty().getName()+" "+this.ticketPlanningModel.getTicketHistoryObject().getAuthorFXProperty().getSurname());
       this.descText.textProperty().bind(this.ticketPlanningModel.getTicketHistoryObject().materialDescriptionPropertyProperty());
       this.projectText.textProperty().bind(this.ticketPlanningModel.getTicketHistoryObject().projectPropertyProperty());
       this.notesText.textProperty().bind(this.ticketPlanningModel.getTicketHistoryObject().notesPropertyProperty());
       this.planText.setText(this.ticketPlanningModel.getTicketHistoryObject().getAuthorFXProperty().getName()+" "+this.ticketPlanningModel.getTicketHistoryObject().getPlannerFXProperty().getSurname());
       this.purText.setText(this.ticketPlanningModel.getTicketHistoryObject().getAuthorFXProperty().getName()+" "+this.ticketPlanningModel.getTicketHistoryObject().getBuyerFXProperty().getSurname());
       this.scmText.setText(this.ticketPlanningModel.getTicketHistoryObject().getAuthorFXProperty().getName()+" "+this.ticketPlanningModel.getTicketHistoryObject().getScmerFXProperty().getSurname());
       this.statusText.setText(this.ticketPlanningModel.getTicketHistoryObject().getStatusProperty().getNameFX());
       this.dataText.setText(this.ticketPlanningModel.getTicketHistoryObject().getData());
       this.activeText.setText(String.valueOf(this.ticketPlanningModel.getTicketHistoryObject().getActiveProperty()));

    }

    @FXML
    void okClick() {
        System.out.println(this.ticketPlanningModel.getTicketHistoryObject().getAuthorFXProperty().nameProperty());

    }

}
