package mainDirectory.modelFX;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mainDirectory.Converters.PersonConverter;
import mainDirectory.database.dao.PersonDao;
import mainDirectory.database.dbutils.dbManager;
import mainDirectory.database.model.Person;

import java.io.IOException;
import java.util.List;

public class AddTicketForPlanningModel {


    ObjectProperty<TicketFX> ticketFXObjectProperty = new SimpleObjectProperty<>( new TicketFX());
    ObservableList<PersonFX> planningFXList = FXCollections.observableArrayList();
    ObservableList<PersonFX> purFXList = FXCollections.observableArrayList();
    ObservableList<PersonFX> scMFXList = FXCollections.observableArrayList();

    public void innit() throws IOException {
        PersonDao personDao = new PersonDao();
        List<Person> plannningList = personDao.queryForDept(Person.class, "Planowanie");
        plannningList.forEach(p->{
            PersonFX plannerFX =new PersonFX();
            plannerFX= PersonConverter.convertToPersonFX(p);
            planningFXList.add(plannerFX);
        });
        List<Person> purList = personDao.queryForDept(Person.class, "Zaopatrzenie");
        purList.forEach(p->{
            PersonFX purFX =new PersonFX();
            purFX= PersonConverter.convertToPersonFX(p);
            purFXList.add(purFX);
        });
        List<Person> scmList = personDao.queryForDept(Person.class, "SCM");
        scmList.forEach(p->{
            PersonFX scmFx =new PersonFX();
            scmFx = PersonConverter.convertToPersonFX(p);
            scMFXList.add(scmFx);
        });
        dbManager.closeConnection();

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
}
