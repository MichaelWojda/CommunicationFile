package mainDirectory.database.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import mainDirectory.database.dbutils.dbManager;
import mainDirectory.database.model.BaseModel;
import mainDirectory.database.model.Ticket;
import mainDirectory.utils.Exceptions.ApplicationException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CommonDao {
    protected final ConnectionSource connectionSource;

    CommonDao() {
        connectionSource = dbManager.getConnectionSource();
    }

    Logger logger = LoggerFactory.getLogger(CommonDao.class);

    public void closeDbConnection() throws ApplicationException {
        try {
            this.connectionSource.close();
        } catch (IOException e) {
            logger.warn(e.getCause().getMessage());
            throw new ApplicationException("Problem z zamknięciem bazy danych");
        }
    }

    public <T extends BaseModel, I> Dao<T, I> createDao(Class<T> cls) throws ApplicationException {
        try {
            return DaoManager.createDao(connectionSource, cls);
        } catch (SQLException e) {
            logger.warn(e.getCause().getMessage());
            throw new ApplicationException("Problem z połączeniem z bazą danych");
        }

    }

    public <T extends BaseModel, I> void createOrUpdate(BaseModel baseModel) throws ApplicationException {
        Dao<T, I> dao = createDao((Class<T>) baseModel.getClass());
        try {
            dao.createOrUpdate((T) baseModel);
        } catch (SQLException e) {
            logger.warn(e.getCause().getMessage());
            throw new ApplicationException("Problem z zapisem do bazy danych");

        }

    }

    public <T extends BaseModel, I> List<T> queryForAll(Class<T> cls) throws ApplicationException {
        Dao<T, I> dao = createDao(cls);
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            logger.warn(e.getCause().getMessage());
            throw new ApplicationException("Problem z odczytem z bazy danych");
        }
    }

    public <T extends BaseModel, I> void deleteById(Class<T> cls, Integer id) throws ApplicationException {
        Dao<T, I> dao = createDao(cls);
        try {
            dao.deleteById((I) id);
        } catch (SQLException e) {
            logger.warn(e.getCause().getMessage());
            throw new ApplicationException("Problem z usunięciem rekordu z bazy danych");
        }
    }

    public <T extends BaseModel, I> T findById(Class<T> cls, Integer id) throws ApplicationException {
        Dao<T, I> dao = createDao(cls);
        try {
            return dao.queryForId((I) id);
        } catch (SQLException e) {
            logger.warn(e.getCause().getMessage());
            throw new ApplicationException("Problem ze znalezieniem rekordu w bazie danych");
        }

    }

    public <T extends BaseModel, I> List<T> queryForOneCondition(Class<T> cls, String columnName, String condition) throws ApplicationException {
        Dao<T, I> dao = createDao(cls);
        QueryBuilder<T, I> queryBuilder = dao.queryBuilder();
        try {
            queryBuilder.where().eq(columnName, condition);
            PreparedQuery<T> prepare = queryBuilder.prepare();
            return dao.query(prepare);
        } catch (SQLException e) {
            logger.warn(e.getCause().getMessage());
            throw new ApplicationException("Problem z odczytem danych z bazy");
        }

    }
    public <T extends BaseModel, I> long countAll(Class<T> cls) throws ApplicationException {
        Dao<T, I> dao = createDao(cls);
        try {
            return dao.queryBuilder().countOf();
        } catch (SQLException e) {
            logger.warn(e.getCause().getMessage());
            throw new ApplicationException("Problem z odczytem z bazy danych");
        }
    }

}
