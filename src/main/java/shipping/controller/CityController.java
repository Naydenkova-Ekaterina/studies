package shipping.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shipping.dto.CityDTO;
import shipping.exception.CustomServiceException;
import shipping.model.City;
import shipping.service.api.CityService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CityController {

    private CityService cityService;

    @Autowired
    private static ModelMapper modelMapper;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/cities")
    public String listCities(Model model) {
        try {
            model.addAttribute("city", new CityDTO());
            List<City> cargoes = cityService.listCities();
            model.addAttribute("listCities", cargoes.stream()
                    .map(cargo -> convertToDto(cargo))
                    .collect(Collectors.toList()));
            return "cargo";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getCityNames")
    public ArrayList<String> getCityNames() {
        try {

            ArrayList<String> list = new ArrayList<>();
            for (City city : cityService.listCities()) {
                list.add(city.getName());
            }
            return list;
        } catch(CustomServiceException e){
                throw new RuntimeException(e);
        }
    }

    @GetMapping("/getCities")
    public ArrayList<CityDTO> getCities() {
        try {

            ArrayList<CityDTO> list = new ArrayList<>();
            for (City city : cityService.listCities()) {
                list.add(convertToDto(city));
            }
            return list;
        } catch(CustomServiceException e){
            throw new RuntimeException(e);
        }
    }

    public static CityDTO convertToDto(City city) {
        CityDTO cityDTO = modelMapper.map(city, CityDTO.class);
        return cityDTO;
    }

    public City convertToEntity(CityDTO cityDTO) {
        City city = modelMapper.map(cityDTO, City.class);
        return city;
    }
}
