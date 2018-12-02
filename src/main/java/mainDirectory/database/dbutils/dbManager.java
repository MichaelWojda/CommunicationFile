package mainDirectory.database.dbutils;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import mainDirectory.database.model.Person;
import mainDirectory.database.model.Ticket;

import java.io.IOException;
import java.sql.SQLException;

public class dbManager {

    static ConnectionSource connectionSource;

    public static void createConnectionSource() {
        {
            try {
                connectionSource = new JdbcConnectionSource("jdbc:h2:file://pllod-v-dfsprn1.pl.abb.com/pllod_06/06 PLPES/320 Dział Zaopatrzenia/Zaopatrzenie baza/TEST", "admin", "pass");
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

    public static void closeConnection() throws IOException {
        connectionSource.close();
    }

    public static void innitDB() throws SQLException, IOException {
        createConnectionSource();
        TableUtils.dropTable(connectionSource,Ticket.class, true);
        TableUtils.dropTable(connectionSource, Person.class, true);
        TableUtils.createTableIfNotExists(connectionSource, Ticket.class);
        TableUtils.createTableIfNotExists(connectionSource, Person.class);
        closeConnection();
    }

}
