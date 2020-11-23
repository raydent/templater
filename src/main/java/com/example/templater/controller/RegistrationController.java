package com.example.templater.controller;

import com.example.templater.model.User;
import com.example.templater.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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
        boolean incosistentDataFlag = false;
        if (bindingResult.hasErrors()) {
            System.out.println("Too long or too short password or login");
            model.addAttribute("DataFormatError",
                    "Too long or too short password or login (must be 2 to 16 characters)");
            incosistentDataFlag = true;
        }
        if (!userService.saveUser(userForm)){
            System.out.println("return2");
            model.addAttribute("usernameError", "User with such login already exists");
            incosistentDataFlag = true;
        }
        if (incosistentDataFlag == true){
            return "registration";
        }
        return "redirect:/login";
    }
}
