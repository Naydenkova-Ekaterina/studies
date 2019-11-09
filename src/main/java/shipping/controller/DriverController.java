package shipping.controller;

import com.jcabi.aspects.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shipping.dto.*;
import shipping.exception.CustomServiceException;
import shipping.service.api.*;


import java.util.List;


@Controller
public class DriverController {

    private DriverService driverService;

    private CityService cityService;

    private WagonService wagonService;

    private OrderService orderService;

    private UserService userService;


    @Autowired
    public DriverController(DriverService driverService, CityService cityService, WagonService wagonService, OrderService orderService,
                            UserService userService) {
        this.driverService = driverService;
        this.cityService = cityService;
        this.wagonService = wagonService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/drivers")
    public String listDrivers(Model model) {
        try {
            model.addAttribute("driver", new DriverDto());
            model.addAttribute("cities", cityService.listCities());
            model.addAttribute("wagons", wagonService.listWagons());
            model.addAttribute("orders", orderService.listOrders());
            model.addAttribute("users", userService.listUser());
            model.addAttribute("listDrivers", driverService.listDrivers());
            return "driver";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/driver/add")
    public String addDriver(@ModelAttribute("driver") DriverDto driverDto) {
        try {
            validate(driverDto);
            driverService.addDriver(driverDto);
            return "redirect:/drivers";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    public void validate(DriverDto driverDto) throws CustomServiceException{
        if (driverDto.getName().equals("")) {
            throw new CustomServiceException("Name is not correct.");
        } else if (driverDto.getSurname().equals("")) {
            throw new CustomServiceException("Surname is not correct.");
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/driver/registerAdd/{email}")
    public ResponseEntity<Void> addUser(@RequestBody DriverDto driverDto, @PathVariable("email") String email) {
        try {
            driverService.addUser(driverDto, email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/driver/remove/{id}")
    public String removeDriver(@PathVariable("id") int id) {
        try {
            driverService.removeDriver(id);
            return "redirect:/drivers";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/driver/edit/{id}")
    public @ResponseBody
    DriverDto edit(@PathVariable("id") int id) {
        try {
            return driverService.getDriverById(id);
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/driver/editAuth")
    public @ResponseBody DriverDto getAuthDriver() {
        try {
            return driverService.getAuthenticatedDriver();
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/driver/update")
    public String updateDriver(@ModelAttribute("driver") DriverDto driverDto) {
        try {
            validate(driverDto);
            driverService.updateDriver(driverDto);
            return "redirect:/drivers";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/driver/info")
    public String info(Model model) {
        try {
            model.addAttribute("selDriverInfo", driverService.info());
            return "driverInfo";
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/driver/updateInfo/{id}")
    public void updateInfo(@PathVariable("id") int id) {
        try {
            driverService.updateDriver(driverService.getDriverById(id));
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/driver/getSuitable/{id}")
    public @ResponseBody List<DriverDto> getSuitableDrivers(@PathVariable("id") int id) {
        try {
            return driverService.getSuitableDrivers(id);
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/driver/setOrder/{idDriver}/{idOrder}")
    public ResponseEntity<Void> setOrder(@PathVariable("idDriver") int idDriver, @PathVariable("idOrder") int idOrder) {
        try {
            driverService.setOrder(idDriver, idOrder);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @PostMapping("/driver/changeDriverStatus/{status}")
    public ResponseEntity<Void> changeDriverStatus(@PathVariable("status") String status) {
        try {
            driverService.changeDriverStatus(status);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/driver/startShift")
    public ResponseEntity<Void> startShift(){
        try {
            driverService.startShift();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/driver/endShift")
    public ResponseEntity<Void> endShift(){
        try {
            driverService.endShift();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Loggable(Loggable.DEBUG)
    @GetMapping("/driver/getDriverCargoes")
    public @ResponseBody List<CargoDTO> getDriverCargoes(){
        try {
           return driverService.getDriverCargoes();
        } catch (CustomServiceException e) {
            throw new RuntimeException(e);
        }
    }


}
