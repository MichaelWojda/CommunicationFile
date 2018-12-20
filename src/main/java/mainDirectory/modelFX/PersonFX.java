package mainDirectory.modelFX;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class PersonFX {

    SimpleIntegerProperty id = new SimpleIntegerProperty();
    SimpleStringProperty name = new SimpleStringProperty();
    SimpleStringProperty surname = new SimpleStringProperty();
    SimpleStringProperty departament = new SimpleStringProperty();


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

    public String getDepartament() {
        return departament.get();
    }

    public SimpleStringProperty departamentProperty() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament.set(departament);
    }

    @Override
    public String toString() {
        return name.getValue() +" "+ surname.getValue();
    }
}
