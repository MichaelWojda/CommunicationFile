package mainDirectory.modelFX;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class TicketFX {

    SimpleIntegerProperty idProperty = new SimpleIntegerProperty();
    SimpleIntegerProperty id_ticketProperty = new SimpleIntegerProperty();
    SimpleStringProperty materialNameProperty = new SimpleStringProperty();
    SimpleStringProperty materialDescriptionProperty = new SimpleStringProperty();
    SimpleStringProperty statusProperty = new SimpleStringProperty();
    SimpleStringProperty notesProperty = new SimpleStringProperty();
    SimpleStringProperty projectProperty = new SimpleStringProperty();
    SimpleStringProperty departamentProperty = new SimpleStringProperty();
    SimpleStringProperty autorProperty = new SimpleStringProperty();
    SimpleObjectProperty<PersonFX> personFXProperty = new SimpleObjectProperty<>();


    public int getIdProperty() {
        return idProperty.get();
    }

    public SimpleIntegerProperty idPropertyProperty() {
        return idProperty;
    }

    public void setIdProperty(int idProperty) {
        this.idProperty.set(idProperty);
    }

    public int getId_ticketProperty() {
        return id_ticketProperty.get();
    }

    public SimpleIntegerProperty id_ticketPropertyProperty() {
        return id_ticketProperty;
    }

    public void setId_ticketProperty(int id_ticketProperty) {
        this.id_ticketProperty.set(id_ticketProperty);
    }

    public String getMaterialNameProperty() {
        return materialNameProperty.get();
    }

    public SimpleStringProperty materialNamePropertyProperty() {
        return materialNameProperty;
    }

    public void setMaterialNameProperty(String materialNameProperty) {
        this.materialNameProperty.set(materialNameProperty);
    }

    public String getMaterialDescriptionProperty() {
        return materialDescriptionProperty.get();
    }

    public SimpleStringProperty materialDescriptionPropertyProperty() {
        return materialDescriptionProperty;
    }

    public void setMaterialDescriptionProperty(String materialDescriptionProperty) {
        this.materialDescriptionProperty.set(materialDescriptionProperty);
    }

    public String getStatusProperty() {
        return statusProperty.get();
    }

    public SimpleStringProperty statusPropertyProperty() {
        return statusProperty;
    }

    public void setStatusProperty(String statusProperty) {
        this.statusProperty.set(statusProperty);
    }

    public String getNotesProperty() {
        return notesProperty.get();
    }

    public SimpleStringProperty notesPropertyProperty() {
        return notesProperty;
    }

    public void setNotesProperty(String notesProperty) {
        this.notesProperty.set(notesProperty);
    }

    public String getProjectProperty() {
        return projectProperty.get();
    }

    public SimpleStringProperty projectPropertyProperty() {
        return projectProperty;
    }

    public void setProjectProperty(String projectProperty) {
        this.projectProperty.set(projectProperty);
    }

    public String getDepartamentProperty() {
        return departamentProperty.get();
    }

    public SimpleStringProperty departamentPropertyProperty() {
        return departamentProperty;
    }

    public void setDepartamentProperty(String departamentProperty) {
        this.departamentProperty.set(departamentProperty);
    }

    public String getAutorProperty() {
        return autorProperty.get();
    }

    public SimpleStringProperty autorPropertyProperty() {
        return autorProperty;
    }

    public void setAutorProperty(String autorProperty) {
        this.autorProperty.set(autorProperty);
    }

    public PersonFX getPersonFXProperty() {
        return personFXProperty.get();
    }

    public SimpleObjectProperty<PersonFX> personFXPropertyProperty() {
        return personFXProperty;
    }

    public void setPersonFXProperty(PersonFX personFXProperty) {
        this.personFXProperty.set(personFXProperty);
    }
}
