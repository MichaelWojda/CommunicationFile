package mainDirectory.database.dbutils;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import mainDirectory.database.model.Person;
import mainDirectory.database.model.Status;
import mainDirectory.database.model.Ticket;
import mainDirectory.utils.Exceptions.ApplicationException;

import java.io.IOException;
import java.sql.SQLException;

public class dbManager {

    public static final String LAN_DATABASE_ADDRESS = "jdbc:h2:file://pllod-v-dfsprn1.pl.abb.com/pllod_06/06 PLPES/320 Dział Zaopatrzenia/Zaopatrzenie baza/TEST";
    public static final String RANDOM_DB = "jdbc:h2:file:/C:/Users/plmiwoj4/Desktop/Temp/randomDB";
    static ConnectionSource connectionSource;

    public static void createConnectionSource() {
        {
            try {
                connectionSource = new JdbcConnectionSource(RANDOM_DB, "admin", "pass");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ConnectionSource getConnectionSource(){
        if(connectionSource==null){
            createConnectionSource();
        }
        return connectionSource;
    }

    public static void closeConnection() throws ApplicationException {
        try {
            connectionSource.close();
        } catch (IOException e) {
            throw new ApplicationException("Problem z połączeniem z bazą danych");
        }
    }


    public static void innitDB() throws ApplicationException, SQLException {
        createConnectionSource();
        TableUtils.dropTable(connectionSource,Ticket.class, true);
        TableUtils.dropTable(connectionSource, Person.class, true);
        TableUtils.dropTable(connectionSource, Status.class, true);
        try {
            TableUtils.createTableIfNotExists(connectionSource, Ticket.class);
            TableUtils.createTableIfNotExists(connectionSource, Person.class);
            TableUtils.createTableIfNotExists(connectionSource, Status.class);
        } catch (SQLException e) {
            throw new ApplicationException("Problem z połączeniem z bazą danych");
        }

        closeConnection();
    }

}
