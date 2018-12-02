package mainDirectory.modelFX;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PersonModel {
    ObjectProperty<PersonFX> personFXSimpleObjectProperty = new SimpleObjectProperty<>(new PersonFX());
    ObservableList<PersonFX> personFXObservableList = FXCollections.observableArrayList();
}
