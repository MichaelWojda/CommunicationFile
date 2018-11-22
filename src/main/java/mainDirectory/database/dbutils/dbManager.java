package mainDirectory.database.dbutils;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class dbManager {


    ConnectionSource connectionSource;

    {
        try {
            connectionSource = new JdbcConnectionSource("jdbc:h2:./bazaTEST","admin","pass");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
