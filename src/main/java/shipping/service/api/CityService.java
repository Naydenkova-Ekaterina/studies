package shipping.service.api;

import shipping.dto.CityDTO;
import shipping.exception.CustomServiceException;

import java.util.ArrayList;
import java.util.List;

public interface CityService {

    List<CityDTO> listCities() throws CustomServiceException;
    CityDTO getCityById(int id) throws CustomServiceException;
    CityDTO getCityByName(String name) throws CustomServiceException;
    List<CityDTO> listCitiesDTO() throws CustomServiceException;
    ArrayList<String> getCityNames() throws CustomServiceException;

    }
