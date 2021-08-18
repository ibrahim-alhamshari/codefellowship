package com.example.codefellowship.controller;

import com.example.codefellowship.entities.ApplicationUser;
import com.example.codefellowship.entities.Post;
import com.example.codefellowship.repository.ApplicationUserRepo;
import com.example.codefellowship.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Date;

@Controller
public class PostController {

    @Autowired
    PostRepo postRepo;

    @Autowired
    ApplicationUserRepo applicationUserRepo;

    @PostMapping("/addPost")
    public RedirectView addPostToDB(@RequestParam(value = "body") String body,
                                    Principal principal){
        System.out.println("=============");
        Date date = new Date();
        ApplicationUser applicationUser = applicationUserRepo.findByUsername(principal.getName());
        Post post = new Post(body , date,applicationUser);
        postRepo.save(post);
        return new RedirectView("/profile");
    }

    @GetMapping("/feed")
    public String getPosts(Principal principal , Model model){
    ApplicationUser user = applicationUserRepo.findByUsername(principal.getName());
        model.addAttribute("followers" , user.getUsersManyToMany());
        return "feed.html";
    }




}
