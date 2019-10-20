package shipping.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shipping.dto.CargoDTO;
import shipping.dto.converter.CargoConverter;
import shipping.dto.converter.OrderConverter;
import shipping.dto.converter.WaypointConverter;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.Cargo;
import shipping.model.Order;
import shipping.model.Waypoint;
import shipping.service.api.CargoService;
import shipping.service.api.OrderService;
import shipping.service.api.WaypointService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CargoController {

    private CargoService cargoService;

    private WaypointService waypointService;

    private OrderService orderService;

    @Autowired
    private ModelMapper modelMapper;

    private CargoConverter cargoConverter;

    private WaypointConverter waypointConverter;

    private OrderConverter orderConverter;

    @Autowired
    public CargoController(CargoService cargoService, WaypointService waypointService, OrderService orderService) {
        this.cargoService = cargoService;
        this.waypointService = waypointService;
        this.orderService = orderService;
    }

    @GetMapping("/cargoes")
    public String listCargoes(Model model) {
        try {
            cargoConverter = new CargoConverter(modelMapper);
            waypointConverter = new WaypointConverter(modelMapper);
            orderConverter = new OrderConverter(modelMapper);
            model.addAttribute("cargo", new CargoDTO());
            List<Waypoint> waypoints = waypointService.listWaypoints();
            model.addAttribute("waypoints", waypoints.stream()
                    .map(waypoint -> waypointConverter.convertToDto(waypoint))
                    .collect(Collectors.toList()));
            List<Order> orders = orderService.listOrders();
            model.addAttribute("orders", orders.stream()
                    .map(order -> orderConverter.convertToDto(order))
                    .collect(Collectors.toList()));
            List<Cargo> cargoes = cargoService.listCargoes();
            model.addAttribute("listCargoes", cargoes.stream()
                    .map(cargo -> cargoConverter.convertToDto(cargo))
                    .collect(Collectors.toList()));
            return "cargo";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/cargo/listCargoDTOS")
    public @ResponseBody List<CargoDTO> listCargoDTOS(){
        try {
        cargoConverter = new CargoConverter(modelMapper);
        List<CargoDTO> cargoDTOS = cargoService.listCargoes().stream().map(cargo -> cargoConverter.convertToDto(cargo)).collect(Collectors.toList());
        return cargoDTOS;
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/cargo/listFreeCargoes")
    public @ResponseBody List<CargoDTO> listFreeCargoes(){
        try {
            cargoConverter = new CargoConverter(modelMapper);
            List<Cargo> result = new ArrayList<>();
            List<Cargo> cargos = cargoService.listCargoes();
            for (Cargo cargo: cargos) {
                if (cargo.getOrder() == null) {
                    result.add(cargo);
                }
            }
            return result.stream().map(cargo -> cargoConverter.convertToDto(cargo)).collect(Collectors.toList());
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/cargo/add")
    public String addCargo(@ModelAttribute("cargo") CargoDTO cargo) {
        try {
            cargoConverter = new CargoConverter(modelMapper);
            waypointConverter = new WaypointConverter(modelMapper);

            Waypoint src = waypointService.getWaypointById(Integer.parseInt(cargo.getSrc_id()));
            cargo.setSrc(waypointConverter.convertToDto(src));

            Waypoint dst = waypointService.getWaypointById(Integer.parseInt(cargo.getDst_id()));
            cargo.setDst(waypointConverter.convertToDto(dst));

            if (cargo.getOrderDTO_id() != null) {
                Order order = orderService.getOrderById(Integer.parseInt(cargo.getOrderDTO_id()));
                cargo.setOrderDTO(orderConverter.convertToDto(order));
            }
            cargoService.addCargo(cargoConverter.convertToEntity(cargo));

            return "redirect:/cargoes";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/cargo/remove/{id}")
    public String removeCargo(@PathVariable("id") int id) {
        try {
            cargoService.removeCargo(id);
            return "redirect:/cargoes";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/cargo/edit/{id}")
    public @ResponseBody CargoDTO edit(@PathVariable("id") int id) {
        try {
            cargoConverter = new CargoConverter(modelMapper);
            return cargoConverter.convertToDto(cargoService.getCargoById(id));
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/cargo/update")
    public String updateCargo(@ModelAttribute("cargo") CargoDTO cargo) {
        try {
            cargoConverter = new CargoConverter(modelMapper);

            waypointConverter = new WaypointConverter(modelMapper);

            Waypoint src = waypointService.getWaypointById(Integer.parseInt(cargo.getSrc_id()));
            cargo.setSrc(waypointConverter.convertToDto(src));

            Waypoint dst = waypointService.getWaypointById(Integer.parseInt(cargo.getDst_id()));
            cargo.setDst(waypointConverter.convertToDto(dst));

            cargoService.updateCargo(cargoConverter.convertToEntity(cargo));
            return "redirect:/cargoes";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

}
