package shipping.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shipping.dto.*;
import shipping.dto.converter.*;
import shipping.exception.CustomServiceException;
import shipping.model.*;
import shipping.service.api.*;

import java.time.LocalTime;
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

            driverService.addDriver(driverConverter.convertToEntity(driverDto));

            return "redirect:/drivers";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

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

    @PostMapping("/driver/remove/{id}")
    public String removeDriver(@PathVariable("id") int id) {
        try {
            driverService.removeDriver(id);
            return "redirect:/drivers";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/driver/edit/{id}")
    public @ResponseBody DriverDto edit(@PathVariable("id") int id) {
        try {
            driverConverter = new DriverConverter(modelMapper);
            return  driverConverter.convertToDto(driverService.getDriverById(id));
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

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

            Wagon wagon = wagonService.getWagonById(driverDto.getWagon_id());
            driverDto.setWagon(wagonConverter.convertToDto(wagon));

            Order order = orderService.getOrderById(Integer.valueOf(driverDto.getOrder_id()));
            driverDto.setOrder(orderConverter.convertToDto(order));

            User user = userService.getUserById(Integer.valueOf(driverDto.getUser_id()));
            driverDto.setUser(userConverter.convertToDto(user));

            driverService.updateDriver(driverConverter.convertToEntity(driverDto));
            return "redirect:/drivers";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/driver/info")
    public String info(Model model) {
        try {
            waypointConverter = new WaypointConverter(modelMapper);
            Driver driver = driverService.getAuthenticatedDriver();

            int personalNumber = driver.getId();
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

            DriverInfoDTO driverInfoDTO = new DriverInfoDTO(personalNumber, wagonId, codrivers, orderId, waypointDTOList);

            model.addAttribute("selDriverInfo", driverInfoDTO);

            return "driverInfo";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/driver/updateInfo/{id}")
    public void updateInfo(@PathVariable("id") int id){
        try {
            Driver driver = driverService.getDriverById(id);


            driverService.updateDriver(driver);

        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

   /* @GetMapping("/driver/info/{id}")
    public @ResponseBody void info(@PathVariable("id") int id) {
        try {
            driverConverter = new DriverConverter(modelMapper);
            Driver driver = driverService.getDriverById(id);
            int personalNumber = driver.getId();
            String wagonId = driver.getWagon().getId();

            List<Integer> codrivers = driverService.driversByWagon(wagonId);

            Order order = orderService.getOrderByWagon(wagonId);
            List<Waypoint> waypointList = waypointService.waypointsForOrder(order.getId());
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }
*/

    @GetMapping("/driver/getSuitable/{id}")
    public @ResponseBody List<DriverDto> getSuitableDrivers(@PathVariable("id") int id) {
        try {
            driverConverter = new DriverConverter(modelMapper);
            routeConverter = new RouteConverter(modelMapper);

            Order order = orderService.getOrderById(id);

            Set<City> cities = order.getRoute().getCityList();

            LocalTime time = routeService.getRouteTime(cities);

            Map<Driver, LocalTime> map = driverService.getSuitableDrivers(order, time);
            List<DriverDto> result = map.keySet().stream().map(driver -> driverConverter.convertToDto(driver)).collect(Collectors.toList());

            return result;
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/driver/setOrder/{idDriver}/{idOrder}")
    public void setOrder(@PathVariable("idDriver") int idDriver, @PathVariable("idOrder") int idOrder) {
        try {
            driverConverter = new DriverConverter(modelMapper);
            orderConverter = new OrderConverter(modelMapper);

            Order order = orderService.getOrderById(idOrder);
            Driver driver = driverService.getDriverById(idDriver);

            driver.setOrder(order);
            driver.setWagon(order.getWagon());

            driverService.updateDriver(driver);

        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }


}
