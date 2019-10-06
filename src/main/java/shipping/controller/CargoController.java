package shipping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import shipping.model.Cargo;
import shipping.service.CargoService;

@Controller
public class CargoController {

    private CargoService cargoService;

    @Autowired(required=true)
    @Qualifier(value="cargoService")
    public void setCargoService(CargoService cargoService){
        this.cargoService = cargoService;
    }

    @RequestMapping(value = "/cargoes", method = RequestMethod.GET)
    public String listCargoes(Model model) {
        model.addAttribute("cargo", new Cargo());
        model.addAttribute("listCargoes", this.cargoService.listCargoes());
        return "cargo";
    }

    @RequestMapping(value= "/cargo/add", method = RequestMethod.POST)
    public String addCargo(@ModelAttribute("cargo") Cargo cargo){

        if(cargo.getId() == 0){
            this.cargoService.addCargo(cargo);
        }else{
            this.cargoService.updateCargo(cargo);
        }
        return "redirect:/cargoes";
    }

    @RequestMapping("/remove/{id}")
    public String removeCargo(@PathVariable("id") int id){

        this.cargoService.removeCargo(id);
        return "redirect:/cargoes";
    }

    @RequestMapping(value="/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("cargo", this.cargoService.getCargoById(id));
        model.addAttribute("listCargoes", this.cargoService.listCargoes());
        return "cargo";
    }

    @RequestMapping(value= "/cargo/update", method = RequestMethod.POST)
    public String updateCargo(@ModelAttribute("cargo") Cargo cargo){
        this.cargoService.updateCargo(cargo);
        return "redirect:/cargoes";
    }

}
