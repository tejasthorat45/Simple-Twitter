package com.example.twitterapp.controller;

import com.example.twitterapp.model.Post;
import com.example.twitterapp.service.PostService;
import com.example.twitterapp.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {
    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "home";
    }

    @GetMapping("/add")
    public String showAddPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "add";  // This corresponds to the "add.html" template
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute Post post) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var user = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        // Correct casting

        var existingUser = userService.findByUsername(user.getUsername());
        post.setUser(existingUser);
        postService.save(post);

        return "redirect:/";  // Redirect to home after adding post
    }
}
