package mainDirectory.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import mainDirectory.dialogs.Dialogs;
import mainDirectory.modelFX.PersonModel;
import mainDirectory.utils.Exceptions.ApplicationException;

import java.io.IOException;

public class EditPersonWindowController {

    @FXML
    private TextField editNameField;

    @FXML
    private TextField editSurnameField;

    @FXML
    private ComboBox<String> editDeptComboBox;

    @FXML
    private Button editSaveButton;

    PersonModel personModel;

    @FXML
    public void initialize(){
        personModel = new PersonModel();
        bindings();
        this.editDeptComboBox.setItems(AdminWindowController.initComboBox());

    }

    public void bindings() {
        this.editNameField.textProperty().bindBidirectional(this.personModel.getPersonFXSimpleObjectProperty().nameProperty());
        this.editSurnameField.textProperty().bindBidirectional(this.personModel.getPersonFXSimpleObjectProperty().surnameProperty());
        this.editDeptComboBox.valueProperty().bindBidirectional(this.personModel.getPersonFXSimpleObjectProperty().departamentProperty());
        this.editSaveButton.disableProperty().bind(this.editNameField.textProperty().isEmpty().or(this.editSurnameField.textProperty().isEmpty().or(this.editDeptComboBox.valueProperty().isNull())));
    }

    @FXML
    void onSelectionEditComboBox() {
        this.personModel.getPersonFXSimpleObjectProperty().setDepartament(this.editDeptComboBox.getSelectionModel().getSelectedItem());
    }
    @FXML
    void saveEdit() {
        try {
            this.personModel.savePersonInDB();
        } catch (ApplicationException e) {
            Dialogs.alertMessage(e.getMessage());
        }
        Stage stage = (Stage)this.editSaveButton.getScene().getWindow();
        stage.close();

    }

    public TextField getEditNameField() {
        return editNameField;
    }

    public void setEditNameField(TextField editNameField) {
        this.editNameField = editNameField;
    }

    public TextField getEditSurnameField() {
        return editSurnameField;
    }

    public void setEditSurnameField(TextField editSurnameField) {
        this.editSurnameField = editSurnameField;
    }

    public ComboBox<String> getEditDeptComboBox() {
        return editDeptComboBox;
    }

    public void setEditDeptComboBox(ComboBox<String> editDeptComboBox) {
        this.editDeptComboBox = editDeptComboBox;
    }

    public PersonModel getPersonModel() {
        return personModel;
    }

    public void setPersonModel(PersonModel personModel) {
        this.personModel = personModel;
    }
}