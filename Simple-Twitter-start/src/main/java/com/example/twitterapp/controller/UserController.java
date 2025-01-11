package com.example.twitterapp.controller;

import com.example.twitterapp.model.User;
import com.example.twitterapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model, User userDto) {
        model.addAttribute("user", userDto);
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model, User userDto) {
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register")
    public String registerSave(@ModelAttribute("user") User userDto, Model model) {
        // Check if the user already exists
        User existingUser = userService.findByUsername(userDto.getUsername());
        if (existingUser != null) {
            model.addAttribute("UserExist", true); // Inform the user that the username is taken
            return "register";
        }

        // Save the new user
        userService.save(userDto);
        return "redirect:/register?success"; // Redirect with success message
    }
}
