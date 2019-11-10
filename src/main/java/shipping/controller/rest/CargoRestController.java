package shipping.controller.rest;

import com.jcabi.aspects.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shipping.dto.rest.CargoDTOrest;
import shipping.exception.CustomDAOException;
import shipping.service.api.CargoService;

import java.util.List;

@RestController
@RequestMapping("/rest/cargo")
public class CargoRestController {

    private CargoService cargoService;

    @Autowired
    public void setCargoService(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @Loggable
    @GetMapping("/all")
    public List<CargoDTOrest> getCargoes() throws CustomDAOException {
        return cargoService.listCargoesDTOrest();
    }

}
