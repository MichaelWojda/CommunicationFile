package mainDirectory.modelFX;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TicketHistoryModel {

    ObservableList<TicketFX_History> ticketFX_history = FXCollections.observableArrayList();
    SimpleObjectProperty<TicketFX_History> ticketHistoryObject = new SimpleObjectProperty<>();

}
