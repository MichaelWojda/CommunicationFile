package mainDirectory.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private PersonModel personModel;



    @FXML
    public void initialize() {
        personModel = new PersonModel();
        personModel.innit();
        initComboBox();
        checkFields();

        this.nameField.textProperty().bindBidirectional(personModel.getPersonFXSimpleObjectProperty().nameProperty());
        this.surnameField.textProperty().bindBidirectional(personModel.getPersonFXSimpleObjectProperty().surnameProperty());
        this.deptComboBox.getItems().setAll(initComboBox());
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
                            personModel.deletePersonFX(item);
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
                        FXMLLoader loader = fxmlUtils.returnLoader("/fxml/EditWindow.fxml");

                        Scene scene = null;
                        try {
                            scene = new Scene(loader.load());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        EditWindowController editWindowController = loader.getController();
                        editWindowController.getPersonModel().setPersonFXSimpleObjectProperty(item);
                        editWindowController.bindings();
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
        this.personModel.savePersonInDB();


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

}
