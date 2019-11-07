package shipping.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shipping.controller.MainController;
import shipping.dao.WagonDAO;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.Wagon;
import shipping.service.api.WagonService;

import java.util.ArrayList;
import java.util.List;

@Service
public class WagonServiceImpl implements WagonService {

    private static final Logger logger = Logger.getLogger(WagonServiceImpl.class);

    private WagonDAO wagonDAO;

    public void setWagonDAO(WagonDAO wagonDAO) {
        this.wagonDAO = wagonDAO;
    }

    @Override
    @Transactional
    public void addWagon(Wagon wagon) throws CustomServiceException {

        try {
            Wagon checkWagon = wagonDAO.getWagon(wagon.getId());

            if (checkWagon != null) {
                throw new CustomServiceException("Selected id already exists");
            }

            wagonDAO.addWagon(wagon);

            logger.debug("New wagon with id = " + wagon.getId() + " was created.");

        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public void updateWagon(Wagon wagon) throws CustomServiceException {
        try {
            wagonDAO.update(wagon);
            logger.debug("Wagon with id = " + wagon.getId() + " was updated.");

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
            } /*else if (!wagon.getOrder().isEmpty() ) {
                throw new CustomServiceException("Wagon can't be removed, because it has an order."); // need check !
            } */else if (!wagon.getDriverSet().isEmpty()) {
                throw new CustomServiceException("Wagon can't be removed, because it has drivers."); // need check !
            }
            wagonDAO.removeWagon(id);
            logger.debug("Wagon with id = " + wagon.getId() + " was removed.");

        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public List<Wagon> getSuitableWagons(double requiredCapacity) throws CustomServiceException {
        try {
            List<String> ids = wagonDAO.getSuitableWagons(requiredCapacity);
            List<Wagon> suitableWagons = new ArrayList<>();

            for (String id: ids) {
                suitableWagons.add(wagonDAO.getWagon(id));
            }
            logger.debug("All suitable wagons were found.");

            return suitableWagons;
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }
}
