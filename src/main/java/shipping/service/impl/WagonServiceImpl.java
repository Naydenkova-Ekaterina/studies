package shipping.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shipping.dao.WagonDAO;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.Wagon;
import shipping.service.api.WagonService;

import java.util.List;

@Service
public class WagonServiceImpl implements WagonService {

    private Logger logger = LoggerFactory.getLogger(WagonServiceImpl.class);

    private WagonDAO wagonDAO;

    public void setWagonDAO(WagonDAO wagonDAO) {
        this.wagonDAO = wagonDAO;
    }

    @Override
    @Transactional
    public void addWagon(Wagon wagon) throws CustomServiceException {

        //validate fields ?
        validate(wagon);

        try {
            Wagon checkWagon = wagonDAO.getWagon(wagon.getId());

            if (checkWagon != null) {
                throw new CustomServiceException("Selected id already exists");
            }

            wagonDAO.addWagon(wagon);

            logger.info("New wagon with id = " + wagon.getId() + " was created.");

        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    public void validate(Wagon wagon) throws CustomServiceException{
        if (wagon.getId() == null || wagon.getId().isEmpty() || wagon.getId().matches("[a-zA-Z]{2}[0-1]{5}")) {
            throw new CustomServiceException("Id is not correct.");
        } else if (wagon.getShiftSize() == null) {
            throw new CustomServiceException("Shift size wasn't set.");
        } else if (wagon.getCapacity() <= 0) {
            throw new CustomServiceException("Capacity should be positive.");
        } else if (wagon.getCity() == null) {
            throw new CustomServiceException("City wasn't set.");
        }
    }

    @Override
    @Transactional
    public void updateWagon(Wagon wagon) throws CustomServiceException {
        try {
            wagonDAO.update(wagon);
            logger.info("Wagon with id = " + wagon.getId() + " was updated.");

        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public List<Wagon> listWagons() throws CustomServiceException {
        try {
            return wagonDAO.listWagons();
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public Wagon getWagonById(String id) throws CustomServiceException {
        try {
            return wagonDAO.getWagon(id);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public void removeWagon(String id) throws CustomServiceException {
        try {
            Wagon wagon = wagonDAO.getWagon(id);

            if (wagon == null) {
                throw new CustomServiceException("This wagon doesn't exist.");
            } else if (wagon.getOrderSet() != null) {
                throw new CustomServiceException("Wagon can't be removed, because it has an order."); // need check !
            } else if (wagon.getDriverSet() != null) {
                throw new CustomServiceException("Wagon can't be removed, because it has drivers."); // need check !
            }
            wagonDAO.removeWagon(id);
            logger.info("Wagon with id = " + wagon.getId() + " was removed.");

        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public List<Wagon> getSuitableWagons(double requiredCapacity) throws CustomServiceException {
        try {
            List<Wagon> suitableWagonList = wagonDAO.getSuitableWagons(requiredCapacity);
            logger.info("All suitable wagons were found.");

            return suitableWagonList;
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }
}
