package shipping.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shipping.dto.CityDTO;
import shipping.dto.DriverDto;
import shipping.dto.OrderDTO;
import shipping.dto.WagonDTO;
import shipping.exception.CustomServiceException;
import shipping.model.City;
import shipping.model.Driver;
import shipping.model.Order;
import shipping.model.Wagon;
import shipping.service.api.CityService;
import shipping.service.api.DriverService;
import shipping.service.api.OrderService;
import shipping.service.api.WagonService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DriverController {

    private DriverService driverService;

    private CityService cityService;

    private WagonService wagonService;

    private OrderService orderService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public DriverController(DriverService driverService, CityService cityService, WagonService wagonService, OrderService orderService) {
        this.driverService = driverService;
        this.cityService = cityService;
        this.wagonService = wagonService;
        this.orderService = orderService;
    }

    @GetMapping("/drivers")
    public String listDrivers(Model model) {
        try {
            model.addAttribute("driver", new DriverDto());
            List<City> cities = cityService.listCities();
            model.addAttribute("cities", cities.stream()
                    .map(city -> convertCityToDto(city))
                    .collect(Collectors.toList()));

            List<Driver> drivers = driverService.listDrivers();
            model.addAttribute("listDrivers", drivers.stream()
                    .map(driver -> convertToDto(driver))
                    .collect(Collectors.toList()));
            return "driver";

        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/driver/add")
    public String addDriver(@ModelAttribute("driver") DriverDto driverDto) {
        try {

            City city = cityService.getCityById(Integer.valueOf(driverDto.getCity_id()));
            driverDto.setCity(convertCityToDto(city));

            Wagon wagon = wagonService.getWagonById(driverDto.getWagon_id());
            driverDto.setWagon(convertWagonToDto(wagon));

            Order order = orderService.getOrderById(Integer.valueOf(driverDto.getOrder_id()));
            driverDto.setOrder(convertOrderToDto(order));

            driverService.addDriver(convertToEntity(driverDto));

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
            return  convertToDto(driverService.getDriverById(id));
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/driver/update")
    public String updateDriver(@ModelAttribute("driver") DriverDto driverDto) {
        try {
            City city = cityService.getCityById(Integer.valueOf(driverDto.getCity_id()));
            driverDto.setCity(convertCityToDto(city));

            Wagon wagon = wagonService.getWagonById(driverDto.getWagon_id());
            driverDto.setWagon(convertWagonToDto(wagon));

            Order order = orderService.getOrderById(Integer.valueOf(driverDto.getOrder_id()));
            driverDto.setOrder(convertOrderToDto(order));
            
            driverService.updateDriver(convertToEntity(driverDto));
            return "redirect:/drivers";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    public DriverDto convertToDto(Driver driver) {
        DriverDto driverDto = modelMapper.map(driver, DriverDto.class);
        return driverDto;
    }

    private Driver convertToEntity(DriverDto driverDto) {
        Driver driver = modelMapper.map(driverDto, Driver.class);
        return driver;
    }

    public CityDTO convertCityToDto(City city) {
        CityDTO cityDTO = modelMapper.map(city, CityDTO.class);
        return cityDTO;
    }

    public WagonDTO convertWagonToDto(Wagon wagon) {
        WagonDTO wagonDTO = modelMapper.map(wagon, WagonDTO.class);
        return wagonDTO;
    }

    public OrderDTO convertOrderToDto(Order order) {
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        return orderDTO;
    }

}
