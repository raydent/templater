package com.example.templater.controller;

import com.example.templater.model.User;
import com.example.templater.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    @Autowired
    private IUserService userService;
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {
        System.out.println(bindingResult.toString());
        if (bindingResult.hasErrors()) {
            System.out.println("Too long or too short password or login");
            model.addAttribute("Incorrect data format error",
                    "Too long or too short password or login (2 to 16 characters");
            return "registration";
        }
        if (!userService.saveUser(userForm)){
            System.out.println("return2");
            model.addAttribute("usernameError", "User with such username already exists");
            return "registration";
        }

        return "redirect:/login";
    }
}
