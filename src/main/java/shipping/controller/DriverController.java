package shipping.controller;

import com.jcabi.aspects.Loggable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import shipping.dto.*;
import shipping.dto.converter.*;
import shipping.enums.DriverStatus;
import shipping.exception.CustomServiceException;
import shipping.model.*;
import shipping.service.api.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class DriverController {

    private DriverService driverService;

    private CityService cityService;

    private WagonService wagonService;

    private OrderService orderService;

    private UserService userService;

    private RouteService routeService;

    private DriverConverter driverConverter;

    private CityConverter cityConverter;

    private WagonConverter wagonConverter;

    private OrderConverter orderConverter;

    private UserConverter userConverter;

    private WaypointConverter waypointConverter;

    private WaypointService waypointService;

    private RouteConverter routeConverter;

    private CargoConverter cargoConverter;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public DriverController(DriverService driverService, CityService cityService, WagonService wagonService, OrderService orderService,
                            UserService userService, WaypointService waypointService, RouteService routeService) {
        this.driverService = driverService;
        this.cityService = cityService;
        this.wagonService = wagonService;
        this.orderService = orderService;
        this.userService = userService;
        this.waypointService = waypointService;
        this.routeService = routeService;
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/drivers")
    public String listDrivers(Model model) {
        try {
            driverConverter = new DriverConverter(modelMapper);
            cityConverter = new CityConverter(modelMapper);
            wagonConverter = new WagonConverter(modelMapper);
            orderConverter = new OrderConverter(modelMapper);
            userConverter = new UserConverter(modelMapper);

            model.addAttribute("driver", new DriverDto());
            List<City> cities = cityService.listCities();
            model.addAttribute("cities", cities.stream()
                    .map(city -> cityConverter.convertToDto(city))
                    .collect(Collectors.toList()));

            List<Wagon> wagons = wagonService.listWagons();
            model.addAttribute("wagons", wagons.stream()
                    .map(wagon -> wagonConverter.convertToDto(wagon))
                    .collect(Collectors.toList()));

            List<Order> orders = orderService.listOrders();
            model.addAttribute("orders", orders.stream()
                    .map(order -> orderConverter.convertToDto(order))
                    .collect(Collectors.toList()));

            List<User> users = userService.listUser();
            model.addAttribute("users", users.stream()
                    .map(user -> userConverter.convertToDto(user))
                    .collect(Collectors.toList()));

            List<Driver> drivers = driverService.listDrivers();
            model.addAttribute("listDrivers", drivers.stream()
                    .map(driver -> driverConverter.convertToDto(driver))
                    .collect(Collectors.toList()));
            return "driver";

        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/driver/add")
    public String addDriver(@ModelAttribute("driver") DriverDto driverDto) {
        try {
            driverConverter = new DriverConverter(modelMapper);
            cityConverter = new CityConverter(modelMapper);
            wagonConverter = new WagonConverter(modelMapper);
            orderConverter = new OrderConverter(modelMapper);
            userConverter = new UserConverter(modelMapper);

            City city = cityService.getCityById(Integer.parseInt(driverDto.getCity_id()));
            driverDto.setCity(cityConverter.convertToDto(city));

//            if (!driverDto.getWagon_id().equals("null")) {
//                Wagon wagon = wagonService.getWagonById(driverDto.getWagon_id());
//                driverDto.setWagon(wagonConverter.convertToDto(wagon));
//            }
//
//            Order order = orderService.getOrderById(Integer.parseInt(driverDto.getOrder_id()));
//            driverDto.setOrder(orderConverter.convertToDto(order));

//            User user = userService.getUserById(Integer.parseInt(driverDto.getUser_id()));
//            driverDto.setUser(userConverter.convertToDto(user));

//            DriverShiftDTO driverShiftDTO = new DriverShiftDTO();
//            driverDto.setDriverShiftDTO(driverShiftDTO);

            validate(driverDto);

            driverService.addDriver(driverConverter.convertToEntity(driverDto));

            return "redirect:/drivers";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    public void validate(DriverDto driverDto) throws CustomServiceException{
        if (driverDto.getName().equals("")) {
            throw new CustomServiceException("Name is not correct.");
        } else if (driverDto.getSurname().equals("")) {
            throw new CustomServiceException("Surname is not correct.");
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/driver/registerAdd/{email}")
    public ResponseEntity<Void> addUser(@RequestBody DriverDto driverDto, @PathVariable("email") String email) {
        try {
            driverConverter = new DriverConverter(modelMapper);
            userConverter = new UserConverter(modelMapper);
            cityConverter = new CityConverter(modelMapper);
            City city = cityService.getCityById(Integer.parseInt(driverDto.getCity_id()));
            driverDto.setCity(cityConverter.convertToDto(city));
            UserDTO user = userConverter.convertToDto(userService.findUserByEmail(email));
            driverDto.setUser(user);
            driverService.addDriver(driverConverter.convertToEntity(driverDto));
            return new ResponseEntity<>(HttpStatus.OK);


        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/driver/remove/{id}")
    public String removeDriver(@PathVariable("id") int id) {
        try {
            driverService.removeDriver(id);
            return "redirect:/drivers";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/driver/edit/{id}")
    public @ResponseBody
    DriverDto edit(@PathVariable("id") int id) {
        try {
            driverConverter = new DriverConverter(modelMapper);
            return driverConverter.convertToDto(driverService.getDriverById(id));
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/driver/editAuth")
    public @ResponseBody
    DriverDto edit() {
        try {
            driverConverter = new DriverConverter(modelMapper);
            Driver driver = driverService.getAuthenticatedDriver();


            return driverConverter.convertToDto(driver);
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/driver/update")
    public String updateDriver(@ModelAttribute("driver") DriverDto driverDto) {
        try {
            driverConverter = new DriverConverter(modelMapper);
            cityConverter = new CityConverter(modelMapper);
            wagonConverter = new WagonConverter(modelMapper);
            orderConverter = new OrderConverter(modelMapper);
            userConverter = new UserConverter(modelMapper);

            City city = cityService.getCityById(Integer.valueOf(driverDto.getCity_id()));
            driverDto.setCity(cityConverter.convertToDto(city));

            if (!driverDto.getWagon_id().equals("-")) {
                Wagon wagon = wagonService.getWagonById(driverDto.getWagon_id());
                driverDto.setWagon(wagonConverter.convertToDto(wagon));
            } else {
                driverDto.setWagon(null);
            }

            if (!driverDto.getOrder_id().equals("-")) {
                Order order = orderService.getOrderById(Integer.valueOf(driverDto.getOrder_id()));
                driverDto.setOrder(orderConverter.convertToDto(order));
            }

            if (!driverDto.getUser_id().equals("-")) {
                User user = userService.getUserById(Integer.valueOf(driverDto.getUser_id()));
                driverDto.setUser(userConverter.convertToDto(user));
            }

            validate(driverDto);

            driverService.updateDriver(driverConverter.convertToEntity(driverDto));
            return "redirect:/drivers";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/driver/info")
    public String info(Model model) {
        try {
            waypointConverter = new WaypointConverter(modelMapper);
            Driver driver = driverService.getAuthenticatedDriver();

            int personalNumber = driver.getId();
            String name = driver.getName();
            String surname = driver.getSurname();
            String wagonId;
            List<Integer> codrivers;
            String orderId;
            List<WaypointDTO> waypointDTOList;
            if (driver.getWagon() != null) {
                wagonId = driver.getWagon().getId();
                codrivers = driverService.driversByWagon(wagonId);
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

            model.addAttribute("selDriverInfo", driverInfoDTO);

            return "driverInfo";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/driver/updateInfo/{id}")
    public void updateInfo(@PathVariable("id") int id) {
        try {
            Driver driver = driverService.getDriverById(id);


            driverService.updateDriver(driver);

        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/driver/getSuitable/{id}")
    public @ResponseBody List<DriverDto> getSuitableDrivers(@PathVariable("id") int id) {
        try {
            driverConverter = new DriverConverter(modelMapper);
            routeConverter = new RouteConverter(modelMapper);

            Order order = orderService.getOrderById(id);

            List<City> cities = orderService.convertWayToList(order);

            LocalTime time = routeService.getRouteTime(cities);

            Map<Driver, LocalTime> map = driverService.getSuitableDrivers(order, time);
            List<DriverDto> result = map.keySet().stream().map(driver -> driverConverter.convertToDto(driver)).collect(Collectors.toList());

            return result;
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/driver/setOrder/{idDriver}/{idOrder}")
    public ResponseEntity<Void> setOrder(@PathVariable("idDriver") int idDriver, @PathVariable("idOrder") int idOrder) {
        try {
            driverConverter = new DriverConverter(modelMapper);
            orderConverter = new OrderConverter(modelMapper);

            Order order = orderService.getOrderById(idOrder);
            Driver driver = driverService.getDriverById(idDriver);

            driver.setOrder(order);
            driver.setWagon(order.getWagon());

            driverService.updateDriver(driver);
            return new ResponseEntity<>(HttpStatus.OK);


        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/driver/changeDriverStatus/{status}")
    public ResponseEntity<Void> changeDriverStatus(@PathVariable("status") String status) {
        try {
            Driver driver = driverService.getAuthenticatedDriver();
            driver.setStatus(DriverStatus.valueOf(status));
            driverService.updateDriver(driver);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/driver/startShift")
    public ResponseEntity<Void> startShift(){
        try {
            Driver driver = driverService.getAuthenticatedDriver();
            driver.setStatus(DriverStatus.shift);
            driverService.updateDriver(driver);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/driver/endShift")
    public ResponseEntity<Void> endShift(){
        try {
            Driver driver = driverService.getAuthenticatedDriver();
            driver.setStatus(DriverStatus.rest);
            if (driver.getWorkedHours() == null){
                driver.setWorkedHours(LocalTime.of(8,0));
            } else {
                driver.setWorkedHours(driver.getWorkedHours().plusHours(8));
            }
            driverService.updateDriver(driver);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/driver/getDriverCargoes")
    public @ResponseBody List<CargoDTO> getDriverCargoes(){
        try {
            driverConverter = new DriverConverter(modelMapper);
            cargoConverter = new CargoConverter(modelMapper);
            Driver driver = driverService.getAuthenticatedDriver();
            List<CargoDTO> list = new ArrayList<>();
            if(driver.getOrder() != null) {
                list = driver.getOrder().getCargoSet().stream().map(cargo -> cargoConverter.convertToDto(cargo)).collect(Collectors.toList());
            }
            return list;

        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }


}
