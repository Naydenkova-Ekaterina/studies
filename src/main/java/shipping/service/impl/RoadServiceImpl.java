package shipping.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shipping.dao.RoadDAO;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.City;
import shipping.model.Road;
import shipping.service.api.RoadService;

import java.util.List;

@Service
public class RoadServiceImpl implements RoadService {

    private RoadDAO roadDAO;

    public void setRoadDAO(RoadDAO roadDAO) {
        this.roadDAO = roadDAO;
    }

    @Override
    @Transactional
    public List<Road> listRoads() throws CustomServiceException {
        try {
            return roadDAO.listRoads();
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }    }

    @Override
    @Transactional
    public double getDistanceBetweenCities(City src, City dst) throws CustomServiceException {
        for (Road road: listRoads()) {
            if (road.getSrc().equals(src) && road.getDst().equals(dst))
                return road.getDistance();
        }
        return 0;
    }
}
