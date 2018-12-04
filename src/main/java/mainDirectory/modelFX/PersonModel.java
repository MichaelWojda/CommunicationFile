package mainDirectory.modelFX;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mainDirectory.Converters.PersonConverter;
import mainDirectory.database.dao.PersonDao;
import mainDirectory.database.model.BaseModel;
import mainDirectory.database.model.Person;

import java.util.List;

public class PersonModel {
    ObjectProperty<PersonFX> personFXSimpleObjectProperty = new SimpleObjectProperty<>(new PersonFX());
    ObservableList<PersonFX> personFXObservableList = FXCollections.observableArrayList();


    public void innit() {
        PersonDao personDao = new PersonDao();
        List<Person> list = personDao.queryForAll(Person.class);
        personFXObservableList.clear();
        list.forEach(person -> {
        PersonFX personFX = PersonConverter.convertToPersonFX(person);
        personFXObservableList.add(personFX);
        });
    }

    public PersonFX getPersonFXSimpleObjectProperty() {
        return personFXSimpleObjectProperty.get();
    }

    public ObjectProperty<PersonFX> personFXSimpleObjectProperty() {
        return personFXSimpleObjectProperty;
    }

    public void setPersonFXSimpleObjectProperty(PersonFX personFXSimpleObjectProperty) {
        this.personFXSimpleObjectProperty.set(personFXSimpleObjectProperty);
    }

    public ObservableList<PersonFX> getPersonFXObservableList() {
        return personFXObservableList;
    }

    public void setPersonFXObservableList(ObservableList<PersonFX> personFXObservableList) {
        this.personFXObservableList = personFXObservableList;
    }

    public void savePersonInDB() {
        PersonDao personDao = new PersonDao();

        personDao.createOrUpdate();
    }
}

