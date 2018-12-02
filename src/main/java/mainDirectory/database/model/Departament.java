package mainDirectory.database.model;

import com.j256.ormlite.field.DatabaseField;

public class Departament implements BaseModel {
    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField(columnName = "NAME", canBeNull = false)
    String name;
}
