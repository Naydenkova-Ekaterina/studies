package shipping.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shipping.dao.CityDAO;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.City;
import shipping.service.api.CityService;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private CityDAO cityDAO;

    public void setCityDAO(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    @Override
    @Transactional
    public List<City> listCities() throws CustomServiceException {
        try {
            return cityDAO.listCities();
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public City getCityById(int id) throws CustomServiceException {
        try {
            return cityDAO.getCity(id);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public City getCityByName(String name) throws CustomServiceException {
        try {
            for (City city: cityDAO.listCities()) {
                if (city.getName().equals(name)) return city;
            }
            return null;
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }
}
