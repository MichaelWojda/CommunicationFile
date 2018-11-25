package mainDirectory.database.model;

import com.j256.ormlite.field.DatabaseField;

public class Ticket {

    @DatabaseField (columnName = "MATERIALNAME")
    String materialName;
}
