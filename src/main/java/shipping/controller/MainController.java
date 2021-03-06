package shipping.controller;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    private static final Logger logger = Logger.getLogger(MainController.class);


    @RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
    public String home(){
        return "index";
    }

    @GetMapping("/suitableWagons")
    public String suitableWagons(){
        return "suitableWagons";
    }

    @GetMapping("/suitableDrivers")
    public String suitableDrivers(){
        return "suitableDrivers";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/reg")
    public String reg(){
        return "reg";
    }


    @GetMapping("/u")
    public String u(){
        return "driverInfo";
    }

    @GetMapping("/updateDriver")
    public String updateDriver(){
        return "updateDriver";
    }

}
