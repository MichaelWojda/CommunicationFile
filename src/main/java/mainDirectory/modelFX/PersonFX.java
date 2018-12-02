package mainDirectory.modelFX;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class PersonFX {

    SimpleIntegerProperty id = new SimpleIntegerProperty();
    SimpleStringProperty name = new SimpleStringProperty();
    SimpleStringProperty surname = new SimpleStringProperty();
    SimpleObjectProperty<DepartamentFX> departamentFXProperty = new SimpleObjectProperty<>();

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public DepartamentFX getDepartamentFXProperty() {
        return departamentFXProperty.get();
    }

    public SimpleObjectProperty<DepartamentFX> departamentFXPropertyProperty() {
        return departamentFXProperty;
    }

    public void setDepartamentFXProperty(DepartamentFX departamentFXProperty) {
        this.departamentFXProperty.set(departamentFXProperty);
    }
}
