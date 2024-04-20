package id.ac.ui.cs.advprog.adproggameshop.controller;


import id.ac.ui.cs.advprog.adproggameshop.enums.CategoryEnums;
import id.ac.ui.cs.advprog.adproggameshop.service.*;
import id.ac.ui.cs.advprog.adproggameshop.utility.CategoryOption;
import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
import id.ac.ui.cs.advprog.adproggameshop.utility.OneClickBuy;
import id.ac.ui.cs.advprog.adproggameshop.utility.UserBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerRequest", new User());
        return "register";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", new User());
        return "login";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        System.out.println("Register request: " + user);
        User registeredUser = new UserBuilder(user.getUsername(), user.getEmail(), user.getPassword())
                .balance(0)
                .isSeller(false)
                .build();
        registeredUser = userService.registerUser(registeredUser);
        return registeredUser == null ? "error_page" : "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session, Model model) {
        System.out.println("Login request: " + user);
        User authenticated = userService.authenticate(user.getUsername(), user.getPassword());
        if (authenticated != null) {
            model.addAttribute("userLogin", authenticated.getUsername());
            session.setAttribute("userLogin", authenticated);
            return "redirect:/personal-page";
        } else {
            return "error_page";
        }
    }

    @GetMapping("/personal-page")
    public  String personalPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("userLogin");
        model.addAttribute("authenticated", user);
        return "personal_page";
    }


    @GetMapping("/edit-profile")
    public String editProfilePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("userLogin");
        model.addAttribute("user", user);
        return "edit_profile";
    }

    @PostMapping("/edit-profile")
    public String editProfile(@ModelAttribute User user, HttpSession session) {
        userService.save(user);
        session.setAttribute("userLogin", user);
        return "redirect:/personal-page";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }

    @PostMapping("/change-role-seller")
    public String changeRoleSeller(HttpSession session, Model model) {
        User user = (User) session.getAttribute("userLogin");
        user.set_seller(true);
        userService.save(user);
        return "redirect:/personal-page";
    }

    @GetMapping("/topUp")
    public String topUp(HttpSession session, Model model) {
        return "topUp";
    }

    @PostMapping("/topUp")
    public String submitInteger(@RequestParam("topUpAmount") int topUpAmount, HttpSession session) {
        User user = (User) session.getAttribute("userLogin");
        userService.topUp(user, topUpAmount);
        return "redirect:/personal-page";
    }

    @GetMapping("/listUsers")
    public String listUsers(Model model, HttpSession httpSession) {
        model.addAttribute("usersList", userService.listUsers());
        return "usersList";
    }
}