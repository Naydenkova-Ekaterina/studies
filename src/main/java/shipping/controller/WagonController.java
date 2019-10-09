package shipping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shipping.exception.CustomServiceException;
import shipping.model.Wagon;
import shipping.service.api.WagonService;

@Controller
public class WagonController {

    private WagonService wagonService;

    @Autowired
    public WagonController(WagonService wagonService) {
        this.wagonService = wagonService;
    }

    @GetMapping("/wagons")
    public String listWagons(Model model) {
        try {
            model.addAttribute("wagon", new Wagon());
            model.addAttribute("listWagons", wagonService.listWagons());
            return "wagon";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/wagon/add")
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
            model.addAttribute("wagon", wagonService.getWagonById(id));
            model.addAttribute("listWagons", wagonService.listWagons());
            return "wagon";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/wagon/update")
    public String updateWagon(@ModelAttribute("wagon") Wagon wagon) {
        try {
            wagonService.updateWagon(wagon);
            return "redirect:/wagons";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

}
