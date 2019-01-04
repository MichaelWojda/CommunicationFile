package mainDirectory.modelFX;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StatusFX {

    SimpleIntegerProperty id = new SimpleIntegerProperty();
    SimpleStringProperty nameFX = new SimpleStringProperty();
    SimpleStringProperty departamentFX = new SimpleStringProperty();

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

    public String getDepartamentFX() {
        return departamentFX.get();
    }

    public SimpleStringProperty departamentFXProperty() {
        return departamentFX;
    }

    public void setDepartamentFX(String departamentFX) {
        this.departamentFX.set(departamentFX);
    }

    @Override
    public String toString() {
        return nameFX.getValue();
    }
}
