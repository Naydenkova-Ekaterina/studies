package shipping.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shipping.exception.CustomServiceException;
import shipping.model.Wagon;
import shipping.service.api.WagonService;

@RestController
//@RequestMapping("/wagons")
public class WagonControllerRest {

    @Autowired
    private WagonService wagonService;

    @GetMapping("/getAll")
    public String listWagons(Model model) {
        try {
            model.addAttribute("wagon", new Wagon());
            model.addAttribute("listWagons", wagonService.listWagons());
            return "wagon";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/add")
    public String addWagon(@ModelAttribute("wagon") Wagon wagon) {
        try {
            if (wagon.getId() == null) {
                wagonService.addWagon(wagon);
            } else {
                wagonService.updateWagon(wagon);
            }
            return "redirect:/wagons";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/remove/{id}")
    public String removeWagon(@PathVariable("id") String id) {
        try {
            wagonService.removeWagon(id);
            return "redirect:/wagons";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, Model model) {
        try {
            model.addAttribute("wagon", wagonService.getWagonById(id));
            model.addAttribute("listWagons", wagonService.listWagons());
            return "wagon";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/update")
    public String updateWagon(@ModelAttribute("wagon") Wagon wagon) {
        try {
            wagonService.updateWagon(wagon);
            return "redirect:/wagons";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

}
