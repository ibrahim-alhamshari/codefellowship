package com.example.codefellowship.controller;

import com.example.codefellowship.ApplicationUser;
import com.example.codefellowship.repository.ApplicationUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.sql.SQLException;


@Controller
public class ApplicationController {

    @Autowired
    ApplicationUserRepo applicationUserRepo;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @RequestMapping("/")
    public String getHome(Principal principal , Model model) {
        try {
        model.addAttribute("principalUser", principal.getName());
        System.out.println("++++++++ "+ principal.getName());
        }catch (Exception e){
            model.addAttribute("signUpErrors" ,applicationUserRepo.findAll() );
        }

        return "home.html";
    }


    @GetMapping("/signUp")
    public String getSignUp() {
        return "signUp.html";
    }

    @PostMapping("/saveUser")
    public RedirectView saveUserData(@RequestParam(value = "username") String username,
                                     @RequestParam(value = "password") String password,
                                     @RequestParam(value = "firstName") String firstName,
                                     @RequestParam(value = "lastName") String lastName,
                                     @RequestParam(value = "dateOfBirth") int dateOfBirth,
                                     @RequestParam(value = "bio") String bio,
                                     Model model) {
        try {
        ApplicationUser user = new ApplicationUser(bio,(int)dateOfBirth, firstName, lastName,passwordEncoder.encode(password) , username);
        applicationUserRepo.save(user);
        }catch (Exception e){
            model.addAttribute("signUpErrors" , "error, this username already exist!!");
        return new RedirectView("/error");
        }
        return new RedirectView("/login");
    }

    @GetMapping("/error")
    public String getError(){
    return "error.html";
    }


    @GetMapping("/login")
    public String logInPage() {
        return "login.html";
    }

    @GetMapping("/profile")
    public String getProfile(Principal principal,Model model){
        model.addAttribute("userData" , applicationUserRepo.findByUsername(principal.getName()));
        return "profile.html";
    }

    @GetMapping("/users/{id}")
    public String getUserData(@PathVariable(value = "id") int id ,
                              Model model,
                              Principal principal){
        try {
        ApplicationUser user = applicationUserRepo.findById(id).get();
        ApplicationUser user1 = applicationUserRepo.findByUsername(principal.getName());

        System.out.println("++++++++++"+ user.getUsername());
        System.out.println("+++++++++++++"+ user1.getUsername());
        if(user.getUsername() == user1.getUsername()){
            model.addAttribute("userData" , applicationUserRepo.findByUsername(user1.getUsername()));
            return "profile.html";
        }else {
        model.addAttribute("userData" , user);
        System.out.println(user);
        }
        }catch (Exception e){
            System.out.println("error==========");
        }
        return "profile.html";
    }


//    @GetMapping("/test")
//    public String testTemplete(Model model) {
//        model.addAttribute("Name", "Ibrahim");
//        model.addAttribute("Age", 23);
//        return "templete.html";
//    }
}
