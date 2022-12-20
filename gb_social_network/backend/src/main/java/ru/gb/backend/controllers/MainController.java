package ru.gb.backend.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller

public class MainController {
    @RequestMapping("/")
    public String showHomePage(HttpServletRequest request, HttpServletResponse response) {
        return "main";
    }
}
