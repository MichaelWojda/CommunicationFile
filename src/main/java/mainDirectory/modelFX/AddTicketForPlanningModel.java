package mainDirectory.modelFX;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mainDirectory.Converters.PersonConverter;
import mainDirectory.database.dao.PersonDao;
import mainDirectory.database.dbutils.dbManager;
import mainDirectory.database.model.Person;
import mainDirectory.dialogs.Dialogs;
import mainDirectory.utils.Exceptions.ApplicationException;

import java.util.List;

public class AddTicketForPlanningModel {


    ObjectProperty<TicketFX> ticketFXObjectProperty = new SimpleObjectProperty<>( new TicketFX());
    ObjectProperty<PersonFX> authorFXObjectProperty = new SimpleObjectProperty<>( new PersonFX());
    ObjectProperty<PersonFX> purFXObjectProperty = new SimpleObjectProperty<>( new PersonFX());
    ObjectProperty<PersonFX> scmFXObjectProperty = new SimpleObjectProperty<>( new PersonFX());
    ObservableList<PersonFX> planningFXList = FXCollections.observableArrayList();
    ObservableList<PersonFX> purFXList = FXCollections.observableArrayList();
    ObservableList<PersonFX> scMFXList = FXCollections.observableArrayList();



    public void innit() {
        PersonDao personDao = new PersonDao();
        List<Person> plannningList = null;
        try {
            plannningList = personDao.queryForDept(Person.class, "Planowanie");
        } catch (ApplicationException e) {
            Dialogs.alertMessage(e.getMessage());
        }
        plannningList.forEach(p->{
            PersonFX plannerFX =new PersonFX();
            plannerFX= PersonConverter.convertToPersonFX(p);
            planningFXList.add(plannerFX);
        });
        List<Person> purList = null;
        try {
            purList = personDao.queryForDept(Person.class, "Zaopatrzenie");
        } catch (ApplicationException e) {
            Dialogs.alertMessage(e.getMessage());
        }
        purList.forEach(p->{
            PersonFX purFX =new PersonFX();
            purFX= PersonConverter.convertToPersonFX(p);
            purFXList.add(purFX);
        });
        List<Person> scmList = null;
        try {
            scmList = personDao.queryForDept(Person.class, "SCM");
        } catch (ApplicationException e) {
            Dialogs.alertMessage(e.getMessage());
        }
        scmList.forEach(p->{
            PersonFX scmFx =new PersonFX();
            scmFx = PersonConverter.convertToPersonFX(p);
            scMFXList.add(scmFx);
        });
        try {
            dbManager.closeConnection();
        } catch (ApplicationException e) {
            Dialogs.alertMessage(e.getMessage());
        }

    }

    public TicketFX getTicketFXObjectProperty() {
        return ticketFXObjectProperty.get();
    }

    public ObjectProperty<TicketFX> ticketFXObjectPropertyProperty() {
        return ticketFXObjectProperty;
    }

    public void setTicketFXObjectProperty(TicketFX ticketFXObjectProperty) {
        this.ticketFXObjectProperty.set(ticketFXObjectProperty);
    }

    public ObservableList<PersonFX> getPlanningFXList() {
        return planningFXList;
    }

    public void setPlanningFXList(ObservableList<PersonFX> planningFXList) {
        this.planningFXList = planningFXList;
    }

    public ObservableList<PersonFX> getPurFXList() {
        return purFXList;
    }

    public void setPurFXList(ObservableList<PersonFX> purFXList) {
        this.purFXList = purFXList;
    }

    public ObservableList<PersonFX> getScMFXList() {
        return scMFXList;
    }

    public void setScMFXList(ObservableList<PersonFX> scMFXList) {
        this.scMFXList = scMFXList;
    }

    public PersonFX getAuthorFXObjectProperty() {
        return authorFXObjectProperty.get();
    }

    public ObjectProperty<PersonFX> authorFXObjectPropertyProperty() {
        return authorFXObjectProperty;
    }

    public void setAuthorFXObjectProperty(PersonFX authorFXObjectProperty) {
        this.authorFXObjectProperty.set(authorFXObjectProperty);
    }

    public PersonFX getPurFXObjectProperty() {
        return purFXObjectProperty.get();
    }

    public ObjectProperty<PersonFX> purFXObjectPropertyProperty() {
        return purFXObjectProperty;
    }

    public void setPurFXObjectProperty(PersonFX purFXObjectProperty) {
        this.purFXObjectProperty.set(purFXObjectProperty);
    }

    public PersonFX getScmFXObjectProperty() {
        return scmFXObjectProperty.get();
    }

    public ObjectProperty<PersonFX> scmFXObjectPropertyProperty() {
        return scmFXObjectProperty;
    }

    public void setScmFXObjectProperty(PersonFX scmFXObjectProperty) {
        this.scmFXObjectProperty.set(scmFXObjectProperty);
    }
}
