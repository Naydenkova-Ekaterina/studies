package shipping.controller;

import com.jcabi.aspects.Loggable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shipping.dto.*;
import shipping.exception.CustomServiceException;
import shipping.service.api.*;

import java.util.*;

@Controller
public class OrderController {

    private OrderService orderService;
    private WagonService wagonService;


    @Autowired
    public OrderController(OrderService orderService, WagonService wagonService) {
        this.orderService = orderService;
        this.wagonService = wagonService;

    }

    @Autowired
    private ModelMapper modelMapper;

    @Loggable(Loggable.DEBUG)
    @GetMapping("/orders/listOrdersDTO")
    public @ResponseBody List<OrderDTO> listOrdersDTO(){
        try {
            return orderService.listOrders();
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/orders/getOrderRoute/{id}")
    public @ResponseBody String getOrderRoute(@PathVariable("id") int id){
        try {
            return orderService.getOrderById(id).getWay();
        } catch (CustomServiceException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/orders/getOrderCargoes/{id}")
    public @ResponseBody List<CargoDTO> getOrderCargoes(@PathVariable("id") int id){
        try {
           return orderService.getOrderCargoes(id);
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/orders/getOrderDrivers/{id}")
    public @ResponseBody List<DriverDto> getOrderDrivers(@PathVariable("id") int id){
        try {
            return orderService.getOrderDrivers(id);
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/orders")
    public String listOrders(Model model) {
        try {
            model.addAttribute("order", new OrderDTO());
            model.addAttribute("wagons", wagonService.listWagons());
            model.addAttribute("listOrders", orderService.listOrders());
            return "order";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/order/add")
    public String addOrder(@ModelAttribute("order") OrderDTO orderDTO){
        try {
            validate(orderDTO);
            orderService.addOrder(orderDTO);
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
            return orderService.addCargoToExistingOrder(idOrder, idCargo);
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
