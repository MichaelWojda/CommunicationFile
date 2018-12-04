package mainDirectory.database.model;

import com.j256.ormlite.field.DatabaseField;

public class Ticket {

    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField(columnName ="ID_TICKET", canBeNull = false)
    int id_ticket;

    @DatabaseField (columnName = "MATERIALNAME", canBeNull = false)
    String materialName;

    @DatabaseField (columnName = "DESCRIPTION")
    String materialDescription;

    @DatabaseField(columnName = "STATUS")
    String status;

    @DatabaseField(columnName = "NOTES")
    String notes;

    @DatabaseField(columnName = "PROJECT")
    String project;

    @DatabaseField(columnName = "PERSON_ID", foreign = true ,foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = false)
    Person person;

    @DatabaseField(columnName = "DEPARTAMENT_ID", canBeNull = false)
    String departament;

    @DatabaseField(columnName = "AUTHOR")
    String autor;




}
