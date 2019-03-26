package mainDirectory.modelFX;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import mainDirectory.database.model.Ticket_History;
import mainDirectory.dialogs.Dialogs;
import mainDirectory.utils.Exceptions.ApplicationException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TicketPlanningModel {


    private ObjectProperty<TicketFX> ticketFXObjectProperty = new SimpleObjectProperty<>(new TicketFX());
    private ObjectProperty<PersonFX> PlannerFX = new SimpleObjectProperty<>(new PersonFX());
    private ObjectProperty<PersonFX> PurFX = new SimpleObjectProperty<>(new PersonFX());
    private ObjectProperty<PersonFX> ScmFX = new SimpleObjectProperty<>(new PersonFX());
    private ObjectProperty<PersonFX> authorFX = new SimpleObjectProperty<>(new PersonFX());
    private SimpleStringProperty searchedName = new SimpleStringProperty();
    private ObjectProperty<StatusFX> statusFX = new SimpleObjectProperty<>(new StatusFX());
    private ObservableList<PersonFX> planningFXList = FXCollections.observableArrayList();
    private ObservableList<StatusFX> statusFXObservableList = FXCollections.observableArrayList();
    private ObservableList<TicketFX> ticketFXObservableListPlanning = FXCollections.observableArrayList();
    private ObservableList<TicketFX> ticketFXObservableListPur = FXCollections.observableArrayList();
    private ObservableList<TicketFX> ticketFXObservableListSCM = FXCollections.observableArrayList();
    private ObservableList<TicketFX> ticketFXMyTicketList = FXCollections.observableArrayList();
    private ObservableList<PersonFX> purFXList = FXCollections.observableArrayList();
    private ObservableList<PersonFX> scMFXList = FXCollections.observableArrayList();
    private ObservableList<TicketFX_History> ticketFX_history = FXCollections.observableArrayList();
    private SimpleObjectProperty<TicketFX_History> ticketHistoryObject = new SimpleObjectProperty<>(new TicketFX_History());
    private List<TicketFX_History> ticketHistoryRandomList= new ArrayList<>();
    private List<TicketFX> ticketFXRandomListPlanning = new ArrayList<>();
    private List<TicketFX> ticketFXRandomListPur = new ArrayList<>();
    private List<TicketFX> ticketFXRandomListSCM = new ArrayList<>();
    private List<TicketFX> ticketFXMyTicketRandomList = new ArrayList<>();

    public void innit() throws ApplicationException {
        innitStaticLists();
        innitTicketLists();

        dbManager.closeConnection();
    }

    public void innitTicketLists() throws ApplicationException {
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

        List<TicketFX> emptylist = new ArrayList<>();
        ticketFXObservableListPlanning.setAll(emptylist);
        ticketFXObservableListPur.setAll(emptylist);
        ticketFXObservableListSCM.setAll(emptylist);
        ticketFXMyTicketList.setAll(emptylist);

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
    public void innitHistory() throws ApplicationException {
        TicketDao ticketDao = new TicketDao();
        List<Ticket_History> list = ticketDao.queryForAll(Ticket_History.class);
        list.forEach(t->{
            TicketFX_History ticketFX_history = TicketConverter.convertToHistoryTicketFX(t);
            ticketHistoryRandomList.add(ticketFX_history);
        });
        ticketFX_history.setAll(ticketHistoryRandomList);
    }


    private Predicate<TicketFX> plannerPredicate(){
        return ticketFX -> ticketFX.getPlannerFXProperty().getId()==this.getPlannerFX().getId();
    }

    private Predicate<TicketFX> authorPredicate(){
        return ticketFX -> ticketFX.getAuthorFXProperty().getId()==this.getAuthorFX().getId();
    }
    private Predicate<TicketFX> purPredicate(){
        return ticketFX -> ticketFX.getBuyerFXProperty().getId()==this.getPurFX().getId();
    }
    private Predicate<TicketFX> scmPredicate(){
        return ticketFX -> ticketFX.getScmerFXProperty().getId()==this.getScmFX().getId();
    }

    private Predicate<TicketFX> statusPredicate(){
        return ticketFX -> ticketFX.getStatusProperty().getId()==this.getStatusFX().getId();
    }

    private Predicate<TicketFX> materialPredicate(){
        return ticketFX -> ticketFX.getMaterialNameProperty().equals(this.getSearchedName());
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



    public void createTicketInDB() throws ApplicationException {
        TicketDao ticketDao = new TicketDao();
        Ticket ticket = TicketConverter.convertToTicket(this.getTicketFXObjectProperty());
        Ticket_History ticket_history = TicketConverter.convertToHistoryTicket(ticket);
        ticket.setData(new Date());
        ticketDao.createOrUpdate(ticket);
        int i = (int)ticketDao.countAll(Ticket.class);
        ticket_history.setId_ticket(i);
        ticket_history.setData(new Date());
        ticketDao.createOrUpdate(ticket_history);
        innitTicketLists();
        dbManager.closeConnection();
    }

    public void saveTicketInDB() throws ApplicationException {
        TicketDao ticketDao = new TicketDao();
        Ticket ticket = TicketConverter.convertToTicket(this.getTicketFXObjectProperty());
        Ticket_History ticket_history = TicketConverter.convertToHistoryTicket(ticket);
        ticket.setData(new Date());
        ticketDao.createOrUpdate(ticket);
        ticketDao.createOrUpdate(ticket_history);
        innitTicketLists();
        dbManager.closeConnection();
    }

    public void filterByPlanner(){
        if(this.getPlannerFX()!=null){
            List<TicketFX> newList = ticketFXRandomListPlanning.stream().filter(plannerPredicate()).collect(Collectors.toList());
            this.ticketFXObservableListPlanning.setAll(newList);
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

    }
    public void filterByPur() {
        if(this.getPurFX()!=null){
            List<TicketFX> newList = ticketFXRandomListPur.stream().filter(purPredicate()).collect(Collectors.toList());
            this.ticketFXObservableListPur.setAll(newList);
        }

    }
    public void filterByScm() {
        if(this.getScmFX()!=null){
            List<TicketFX> newList = ticketFXRandomListSCM.stream().filter(scmPredicate()).collect(Collectors.toList());
            this.ticketFXObservableListSCM.setAll(newList);
        }

    }
    public void filterByName(){
        if(this.getSearchedName()!=null){
            List<TicketFX_History> newList = ticketHistoryRandomList.stream().filter(materialPredicate()).collect(Collectors.toList());
            ticketFX_history.setAll(newList);
        }
    }

    public void filterHistory(){
        if(this.getAuthorFX()==null && this.getStatusFX()!=null && this.getSearchedName()==null){
            List<TicketFX_History> newList = ticketHistoryRandomList.stream().filter(statusPredicate()).collect(Collectors.toList());
            ticketFX_history.setAll(newList);
        }
        if(this.getAuthorFX()!=null && this.getStatusFX()==null && this.getSearchedName()==null){
            List<TicketFX_History> newList = ticketHistoryRandomList.stream().filter(authorPredicate()).collect(Collectors.toList());
            ticketFX_history.setAll(newList);
        }
        if(this.getAuthorFX()!=null && this.getStatusFX()!=null && this.getSearchedName()==null){
            List<TicketFX_History> newList = ticketHistoryRandomList.stream().filter(authorPredicate().and(statusPredicate())).collect(Collectors.toList());
            ticketFX_history.setAll(newList);
        }
        if(this.getAuthorFX()==null && this.getStatusFX()==null && this.getSearchedName()==null){
            ticketFX_history.setAll(ticketHistoryRandomList);
        }
        if(this.getAuthorFX()==null && this.getStatusFX()!=null && this.getSearchedName()!=null){
            List<TicketFX_History> newList = ticketHistoryRandomList.stream().filter(statusPredicate().and(materialPredicate())).collect(Collectors.toList());
            ticketFX_history.setAll(newList);
        }
        if(this.getAuthorFX()!=null && this.getStatusFX()==null && this.getSearchedName()!=null){
            List<TicketFX_History> newList = ticketHistoryRandomList.stream().filter(authorPredicate().and(materialPredicate())).collect(Collectors.toList());
            ticketFX_history.setAll(newList);
        }
        if(this.getAuthorFX()!=null && this.getStatusFX()!=null && this.getSearchedName()!=null){
            List<TicketFX_History> newList = ticketHistoryRandomList.stream().filter(authorPredicate().and(statusPredicate().and(materialPredicate()))).collect(Collectors.toList());
            ticketFX_history.setAll(newList);
        }
        if(this.getAuthorFX()==null && this.getStatusFX()==null && this.getSearchedName()!=null){
            filterByName();
        }



    }


    public void updateTicketInDB(TicketFX item) throws ApplicationException {
        TicketDao ticketDao = new TicketDao();
        try {
            Ticket temp = ticketDao.findById(Ticket.class, item.getIdProperty());
            Ticket temphist = TicketConverter.convertToTicket(item);
            Ticket_History ticket_history = TicketConverter.convertToHistoryTicket(temphist);
            temp.setActive(item.getActiveProperty());
            ticketDao.createOrUpdate(temp);
            ticketDao.createOrUpdate(ticket_history);
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

    public ObservableList<TicketFX_History> getTicketFX_history() {
        return ticketFX_history;
    }

    public void setTicketFX_history(ObservableList<TicketFX_History> ticketFX_history) {
        this.ticketFX_history = ticketFX_history;
    }

    public TicketFX_History getTicketHistoryObject() {
        return ticketHistoryObject.get();
    }

    public SimpleObjectProperty<TicketFX_History> ticketHistoryObjectProperty() {
        return ticketHistoryObject;
    }

    public void setTicketHistoryObject(TicketFX_History ticketHistoryObject) {
        this.ticketHistoryObject.set(ticketHistoryObject);
    }

    public List<TicketFX_History> getTicketHistoryRandomList() {
        return ticketHistoryRandomList;
    }

    public void setTicketHistoryRandomList(List<TicketFX_History> ticketHistoryRandomList) {
        this.ticketHistoryRandomList = ticketHistoryRandomList;
    }

    public StatusFX getStatusFX() {
        return statusFX.get();
    }

    public ObjectProperty<StatusFX> statusFXProperty() {
        return statusFX;
    }

    public void setStatusFX(StatusFX statusFX) {
        this.statusFX.set(statusFX);
    }

    public List<Ticket> searchForMaterial(String searchedMaterial) throws ApplicationException {
        TicketDao ticketDao = new TicketDao();
        List<Ticket> list = ticketDao.queryForOneCondition(Ticket.class, "MATERIALNAME", searchedMaterial);
        return list;
    }

    public String getSearchedName() {
        return searchedName.get();
    }

    public SimpleStringProperty searchedNameProperty() {
        return searchedName;
    }

    public void setSearchedName(String searchedName) {
        this.searchedName.set(searchedName);
    }
}
