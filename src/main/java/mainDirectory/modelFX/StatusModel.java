package mainDirectory.modelFX;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mainDirectory.Converters.StatusConverter;
import mainDirectory.database.dao.StatusDao;
import mainDirectory.database.dbutils.dbManager;
import mainDirectory.database.model.Status;
import mainDirectory.utils.Exceptions.ApplicationException;

import java.util.List;

public class StatusModel {
    ObjectProperty<StatusFX> statusFXObjectProperty = new SimpleObjectProperty<>(new StatusFX());
    ObservableList<StatusFX> statusFXObservableList = FXCollections.observableArrayList();



    public void innit() throws ApplicationException {
        StatusDao statusDao = new StatusDao();
        List<Status> list = statusDao.queryForAll(Status.class);
        statusFXObservableList.clear();
        list.forEach(status->{
            StatusFX statusFX;
            statusFX = StatusConverter.convertToStatusFX(status);
            statusFXObservableList.add(statusFX);
                }
                );
        dbManager.closeConnection();
    }

    public StatusFX getStatusFXObjectProperty() {
        return statusFXObjectProperty.get();
    }

    public ObjectProperty<StatusFX> statusFXObjectPropertyProperty() {
        return statusFXObjectProperty;
    }

    public void setStatusFXObjectProperty(StatusFX statusFXObjectProperty) {
        this.statusFXObjectProperty.set(statusFXObjectProperty);
    }

    public ObservableList<StatusFX> getStatusFXObservableList() {
        return statusFXObservableList;
    }

    public void setStatusFXObservableList(ObservableList<StatusFX> statusFXObservableList) {
        this.statusFXObservableList = statusFXObservableList;
    }

    public void saveStatusInDB() throws ApplicationException {
        StatusDao statusDao = new StatusDao();
        Status status;
        status = StatusConverter.convertToStatus(this.getStatusFXObjectProperty());
        statusDao.createOrUpdate(status);
        dbManager.closeConnection();
        innit();
    }
}
