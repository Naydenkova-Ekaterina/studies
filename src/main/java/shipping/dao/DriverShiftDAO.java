package shipping.dao;

import shipping.exception.CustomDAOException;
import shipping.model.Driver;
import shipping.model.DriverShift;

import java.util.List;

public interface DriverShiftDAO {

    void addDriverShift(DriverShift driverShift) throws CustomDAOException;
    void update(DriverShift driverShift) throws CustomDAOException;
    List<DriverShift> listDriverShifts() throws CustomDAOException;
    DriverShift getDriverShift(int id) throws CustomDAOException;
    void removeDriverShift(int id) throws CustomDAOException;
    List<DriverShift> getDriverShiftInThisMonth(List<Driver> driverList);
}
