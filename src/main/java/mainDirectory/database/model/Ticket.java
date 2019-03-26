package mainDirectory.database.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName="Ticket_Table")
public class Ticket implements BaseModel {

    public Ticket(){

    }

    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField (columnName = "MATERIALNAME", canBeNull = false)
    String materialName;

    @DatabaseField (columnName = "DESCRIPTION")
    String materialDescription;

    @DatabaseField(columnName = "STATUS_ID",foreign = true ,foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = false)
    Status status;

    @DatabaseField(columnName = "NOTES")
    String notes;

    @DatabaseField(columnName = "PROJECT")
    String project;

    @DatabaseField(columnName = "AUTHOR_ID", foreign = true ,foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = false)
    Person author;

    @DatabaseField(columnName = "PlANNER_ID", foreign = true ,foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = false)
    Person planner;

    @DatabaseField(columnName = "SCMER_ID", foreign = true ,foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = false)
    Person scmer;

    @DatabaseField(columnName = "BUYER_ID", foreign = true ,foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = false)
    Person buyer;

    @DatabaseField(columnName = "DATE")
    Date data;

    @DatabaseField(columnName = "ACTIVE")
    Boolean active;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public Person getPlanner() {
        return planner;
    }

    public void setPlanner(Person planner) {
        this.planner = planner;
    }

    public Person getScmer() {
        return scmer;
    }

    public void setScmer(Person scmer) {
        this.scmer = scmer;
    }

    public Person getBuyer() {
        return buyer;
    }

    public void setBuyer(Person buyer) {
        this.buyer = buyer;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
