package shipping.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import shipping.dto.CityDTO;
import shipping.dto.OrderDTO;
import shipping.dto.converter.CityConverter;
import shipping.dto.converter.OrderConverter;
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

    private CityConverter cityConverter;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }


    @GetMapping("/cities/listCitiesDTO")
    public @ResponseBody List<CityDTO> listCitiesDTO(){
        try {
            List<CityDTO> cityDTOList = new ArrayList<>();
            for (City city: cityService.listCities()) {
                CityDTO cityDTO = new CityDTO();
                cityDTO.setId(city.getId());
                cityDTO.setName(city.getName());
                cityDTOList.add(cityDTO);
            }
            //List<CityDTO> cityDTOList = cityService.listCities().stream().map(city -> cityConverter.convertToDto(city)).collect(Collectors.toList());

            return cityDTOList;
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/cities")
    public String listCities(Model model) {
        try {
            cityConverter = new CityConverter(modelMapper);
            model.addAttribute("city", new CityDTO());
            List<City> cargoes = cityService.listCities();
            model.addAttribute("listCities", cargoes.stream()
                    .map(cargo -> cityConverter.convertToDto(cargo))
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
            cityConverter = new CityConverter(modelMapper);

            ArrayList<CityDTO> list = new ArrayList<>();
            for (City city : cityService.listCities()) {
                list.add(cityConverter.convertToDto(city));
            }
            return list;
        } catch(CustomServiceException e){
            throw new RuntimeException(e);
        }
    }

}
