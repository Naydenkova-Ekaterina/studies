package shipping.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/wagon/add")
    public String addWagon(@ModelAttribute("wagon") WagonDTO wagonDTO) {
        try {
            wagonConverter = new WagonConverter(modelMapper);
            cityConverter = new CityConverter(modelMapper);
            City city = cityService.getCityById(Integer.valueOf(wagonDTO.getCity_id()));
            wagonDTO.setCity(cityConverter.convertToDto(city));
            wagonService.addWagon(wagonConverter.convertToEntity(wagonDTO));

            return "redirect:/wagons";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/wagon/remove/{id}")
    public String removeWagon(@PathVariable("id") String id) {
        try {
            wagonService.removeWagon(id);
            return "redirect:/wagons";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/wagon/edit/{id}")
    public @ResponseBody WagonDTO edit(@PathVariable("id") String id) {
        try {
            wagonConverter = new WagonConverter(modelMapper);
            return wagonConverter.convertToDto(wagonService.getWagonById(id));
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/wagon/update")
    public String updateWagon(@ModelAttribute("wagon") WagonDTO wagonDTO) {
        try {
            wagonConverter = new WagonConverter(modelMapper);
            cityConverter = new CityConverter(modelMapper);
            City city = cityService.getCityById(Integer.valueOf(wagonDTO.getCity_id()));
            wagonDTO.setCity(cityConverter.convertToDto(city));
            wagonService.updateWagon(wagonConverter.convertToEntity(wagonDTO));
            return "redirect:/wagons";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/wagon/getSuitable/{id}")
    public @ResponseBody List<WagonDTO> getSuitableWagons(@PathVariable("id") int id) {
        try {
            wagonConverter = new WagonConverter(modelMapper);
            orderConverter = new OrderConverter(modelMapper);
            Order order = orderService.getOrderById(id);
            double orderWeight = orderService.countOrderWeight(orderConverter.convertToDto(order));
            List<WagonDTO> result =  wagonService.getSuitableWagons(orderWeight).stream()
                    .map(wagon -> wagonConverter.convertToDto(wagon))
                    .collect(Collectors.toList());
            return result;
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

}
