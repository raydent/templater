package com.example.templater.controller;

import com.example.templater.model.User;
import com.example.templater.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;
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

    @RequestMapping(value = "/angular/registration", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    User angularRegistration(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String header = httpServletRequest.getHeader("authorization");
        String usernameAndPass = new String(Base64.getDecoder().decode(header.substring(header.indexOf(' ') + 1)));
        String username = usernameAndPass.substring(0, usernameAndPass.indexOf(':'));
        String passwordConfirmationPassword = usernameAndPass.substring(usernameAndPass.indexOf(':') + 1);
        String password = passwordConfirmationPassword.substring(0, passwordConfirmationPassword.indexOf(':'));
        String confirmationPassword = passwordConfirmationPassword.substring(passwordConfirmationPassword.indexOf(':') + 1);

        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        user.setConfirmationPassword(confirmationPassword);
        System.out.println(username + " " + password + " " + confirmationPassword);

        if (password == confirmationPassword && userService.saveUser(user)) {
            return user;
        }

        return null;
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {
        System.out.println(bindingResult.toString());
        boolean incosistentDataFlag = false;
        if (bindingResult.hasErrors()) {
            System.out.println("Too long or too short password or login");
            model.addAttribute("dataFormatError",
                    "Too long or too short password or login (must be 2 to 16 characters)");
            incosistentDataFlag = true;
        }
        else if (!userForm.getConfirmationPassword().equals(userForm.getPassword())) {
            model.addAttribute("passwordConfirmationError",
                    "Passwords are not identical");
            incosistentDataFlag = true;
        }
        else if (!userService.saveUser(userForm)){
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
