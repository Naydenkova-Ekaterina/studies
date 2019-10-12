package shipping.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shipping.dto.CityDTO;
import shipping.dto.WagonDTO;
import shipping.exception.CustomServiceException;
import shipping.model.City;
import shipping.model.Wagon;
import shipping.service.api.CityService;
import shipping.service.api.WagonService;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WagonController {

    private WagonService wagonService;

    private CityService cityService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public WagonController(WagonService wagonService, CityService cityService) {
        this.wagonService = wagonService;
        this.cityService = cityService;
    }

    @GetMapping("/wagons")
    public String listWagons(Model model) {
        try {
            model.addAttribute("wagon", new WagonDTO());
            List<City> cities = cityService.listCities();
            model.addAttribute("cities", cities.stream()
                    .map(city -> convertCityToDto(city))
                    .collect(Collectors.toList()));

            List<Wagon> wagons = wagonService.listWagons();
            model.addAttribute("listWagons", wagons.stream()
                    .map(wagon -> convertToDto(wagon))
                    .collect(Collectors.toList()));
            return "wagon";

        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/wagon/add")
    public String addWagon(@ModelAttribute("wagon") WagonDTO wagonDTO) {
        try {

            City city = cityService.getCityById(Integer.valueOf(wagonDTO.getCity_id()));
            wagonDTO.setCity(convertCityToDto(city));
            wagonService.addWagon(convertToEntity(wagonDTO));

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
            return  convertToDto(wagonService.getWagonById(id));
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/wagon/update")
    public String updateWagon(@ModelAttribute("wagon") WagonDTO wagonDTO) {
        try {
            City city = cityService.getCityById(Integer.valueOf(wagonDTO.getCity_id()));
            wagonDTO.setCity(convertCityToDto(city));
            wagonService.updateWagon(convertToEntity(wagonDTO));
            return "redirect:/wagons";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    private WagonDTO convertToDto(Wagon wagon) {
        WagonDTO wagonDTO = modelMapper.map(wagon, WagonDTO.class);
        return wagonDTO;
    }

    private Wagon convertToEntity(WagonDTO wagonDTO) {
        Wagon wagon = modelMapper.map(wagonDTO, Wagon.class);
        wagon.setShiftSize(LocalTime.parse(wagonDTO.getShiftSize()));
        return wagon;
    }

    public CityDTO convertCityToDto(City city) {
        CityDTO cityDTO = modelMapper.map(city, CityDTO.class);
        return cityDTO;
    }

}
