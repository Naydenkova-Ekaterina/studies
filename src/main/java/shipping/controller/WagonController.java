package shipping.controller;

import com.jcabi.aspects.Loggable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shipping.dto.converter.CityConverter;
import shipping.dto.converter.OrderConverter;
import shipping.dto.converter.WagonConverter;
import shipping.dto.WagonDTO;
import shipping.exception.CustomServiceException;
import shipping.model.City;
import shipping.model.Order;
import shipping.model.Wagon;
import shipping.service.api.CityService;
import shipping.service.api.OrderService;
import shipping.service.api.WagonService;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WagonController {

    private WagonService wagonService;

    private CityService cityService;

    private OrderService orderService;

    @Autowired
    private ModelMapper modelMapper;

    private WagonConverter wagonConverter;

    private CityConverter cityConverter;

    private OrderConverter orderConverter;


    @Autowired
    public WagonController(WagonService wagonService, CityService cityService, OrderService orderService) {
        this.wagonService = wagonService;
        this.cityService = cityService;
        this.orderService = orderService;
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/wagons")
    public String listWagons(Model model) {
        try {
            wagonConverter = new WagonConverter(modelMapper);
            cityConverter = new CityConverter(modelMapper);
            model.addAttribute("wagon", new WagonDTO());
            List<City> cities = cityService.listCities();
            model.addAttribute("cities", cities.stream()
                    .map(city -> cityConverter.convertToDto(city))
                    .collect(Collectors.toList()));

            List<Wagon> wagons = wagonService.listWagons();
            model.addAttribute("listWagons", wagons.stream()
                    .map(wagon -> wagonConverter.convertToDto(wagon))
                    .collect(Collectors.toList()));
            return "wagon";

        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/wagon/add")
    public String addWagon(@ModelAttribute("wagon") WagonDTO wagonDTO) {
        try {
            wagonConverter = new WagonConverter(modelMapper);
            cityConverter = new CityConverter(modelMapper);
            City city = cityService.getCityById(Integer.valueOf(wagonDTO.getCity_id()));
            wagonDTO.setCity(cityConverter.convertToDto(city));
            validate(wagonDTO);
            wagonService.addWagon(wagonConverter.convertToEntity(wagonDTO));

            return "redirect:/wagons";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    public void validate(WagonDTO wagon) throws CustomServiceException{
        if (wagon.getId() == null || wagon.getId().isEmpty() || !wagon.getId().matches("[a-zA-Z]{2}[0-9]{5}")) {
            throw new CustomServiceException("Id is not correct.");
        } else if (wagon.getShiftSize().equals("") || wagon.getShiftSize().equals("0") || !wagon.getShiftSize().matches("[0-9]{2}:[0-9]{2}")) {
            throw new CustomServiceException("Shift size incorrect.");
        } else if (wagon.getCapacity() <= 0) {
            throw new CustomServiceException("Capacity should be positive.");
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/wagon/remove/{id}")
    public String removeWagon(@PathVariable("id") String id) {
        try {
            wagonService.removeWagon(id);
            return "redirect:/wagons";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/wagon/edit/{id}")
    public @ResponseBody WagonDTO edit(@PathVariable("id") String id) {
        try {
            wagonConverter = new WagonConverter(modelMapper);
            return wagonConverter.convertToDto(wagonService.getWagonById(id));
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/wagon/update")
    public String updateWagon(@ModelAttribute("wagon") WagonDTO wagonDTO) {
        try {
            wagonConverter = new WagonConverter(modelMapper);
            cityConverter = new CityConverter(modelMapper);
            City city = cityService.getCityById(Integer.valueOf(wagonDTO.getCity_id()));
            wagonDTO.setCity(cityConverter.convertToDto(city));
            validate(wagonDTO);

            wagonService.updateWagon(wagonConverter.convertToEntity(wagonDTO));
            return "redirect:/wagons";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/wagon/getSuitable/{id}")
    public @ResponseBody List<WagonDTO> getSuitableWagons(@PathVariable("id") int id) {
        try {
            wagonConverter = new WagonConverter(modelMapper);
            Order order = orderService.getOrderById(id);
            double orderWeight = orderService.countOrderWeight(order);
            List<WagonDTO> result =  wagonService.getSuitableWagons(orderWeight).stream()
                    .map(wagon -> wagonConverter.convertToDto(wagon))
                    .collect(Collectors.toList());
            return result;
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/wagon/setOrder/{idWagon}/{idOrder}")
    public ResponseEntity<Void> setOrder(@PathVariable("idWagon") String idWagon, @PathVariable("idOrder") int idOrder) {
        try {
            wagonConverter = new WagonConverter(modelMapper);
            orderConverter = new OrderConverter(modelMapper);

            Order order = orderService.getOrderById(idOrder);
            Wagon wagon = wagonService.getWagonById(idWagon);

            order.setWagon(wagon);
            orderService.updateOrder(order);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

}
