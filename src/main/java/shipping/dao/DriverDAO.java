package shipping.dao;

import shipping.exception.CustomDAOException;
import shipping.model.City;
import shipping.model.Driver;

import java.util.List;

public interface DriverDAO {

    void addDriver(Driver driver) throws CustomDAOException;
    void update(Driver driver) throws CustomDAOException;
    List<Driver> listDrivers() throws CustomDAOException;
    Driver getDriver(int id) throws CustomDAOException;
    void removeDriver(int id) throws CustomDAOException;
    List<Driver> getAvailableDrivers(City city) throws CustomDAOException;

}
