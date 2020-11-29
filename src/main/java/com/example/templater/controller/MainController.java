package com.example.templater.controller;

import com.example.templater.model.Temp;
import com.example.templater.model.User;
import com.example.templater.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Controller
public class MainController {
    @Autowired
    private IUserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    //test
    @GetMapping("/login")
    public String getLogin(Model model, @AuthenticationPrincipal User authenticatedUser, @RequestParam(required = false) String error) {
        System.out.println(model.toString());
        if (error != null){
            model.addAttribute("loginError", "Login or password is incorrect");
        }
        if (Objects.nonNull(authenticatedUser)) {
            return "redirect:/user";
        }
        model.addAttribute("userForm", new User());
        return "login";
    }

    @PostMapping("/login")
    public String setLogin(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("userForm") User user,
                           BindingResult result) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        return "redirect:/user";
    }


    @GetMapping("/403")
    public String error403() {
        return "/error";
    }

    @GetMapping("/user")
    public String user() {
        return "/user";
    }

    @GetMapping("/about")
    public String about(){
        return "/about";
    }

    @GetMapping("/home")
    public String home() {return "/home";}

    @GetMapping("/template")
    public String template() { return "/template";}



    @GetMapping("/temp")
    public String tempForm(Model model) {
        Temp temp1 = new Temp();
        Temp temp2 = new Temp();
        Temp temp3 = new Temp();
        Temp temp4 = new Temp();
        Temp temp5 = new Temp();
        model.addAttribute("temp1", temp1);
        model.addAttribute("temp2",temp2);
        model.addAttribute("temp3",temp3);
        model.addAttribute("temp4",temp4);
        model.addAttribute("temp5",temp5);
        return "temp";
    }





    @PostMapping("/temp")
    public String saveBooks(@ModelAttribute("temp1") Temp temp1,
                            @ModelAttribute("temp2") Temp temp2,
                            @ModelAttribute("temp3") Temp temp3,
                            @ModelAttribute("temp4") Temp temp4,
                            @ModelAttribute("temp5") Temp temp5) {
        System.out.println(temp4.getBold());

        return "tempresult";
    }




}