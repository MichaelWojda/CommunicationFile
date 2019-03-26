package mainDirectory.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mainDirectory.Converters.PersonConverter;
import mainDirectory.database.model.Ticket;
import mainDirectory.dialogs.Dialogs;
import mainDirectory.modelFX.TicketPlanningModel;
import mainDirectory.modelFX.PersonFX;
import mainDirectory.modelFX.StatusFX;
import mainDirectory.modelFX.TicketFX;
import mainDirectory.utils.Exceptions.ApplicationException;
import mainDirectory.utils.fxmlUtils;
import mainDirectory.utils.mailSender;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class PlanningWindowController {

    @FXML
    private Button importButton;

    @FXML
    private Button addTicketButton;

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

    @FXML
    private ComboBox<StatusFX> statusComboBox;

    @FXML
    private ComboBox<PersonFX> filterByPlannerComboBox;

    @FXML
    private TableView<TicketFX> ticketTableView;

    @FXML
    private TableColumn<TicketFX, Number> ticketIdColumn;

    @FXML
    private TableColumn<TicketFX, String> ticketMatNameColumn;

    @FXML
    private TableColumn<TicketFX, String> ticketMatDescColumn;

    @FXML
    private TableColumn<TicketFX, StatusFX> ticketStatusColumn;

    @FXML
    private TableColumn<TicketFX, PersonFX> ticketAuthorColumn;

    @FXML
    private TableColumn<TicketFX, TicketFX> receiveColumn;

    @FXML
    private TableView<TicketFX> myTicketsTable;

    @FXML
    private TableColumn<TicketFX, Number> myTicketsId;

    @FXML
    private TableColumn<TicketFX, String> myTicketsMatNum;

    @FXML
    private TableColumn<TicketFX, String> myTicketsMatDesc;

    @FXML
    private TableColumn<TicketFX, StatusFX> myTicketsMatStatus;

    @FXML
    private TableColumn<TicketFX, PersonFX> myTicketsPurColumn;

    @FXML
    private TableColumn<TicketFX, PersonFX> myTicketsScmColumn;

    @FXML
    private TableColumn<TicketFX, TicketFX> closeTicketColumn;

    @FXML
    private TableColumn<TicketFX, TicketFX> alertColumn;

    @FXML
    private ComboBox<PersonFX> myTicketComboBox;

    @FXML
    private TableColumn<TicketFX, String> myDataColumn;

    @FXML
    private TableColumn<TicketFX, String> dateColumn;

    @FXML
    private Button searchButton;



    private TicketPlanningModel ticketPlanningModel;

    @FXML
    private void initialize(){
        ticketPlanningModel = new TicketPlanningModel();
        try {
            ticketPlanningModel.innit();
        } catch (ApplicationException e) {
            Dialogs.alertMessage(e.getMessage());
        }
        this.myTicketsTable.setItems(this.ticketPlanningModel.getTicketFXMyTicketList());
        this.myTicketsId.setCellValueFactory(c->c.getValue().idPropertyProperty());
        this.myTicketsMatNum.setCellValueFactory(c->c.getValue().materialNamePropertyProperty());
        this.myTicketsMatDesc.setCellValueFactory(c->c.getValue().materialDescriptionPropertyProperty());
        this.myTicketsPurColumn.setCellValueFactory(c->c.getValue().buyerFXPropertyProperty());
        this.myTicketsScmColumn.setCellValueFactory(c->c.getValue().scmerFXPropertyProperty());
        this.myTicketsMatStatus.setCellValueFactory(c->c.getValue().statusPropertyProperty());
        this.myDataColumn.setCellValueFactory(c->c.getValue().dataProperty());
        this.closeTicketColumn.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue()));
        this.closeTicketColumn.setCellFactory(c->new TableCell<TicketFX, TicketFX>(){
            Button button = createButton("/icons/racing.png");

            @Override
            protected void updateItem(TicketFX item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setGraphic(null);
                }
                if(!empty){
                    setGraphic(button);
                }
                button.setOnAction(event->{
                    Optional<ButtonType> result = Dialogs.confirmFinished();
                    if(result.get()==ButtonType.OK) {
                        item.setActiveProperty(false);
                        try {
                            ticketPlanningModel.updateTicketInDB(item);
                        } catch (ApplicationException e) {
                            Dialogs.alertMessage(e.getMessage());
                        }
                    }


                });
            }
        });
        this.alertColumn.setCellValueFactory(c-> new SimpleObjectProperty<>(c.getValue()));
        this.alertColumn.setCellFactory(c->new TableCell<TicketFX, TicketFX>(){
            Button button = createButton("/icons/bell.png");

            @Override
            protected void updateItem(TicketFX item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setGraphic(null);
                }
                if(!empty){
                    setGraphic(button);
                }
                button.setOnAction(event->{
                    mailSender.sendEmail("michal.wojda@pl.abb.com");

                });
            }
        });


        editTicketColumnBindings();
        addTicketBindings();
        checkUpFields();



    }

    protected void editTicketColumnBindings() {
        this.ticketTableView.setItems(this.ticketPlanningModel.getTicketFXObservableListPlanning());
        this.ticketIdColumn.setCellValueFactory(c->c.getValue().idPropertyProperty());
        this.ticketMatNameColumn.setCellValueFactory(c->c.getValue().materialNamePropertyProperty());
        this.ticketMatDescColumn.setCellValueFactory(c->c.getValue().materialDescriptionPropertyProperty());
        this.ticketStatusColumn.setCellValueFactory(c->c.getValue().statusPropertyProperty());
        this.ticketAuthorColumn.setCellValueFactory(c->c.getValue().authorFXPropertyProperty());
        this.dateColumn.setCellValueFactory(c->c.getValue().dataProperty());
        this.receiveColumn.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue()));
        this.receiveColumn.setCellFactory(c->new TableCell<TicketFX,TicketFX>(){
            Button button = createButton("/icons/mail.png");

            @Override
            protected void updateItem(TicketFX item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setGraphic(null);
                }
                if(!empty){
                    setGraphic(button);
                }
                button.setOnAction(event->{
                    FXMLLoader loader = fxmlUtils.returnLoader("/fxml/EditTicketWindow.fxml");
                    Scene scene = null;
                    try {
                        scene = new Scene(loader.load());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    EditTicketWindowController editTicketWindowController = loader.getController();
                    editTicketWindowController.ticketPlanningModel.setTicketFXObjectProperty(item);
                    editTicketWindowController.bindings();
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Edycja ticketa");
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait();


                });
            }
        });

    }

    private void addTicketBindings() {
        this.choosePlanAuthorBox.setItems(ticketPlanningModel.getPlanningFXList());
        this.filterByPlannerComboBox.setItems(ticketPlanningModel.getPlanningFXList());
        this.myTicketComboBox.setItems(ticketPlanningModel.getPlanningFXList());
        this.purComboBox.setItems(ticketPlanningModel.getPurFXList());
        this.scmComboBox.setItems(ticketPlanningModel.getScMFXList());
        this.statusComboBox.setItems(ticketPlanningModel.getStatusFXObservableList());
        this.materialNameField.textProperty().bindBidirectional(ticketPlanningModel.getTicketFXObjectProperty().materialNamePropertyProperty());
        this.materialDescField.textProperty().bindBidirectional(ticketPlanningModel.getTicketFXObjectProperty().materialDescriptionPropertyProperty());
        this.notesField.textProperty().bindBidirectional(ticketPlanningModel.getTicketFXObjectProperty().notesPropertyProperty());
        this.projectNameField.textProperty().bindBidirectional(ticketPlanningModel.getTicketFXObjectProperty().projectPropertyProperty());
        //this.choosePlanAuthorBox.valueProperty().bindBidirectional(this.filterByPlannerComboBox.valueProperty());
        this.ticketPlanningModel.plannerFXProperty().bind(this.filterByPlannerComboBox.valueProperty());
        this.ticketPlanningModel.authorFXProperty().bind(this.myTicketComboBox.valueProperty());
        this.ticketPlanningModel.getTicketFXObjectProperty().setActiveProperty(true);
    }

    private void checkUpFields() {
        this.addTicketButton.disableProperty().bind(choosePlanAuthorBox.valueProperty().isNull()
                .or(materialNameField.textProperty().isEmpty()
                        .or(materialDescField.textProperty().isEmpty()
                                .or(projectNameField.textProperty().isEmpty()
                                        .or(notesField.textProperty().isEmpty()
                                                .or(purComboBox.valueProperty().isNull().
                                                        or(scmComboBox.valueProperty().isNull()
                                                                .or(statusComboBox.valueProperty().isNull()))))))));
        this.searchButton.disableProperty().bind(materialNameField.textProperty().isEmpty());
    }

    @FXML
    void onSelectionAuthorBox() {
        this.ticketPlanningModel.getTicketFXObjectProperty().setAuthorFXProperty(this.choosePlanAuthorBox.getSelectionModel().getSelectedItem());
        this.ticketPlanningModel.getTicketFXObjectProperty().setPlannerFXProperty(this.choosePlanAuthorBox.getSelectionModel().getSelectedItem());
        this.filterByPlannerComboBox.setValue(this.choosePlanAuthorBox.getSelectionModel().getSelectedItem());
        this.ticketPlanningModel.filterByPlanner();
    }
    @FXML
    void purBoxOnSelection() {
        this.ticketPlanningModel.getTicketFXObjectProperty().setBuyerFXProperty(this.purComboBox.getSelectionModel().getSelectedItem());
    }

    @FXML
    void scmBoxOnSelection() {
        this.ticketPlanningModel.getTicketFXObjectProperty().setScmerFXProperty(this.scmComboBox.getSelectionModel().getSelectedItem());
    }

    @FXML
    void statusBoxOnSelection() {
        this.ticketPlanningModel.getTicketFXObjectProperty().setStatusProperty(this.statusComboBox.getSelectionModel().getSelectedItem());

    }
    @FXML
    void myTicketOnSelectionComboBox() {
        this.ticketPlanningModel.filterByAuthor();

    }

    public void clearButtonOnClick() {
        this.choosePlanAuthorBox.setValue(null);
        this.materialNameField.clear();
        this.materialDescField.clear();
        this.projectNameField.clear();
        this.notesField.clear();
        this.scmComboBox.setValue(null);
        this.purComboBox.setValue(null);
        this.statusComboBox.setValue(null);
    }

    public void addTicketButtonOnClick() {
        try {
            this.ticketPlanningModel.createTicketInDB();
        } catch (ApplicationException e) {
            Dialogs.alertMessage(e.getMessage());
        }

        this.materialNameField.clear();
        /*this.choosePlanAuthorBox.setValue(this.ticketPlanningModel.getTicketFXObjectProperty().getAuthorFXProperty());
        this.purComboBox.setValue(this.ticketPlanningModel.getTicketFXObjectProperty().getBuyerFXProperty());
        this.scmComboBox.setValue(this.ticketPlanningModel.getTicketFXObjectProperty().getScmerFXProperty());
        this.statusComboBox.setValue(this.ticketPlanningModel.getTicketFXObjectProperty().getStatusProperty());*/
    }

    public void filterbyPlannerOnSelection() {
        this.ticketPlanningModel.filterByPlanner();
    }


    public Button createButton(String path) {
        Button button = new Button();
        Image image = new Image(this.getClass().getResource(path).toString());
        ImageView imageView = new ImageView(image);
        button.setGraphic(imageView);
        return button;


    }
    public void searchForMaterial() {
        String searchedMaterial = this.materialNameField.textProperty().getValue();
        try {
            List<Ticket> list = ticketPlanningModel.searchForMaterial(searchedMaterial);
            if(list.isEmpty()){
                Dialogs.alertMessage("Nie znaleziono materiału");
            }
            else{
                Ticket ticket = list.get(0);
                this.materialDescField.setText(ticket.getMaterialDescription());
                this.projectNameField.setText(ticket.getProject());
                this.purComboBox.setValue(PersonConverter.convertToPersonFX(ticket.getBuyer()));
                this.scmComboBox.setValue(PersonConverter.convertToPersonFX(ticket.getScmer()));
            }
        } catch (ApplicationException e) {
            Dialogs.alertMessage(e.getMessage());
        }

    }


    public void importFromExcel() {
    }
}


