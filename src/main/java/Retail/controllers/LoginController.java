package Retail.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin( origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class LoginController {
    @PostMapping
    private String logearse(@RequestBody String user, String pass){
        return "EstasLogeado";
    }
}
