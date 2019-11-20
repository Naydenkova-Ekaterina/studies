package shipping.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shipping.dao.CityDAO;
import shipping.dto.CityDTO;
import shipping.dto.converter.CityConverter;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.City;
import shipping.service.api.CityService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    private CityDAO cityDAO;

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        cityConverter = new CityConverter(modelMapper);
    }

    private CityConverter cityConverter;

    public void setCityDAO(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    @Override
    @Transactional
    public List<CityDTO> listCities() throws CustomServiceException {
        try {
//            cityConverter = new CityConverter(modelMapper);
            return cityDAO.listCities().stream()
                    .map(cargo -> cityConverter.convertToDto(cargo))
                    .collect(Collectors.toList());
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public CityDTO getCityById(int id) throws CustomServiceException {
        try {
            cityConverter = new CityConverter(modelMapper);
            return cityConverter.convertToDto(cityDAO.getCity(id));
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public CityDTO getCityByName(String name) throws CustomServiceException {
        for (CityDTO city: listCities()) {
            if (city.getName().equals(name)) return city;
        }
        return null;
    }

    @Override
    @Transactional
    public List<CityDTO> listCitiesDTO() throws CustomServiceException {
        List<CityDTO> cityDTOList = new ArrayList<>();
        for (CityDTO city: listCities()) {
            CityDTO cityDTO = new CityDTO();
            cityDTO.setId(city.getId());
            cityDTO.setName(city.getName());
            cityDTOList.add(cityDTO);
        }
        return cityDTOList;
    }

    @Override
    @Transactional
    public ArrayList<String> getCityNames() throws CustomServiceException {
        ArrayList<String> list = new ArrayList<>();
        for (CityDTO city : listCities()) {
            list.add(city.getName());
        }
        return list;
    }


    }
