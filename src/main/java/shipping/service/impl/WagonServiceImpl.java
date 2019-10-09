package shipping.service.impl;

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
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public void updateWagon(Wagon wagon) throws CustomServiceException {
        try {
            wagonDAO.update(wagon);
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
            wagonDAO.removeWagon(id);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public List<Wagon> getSuitableWagons(double requiredCapacity) throws CustomServiceException {
        try {
            List<Wagon> suitableWagonList = wagonDAO.getSuitableWagons(requiredCapacity);
            return suitableWagonList;
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }
}
