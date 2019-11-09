package shipping.controller;

import com.jcabi.aspects.Loggable;
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
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/cities/listCitiesDTO")
    public @ResponseBody List<CityDTO> listCitiesDTO(){
        try {
            return cityService.listCitiesDTO();
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/cities")
    public String listCities(Model model) {
        try {
            model.addAttribute("city", new CityDTO());
            model.addAttribute("listCities", cityService.listCities());
            return "city";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/getCityNames")
    public ArrayList<String> getCityNames() {
        try {
            return cityService.getCityNames();
        } catch(CustomServiceException e){
                throw new RuntimeException(e);
        }
    }

}
