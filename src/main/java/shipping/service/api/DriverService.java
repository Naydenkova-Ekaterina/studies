package shipping.service.api;

import shipping.exception.CustomServiceException;
import shipping.model.Driver;

import java.util.List;

public interface DriverService {

    void addDriver(Driver driver) throws CustomServiceException;
    void updateDriver(Driver driver) throws CustomServiceException;
    List<Driver> listDrivers() throws CustomServiceException;
    Driver getDriverById(int id) throws CustomServiceException;
    void removeDriver(int id) throws CustomServiceException;
    List<Driver> getSuitableDrivers() throws CustomServiceException;

}
