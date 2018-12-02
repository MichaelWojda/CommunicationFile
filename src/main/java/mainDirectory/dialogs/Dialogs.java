package mainDirectory.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class Dialogs {

    public static String passwdCheckDialog(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Hasło");
        dialog.setHeaderText("Wprowadz haslo");
        Optional<String> result = dialog.showAndWait();
        if(result!=null){
            return result.get();
        }
        return null;
    }

    public static void wrongPasswd() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText("Błędne hasło");
        alert.setContentText("Wprowadzono błędne hasło");
        alert.showAndWait();
    }
}
