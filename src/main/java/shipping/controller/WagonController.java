package shipping.controller;

import com.jcabi.aspects.Loggable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shipping.dto.CityDTO;
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

    @Autowired
    public WagonController(WagonService wagonService, CityService cityService) {
        this.wagonService = wagonService;
        this.cityService = cityService;
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/wagons")
    public String listWagons(Model model) {
        try {
            model.addAttribute("wagon", new WagonDTO());
            model.addAttribute("cities", cityService.listCities());
            model.addAttribute("listWagons", wagonService.listWagons());
            return "wagon";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/wagon/add")
    public String addWagon(@ModelAttribute("wagon") WagonDTO wagonDTO) {
        try {
            validate(wagonDTO);
            wagonService.addWagon(wagonDTO);
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
            return wagonService.getWagonById(id);
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/wagon/update")
    public String updateWagon(@ModelAttribute("wagon") WagonDTO wagonDTO) {
        try {
            validate(wagonDTO);
            wagonService.updateWagon(wagonDTO);
            return "redirect:/wagons";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/wagon/getSuitable/{id}")
    public @ResponseBody List<WagonDTO> getSuitableWagons(@PathVariable("id") int id) {
        try {
            return wagonService.getSuitableWagons(id);
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/wagon/setOrder/{idWagon}/{idOrder}")
    public ResponseEntity<Void> setOrder(@PathVariable("idWagon") String idWagon, @PathVariable("idOrder") int idOrder) {
        try {
            wagonService.setOrder(idWagon, idOrder);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

}
