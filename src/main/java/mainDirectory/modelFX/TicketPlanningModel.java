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
    ObjectProperty<PersonFX> PurFX = new SimpleObjectProperty<>(new PersonFX());
    ObjectProperty<PersonFX> ScmFX = new SimpleObjectProperty<>(new PersonFX());
    ObjectProperty<PersonFX> authorFX = new SimpleObjectProperty<>(new PersonFX());
    ObservableList<PersonFX> planningFXList = FXCollections.observableArrayList();
    ObservableList<StatusFX> statusFXObservableList = FXCollections.observableArrayList();
    ObservableList<TicketFX> ticketFXObservableListPlanning = FXCollections.observableArrayList();
    ObservableList<TicketFX> ticketFXObservableListPur = FXCollections.observableArrayList();
    ObservableList<TicketFX> ticketFXObservableListSCM = FXCollections.observableArrayList();
    ObservableList<TicketFX> ticketFXMyTicketList = FXCollections.observableArrayList();
    ObservableList<PersonFX> purFXList = FXCollections.observableArrayList();
    ObservableList<PersonFX> scMFXList = FXCollections.observableArrayList();
    private List<TicketFX> ticketFXRandomListPlanning = new ArrayList<>();
    private List<TicketFX> ticketFXRandomListPur = new ArrayList<>();
    private List<TicketFX> ticketFXRandomListSCM = new ArrayList<>();
    private List<TicketFX> ticketFXMyTicketRandomList = new ArrayList<>();

    public void innit() throws ApplicationException {
        innitStaticLists();
        innitTicketLists();

        dbManager.closeConnection();
    }

    private void innitTicketLists() throws ApplicationException {
        ticketFXRandomListPlanning.clear();
        ticketFXRandomListPur.clear();
        ticketFXRandomListSCM.clear();
        ticketFXObservableListPur.clear();
        ticketFXObservableListSCM.clear();
        ticketFXObservableListPlanning.clear();
        ticketFXMyTicketRandomList.clear();
        ticketFXMyTicketList.clear();
        TicketDao ticketDao = new TicketDao();
        List<Ticket> list = ticketDao.queryForOneCondition(Ticket.class, "ACTIVE", "true");
        list.forEach(t -> {
            TicketFX allTickets = TicketConverter.convertToTicketFX(t);
            ticketFXMyTicketRandomList.add(allTickets);
            if (t.getStatus().getDepartamentDependency().equals("Planowanie")) {
                TicketFX ticketFX = TicketConverter.convertToTicketFX(t);
                ticketFXRandomListPlanning.add(ticketFX);
            }
            if(t.getStatus().getDepartamentDependency().equals("Zaopatrzenie")){
                TicketFX ticketFX = TicketConverter.convertToTicketFX(t);
                ticketFXRandomListPur.add(ticketFX);
            }
            if(t.getStatus().getDepartamentDependency().equals("SCM")){
                TicketFX ticketFX = TicketConverter.convertToTicketFX(t);
                ticketFXRandomListSCM.add(ticketFX);
            }

        });
        ticketFXObservableListPlanning.setAll(ticketFXRandomListPlanning);
        ticketFXObservableListPur.setAll(ticketFXRandomListPur);
        ticketFXObservableListSCM.setAll(ticketFXRandomListSCM);
        ticketFXMyTicketList.setAll(ticketFXMyTicketRandomList);

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

    private Predicate<TicketFX> authorPredicate(){
        return ticketFX -> ticketFX.getAuthorFXProperty().getId()==this.getAuthorFX().getId();
    }
    private Predicate<TicketFX> purPredicate(){
        return ticketFX -> ticketFX.getBuyerFXProperty().getId()==this.getPurFX().getId();
    }
    /*private Predicate<TicketFX> statusFXPredicate(String departament){
        return ticketFX -> ticketFX.getStatusProperty().getDepartamentFX().equals(departament);

    }
    public ObservableList<TicketFX> filteredTickets(String departament){
        List<TicketFX> list = this.ticketFXMyTicketRandomList.stream().filter(statusFXPredicate(departament)).collect(Collectors.toList());
        ObservableList<TicketFX> fxObservableList=null;
        fxObservableList.setAll(list);
        return fxObservableList;
    }*/



    public void saveTicketInDB() throws ApplicationException {
        TicketDao ticketDao = new TicketDao();
        Ticket ticket = TicketConverter.convertToTicket(this.getTicketFXObjectProperty());
        ticketDao.createOrUpdate(ticket);
        innitTicketLists();
        dbManager.closeConnection();
    }
    public void filterByPlanner(){
        if(this.getPlannerFX()!=null){
            List<TicketFX> newList = ticketFXRandomListPlanning.stream().filter(ticketPredicate()).collect(Collectors.toList());
            this.ticketFXObservableListPlanning.setAll(newList);
        }
        if(this.getPlannerFX()==null){
            this.ticketFXObservableListPlanning.setAll(ticketFXRandomListPlanning);

        }

    }
    public void deleteTicketFX(TicketFX item) throws ApplicationException {
        PersonDao personDao = new PersonDao();
        personDao.deleteById(Ticket.class, item.getIdProperty());
        innit();
    }
    public void filterByAuthor() {
        if (this.getAuthorFX()!= null) {
            List<TicketFX> newList2 = ticketFXMyTicketRandomList.stream().filter(authorPredicate()).collect(Collectors.toList());
            this.ticketFXMyTicketList.setAll(newList2);
        }
        else {
            ticketFXMyTicketList.setAll(ticketFXMyTicketRandomList);
        }

    }
    public void filterByPur() {
        if(this.getPurFX()!=null){
            List<TicketFX> newList = ticketFXRandomListPur.stream().filter(purPredicate()).collect(Collectors.toList());
            this.ticketFXObservableListPur.setAll(newList);
        }
        if(this.getPurFX()==null){
            this.ticketFXObservableListPur.setAll(ticketFXRandomListPur);
        }
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

    public ObservableList<TicketFX> getTicketFXObservableListPlanning() {
        return ticketFXObservableListPlanning;
    }

    public void setTicketFXObservableListPlanning(ObservableList<TicketFX> ticketFXObservableListPlanning) {
        this.ticketFXObservableListPlanning = ticketFXObservableListPlanning;
    }
    public ObservableList<TicketFX> getTicketFXMyTicketList() {
        return ticketFXMyTicketList;
    }

    public void setTicketFXMyTicketList(ObservableList<TicketFX> ticketFXMyTicketList) {
        this.ticketFXMyTicketList = ticketFXMyTicketList;
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

    public ObservableList<TicketFX> getTicketFXObservableListPur() {
        return ticketFXObservableListPur;
    }

    public void setTicketFXObservableListPur(ObservableList<TicketFX> ticketFXObservableListPur) {
        this.ticketFXObservableListPur = ticketFXObservableListPur;
    }

    public ObservableList<TicketFX> getTicketFXObservableListSCM() {
        return ticketFXObservableListSCM;
    }

    public void setTicketFXObservableListSCM(ObservableList<TicketFX> ticketFXObservableListSCM) {
        this.ticketFXObservableListSCM = ticketFXObservableListSCM;
    }

    public PersonFX getPurFX() {
        return PurFX.get();
    }

    public ObjectProperty<PersonFX> purFXProperty() {
        return PurFX;
    }

    public void setPurFX(PersonFX purFX) {
        this.PurFX.set(purFX);
    }

    public PersonFX getScmFX() {
        return ScmFX.get();
    }

    public ObjectProperty<PersonFX> scmFXProperty() {
        return ScmFX;
    }

    public void setScmFX(PersonFX scmFX) {
        this.ScmFX.set(scmFX);
    }
}
