package shipping.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shipping.dto.WagonDTO;
import shipping.exception.CustomServiceException;
import shipping.model.Wagon;
import shipping.service.api.WagonService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WagonController {

    private WagonService wagonService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public WagonController(WagonService wagonService) {
        this.wagonService = wagonService;
    }

    @GetMapping("/wagons")
    public String listWagons(Model model) {
        try {
            model.addAttribute("wagon", new WagonDTO());

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
            if (wagonDTO.getId() == null) {
                wagonService.addWagon(convertToEntity(wagonDTO));
            } else {
                wagonService.updateWagon(convertToEntity(wagonDTO));
            }
            return "redirect:/wagons";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/wagon/remove/{id}")
    public String removeWagon(@PathVariable("id") String id) {
        try {
            wagonService.removeWagon(id);
            return "redirect:/wagons";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/wagon/edit/{id}")
    public String edit(@PathVariable("id") String id, Model model) {
        try {
            model.addAttribute("wagon", convertToDto(wagonService.getWagonById(id)));
            List<Wagon> wagons = wagonService.listWagons();
            model.addAttribute("listWagons", wagons.stream()
                    .map(wagon -> convertToDto(wagon))
                    .collect(Collectors.toList()));
            return "wagon";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/wagon/update")
    public String updateWagon(@ModelAttribute("wagon") WagonDTO wagonDTO) {
        try {
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
        return wagon;
    }

}
