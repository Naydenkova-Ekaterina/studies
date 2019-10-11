package shipping.service.api;

import shipping.exception.CustomServiceException;
import shipping.model.Driver;
import shipping.model.Order;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface DriverService {

    void addDriver(Driver driver) throws CustomServiceException;
    void updateDriver(Driver driver) throws CustomServiceException;
    List<Driver> listDrivers() throws CustomServiceException;
    Driver getDriverById(int id) throws CustomServiceException;
    void removeDriver(int id) throws CustomServiceException;
    Map<Driver, LocalTime> getSuitableDrivers(Order order) throws CustomServiceException;

}
