package shipping.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shipping.dao.DriverDAO;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.Driver;
import shipping.service.api.DriverService;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    private DriverDAO driverDAO;

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    @Override
    @Transactional
    public void addDriver(Driver driver) throws CustomServiceException {
        try {
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
            driverDAO.removeDriver(id);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    public List<Driver> getSuitableDrivers() throws CustomServiceException {
        try {
            List<Driver> suitableDrivers = driverDAO.getSuitableDrivers();
            return suitableDrivers;
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }
}
