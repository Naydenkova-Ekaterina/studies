package shipping.controller;

import com.jcabi.aspects.Loggable;
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

    @Loggable(Loggable.DEBUG)
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

    @Loggable(Loggable.DEBUG)
    @GetMapping("/orders/getOrderRoute/{id}")
    public @ResponseBody String getOrderRoute(@PathVariable("id") int id){
        try {

           // cityConverter = new CityConverter(modelMapper);
            Order order = orderService.getOrderById(id);
//            Set<City> cities = order.getRoute().getCityList();
//            for (City c: cities) {
//                System.out.println(c.getName());
//            }
//            List<City> sorted = cities.stream().sorted(Comparator.comparing(City::getId)).collect(Collectors.toList());
//            List<CityDTO> cityDTOS = sorted.stream().map(city -> cityConverter.convertToDto(city)).collect(Collectors.toList());
            return order.getWay();
        } catch (CustomServiceException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
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

    @Loggable(Loggable.DEBUG)
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

    @Loggable(Loggable.DEBUG)
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

    @Loggable(Loggable.DEBUG)
    @PostMapping("/order/add")
    public String addOrder(@ModelAttribute("order") OrderDTO orderDTO){
        try {
            orderConverter = new OrderConverter(modelMapper);
            cargoConverter = new CargoConverter(modelMapper);
            cityConverter = new CityConverter(modelMapper);
            routeConverter = new RouteConverter(modelMapper);

            validate(orderDTO);

            Cargo cargo = cargoService.getCargoById(Integer.valueOf(orderDTO.getCargoDTO_id()));

            int from = cityService.listCities().indexOf(cargo.getSrc().getCity());
            int to = cityService.listCities().indexOf(cargo.getDst().getCity());

            List<City> cities = routeService.getPath(from, to);
            String way = "";
            for (City c: cities) {
                way += c.getName() + " ";
            }
            System.out.println("WAY = " + way);

            RouteDTO routeDTO = new RouteDTO();
            routeDTO.setCityDTOList(cities.stream().map(city -> cityConverter.convertToDto(city)).collect(Collectors.toList()));

            orderDTO.setWay(way);
           // orderDTO.setRouteDTO(routeDTO);

            cargo.setOrder(orderConverter.convertToEntity(orderDTO));
            CargoDTO cargoDTO = cargoConverter.convertToDto(cargo);
            cargoDTO.setOrderDTO_id(String.valueOf(orderDTO.getId()));
            Set<CargoDTO> cargoDTOS = new HashSet<>();
            cargoDTOS.add(cargoDTO);

            orderDTO.setCargoDTOS(cargoDTOS);

            orderService.addOrder(orderConverter.convertToEntity(orderDTO));

            return "redirect:/orders";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    public void validate(OrderDTO orderDTO) throws CustomServiceException{
        if (orderDTO.getCargoDTO_id()==null || orderDTO.getCargoDTO_id().equals("")) {
            throw new CustomServiceException("No cargo selected.");
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/order/addCargoToExistingOrder/{idOrder}/{idCargo}")
    public @ResponseBody String addCargoToExistingOrder(@PathVariable("idOrder") int idOrder, @PathVariable("idCargo") int idCargo) {
        try {
            orderConverter = new OrderConverter(modelMapper);
            cargoConverter = new CargoConverter(modelMapper);
            routeConverter = new RouteConverter(modelMapper);
            cityConverter = new CityConverter(modelMapper);

            Order order = orderService.getOrderById(idOrder);
          //  Set<City> cities = order.getRoute().getCityList();
            //List<CityDTO> cityDTOS = cities.stream().map(city -> cityConverter.convertToDto(city)).collect(Collectors.toList());
            //RouteDTO routeDTO = routeConverter.convertToDto(order.getRoute());
            //routeDTO.setCityDTOList(cityDTOS);

            Cargo cargo = cargoService.getCargoById(idCargo);

            Set<Cargo> cargos = order.getCargoSet();
            Set<CargoDTO> list = cargos.stream().map(c -> cargoConverter.convertToDto(c)).collect(Collectors.toSet());

            OrderDTO orderDTO = orderConverter.convertToDto(order);
            CargoDTO cargoDTO = cargoConverter.convertToDto(cargo);

            orderDTO.setCargoDTOS(list);

         //   cityDTOS.stream().forEach(cityDTO -> System.out.println(cityDTO.getId() + " " + cityDTO.getName()));

//            if (!cityDTOS.contains(cargoDTO.getSrc().getCity())) {
//                return "Make a new order for this cargo.";
//            }

            if (!orderService.convertWayToList(order).contains(cargo.getSrc().getCity())) {
//                for (String str: orderService.convertWayToList(order)) {
//
//                }
//                System.out.println(order.getWay());
//                System.out.println(cargoDTO.getSrc().getCity().getName());
                return "Make a new order for this cargo.";

            }

            //RouteDTO newRouteDTO = routeService.remakeRoute(routeDTO, cargoDTO);

            String way = routeService.remakeRoute(order.getWay(), cargoDTO);

            //String way = "";
//            for (CityDTO c: newRouteDTO.getCityDTOList()) {
//                way += c.getName() + " ";
//            }


            //newRouteDTO.getCityDTOList().stream().forEach(cityDTO -> System.out.println(cityDTO.getId() + " " + cityDTO.getName()));
            orderDTO.getCargoDTOS().add(cargoDTO);
            orderDTO.setWay(way);
            //orderDTO.setRouteDTO(newRouteDTO);

            orderService.updateOrderAfterChangingRoute(orderConverter.convertToEntity(orderDTO));
            return "cargo was added";

        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
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
