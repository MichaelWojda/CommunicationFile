package mainDirectory.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import mainDirectory.dialogs.Dialogs;

public class LoginWindowController {

    @FXML
    private PasswordField passField;
    public static final String PASSWORD = "AT05";

    private MainWindowController mainWindowController;



    @FXML
    private Button loginButton;




    @FXML
    void loginButtonOnClick() {
        if(passField.textProperty().getValue().equals(PASSWORD)){

            mainWindowController.setCenter("/fxml/AdminWindow.fxml");
            Stage stage =(Stage) loginButton.getScene().getWindow();
            stage.close();

        }
        else{
            Dialogs.alertMessage("Wprowadzono błędne hasło");
        }

    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }
}
