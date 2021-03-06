package mainDirectory.controllers;

import javafx.beans.property.SimpleObjectProperty;
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
import mainDirectory.modelFX.PersonFX;
import mainDirectory.modelFX.StatusFX;
import mainDirectory.modelFX.TicketFX;
import mainDirectory.modelFX.TicketPlanningModel;
import mainDirectory.utils.ExcelUtilsPOI;
import mainDirectory.utils.Exceptions.ApplicationException;
import mainDirectory.utils.Other;
import mainDirectory.utils.fxmlUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ScmWindowController {

    @FXML
    private TextField materialNameField;

    @FXML
    private TextField materialDescField;

    @FXML
    private TextField projectNameField;

    @FXML
    private TextArea notesField;

    @FXML
    private ComboBox<PersonFX> planComboBox;

    @FXML
    private ComboBox<PersonFX> purComboBox;

    @FXML
    private ComboBox<StatusFX> statusComboBox;

    @FXML
    private ComboBox<PersonFX> chooseScmAuthorBox;

    @FXML
    private Button clearButton;

    @FXML
    private Button addTicketButton;

    @FXML
    private ComboBox<PersonFX> filterBySCMComboBox;

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
    private TableColumn<TicketFX, PersonFX> myTicketsPlanColumn;

    @FXML
    private TableColumn<TicketFX, TicketFX> alertColumn;

    @FXML
    private TableColumn<TicketFX, TicketFX> closeTicketColumn;

    @FXML
    private ComboBox<PersonFX> myTicketComboBox;

    @FXML
    private Button searchButton;

    @FXML
    private TableColumn<TicketFX, String> myDataColumn;

    @FXML
    private TableColumn<TicketFX, String> dateColumn;

    @FXML
    private Button exportButton;

    private TicketPlanningModel ticketPlanningModel;

    @FXML
    private void initialize(){
        ticketPlanningModel = new TicketPlanningModel();
        try {
            ticketPlanningModel.innit();
        } catch (ApplicationException e) {
            Dialogs.alertMessage(e.getMessage());
        }
        this.chooseScmAuthorBox.setItems(this.ticketPlanningModel.getScMFXList());
        this.planComboBox.setItems(this.ticketPlanningModel.getPlanningFXList());
        this.purComboBox.setItems(this.ticketPlanningModel.getPurFXList());
        this.statusComboBox.setItems(this.ticketPlanningModel.getStatusFXObservableList());
        this.materialNameField.textProperty().bindBidirectional(ticketPlanningModel.getTicketFXObjectProperty().materialNamePropertyProperty());
        this.materialDescField.textProperty().bindBidirectional(ticketPlanningModel.getTicketFXObjectProperty().materialDescriptionPropertyProperty());
        this.notesField.textProperty().bindBidirectional(ticketPlanningModel.getTicketFXObjectProperty().notesPropertyProperty());
        this.projectNameField.textProperty().bindBidirectional(ticketPlanningModel.getTicketFXObjectProperty().projectPropertyProperty());
        this.ticketPlanningModel.getTicketFXObjectProperty().setActiveProperty(true);
        checkUpFields();
        this.filterBySCMComboBox.setItems(this.ticketPlanningModel.getScMFXList());
        this.ticketPlanningModel.scmFXProperty().bind(this.filterBySCMComboBox.valueProperty());

        TableView.TableViewSelectionModel<TicketFX> selectionModel = ticketTableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        this.ticketTableView.setItems(this.ticketPlanningModel.getTicketFXObservableListSCM());
        this.ticketIdColumn.setCellValueFactory(c->c.getValue().idPropertyProperty());
        this.ticketMatNameColumn.setCellValueFactory(c->c.getValue().materialNamePropertyProperty());
        this.ticketMatDescColumn.setCellValueFactory(c->c.getValue().materialDescriptionPropertyProperty());
        this.ticketStatusColumn.setCellValueFactory(c->c.getValue().statusPropertyProperty());
        this.dateColumn.setCellValueFactory(c->c.getValue().dataProperty());
        this.ticketAuthorColumn.setCellValueFactory(c->c.getValue().authorFXPropertyProperty());
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
                    if(selectionModel.getSelectedItems().size()>1){
                        editTicketWindowController.ticketPlanningModel.setMultipleTicketsList(selectionModel.getSelectedItems());
                    }
                    editTicketWindowController.bindings();
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Edycja ticketa");
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait();


                });
            }
        });

        this.myTicketComboBox.setItems(this.ticketPlanningModel.getScMFXList());
        this.ticketPlanningModel.authorFXProperty().bind(this.myTicketComboBox.valueProperty());
        this.myTicketsTable.setItems(this.ticketPlanningModel.getTicketFXMyTicketList());
        this.myTicketsId.setCellValueFactory(c->c.getValue().idPropertyProperty());
        this.myTicketsMatNum.setCellValueFactory(c->c.getValue().materialNamePropertyProperty());
        this.myTicketsMatDesc.setCellValueFactory(c->c.getValue().materialDescriptionPropertyProperty());
        this.myTicketsPlanColumn.setCellValueFactory(c->c.getValue().plannerFXPropertyProperty());
        this.myTicketsPurColumn.setCellValueFactory(c->c.getValue().buyerFXPropertyProperty());
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
                    try {
                        Other.sendEmail(item,
                                "PLIK KOMUNIKACJI PRZYPOMNIENIE TICKET NR",
                                "Uwaga \n Powyższy ticket oczekuje na Twoje działanie od",
                                true);
                        button.disableProperty().set(true);
                    } catch (IOException e) {
                        Dialogs.alertMessage(e.getMessage());
                    }

                });
            }
        });

    }

    @FXML
    void addTicketButtonOnClick() {
        try {
            this.ticketPlanningModel.createTicketInDB();
        } catch (ApplicationException e) {
            Dialogs.alertMessage(e.getMessage());
        }
        this.materialNameField.clear();

    }

    @FXML
    void clearButtonOnClick() {
        this.chooseScmAuthorBox.setValue(null);
        this.materialNameField.clear();
        this.materialDescField.clear();
        this.projectNameField.clear();
        this.notesField.clear();
        this.planComboBox.setValue(null);
        this.purComboBox.setValue(null);
        this.statusComboBox.setValue(null);
    }

    @FXML
    void filterbySCMOnSelection() {
        this.ticketPlanningModel.filterByScm();
    }

    @FXML
    void myTicketOnSelectionComboBox() {
        this.ticketPlanningModel.filterByAuthor();
    }

    @FXML
    void onSelectionAuthorBox() {
        this.ticketPlanningModel.getTicketFXObjectProperty().setAuthorFXProperty(this.chooseScmAuthorBox.getSelectionModel().getSelectedItem());
        this.ticketPlanningModel.getTicketFXObjectProperty().setScmerFXProperty(this.chooseScmAuthorBox.getSelectionModel().getSelectedItem());
        this.filterBySCMComboBox.setValue(this.chooseScmAuthorBox.getSelectionModel().getSelectedItem());
        this.ticketPlanningModel.filterByScm();

    }

    @FXML
    void planBoxOnSelection() {
        this.ticketPlanningModel.getTicketFXObjectProperty().setPlannerFXProperty(this.planComboBox.getSelectionModel().getSelectedItem());
    }

    @FXML
    void purBoxOnSelection() {
        this.ticketPlanningModel.getTicketFXObjectProperty().setBuyerFXProperty(this.purComboBox.getSelectionModel().getSelectedItem());

    }

    @FXML
    void statusBoxOnSelection() {
        this.ticketPlanningModel.getTicketFXObjectProperty().setStatusProperty(this.statusComboBox.getSelectionModel().getSelectedItem());

    }
    public Button createButton(String path) {
        Button button = new Button();
        Image image = new Image(this.getClass().getResource(path).toString());
        ImageView imageView = new ImageView(image);
        button.setGraphic(imageView);
        return button;

    }
    private void checkUpFields() {
        this.addTicketButton.disableProperty().bind(chooseScmAuthorBox.valueProperty().isNull()
                .or(materialNameField.textProperty().isEmpty()
                        .or(materialDescField.textProperty().isEmpty()
                                .or(projectNameField.textProperty().isEmpty()
                                        .or(notesField.textProperty().isEmpty()
                                                .or(planComboBox.valueProperty().isNull().
                                                        or(purComboBox.valueProperty().isNull()
                                                                .or(statusComboBox.valueProperty().isNull()))))))));
        this.searchButton.disableProperty().bind(materialNameField.textProperty().isEmpty());
        this.exportButton.disableProperty().bind(filterBySCMComboBox.valueProperty().isNull());
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
                this.planComboBox.setValue(PersonConverter.convertToPersonFX(ticket.getPlanner()));
                this.purComboBox.setValue(PersonConverter.convertToPersonFX(ticket.getScmer()));
            }
        } catch (ApplicationException e) {
            Dialogs.alertMessage(e.getMessage());
        }

    }

    public void export() {
        try {
            //excelUtils.exportToExcel(this.ticketPlanningModel.getTicketFXObservableListSCM());
            ExcelUtilsPOI excelUtilsPOI = new ExcelUtilsPOI();
            excelUtilsPOI.exportToExcel(this.ticketPlanningModel.getTicketFXObservableListSCM());
        } catch (IOException e) {
            Dialogs.alertMessage(e.getMessage());
        }

    }

}
