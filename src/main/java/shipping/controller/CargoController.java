package shipping.controller;

import com.jcabi.aspects.Loggable;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shipping.dto.CargoDTO;
import shipping.dto.converter.OrderConverter;
import shipping.dto.converter.WaypointConverter;
import shipping.enums.CargoStatus;
import shipping.exception.CustomServiceException;
import shipping.service.api.CargoService;
import shipping.service.api.OrderService;
import shipping.service.api.WaypointService;

import java.util.List;

@Controller
public class CargoController {

    private static final Logger logger = Logger.getLogger(CargoController.class);

    private CargoService cargoService;

    private WaypointService waypointService;

    private OrderService orderService;

    @Autowired
    public CargoController(CargoService cargoService, WaypointService waypointService, OrderService orderService) {
        this.cargoService = cargoService;
        this.waypointService = waypointService;
        this.orderService = orderService;
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/cargoes")
    public String listCargoes(Model model) {
        try {
            model.addAttribute("cargo", new CargoDTO());
            model.addAttribute("waypointsSrc", waypointService.listWaypointsSrc());
            model.addAttribute("waypointsDst", waypointService.listWaypointsDst());
            model.addAttribute("orders", orderService.listOrders());
            model.addAttribute("listCargoes", cargoService.listCargoes());
            return "cargo";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/cargo/listCargoDTOS")
    public @ResponseBody List<CargoDTO> listCargoDTOS(){
        try {
        return cargoService.listCargoes();
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/cargo/listFreeCargoes")
    public @ResponseBody List<CargoDTO> listFreeCargoes(){
        try {
            return cargoService.listFreeCargoes();
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/cargo/add")
    public String addCargo(@ModelAttribute("cargo") CargoDTO cargo) {
        try {
            validate(cargo);
            cargoService.addCargo(cargo);
            return "redirect:/cargoes";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    public void validate(CargoDTO cargoDTO) throws CustomServiceException{
        if (cargoDTO.getName().equals("")) {
            throw new CustomServiceException("Incorrect name.");
        } else if (cargoDTO.getWeight() == 0.0) {
            throw new CustomServiceException("Weight cannot be 0.0.");
        } else if (cargoDTO.getSrc_id().equals(cargoDTO.getDst_id())) {
            throw new CustomServiceException("Source and destination cannot be the same.");
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/cargo/remove/{id}")
    public String removeCargo(@PathVariable("id") int id) {
        try {
            cargoService.removeCargo(id);
            return "redirect:/cargoes";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/cargo/edit/{id}")
    public @ResponseBody CargoDTO edit(@PathVariable("id") int id) {
        try {
            return cargoService.getCargoById(id);
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/cargo/update")
    public String updateCargo(@ModelAttribute("cargo") CargoDTO cargo) {
        try {
            validate(cargo);
            cargoService.updateCargo(cargo);
            return "redirect:/cargoes";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/cargo/changeCargoStatus/{status}/{cargoId}")
    public ResponseEntity<Void> changeDriverStatus(@PathVariable("status") String status, @PathVariable("cargoId") int cargoId) {
        try {
            CargoDTO cargo = cargoService.getCargoById(cargoId);
            cargo.setStatus(String.valueOf(CargoStatus.valueOf(status)));
            cargoService.updateCargo(cargo);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

}
