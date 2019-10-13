package shipping.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shipping.dao.DriverDAO;
import shipping.dao.DriverShiftDAO;
import shipping.dao.WagonDAO;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.Driver;
import shipping.model.DriverShift;
import shipping.model.Order;
import shipping.model.Wagon;
import shipping.service.api.DriverService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DriverServiceImpl implements DriverService {

    private Logger logger = LoggerFactory.getLogger(DriverServiceImpl.class);

    private DriverDAO driverDAO;

    private DriverShiftDAO driverShiftDAO;

    private WagonDAO wagonDAO;

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    @Override
    @Transactional
    public void addDriver(Driver driver) throws CustomServiceException {

        //validate fields

        try {
            Driver checkDriver = driverDAO.getDriver(driver.getId());

            if (checkDriver != null) {
                throw new CustomServiceException("Selected id already exists");
            }

            driverDAO.addDriver(driver);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public void updateDriver(Driver driver) throws CustomServiceException {
        try {
            driverDAO.update(driver);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public List<Driver> listDrivers() throws CustomServiceException {
        try {
            return driverDAO.listDrivers();
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public Driver getDriverById(int id) throws CustomServiceException {
        try {
            return driverDAO.getDriver(id);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public void removeDriver(int id) throws CustomServiceException {
        try {
            Driver driver = driverDAO.getDriver(id);
            if (driver == null) {
                throw new CustomServiceException("Driver wasn't found.");
            } /*else if (driver.getOrder() != null) {
                throw new CustomServiceException("Driver can't be removed, because he has an order.");
            } else if (driver.getWagon() != null) {
                // need or not
            }*/
            driverDAO.removeDriver(id);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public Map<Driver, LocalTime> getSuitableDrivers(Order order) throws CustomServiceException {
        try {
            Map<Driver, LocalTime> driversHoursMap = new HashMap<>();

            Wagon wagonForThisOrder = order.getWagon();
            List<Driver> availableDriversInTheSameCity = driverDAO.getAvailableDrivers(wagonForThisOrder.getCity());

            for (Driver driver: availableDriversInTheSameCity) {
                int sumHours = 0;
                // do smth
                if (sumHours <= 176) {
                    driversHoursMap.put(driver, LocalTime.of(sumHours,0));
                }
            }


            List<DriverShift> driverShifts = driverShiftDAO.getDriverShiftInThisMonth(availableDriversInTheSameCity);

            return driversHoursMap;
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }
}
