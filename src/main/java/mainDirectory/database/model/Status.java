package mainDirectory.database.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "STATUS")
public class Status implements BaseModel {

    public Status() {
    }

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "STATUS_NAME")
    String statusName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
