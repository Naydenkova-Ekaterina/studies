package shipping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/suitableWagons")
    public String suitableWagons(){
        return "suitableWagons";
    }

}
