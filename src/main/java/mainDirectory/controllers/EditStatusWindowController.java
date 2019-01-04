package mainDirectory.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainDirectory.dialogs.Dialogs;
import mainDirectory.modelFX.PersonModel;
import mainDirectory.modelFX.StatusModel;
import mainDirectory.utils.Exceptions.ApplicationException;

public class EditStatusWindowController {

    @FXML
    private Button editSaveButton;

    @FXML
    private TextField editStatusNameField;

    @FXML
    private ComboBox<String> editDeptComboBox;

    StatusModel statusModel;

    @FXML
    public void initialize(){
        statusModel = new StatusModel();
        bindings();
        this.editDeptComboBox.setItems(AdminWindowController.initComboBox());
    }

    public void bindings() {
        this.editStatusNameField.textProperty().bindBidirectional(this.statusModel.getStatusFXObjectProperty().nameFXProperty());
        this.editDeptComboBox.valueProperty().bindBidirectional(this.statusModel.getStatusFXObjectProperty().departamentFXProperty());
        this.editSaveButton.disableProperty().bind(this.editStatusNameField.textProperty().isEmpty().or(this.editDeptComboBox.valueProperty().isNull()));
    }

    @FXML
    void onSelectionEditComboBox() {
        this.statusModel.getStatusFXObjectProperty().setDepartamentFX(this.editDeptComboBox.getSelectionModel().getSelectedItem());
    }

    @FXML
    void saveEdit() {
        try {
            this.statusModel.saveStatusInDB();
        } catch (ApplicationException e) {
            Dialogs.alertMessage(e.getMessage());
        }
        Stage stage = (Stage)this.editSaveButton.getScene().getWindow();
        stage.close();

    }

}