package shipping.service.impl;

import org.apache.log4j.Logger;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shipping.dao.DriverDAO;
import shipping.dao.DriverShiftDAO;
import shipping.dao.WagonDAO;
import shipping.dto.*;
import shipping.dto.converter.*;
import shipping.enums.DriverStatus;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.Driver;
import shipping.model.Order;
import shipping.model.Wagon;
import shipping.service.api.*;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements DriverService {

    private static final Logger logger = Logger.getLogger(DriverServiceImpl.class);

    private DriverDAO driverDAO;

    private ModelMapper modelMapper;

    private DriverConverter driverConverter;


    private UserConverter userConverter;

    private CargoConverter cargoConverter;

    private CityConverter cityConverter;

    private CityService cityService;

    private WaypointConverter waypointConverter;

    private OrderConverter orderConverter;

    private WagonService wagonService;

    private WaypointService waypointService;

    @Autowired
    public void setWaypointService(WaypointService waypointService) {
        this.waypointService = waypointService;
    }

    @Autowired
    public void setWagonService(WagonService wagonService) {
        this.wagonService = wagonService;
    }

    private OrderService orderService;

    private RouteService routeService;

    @Autowired
    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    private UserService userService;

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    @Override
    @Transactional
    public void addDriver(DriverDto driver) throws CustomServiceException {
        try {
            driverConverter = new DriverConverter(modelMapper);

            CityDTO city = cityService.getCityById(Integer.parseInt(driver.getCity_id()));
            driver.setCity(city);

            Driver checkDriver = driverDAO.getDriver(driver.getId());

            if (checkDriver != null) {
                throw new CustomServiceException("Selected id already exists");
            }

            driverDAO.addDriver(driverConverter.convertToEntity(driver));
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public void updateDriver(DriverDto driverDto) throws CustomServiceException {
        try {
            driverConverter = new DriverConverter(modelMapper);
            CityDTO city = cityService.getCityById(Integer.valueOf(driverDto.getCity_id()));

            driverDto.setCity(city);
            if (!driverDto.getWagon_id().equals("-")) {
                WagonDTO wagon = wagonService.getWagonById(driverDto.getWagon_id());
                driverDto.setWagon(wagon);
            } else {
                driverDto.setWagon(null);
            }

            if (!driverDto.getOrder_id().equals("-")) {
                OrderDTO order = orderService.getOrderById(Integer.valueOf(driverDto.getOrder_id()));
                driverDto.setOrder(order);
            }
            if (!driverDto.getUser_id().equals("-")) {
                UserDTO user = userService.getUserById(Integer.valueOf(driverDto.getUser_id()));
                driverDto.setUser(user);
            }
            driverDAO.update(driverConverter.convertToEntity(driverDto));
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public void updateDriverAfterSetOrder(DriverDto driverDto) throws CustomServiceException {
        try {
            driverConverter = new DriverConverter(modelMapper);
            CityDTO city = cityService.getCityById(driverDto.getCity().getId());

            driverDto.setCity(city);
            if (driverDto.getWagon() != null) {
                WagonDTO wagon = wagonService.getWagonById(driverDto.getWagon().getId());
                driverDto.setWagon(wagon);
            } else {
                driverDto.setWagon(null);
            }

            if (driverDto.getOrder() != null) {
                OrderDTO order = orderService.getOrderById(driverDto.getOrder().getId());
                driverDto.setOrder(order);
            }
            if (driverDto.getUser() != null) {
                UserDTO user = userService.getUserById(driverDto.getUser().getId());
                driverDto.setUser(user);
            }
            driverDAO.update(driverConverter.convertToEntity(driverDto));
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public List<DriverDto> listDrivers() throws CustomServiceException {
        try {
            driverConverter = new DriverConverter(modelMapper);
            return driverDAO.listDrivers().stream().map(driver -> driverConverter.convertToDto(driver)).collect(Collectors.toList());
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public DriverDto getDriverById(int id) throws CustomServiceException {
        try {
            driverConverter = new DriverConverter(modelMapper);

            return driverConverter.convertToDto(driverDAO.getDriver(id));
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public void removeDriver(int id) throws CustomServiceException {
        try {
            driverConverter = new DriverConverter(modelMapper);

            DriverDto driver = driverConverter.convertToDto(driverDAO.getDriver(id));
            if (driver == null) {
                throw new CustomServiceException("Driver wasn't found.");
            } /*else if (driver.getOrder() != null) {                           // should be returned
                throw new CustomServiceException("Driver can't be removed, because he has an order.");
            } else if (driver.getWagon() != null) {
                // need or not
            }*/
            driverDAO.removeDriver(id);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public List<Integer> driversByWagon(String id) throws CustomServiceException {
        List<Integer> drivers = new ArrayList<>();
        for (DriverDto driver: listDrivers()) {
            if (driver.getWagon() != null && driver.getWagon().getId().equals(id)) drivers.add(driver.getId());
        }
        return drivers;
    }

    @Override
    @Transactional
    public Map<DriverDto, LocalTime> getSuitableDrivers(Order order, LocalTime orderTime) throws CustomServiceException {
        try {
            driverConverter = new DriverConverter(modelMapper);

            Map<DriverDto, LocalTime> driversHoursMap = new HashMap<>();

            Wagon wagonForThisOrder = order.getWagon();
            if (wagonForThisOrder != null) {


                List<Driver> availableDriversInTheSameCity = driverDAO.getAvailableDrivers(wagonForThisOrder.getCity());

                //   LocalTime wagonShiftSize = wagonForThisOrder.getShiftSize(); // сколько часов длится одна смена
                //  int numberOfShifts = orderTime.getHour() / wagonShiftSize.getHour(); // сколько смен нужно для доставки заказа

                LocalDate now = LocalDate.now();// текущая дата
                LocalDate firstDayOfNextMonth = now.with(TemporalAdjusters.firstDayOfNextMonth());  // дата 1-го дня след.месяца
                int hoursUntilMonthEnd = Period.between(now, firstDayOfNextMonth).getDays() * 24;

                for (Driver driver : availableDriversInTheSameCity) {
                    if (driver.getWorkedHours() == null) { // если водитель ещё нигде не работал
                        driversHoursMap.put(driverConverter.convertToDto(driver), LocalTime.of(0, 0)); // добавляем
                        continue;
                    }
                    LocalTime workedHours = driver.getWorkedHours(); // сколько отработано уже часов у водителя

                    int hoursToWorkThisMonth = 176 - workedHours.getHour(); // сколько осталось часов работать в этом месяце
                            //LocalTime.of(176, 0).minus(workedHours.getHour(), ChronoUnit.HOURS).getHour();


                    // если часов до конца месяца больше, чем рабочих часов, а время в пути меньше, то добавляем
                    if (hoursUntilMonthEnd >= hoursToWorkThisMonth && hoursToWorkThisMonth >= orderTime.getHour() ||
                            now.atTime(LocalTime.now()).plusDays(orderTime.getHour() / 24).isAfter(firstDayOfNextMonth.atStartOfDay())) {
                        driversHoursMap.put(driverConverter.convertToDto(driver), workedHours);
                        continue;
                    }
                    // to do what?
                    // если часов до конца месяца меньше чем время в пути

                }
            }

            return driversHoursMap;
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public DriverDto getAuthenticatedDriver() throws CustomServiceException {
        org.springframework.security.core.userdetails.User user = userService.getAuthenticatedUser();
        try {
            driverConverter = new DriverConverter(modelMapper);

            List<Driver> drivers = driverDAO.listDrivers();
            for (Driver driver : drivers) {
                if (driver.getUser() != null && user.getUsername().equals(driver.getUser().getEmail())) {
                    return driverConverter.convertToDto(driver);
                }
            }
        }
        catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
        return driverConverter.convertToDto(new Driver());
    }

    @Override
    @Transactional
    public void addUser(DriverDto driverDto, String email) throws CustomServiceException {
        driverConverter = new DriverConverter(modelMapper);
        userConverter = new UserConverter(modelMapper);
        cityConverter = new CityConverter(modelMapper);
        CityDTO city = cityService.getCityById(Integer.parseInt(driverDto.getCity_id()));
        driverDto.setCity(city);
        UserDTO user = userService.findUserByEmail(email);
        driverDto.setUser(user);
        addDriver(driverDto);
    }

    @Override
    @Transactional
    public DriverInfoDTO info() throws CustomServiceException {
            waypointConverter = new WaypointConverter(modelMapper);
            DriverDto driver = getAuthenticatedDriver();

            int personalNumber = driver.getId();
            String name = driver.getName();
            String surname = driver.getSurname();
            String wagonId;
            List<Integer> codrivers;
            String orderId;
            List<WaypointDTO> waypointDTOList;
            if (driver.getWagon() != null) {
                wagonId = driver.getWagon().getId();
                codrivers = driversByWagon(wagonId);
                orderId = String.valueOf(orderService.getOrderByWagon(wagonId).getId());
                waypointDTOList = waypointService.waypointsForOrder(Integer.valueOf(orderId)).stream()
                        .map(waypoint -> waypointConverter.convertToDto(waypoint))
                        .collect(Collectors.toList());
            } else {
                wagonId = null;
                codrivers = null;
                orderId = null;
                waypointDTOList = null;
            }

            DriverInfoDTO driverInfoDTO = new DriverInfoDTO(personalNumber, name, surname, wagonId, codrivers, orderId, waypointDTOList);
            return driverInfoDTO;
        }

    @Override
    @Transactional
    public List<DriverDto> getSuitableDrivers(int id) throws CustomServiceException {
        driverConverter = new DriverConverter(modelMapper);
        orderConverter = new OrderConverter(modelMapper);

        OrderDTO order = orderService.getOrderById(id);

        List<CityDTO> cities = orderService.convertWayToList(order);

        LocalTime time = routeService.getRouteTime(cities);

        Map<DriverDto, LocalTime> map = getSuitableDrivers(orderConverter.convertToEntity(order), time);
        List<DriverDto> result = map.keySet().stream().collect(Collectors.toList());

        return result;
    }

    @Override
    @Transactional
    public void setOrder(int idDriver, int idOrder) throws CustomServiceException {
        driverConverter = new DriverConverter(modelMapper);
        orderConverter = new OrderConverter(modelMapper);

        OrderDTO order = orderService.getOrderById(idOrder);
        DriverDto driver = getDriverById(idDriver);
        driver.setOrder(order);
        driver.setWagon(order.getWagon());
        updateDriverAfterSetOrder(driver);
    }

    @Override
    @Transactional
    public void changeDriverStatus(String status) throws CustomServiceException {
        DriverDto driver = getAuthenticatedDriver();
        driver.setStatus(String.valueOf(DriverStatus.valueOf(status)));
        updateDriverAfterSetOrder(driver);
    }

    @Override
    @Transactional
    public void startShift() throws CustomServiceException {
        DriverDto driver = getAuthenticatedDriver();
        driver.setStatus(String.valueOf(DriverStatus.shift));
        updateDriverAfterSetOrder(driver);
    }

    @Override
    @Transactional
    public void endShift() throws CustomServiceException {
        driverConverter = new DriverConverter(modelMapper);
        Driver driver = driverConverter.convertToEntity(getAuthenticatedDriver());
        driver.setStatus(DriverStatus.rest);
        if (driver.getWorkedHours() == null){
            driver.setWorkedHours(LocalTime.of(8,0));
        } else {
            driver.setWorkedHours(driver.getWorkedHours().plusHours(8));
        }
        updateDriverAfterSetOrder(driverConverter.convertToDto(driver));
    }

    @Override
    @Transactional
    public List<CargoDTO> getDriverCargoes() throws CustomServiceException {
        driverConverter = new DriverConverter(modelMapper);
        cargoConverter = new CargoConverter(modelMapper);
        Driver driver = driverConverter.convertToEntity(getAuthenticatedDriver());
        List<CargoDTO> list = new ArrayList<>();
        if(driver.getOrder() != null) {
            list = driver.getOrder().getCargoSet().stream().map(cargo -> cargoConverter.convertToDto(cargo)).collect(Collectors.toList());
        }
        return list;
    }

    }
