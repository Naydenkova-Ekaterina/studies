package shipping.controller.rest;

import com.jcabi.aspects.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shipping.dto.rest.DriverDTOrest;
import shipping.exception.CustomDAOException;
import shipping.service.api.DriverService;

import java.util.List;

@RestController
@RequestMapping("/rest/driver")
public class DriverRestController {

    private DriverService driverService;

    @Autowired
    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }

    @Loggable
    @GetMapping("/all")
    public List<DriverDTOrest> getDrivers() throws CustomDAOException {
        return driverService.listDriversDTOrest();
    }

}

