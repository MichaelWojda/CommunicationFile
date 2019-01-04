package mainDirectory.modelFX;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mainDirectory.Converters.PersonConverter;
import mainDirectory.Converters.StatusConverter;
import mainDirectory.Converters.TicketConverter;
import mainDirectory.database.dao.PersonDao;
import mainDirectory.database.dao.StatusDao;
import mainDirectory.database.dao.TicketDao;
import mainDirectory.database.dbutils.dbManager;
import mainDirectory.database.model.Person;
import mainDirectory.database.model.Status;
import mainDirectory.database.model.Ticket;
import mainDirectory.dialogs.Dialogs;
import mainDirectory.utils.Exceptions.ApplicationException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TicketPlanningModel {


    ObjectProperty<TicketFX> ticketFXObjectProperty = new SimpleObjectProperty<>(new TicketFX());
    ObjectProperty<PersonFX> PlannerFX = new SimpleObjectProperty<>(new PersonFX());
    ObjectProperty<PersonFX> authorFX = new SimpleObjectProperty<>(new PersonFX());
    ObservableList<PersonFX> planningFXList = FXCollections.observableArrayList();
    ObservableList<StatusFX> statusFXObservableList = FXCollections.observableArrayList();
    ObservableList<TicketFX> ticketFXObservableList = FXCollections.observableArrayList();
    ObservableList<TicketFX> ticketFXObservableListForAuthor = FXCollections.observableArrayList();
    ObservableList<PersonFX> purFXList = FXCollections.observableArrayList();
    ObservableList<PersonFX> scMFXList = FXCollections.observableArrayList();
    private List<TicketFX> ticketFXRandomList = new ArrayList<>();
    private List<TicketFX> ticketFXRandomListForAuthor = new ArrayList<>();

    public void innit() throws ApplicationException {
        innitStaticLists();
        innitTicketLists();

        dbManager.closeConnection();
    }

    private void innitTicketLists() throws ApplicationException {
        ticketFXRandomList.clear();
        ticketFXObservableList.clear();
        ticketFXRandomListForAuthor.clear();
        ticketFXObservableListForAuthor.clear();
        TicketDao ticketDao = new TicketDao();
        List<Ticket> list = ticketDao.queryForOneCondition(Ticket.class, "ACTIVE", "true");
        list.forEach(t -> {
            TicketFX allTickets = TicketConverter.convertToTicketFX(t);
            ticketFXRandomListForAuthor.add(allTickets);
            if (t.getStatus().getDepartamentDependency().equals("Planowanie")) {
                TicketFX ticketFX = TicketConverter.convertToTicketFX(t);
                ticketFXRandomList.add(ticketFX);
            }

        });
        ticketFXObservableList.setAll(ticketFXRandomList);
        ticketFXObservableListForAuthor.setAll(ticketFXRandomListForAuthor);
    }

    public void innitStaticLists() throws ApplicationException {
        planningFXList.clear();
        purFXList.clear();
        scMFXList.clear();
        statusFXObservableList.clear();
        PersonDao personDao = new PersonDao();
        List<Person> plannningList = null;
        try {
            plannningList = personDao.queryForOneCondition(Person.class, "DEPARTAMENT", "Planowanie");
        } catch (ApplicationException e) {
            Dialogs.alertMessage(e.getMessage());
        }
        plannningList.forEach(p -> {
            PersonFX plannerFX = new PersonFX();
            plannerFX = PersonConverter.convertToPersonFX(p);
            planningFXList.add(plannerFX);
        });
        List<Person> purList = null;
        try {
            purList = personDao.queryForOneCondition(Person.class, "DEPARTAMENT", "Zaopatrzenie");
        } catch (ApplicationException e) {
            Dialogs.alertMessage(e.getMessage());
        }
        purList.forEach(p -> {
            PersonFX purFX = new PersonFX();
            purFX = PersonConverter.convertToPersonFX(p);
            purFXList.add(purFX);
        });
        List<Person> scmList = null;
        try {
            scmList = personDao.queryForOneCondition(Person.class, "DEPARTAMENT", "SCM");
        } catch (ApplicationException e) {
            Dialogs.alertMessage(e.getMessage());
        }
        scmList.forEach(p -> {
            PersonFX scmFx = new PersonFX();
            scmFx = PersonConverter.convertToPersonFX(p);
            scMFXList.add(scmFx);
        });
        try {
            dbManager.closeConnection();
        } catch (ApplicationException e) {
            Dialogs.alertMessage(e.getMessage());
        }
        StatusDao statusDao = new StatusDao();
        List<Status> statusList = statusDao.queryForAll(Status.class);
        statusList.forEach(s -> {
            StatusFX statusFX = StatusConverter.convertToStatusFX(s);
            statusFXObservableList.add(statusFX);
        });
    }


    private Predicate<TicketFX> ticketPredicate(){
        return ticketFX -> ticketFX.getPlannerFXProperty().getId()==this.getPlannerFX().getId();
    }

    public Predicate<TicketFX> authorPredicate(){
        return ticketFX -> ticketFX.getAuthorFXProperty().getId()==this.getAuthorFX().getId();
    }



    public void saveTicketInDB() throws ApplicationException {
        TicketDao ticketDao = new TicketDao();
        Ticket ticket = TicketConverter.convertToTicket(this.getTicketFXObjectProperty());
        ticketDao.createOrUpdate(ticket);
        innitTicketLists();
        dbManager.closeConnection();
    }

    public void filterByPlanner(){
        if(this.getPlannerFX()!=null){
            List<TicketFX> newList = ticketFXRandomList.stream().filter(ticketPredicate()).collect(Collectors.toList());
            this.ticketFXObservableList.setAll(newList);
        }
        if(this.getPlannerFX()==null){
            this.ticketFXObservableList.setAll(ticketFXRandomList);
        }

    }
    public void deleteTicketFX(TicketFX item) throws ApplicationException {
        PersonDao personDao = new PersonDao();
        personDao.deleteById(Ticket.class, item.getIdProperty());
        innit();
    }
    public void filterByAuthor() {
        if (this.getAuthorFX()!= null) {
            List<TicketFX> newList2 = ticketFXRandomListForAuthor.stream().filter(authorPredicate()).collect(Collectors.toList());
            this.ticketFXObservableListForAuthor.setAll(newList2);
        }
        else {
            ticketFXObservableListForAuthor.setAll(ticketFXRandomListForAuthor);
        }

    }
    ////////////////GETTERS AND SETTERS///////////////////////////////////////////////////////////////
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

    public ObservableList<StatusFX> getStatusFXObservableList() {
        return statusFXObservableList;
    }

    public void setStatusFXObservableList(ObservableList<StatusFX> statusFXObservableList) {
        this.statusFXObservableList = statusFXObservableList;
    }

    public PersonFX getPlannerFX() {
        return PlannerFX.get();
    }

    public ObjectProperty<PersonFX> plannerFXProperty() {
        return PlannerFX;
    }

    public void setPlannerFX(PersonFX plannerFX) {
        this.PlannerFX.set(plannerFX);
    }

    public ObservableList<TicketFX> getTicketFXObservableList() {
        return ticketFXObservableList;
    }

    public void setTicketFXObservableList(ObservableList<TicketFX> ticketFXObservableList) {
        this.ticketFXObservableList = ticketFXObservableList;
    }
    public ObservableList<TicketFX> getTicketFXObservableListForAuthor() {
        return ticketFXObservableListForAuthor;
    }

    public void setTicketFXObservableListForAuthor(ObservableList<TicketFX> ticketFXObservableListForAuthor) {
        this.ticketFXObservableListForAuthor = ticketFXObservableListForAuthor;
    }

    public PersonFX getAuthorFX() {
        return authorFX.get();
    }

    public ObjectProperty<PersonFX> authorFXProperty() {
        return authorFX;
    }

    public void setAuthorFX(PersonFX authorFX) {
        this.authorFX.set(authorFX);
    }

    public void updateTicketInDB(TicketFX item) throws ApplicationException {
        TicketDao ticketDao = new TicketDao();
        try {
            Ticket temp = ticketDao.findById(Ticket.class, item.getIdProperty());
            temp.setActive(item.getActiveProperty());
            ticketDao.createOrUpdate(temp);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        innitTicketLists();
        filterByAuthor();
        dbManager.closeConnection();
    }
}
