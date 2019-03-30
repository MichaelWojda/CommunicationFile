package mainDirectory.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class Dialogs {

    public static String passwdCheckDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Hasło");
        dialog.setHeaderText("Wprowadz haslo");
        Optional<String> result = dialog.showAndWait();
        if (result != null) {
            return result.get();
        }
        return null;
    }

    public static void alertMessage(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText("Wystąpił błąd");
        alert.setContentText(error);
        alert.showAndWait();
    }

    public static Optional<ButtonType> confirmDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Uwaga");
        alert.setHeaderText("UWAGA!!!");
        alert.setContentText("Usunięcie rekordu spowoduje nieodwaracalne zmiany w systemie, stosuj jedynie w ostateczności, czy napewno chcesz usunąć");
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }

    public static String editDialog(String value) {
        TextInputDialog dialog = new TextInputDialog(value);
        dialog.setTitle("Edycja");
        dialog.setHeaderText("Edycja nazwy");
        dialog.setContentText("Wprowadź nową nazwę");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        }
        return null;

    }

    public static Optional<ButtonType> confirmFinished(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Uwaga");
        alert.setHeaderText("Uwaga");
        alert.setContentText("Czy napewno chcesz zamknąć ticket?");
        return alert.showAndWait();
    }

    public static void informationDialog(String error) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText("Informacja");
        alert.setContentText(error);
        alert.showAndWait();
    }
}