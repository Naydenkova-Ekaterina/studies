package shipping.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import shipping.dao.DriverDAO;
import shipping.dto.*;
import shipping.dto.converter.DriverConverter;
import shipping.enums.DriverStatus;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.*;
import shipping.service.api.CityService;
import shipping.service.api.MqService;
import shipping.service.api.OrderService;
import shipping.service.api.UserService;
import shipping.service.impl.DriverServiceImpl;

import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DriverServiceTest {

    @InjectMocks
    private DriverServiceImpl driverService;

    @Mock
    private OrderService orderService;

    @Mock
    private CityService cityService;

    @Mock
    private UserService userService;

    @Mock
    private MqService mqService;

    @Mock
    private DriverDAO driverDAO;

    private ModelMapper modelMapper = new ModelMapper();

    private DriverDto driverDto;

    private List<Driver> driverList = new ArrayList<>();

    @Mock
    private DriverConverter driverConverter = new DriverConverter(modelMapper);

    @Before
    public void init() {
        User user = new User();
        user.setId(1);
        user.setEmail("test");
        user.setPassword("test");

        Driver driver = new Driver();
        driver.setId(1);
        driver.setName("test");
        driver.setSurname("test");
        driver.setStatus(DriverStatus.rest);
        driver.setUser(user);
        driver.setWorkedHours(LocalTime.of(2,0));

        driverList.add(driver);
    }

    @Before
    public void initDTO() {

        CityDTO cityDTO = new CityDTO();
        cityDTO.setId(1);
        cityDTO.setName("Moscow");

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test");
        userDTO.setPassword("test");

        driverDto = new DriverDto();
        driverDto.setId(1);
        driverDto.setName("name");
        driverDto.setSurname("surname");
        driverDto.setCity(cityDTO);
        driverDto.setStatus(String.valueOf(DriverStatus.rest));
        driverDto.setUser(userDTO);


        driverDto.setWorkedHours("08:08");
        WagonDTO wagonDTO = new WagonDTO();
        wagonDTO.setId("ab12345");
        driverDto.setWagon(wagonDTO);
    }


    @Test
    public void listDriversTest() throws CustomDAOException, CustomServiceException {
        when(driverConverter.convertToDto(any(Driver.class))).thenReturn(driverDto);

        when(driverDAO.listDrivers()).thenReturn(driverList);

        driverService.listDrivers();
        verify(driverDAO).listDrivers();
    }

    @Test(expected = CustomServiceException.class)
    public void removeDriverTest() throws CustomServiceException, CustomDAOException {
        Driver driver = new Driver();
        driver.setId(1);
        driver.setName("name");
        driver.setSurname("surname");
        driver.setOrder(new Order());

        when(driverDAO.getDriver(1)).thenReturn(driver);

        driverService.removeDriver(1);

    }

    @Test
    public void driversByWagonTest() throws CustomServiceException, CustomDAOException {

        when(driverConverter.convertToDto(any(Driver.class))).thenReturn(driverDto);

        when(driverDAO.listDrivers()).thenReturn(driverList);

        List<Integer> list = driverService.driversByWagon("ab12345");

        assertTrue(list.contains(1));
        assertTrue(!list.contains(2));

    }

    @Test
    public void getSuitableDriversTest() throws CustomServiceException, CustomDAOException {

        City city = new City();
        city.setId(1);
        city.setName("Moscow");
        Order order = new Order();
        order.setId(1);
        Wagon wagon = new Wagon();
        wagon.setId("ab12345");
        wagon.setCity(city);
        order.setWagon(wagon);

        when(driverConverter.convertToDto(any(Driver.class))).thenReturn(driverDto);

        when(driverDAO.getAvailableDrivers(wagon.getCity())).thenReturn(driverList);

        Map<DriverDto, LocalTime> map = driverService.getSuitableDrivers(order, LocalTime.of(5,0));

        for (DriverDto d: map.keySet()) {
            assertTrue(LocalTime.parse(d.getWorkedHours()).getHour() <= 176);
        }

    }

    @Test
    public void driverInfoTest() throws CustomServiceException {

        driverDto.setWagon(null);

        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User("test" , "test" , AuthorityUtils.NO_AUTHORITIES);

        when(userService.getAuthenticatedUser()).thenReturn(user);

        when(driverConverter.convertToDto(any(Driver.class))).thenReturn(driverDto);

        DriverInfoDTO driverInfoDTO = driverService.info();

        assertTrue(driverInfoDTO.getName().equals("name"));

        assertTrue(driverInfoDTO.getSurname().equals("surname"));

    }

    @Test
    public void setOrderTest() throws CustomServiceException {

        CityDTO cityDTO = new CityDTO();
        cityDTO.setId(1);
        cityDTO.setName("Moscow");

        DriverDto driverDto = new DriverDto();
        driverDto.setId(1);
        driverDto.setCity(cityDTO);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1);

        when(driverConverter.convertToDto(any(Driver.class))).thenReturn(driverDto);

        when(cityService.getCityById(1)).thenReturn(cityDTO);

        when(orderService.getOrderById(1)).thenReturn(orderDTO);

        driverService.setOrder(1,1);
        assertTrue(driverDto.getOrder().equals(orderDTO));

    }

    @Test
    public void changeDriverStatusTest() throws CustomServiceException, CustomDAOException {

        driverDto.setWagon(null);

        assertTrue(driverDto.getStatus().equals("rest"));

        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User("test" , "test" , AuthorityUtils.NO_AUTHORITIES);

        when(userService.getAuthenticatedUser()).thenReturn(user);

        when(driverConverter.convertToDto(any(Driver.class))).thenReturn(driverDto);

        when(driverDAO.listDrivers()).thenReturn(driverList);

        driverService.changeDriverStatus("shift");

        assertTrue(driverDto.getStatus().equals("shift"));

    }

    @Test
    public void startShiftTest() throws CustomServiceException, CustomDAOException {
        driverDto.setWagon(null);

        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User("test" , "test" , AuthorityUtils.NO_AUTHORITIES);

        when(userService.getAuthenticatedUser()).thenReturn(user);

        when(driverConverter.convertToDto(any(Driver.class))).thenReturn(driverDto);

        when(driverDAO.listDrivers()).thenReturn(driverList);

        assertTrue(driverDto.getStatus().equals("rest"));

        driverService.startShift();

        assertTrue(driverDto.getStatus().equals("shift"));

    }

    @Test
    public void endShiftTest() throws CustomServiceException, CustomDAOException {
        driverDto.setWagon(null);

        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User("test" , "test" , AuthorityUtils.NO_AUTHORITIES);

        when(userService.getAuthenticatedUser()).thenReturn(user);

        when(driverConverter.convertToDto(any(Driver.class))).thenReturn(driverDto);

        when(driverDAO.listDrivers()).thenReturn(driverList);

        driverService.endShift();

        assertTrue(driverDto.getStatus().equals("rest"));

    }

    @Test
    public void getDriverCargoesTest() throws CustomServiceException {
        driverDto.setWagon(null);

        CargoDTO cargoDTO = new CargoDTO();
        cargoDTO.setName("cargo");

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1);
        Set<CargoDTO> set = new HashSet<>();
        set.add(cargoDTO);
        orderDTO.setCargoSet(set);

        driverDto.setOrder(orderDTO);

        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User("test" , "test" , AuthorityUtils.NO_AUTHORITIES);

        when(userService.getAuthenticatedUser()).thenReturn(user);

        when(driverConverter.convertToDto(any(Driver.class))).thenReturn(driverDto);

        List<CargoDTO> list = driverService.getDriverCargoes();

        assertTrue(list.size() == 1);

    }


}
