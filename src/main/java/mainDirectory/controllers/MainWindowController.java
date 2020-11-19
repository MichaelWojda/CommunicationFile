package mainDirectory.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainWindowController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private TopMenuButtonsController topMenuButtonsController;








    @FXML
    private void initialize(){
        topMenuButtonsController.setMainWindowController(this);


    }

    public void setCenter(String path){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(path));
        Parent parent=null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mainBorderPane.setCenter(parent);
    }


}