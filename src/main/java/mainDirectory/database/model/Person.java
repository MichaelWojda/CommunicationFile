package mainDirectory.database.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

public class Person implements BaseModel {

    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField(columnName = "NAME", canBeNull = false)
    String name;

    @DatabaseField(columnName = "SURNAME", canBeNull = false)
    String surname;

    @DatabaseField(columnName = "DEPARTAMENT_ID", canBeNull = false)
    String departament;

    @ForeignCollectionField
    ForeignCollection<Ticket> tickets;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public ForeignCollection<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ForeignCollection<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", departament='" + departament + '\'' +
                ", tickets=" + tickets +
                '}';
    }
}
