package com.example.codefellowship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;


@Controller
public class ApplicationController {
    @GetMapping("/")
    @ResponseBody
    public String getHome(Principal p){
        return "Hello World" + p.getName();
    }


    @GetMapping("/test")
    public String testTemplete (Model model){
        model.addAttribute("Name" , "Ibrahim");
        model.addAttribute("Age" ,23);
        return "templete.html";
    }
}
