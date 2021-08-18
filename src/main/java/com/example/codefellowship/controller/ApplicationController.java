package com.example.codefellowship.controller;

import com.example.codefellowship.entities.ApplicationUser;
import com.example.codefellowship.entities.Post;
import com.example.codefellowship.repository.ApplicationUserRepo;
import com.example.codefellowship.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.awt.*;
import java.security.Principal;


@Controller
public class ApplicationController {

    @Autowired
    ApplicationUserRepo applicationUserRepo;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    PostRepo postRepo;



    @RequestMapping("/")
    public String getHome(Principal principal , Model model) {
        model.addAttribute("ifCondition2" , true);
        model.addAttribute("ifCondition" , false);
        try {
        model.addAttribute("principalUser" , ("Welcome "+principal.getName()));
        if(principal.getName() != null){
            model.addAttribute("ifCondition" , true);
            model.addAttribute("ifCondition2" , false);
        }
        }catch (NullPointerException e){
            model.addAttribute("signUpErrors" , "NullPointerException");
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
//        try {
        ApplicationUser user = new ApplicationUser(bio,(int)dateOfBirth, firstName, lastName,passwordEncoder.encode(password) , username);
        applicationUserRepo.save(user);
//        }catch (Exception e){
//            model.addAttribute("signUpErrors" , "error, this username already exist!!");
//        return new RedirectView("/error");
//        }
        return new RedirectView("/login");
    }

//    @GetMapping("/error")
//    public String getError(Model model){
//        model.addAttribute("signUpErrors" , "In correct Id");
//        return "error.html";
//    }


    @GetMapping("/login")
    public String logInPage() {
        return "login.html";
    }

    @GetMapping("/profile")
    public String getProfile(Principal principal,Model model){
        ApplicationUser user = applicationUserRepo.findByUsername(principal.getName());
        model.addAttribute("principalUser" , ("Welcome "+principal.getName()));
        ApplicationUser user1 = applicationUserRepo.findByUsername(principal.getName());

        model.addAttribute("userData" , applicationUserRepo.findByUsername(principal.getName()));
        System.out.println(user.getUsername()+"  +++++++++++++++++++++++++++");

        model.addAttribute("userPosts" ,user.getPostList());

        return "profile.html";
    }

    @GetMapping("/users/{id}")
    public String getUserData(@PathVariable(value = "id") int id ,
                              Model model,
                              Principal principal){
        try {
        ApplicationUser user = applicationUserRepo.findById(id).get();
        ApplicationUser user1 = applicationUserRepo.findByUsername(principal.getName());

        if(user.getUsername() == user1.getUsername()){
            model.addAttribute("userData" , applicationUserRepo.findByUsername(user1.getUsername()));
            return "users.html";
        }else {
        model.addAttribute("userData" , user);
        System.out.println(user);
        }
        }catch (Exception e){
            model.addAttribute("signUpErrors" , "In correct Id");
            System.out.println("error==========");
        }
        return "users.html";
    }

    @GetMapping("/allUsers")
    public String getAllUsers(Principal principal , Model model){
        Iterable user = applicationUserRepo.findAll();
        model.addAttribute("allUsers" , user);
        return "allUsers.html";
    }

    @PostMapping("/follow/{id}")
    public RedirectView addFollow(@PathVariable("id") int id , Principal principal , Model model){

        System.out.println(id+ " ========================================");
        ApplicationUser user = applicationUserRepo.findByUsername(principal.getName());
        ApplicationUser followed = applicationUserRepo.findById(id).get();
        user.addNewFollowed(followed);
        applicationUserRepo.save(user);
        return new RedirectView("/allUsers");
    }



}
