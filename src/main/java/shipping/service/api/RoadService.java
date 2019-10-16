package shipping.service.api;

import shipping.exception.CustomServiceException;
import shipping.model.City;
import shipping.model.Road;

import java.util.List;

public interface RoadService {

    List<Road> listRoads() throws CustomServiceException;
    double getDistanceBetweenCities(City src, City dst) throws CustomServiceException;

}
