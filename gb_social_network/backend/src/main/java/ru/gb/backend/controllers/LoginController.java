package ru.gb.backend.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
//@RequestMapping("/gb_social_network")
public class LoginController {

    @RequestMapping(value = "/login")
    public String showMyLoginPage(){
        return "login_page.html";
    }
}
