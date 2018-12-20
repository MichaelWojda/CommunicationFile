package mainDirectory.Converters;

import mainDirectory.database.model.Status;
import mainDirectory.modelFX.StatusFX;

public class StatusConverter {
    public static StatusFX convertToStatusFX(Status status){
        StatusFX statusFX = new StatusFX();
        statusFX.setId(status.getId());
        statusFX.setNameFX(status.getStatusName());
        return statusFX;

    }
    public static Status convertToStatus(StatusFX statusFX){
        Status status = new Status();
        status.setId(statusFX.getId());
        status.setStatusName(statusFX.getNameFX());
        return status;
    }
}
