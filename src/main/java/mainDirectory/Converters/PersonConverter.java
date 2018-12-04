package mainDirectory.Converters;

import mainDirectory.database.model.Person;
import mainDirectory.modelFX.PersonFX;

public class PersonConverter {
    public static PersonFX convertToPersonFX(Person person){
        PersonFX personFX = new PersonFX();
        personFX.setId(person.getId());
        personFX.setName(person.getName());
        personFX.setSurname(person.getSurname());
        personFX.setDepartament(person.getDepartament());
        return personFX;
    }
}
