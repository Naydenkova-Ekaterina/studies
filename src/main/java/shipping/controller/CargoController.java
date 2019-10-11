package shipping.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shipping.dto.CargoDTO;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.Cargo;
import shipping.service.api.CargoService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CargoController {

    private CargoService cargoService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping("/cargoes")
    public String listCargoes(Model model) {
        try {
            model.addAttribute("cargo", new CargoDTO());
            List<Cargo> cargoes = cargoService.listCargoes();
            model.addAttribute("listCargoes", cargoes.stream()
                    .map(cargo -> convertToDto(cargo))
                    .collect(Collectors.toList()));
            return "cargo";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/cargo/add")
    public String addCargo(@ModelAttribute("cargo") CargoDTO cargo) {
        try {
            if (cargo.getId() == 0) {
                cargoService.addCargo(convertToEntity(cargo));
            } else {
                cargoService.updateCargo(convertToEntity(cargo));
            }
            return "redirect:/cargoes";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/cargo/remove/{id}")
    public String removeCargo(@PathVariable("id") int id) {
        try {
            cargoService.removeCargo(id);
            return "redirect:/cargoes";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/cargo/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        try {
            model.addAttribute("cargo", convertToDto(cargoService.getCargoById(id)));
            List<Cargo> cargoes = cargoService.listCargoes();
            model.addAttribute("listCargoes", cargoes.stream()
                    .map(cargo -> convertToDto(cargo))
                    .collect(Collectors.toList()));
            return "cargo";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/cargo/update")
    public String updateCargo(@ModelAttribute("cargo") CargoDTO cargo) {
        try {
            cargoService.updateCargo(convertToEntity(cargo));
            return "redirect:/cargoes";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    private CargoDTO convertToDto(Cargo cargo) {
        CargoDTO cargoDTO = modelMapper.map(cargo, CargoDTO.class);
        return cargoDTO;
    }

    private Cargo convertToEntity(CargoDTO cargoDTO) {
        Cargo cargo = modelMapper.map(cargoDTO, Cargo.class);
        return cargo;
    }

}
