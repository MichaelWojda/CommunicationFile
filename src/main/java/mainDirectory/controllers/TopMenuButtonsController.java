package mainDirectory.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mainDirectory.dialogs.Dialogs;
import mainDirectory.utils.Exceptions.ApplicationException;
import mainDirectory.utils.fxmlUtils;

import java.io.IOException;

public class TopMenuButtonsController {

    public static final String ADMIN_WINDOW_FXML = "/fxml/AdminWindow.fxml";
    public static final String PURCHASING_WINDOW_FXML = "/fxml/PurchasingWindow.fxml";
    public static final String PLANNING_WINDOW_FXML = "/fxml/PlanningWindow.fxml";
    public static final String SCM_WINDOW_FXML = "/fxml/ScmWindow.fxml";

    @FXML
    private ToggleGroup toggleGroup1;


    private MainWindowController mainWindowController;







    @FXML
    void enterAdmin() {
        FXMLLoader loader = fxmlUtils.returnLoader("/fxml/LoginWindow.fxml");
        Scene scene=null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Wprowadź hasło");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        LoginWindowController loginWindowController = loader.getController();
        loginWindowController.setMainWindowController(mainWindowController);

    }


    @FXML
    void enterPUR() {
        mainWindowController.setCenter(PURCHASING_WINDOW_FXML);
    }


    public void login() {
        mainWindowController.setCenter(ADMIN_WINDOW_FXML);
    }

    @FXML
    void enterPlanning() {
        mainWindowController.setCenter(PLANNING_WINDOW_FXML);
    }

    @FXML
    void enterSCM() {
        mainWindowController.setCenter(SCM_WINDOW_FXML);
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }


}
