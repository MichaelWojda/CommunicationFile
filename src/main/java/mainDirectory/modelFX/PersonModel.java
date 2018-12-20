package mainDirectory.modelFX;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mainDirectory.Converters.PersonConverter;
import mainDirectory.database.dao.PersonDao;
import mainDirectory.database.dbutils.dbManager;
import mainDirectory.database.model.BaseModel;
import mainDirectory.database.model.Person;
import mainDirectory.utils.Exceptions.ApplicationException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PersonModel {
    ObjectProperty<PersonFX> personFXSimpleObjectProperty = new SimpleObjectProperty<>(new PersonFX());
    ObservableList<PersonFX> personFXObservableList = FXCollections.observableArrayList();


    public void innit() throws ApplicationException {
        PersonDao personDao = new PersonDao();
        List<Person> list = personDao.queryForAll(Person.class);
        personFXObservableList.clear();
        list.forEach(person -> {
            PersonFX personFX = PersonConverter.convertToPersonFX(person);
            personFXObservableList.add(personFX);
        });
        dbManager.closeConnection();
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

    public void savePersonInDB() throws ApplicationException {
        PersonDao personDao = new PersonDao();
        Person person = new Person();
        person = PersonConverter.convertToPerson(this.getPersonFXSimpleObjectProperty());
        personDao.createOrUpdate(person);
        dbManager.closeConnection();
        innit();
    }

    public void deletePersonFX(PersonFX item) throws ApplicationException {
        PersonDao personDao = new PersonDao();
        personDao.deleteById(Person.class, item.getId());
        innit();
    }
}

