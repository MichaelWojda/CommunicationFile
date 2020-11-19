package mainDirectory.Converters;

import javafx.collections.ObservableList;
import mainDirectory.database.model.Person;
import mainDirectory.modelFX.PersonFX;

import java.util.ArrayList;
import java.util.List;

public class PersonConverter {
    public static PersonFX convertToPersonFX(Person person){
        PersonFX personFX = new PersonFX();
        personFX.setId(person.getId());
        personFX.setName(person.getName());
        personFX.setSurname(person.getSurname());
        personFX.setDepartament(person.getDepartament());
        personFX.setEmailFX(person.getEmail());
        return personFX;
    }

    public static Person convertToPerson(PersonFX personFX) {
        Person person = new Person();
        person.setId(personFX.getId());
        person.setName(personFX.getName());
        person.setSurname(personFX.getSurname());
        person.setDepartament(personFX.getDepartament());
        person.setEmail(personFX.getEmailFX());
        return person;
    }

    public static List<String> convertFXIntoSimpleNameSurname(ObservableList<PersonFX> personFXList) {
        List<String> list = new ArrayList<>();
        personFXList.forEach(p->{
            list.add(p.getName()+" "+p.getSurname());

        });

        return list;
    }
}
