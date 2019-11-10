package shipping.controller.rest;

import com.jcabi.aspects.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shipping.dto.rest.WagonDTOrest;
import shipping.exception.CustomDAOException;
import shipping.service.api.WagonService;

import java.util.List;

@RestController
@RequestMapping("/rest/wagon")
public class WagonRestController {

    private WagonService wagonService;

    @Autowired
    public void setWagonService(WagonService wagonService) {
        this.wagonService = wagonService;
    }

    @Loggable
    @GetMapping("/all")
    public List<WagonDTOrest> getWagons() throws CustomDAOException {
        return wagonService.listWagonDTOrest();
    }
}
