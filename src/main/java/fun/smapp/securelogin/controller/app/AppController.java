package fun.smapp.securelogin.controller.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AppController {

    @RequestMapping( path = {"/", "/dashboard"}, method = RequestMethod.GET)
    public String dashboard() {
        return "app/welcome";
    }
}
