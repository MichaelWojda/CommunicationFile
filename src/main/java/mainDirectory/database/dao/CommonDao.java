package mainDirectory.database.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import mainDirectory.database.dbutils.dbManager;
import mainDirectory.database.model.BaseModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CommonDao {
    protected final ConnectionSource connectionSource;
    CommonDao(){
        connectionSource = dbManager.getConnectionSource();
    }

    Logger logger = LoggerFactory.getLogger(CommonDao.class);

    public void closeDbConnection(){
        try {
            this.connectionSource.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn(e.getCause().getMessage());
        }
    }

    public <T extends BaseModel, I> Dao<T, I> createDao (Class<T> cls){
        try {
            return DaoManager.createDao(connectionSource,cls);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public <T extends BaseModel, I> void createOrUpdate (BaseModel baseModel){
      Dao<T,I> dao = createDao((Class<T>) baseModel.getClass());
        try {
            dao.createOrUpdate((T)baseModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public <T extends BaseModel, I> List<T> queryForAll(Class<T> cls){
        Dao<T,I> dao = createDao(cls);
        try {
           return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public <T extends BaseModel, I> void deleteById (Class<T> cls, Integer id) {
        Dao<T, I> dao = createDao(cls);
        try {
            dao.deleteById((I) id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public <T extends BaseModel, I> T findById(Class<T> cls, Integer id) {
        Dao<T, I> dao = createDao(cls);
        try {
            return dao.queryForId((I) id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
