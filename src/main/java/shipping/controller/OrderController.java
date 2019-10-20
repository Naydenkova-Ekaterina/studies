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

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class OrderController {

    private OrderService orderService;
    private WagonService wagonService;
    private WaypointService waypointService;
    private CargoService cargoService;
    private RouteService routeService;
    private CityService cityService;

    private OrderConverter orderConverter;
    private WagonConverter wagonConverter;
    private CargoConverter cargoConverter;
    private CityConverter cityConverter;
    private RouteConverter routeConverter;

    @Autowired
    public OrderController(OrderService orderService, WagonService wagonService, WaypointService waypointService, CargoService cargoService,
                           RouteService routeService, CityService cityService) {
        this.orderService = orderService;
        this.wagonService = wagonService;
        this.waypointService = waypointService;
        this.cargoService = cargoService;
        this.routeService = routeService;
        this.cityService = cityService;
    }

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/orders/listOrdersDTO")
    public @ResponseBody List<OrderDTO> listOrdersDTO(){
        try {
            orderConverter = new OrderConverter(modelMapper);

            List<OrderDTO> orderDTOS = orderService.listOrders().stream().map(order -> orderConverter.convertToDto(order)).collect(Collectors.toList());
            return orderDTOS;
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/orders/getOrderRoute/{id}")
    public @ResponseBody List<CityDTO> getOrderRoute(@PathVariable("id") int id){
        try {

            cityConverter = new CityConverter(modelMapper);
            Order order = orderService.getOrderById(id);
            Set<City> cities = order.getRoute().getCityList();
            List<City> sorted = cities.stream().sorted(Comparator.comparing(City::getId)).collect(Collectors.toList());
            List<CityDTO> cityDTOS = sorted.stream().map(city -> cityConverter.convertToDto(city)).collect(Collectors.toList());
            return cityDTOS;
        } catch (CustomServiceException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/orders/getOrderCargoes/{id}")
    public @ResponseBody List<CargoDTO> getOrderCargoes(@PathVariable("id") int id){
        try {
            orderConverter = new OrderConverter(modelMapper);
            cargoConverter = new CargoConverter(modelMapper);
            Order order = orderService.getOrderById(id);
            Set<Cargo> cargos = order.getCargoSet();
            List<CargoDTO> list = cargos.stream().map(cargo -> cargoConverter.convertToDto(cargo)).collect(Collectors.toList());
            return list;

        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/orders/getOrderDrivers/{id}")
    public @ResponseBody List<DriverDto> getOrderDrivers(@PathVariable("id") int id){
        try {
            orderConverter = new OrderConverter(modelMapper);

            OrderDTO orderDTO = orderConverter.convertToDto(orderService.getOrderById(id));
            List<DriverDto> list = orderDTO.getDriverSet();
            return list;

        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/orders")
    public String listOrders(Model model) {
        try {
            orderConverter = new OrderConverter(modelMapper);
            wagonConverter = new WagonConverter(modelMapper);
            cargoConverter = new CargoConverter(modelMapper);

            model.addAttribute("order", new OrderDTO());

            List<Wagon> wagons = wagonService.listWagons();
            model.addAttribute("wagons", wagons.stream()
                    .map(wagon -> wagonConverter.convertToDto(wagon))
                    .collect(Collectors.toList()));

            List<Order> orders = orderService.listOrders();
            model.addAttribute("listOrders", orders.stream()
                    .map(order -> orderConverter.convertToDto(order))
                    .collect(Collectors.toList()));


            return "order";

        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/order/add")
    public String addOrder(@ModelAttribute("order") OrderDTO orderDTO){
        try {
            orderConverter = new OrderConverter(modelMapper);
            cargoConverter = new CargoConverter(modelMapper);
            cityConverter = new CityConverter(modelMapper);
            routeConverter = new RouteConverter(modelMapper);

            Cargo cargo = cargoService.getCargoById(Integer.valueOf(orderDTO.getCargoDTO_id()));

            int from = cityService.listCities().indexOf(cargo.getSrc().getCity());
            int to = cityService.listCities().indexOf(cargo.getDst().getCity());

            List<City> cities = routeService.getPath(from, to);

            RouteDTO routeDTO = new RouteDTO();
            routeDTO.setCityDTOList(cities.stream().map(city -> cityConverter.convertToDto(city)).collect(Collectors.toList()));

            orderDTO.setRouteDTO(routeDTO);

            cargo.setOrder(orderConverter.convertToEntity(orderDTO));
            CargoDTO cargoDTO = cargoConverter.convertToDto(cargo);
            cargoDTO.setOrderDTO(orderDTO);
            Set<CargoDTO> cargoDTOS = new HashSet<>();
            cargoDTOS.add(cargoDTO);

            orderDTO.setCargoDTOS(cargoDTOS);

            orderService.addOrder(orderConverter.convertToEntity(orderDTO));

            return "redirect:/orders";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/order/remove/{id}")
    public String removeOrder(@PathVariable("id") int id) {
        try {
            orderService.removeOrder(id);
            return "redirect:/orders";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }


}
