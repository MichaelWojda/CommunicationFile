package mainDirectory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import mainDirectory.database.dbutils.dbManager;
import mainDirectory.utils.fxmlUtils;

public class Main extends Application {

    public static final String MAIN_WINDOW_FXML = "/fxml/MainWindow.fxml";

    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(fxmlUtils.loadFXML(MAIN_WINDOW_FXML));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Plik komunikacji");
        primaryStage.show();
        dbManager.innitDB();

    }
}
