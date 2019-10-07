package test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("")
@Controller
public class TestController {

    @RequestMapping(method = RequestMethod.GET)
    public String test(ModelMap modelMap) {
        modelMap.addAttribute("message", "spring+jsp test");
        return "test";
    }

}
