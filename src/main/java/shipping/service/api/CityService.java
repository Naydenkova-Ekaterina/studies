package shipping.service.api;

import shipping.exception.CustomServiceException;
import shipping.model.City;

import java.util.List;

public interface CityService {

    List<City> listCities() throws CustomServiceException;
    City getCityById(int id) throws CustomServiceException;

}
