package shipping.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shipping.dto.*;
import shipping.dto.converter.*;
import shipping.exception.CustomServiceException;
import shipping.model.*;
import shipping.service.api.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DriverController {

    private DriverService driverService;

    private CityService cityService;

    private WagonService wagonService;

    private OrderService orderService;

    private UserService userService;

    private DriverConverter driverConverter;

    private CityConverter cityConverter;

    private WagonConverter wagonConverter;

    private OrderConverter orderConverter;

    private UserConverter userConverter;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public DriverController(DriverService driverService, CityService cityService, WagonService wagonService, OrderService orderService, UserService userService) {
        this.driverService = driverService;
        this.cityService = cityService;
        this.wagonService = wagonService;
        this.orderService = orderService;
        this.userService = userService;
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

            Wagon wagon = wagonService.getWagonById(driverDto.getWagon_id());
            driverDto.setWagon(wagonConverter.convertToDto(wagon));

            Order order = orderService.getOrderById(Integer.parseInt(driverDto.getOrder_id()));
            driverDto.setOrder(orderConverter.convertToDto(order));

            User user = userService.getUserById(Integer.parseInt(driverDto.getUser_id()));
            driverDto.setUser(userConverter.convertToDto(user));

            driverService.addDriver(driverConverter.convertToEntity(driverDto));

            return "redirect:/drivers";
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

}
