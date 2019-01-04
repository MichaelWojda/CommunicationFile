package mainDirectory.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mainDirectory.dialogs.Dialogs;
import mainDirectory.modelFX.PersonFX;
import mainDirectory.modelFX.PersonModel;
import mainDirectory.modelFX.StatusFX;
import mainDirectory.modelFX.StatusModel;
import mainDirectory.utils.Exceptions.ApplicationException;
import mainDirectory.utils.fxmlUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class AdminWindowController {



    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private ComboBox<String> deptComboBox;

    @FXML
    private Button addPersonButton;

    @FXML
    private TableView<PersonFX> personTableView;

    @FXML
    private TableColumn<PersonFX, String> nameColumn;

    @FXML
    private TableColumn<PersonFX, String> surnameColumn;

    @FXML
    private TableColumn<PersonFX, String> deptColumn;

    @FXML
    private TableColumn<PersonFX, PersonFX> editColumn;

    @FXML
    private TableColumn<PersonFX, PersonFX> deleteColumn;
    @FXML
    private TextField statusNameField;

    @FXML
    private Button addStatusButton;

    @FXML
    private TableView<StatusFX> statusTableView;

    @FXML
    private TableColumn<StatusFX, String> statusNameColumn;

    @FXML
    private TableColumn<StatusFX, StatusFX> statusEditColumn;

    @FXML
    private TableColumn<StatusFX, StatusFX> statusDeleteColumn;

    @FXML
    private TableColumn<StatusFX,String> statusDeptColumn;

    @FXML
    private ComboBox<String> statusDeptComboBox;


    private PersonModel personModel;
    private StatusModel statusModel;


    @FXML
    public void initialize() {
        personModel = new PersonModel();
        statusModel = new StatusModel();
        try {
            statusModel.innit();
            personModel.innit();
        } catch (ApplicationException e) {
            Dialogs.alertMessage(e.getMessage());
        }
        initComboBox();
        checkFields();
        personBindings();

        this.statusNameField.textProperty().bindBidirectional(statusModel.getStatusFXObjectProperty().nameFXProperty());
        this.statusDeptComboBox.getItems().setAll(initComboBox());
        this.statusTableView.setItems(this.statusModel.getStatusFXObservableList());
        this.statusNameColumn.setCellValueFactory(c -> c.getValue().nameFXProperty());
        this.statusDeptColumn.setCellValueFactory(c->c.getValue().departamentFXProperty());
        this.statusDeleteColumn.setCellValueFactory(c-> new SimpleObjectProperty<>(c.getValue()));
        this.statusDeleteColumn.setCellFactory(c->new TableCell<StatusFX,StatusFX>(){
            Button button = createButton("/icons/delete.png");

            @Override
            protected void updateItem(StatusFX item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setGraphic(null);
                }
                if (!empty){
                    setGraphic(button);
                    button.setOnAction(event->{
                        Optional<ButtonType> result = Dialogs.confirmDelete();
                        if(result.get()==ButtonType.OK){
                            try {
                                statusModel.deleteStatusFX(item);
                            } catch (ApplicationException e) {
                                Dialogs.alertMessage(e.getMessage());
                            }
                        }
                    });
                }
            }
        });
        this.statusEditColumn.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue()));
        this.statusEditColumn.setCellFactory(c->new TableCell<StatusFX, StatusFX>(){
            Button button = createButton("/icons/edit.png");

            @Override
            protected void updateItem(StatusFX item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setGraphic(null);
                }
                if(!empty){
                    setGraphic(button);
                    button.setOnAction(event->{
                      FXMLLoader fxmlLoader = fxmlUtils.returnLoader("/fxml/EditStatusWindow.fxml");
                      Scene scene = null;
                        try {
                            scene = new Scene(fxmlLoader.load());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        EditStatusWindowController editStatusWindowController = fxmlLoader.getController();
                      editStatusWindowController.statusModel.setStatusFXObjectProperty(item);
                      editStatusWindowController.bindings();
                      Stage stage = new Stage();
                      stage.setScene(scene);
                      stage.setTitle("Edycja");
                      stage.initModality(Modality.APPLICATION_MODAL);
                      stage.showAndWait();

                    });
                }
            }
        });
        personTableViewCreation();


    }

    private void personBindings() {
        this.nameField.textProperty().bindBidirectional(personModel.getPersonFXSimpleObjectProperty().nameProperty());
        this.surnameField.textProperty().bindBidirectional(personModel.getPersonFXSimpleObjectProperty().surnameProperty());
        this.deptComboBox.valueProperty().bindBidirectional(personModel.getPersonFXSimpleObjectProperty().departamentProperty());
        this.deptComboBox.getItems().setAll(initComboBox());
    }

    private void personTableViewCreation() {
        this.personTableView.setItems(this.personModel.getPersonFXObservableList());
        this.nameColumn.setCellValueFactory(c -> c.getValue().nameProperty());
        this.surnameColumn.setCellValueFactory(c -> c.getValue().surnameProperty());
        this.deptColumn.setCellValueFactory(c -> c.getValue().departamentProperty());
        this.deleteColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue()));
        this.deleteColumn.setCellFactory(c -> new TableCell<PersonFX, PersonFX>() {
            Button button = createButton("/icons/deletePerson.png");

            @Override
            protected void updateItem(PersonFX item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                }
                if (!empty) {
                    setGraphic(button);
                    button.setOnAction(event -> {
                        Optional<ButtonType> result = Dialogs.confirmDelete();
                        if (result.get() == ButtonType.OK) {
                            try {
                                personModel.deletePersonFX(item);
                            } catch (ApplicationException e) {
                                Dialogs.alertMessage(e.getMessage());
                            }
                        }
                    });
                }
            }
        });

        this.editColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue()));
        this.editColumn.setCellFactory(c -> new TableCell<PersonFX, PersonFX>() {
            Button button = createButton("/icons/edit.png");

            @Override
            protected void updateItem(PersonFX item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                }
                if (!empty) {
                    setGraphic(button);
                    button.setOnAction(event -> {
                        FXMLLoader loader = fxmlUtils.returnLoader("/fxml/EditPersonWindow.fxml");

                        Scene scene = null;
                        try {
                            scene = new Scene(loader.load());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        EditPersonWindowController editPersonWindowController = loader.getController();
                        editPersonWindowController.getPersonModel().setPersonFXSimpleObjectProperty(item);
                        editPersonWindowController.bindings();
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("Edycja");
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();

                    });
                }
            }
        });
    }

    private void checkFields() {
        this.addStatusButton.disableProperty().bind(this.statusNameField.textProperty().isEmpty().or(this.statusDeptComboBox.valueProperty().isNull()));
        this.addPersonButton.disableProperty().bind(this.nameField.textProperty().isEmpty().or(this.surnameField.textProperty().isEmpty().or(this.deptComboBox.valueProperty().isNull())));
    }

    public static ObservableList<String> initComboBox() {
        ArrayList<String> listOfDepts = new ArrayList<>();
        listOfDepts.add("Zaopatrzenie");
        listOfDepts.add("SCM");
        listOfDepts.add("Planowanie");
        return FXCollections.observableArrayList(listOfDepts);

    }


    @FXML
    void addPersonOnClick() {
        try {
            this.personModel.savePersonInDB();
            this.nameField.clear();
            this.surnameField.clear();
            this.deptComboBox.setValue(null);
        } catch (ApplicationException e) {
            Dialogs.alertMessage(e.getMessage());
        }


    }

    public void onSelection() {
        this.personModel.getPersonFXSimpleObjectProperty().setDepartament(this.deptComboBox.getSelectionModel().getSelectedItem());
    }

    public Button createButton(String path) {
        Button button = new Button();
        Image image = new Image(this.getClass().getResource(path).toString());
        ImageView imageView = new ImageView(image);
        button.setGraphic(imageView);
        return button;


    }

    @FXML
    void addStatusOnClick() {
        try {
            this.statusModel.saveStatusInDB();
            this.statusNameField.clear();
            this.statusDeptComboBox.setValue(null);
        } catch (ApplicationException e) {
            Dialogs.alertMessage(e.getMessage());
        }
    }

    public void onSelectionStatusDept() {
        this.statusModel.getStatusFXObjectProperty().setDepartamentFX(this.statusDeptComboBox.getSelectionModel().getSelectedItem());
    }
}
