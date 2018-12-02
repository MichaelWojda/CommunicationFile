package mainDirectory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import mainDirectory.database.dbutils.dbManager;

public class Main extends Application {

    public static final String MAIN_WINDOW_FXML = "/fxml/MainWindow.fxml";

    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(MAIN_WINDOW_FXML));
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Plik komunikacji");
        primaryStage.show();
        dbManager.innitDB();

    }
}
