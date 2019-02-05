package mainDirectory.modelFX;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class TicketFX {

    SimpleIntegerProperty idProperty = new SimpleIntegerProperty();
    SimpleStringProperty materialNameProperty = new SimpleStringProperty();
    SimpleStringProperty materialDescriptionProperty = new SimpleStringProperty();
    SimpleStringProperty notesProperty = new SimpleStringProperty();
    SimpleStringProperty projectProperty = new SimpleStringProperty();
    SimpleObjectProperty<PersonFX> authorFXProperty = new SimpleObjectProperty<>();
    SimpleObjectProperty<PersonFX> plannerFXProperty = new SimpleObjectProperty<>();
    SimpleObjectProperty<PersonFX> scmerFXProperty = new SimpleObjectProperty<>();
    SimpleObjectProperty<PersonFX> buyerFXProperty = new SimpleObjectProperty<>();
    SimpleObjectProperty<StatusFX> statusProperty = new SimpleObjectProperty<>();
    SimpleBooleanProperty activeProperty = new SimpleBooleanProperty();


    public int getIdProperty() {
        return idProperty.get();
    }

    public SimpleIntegerProperty idPropertyProperty() {
        return idProperty;
    }

    public void setIdProperty(int idProperty) {
        this.idProperty.set(idProperty);
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

    public StatusFX getStatusProperty() {
        return statusProperty.get();
    }

    public SimpleObjectProperty<StatusFX> statusPropertyProperty() {
        return statusProperty;
    }

    public void setStatusProperty(StatusFX statusProperty) {
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

    public PersonFX getAuthorFXProperty() {
        return authorFXProperty.get();
    }

    public SimpleObjectProperty<PersonFX> authorFXPropertyProperty() {
        return authorFXProperty;
    }

    public void setAuthorFXProperty(PersonFX authorFXProperty) {
        this.authorFXProperty.set(authorFXProperty);
    }

    public PersonFX getPlannerFXProperty() {
        return plannerFXProperty.get();
    }

    public SimpleObjectProperty<PersonFX> plannerFXPropertyProperty() {
        return plannerFXProperty;
    }

    public void setPlannerFXProperty(PersonFX plannerFXProperty) {
        this.plannerFXProperty.set(plannerFXProperty);
    }

    public PersonFX getScmerFXProperty() {
        return scmerFXProperty.get();
    }

    public SimpleObjectProperty<PersonFX> scmerFXPropertyProperty() {
        return scmerFXProperty;
    }

    public void setScmerFXProperty(PersonFX scmerFXProperty) {
        this.scmerFXProperty.set(scmerFXProperty);
    }

    public PersonFX getBuyerFXProperty() {
        return buyerFXProperty.get();
    }

    public SimpleObjectProperty<PersonFX> buyerFXPropertyProperty() {
        return buyerFXProperty;
    }

    public void setBuyerFXProperty(PersonFX buyerFXProperty) {
        this.buyerFXProperty.set(buyerFXProperty);
    }

    public boolean getActiveProperty() {
        return activeProperty.get();
    }

    public SimpleBooleanProperty activePropertyProperty() {
        return activeProperty;
    }

    public void setActiveProperty(boolean activeProperty) {
        this.activeProperty.set(activeProperty);
    }


}
