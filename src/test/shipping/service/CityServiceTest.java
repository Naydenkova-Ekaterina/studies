package shipping.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import shipping.dao.CityDAO;
import shipping.dto.CityDTO;
import shipping.dto.converter.CityConverter;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.City;
import shipping.service.impl.CityServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static  org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {

    @InjectMocks
    private CityServiceImpl cityService;

    @Mock
    private CityDAO cityDAO;

    private List<City> cityList = new ArrayList<>();

    private CityDTO cityDTO;

    private ModelMapper modelMapper = new ModelMapper();

    @Mock
    private CityConverter cityConverter = new CityConverter(modelMapper);


    @Before
    public void init() {

        City city1 = new City();
        city1.setId(1);
        city1.setName("Moscow");

        City city2 = new City();
        city2.setId(2);
        city2.setName("Saint-Petersburg");

        City city3 = new City();
        city3.setId(3);
        city3.setName("Vladivostok");

        cityList.add(city1);
        cityList.add(city2);
        cityList.add(city3);

    }

    @Before
    public void initDTO() {
        cityDTO = new CityDTO();
        cityDTO.setId(1);
        cityDTO.setName("Moscow");
    }

    @Test
    public void getCityByNameTest() throws CustomServiceException, CustomDAOException {

        when(cityConverter.convertToDto(any(City.class))).thenReturn(cityDTO);

        when(cityDAO.listCities()).thenReturn(cityList);

        CityDTO city = cityService.getCityByName("Moscow");

        assertEquals(1, city.getId());

    }


}
