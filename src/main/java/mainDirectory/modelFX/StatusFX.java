package mainDirectory.modelFX;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StatusFX {

    SimpleIntegerProperty id = new SimpleIntegerProperty();
    SimpleStringProperty nameFX = new SimpleStringProperty();

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getNameFX() {
        return nameFX.get();
    }

    public SimpleStringProperty nameFXProperty() {
        return nameFX;
    }

    public void setNameFX(String nameFX) {
        this.nameFX.set(nameFX);
    }
}
