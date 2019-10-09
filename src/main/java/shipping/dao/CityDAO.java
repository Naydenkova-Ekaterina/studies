package shipping.dao;

import shipping.exception.CustomDAOException;
import shipping.model.City;

import java.util.List;

public interface CityDAO {

    void addCity(City city) throws CustomDAOException;
    void update(City city) throws CustomDAOException;
    List<City> listCities() throws CustomDAOException;
    City getCity(int id) throws CustomDAOException;
    void removeCity(int id) throws CustomDAOException;

}
