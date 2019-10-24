package shipping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

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

}
