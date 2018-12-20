package mainDirectory.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import mainDirectory.dialogs.Dialogs;

public class TopMenuButtonsController {

    public static final String ADMIN_WINDOW_FXML = "/fxml/AdminWindow.fxml";
    public static final String PURCHASING_WINDOW_FXML = "/fxml/PurchasingWindow.fxml";
    public static final String PLANNING_WINDOW_FXML = "/fxml/PlanningWindow.fxml";
    public static final String SCM_WINDOW_FXML = "/fxml/ScmWindow.fxml";
    public static final String PASSWORD = "AT05";
    @FXML
    private ToggleGroup toggleGroup1;


    private MainWindowController mainWindowController;

    @FXML
    void enterAdmin() throws NullPointerException {
        String result = Dialogs.passwdCheckDialog();
        if(result==null){
            Dialogs.alertMessage("Wprowadziłeś błędne hasło");
        }
        else if(result.equals(PASSWORD))
        {
        mainWindowController.setCenter(ADMIN_WINDOW_FXML);}
        else{
            Dialogs.alertMessage("Wprowadziłeś błędne hasło");
        }


    }

    @FXML
    void enterPUR() {
        mainWindowController.setCenter(PURCHASING_WINDOW_FXML);
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
}
