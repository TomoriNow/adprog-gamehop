package id.ac.ui.cs.advprog.adproggameshop.controller;


import id.ac.ui.cs.advprog.adproggameshop.enums.CategoryEnums;
import id.ac.ui.cs.advprog.adproggameshop.factory.CategoryFactory;
import id.ac.ui.cs.advprog.adproggameshop.factory.CategoryHandler;
import id.ac.ui.cs.advprog.adproggameshop.service.GameServiceImpl;
import id.ac.ui.cs.advprog.adproggameshop.model.Transaction;
import id.ac.ui.cs.advprog.adproggameshop.service.TransactionServiceImpl;
import id.ac.ui.cs.advprog.adproggameshop.service.*;
import id.ac.ui.cs.advprog.adproggameshop.model.ShoppingCart;
import id.ac.ui.cs.advprog.adproggameshop.utility.CategoryOption;
import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
import id.ac.ui.cs.advprog.adproggameshop.utility.TransactionDTO;
import id.ac.ui.cs.advprog.adproggameshop.utility.OneClickBuy;
import id.ac.ui.cs.advprog.adproggameshop.utility.UserBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import id.ac.ui.cs.advprog.adproggameshop.repository.GameRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
@Controller
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TransactionServiceImpl transactionService;
  
    @Autowired
    private GameServiceImpl gameService;

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

    @GetMapping("/profile-page")
    public  String profilePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("userLogin");
        model.addAttribute("authenticated", user);
        return "profile_page";
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

    @GetMapping("/profile/{userId}")
    public String getProfilePage(@PathVariable Long userId, Model model) {
        User user = userService.findUserById(userId);
        if (user == null) {
            return "error_page";
        }

        if (user.is_seller()) {
            List<GameDTO> games = gameService.findAllByOwner(user);
            model.addAttribute("games", games);
            model.addAttribute("user", user);
            return "other_user_profile";
        }

        model.addAttribute("user", user);
        return "other_user_profile";
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

    @GetMapping("/transaction-history")
    public String transactionHistory(HttpSession session, Model model) {
        User user = (User) session.getAttribute("userLogin");
        List<TransactionDTO> transactions = transactionService.findAllByBuyerOrSeller(user, user);
        transactions.sort(Comparator.comparing(TransactionDTO::getTransactionId).reversed());
        model.addAttribute("user", user);
        model.addAttribute("transactions", transactions);
        return "transactionHistory";
    }

    @GetMapping("/extract")
    public String extractGameData(Model model) {
        List<Game> games = gameService.extractGameData();
        model.addAttribute("games", games);
        return "gameList";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam String gameId, HttpSession session) {
        Game game = gameService.findByProductId(Long.parseLong(gameId));
        ShoppingCart cart = ShoppingCart.getInstance();

        cart.addItem(game.getName(), 1);

        session.setAttribute("cart", cart);

        return "redirect:/game/list";
    }
    @PostMapping("/shopping-cart/delete")
    public String deleteFromCart(@RequestParam String itemName, HttpSession session) {
        ShoppingCart cart = ShoppingCart.getInstance();
        cart.removeItem(itemName);
        session.setAttribute("cart", cart);
        return "redirect:/shopping-cart"; // Redirect to the shopping cart page
    }

    @GetMapping("/shopping-cart")
    public String viewShoppingCart(HttpSession session, Model model) {
        ShoppingCart cart = ShoppingCart.getInstance();
        model.addAttribute("cart", cart.getItems());
        return "shoppingCart"; // Adjusted to match the name of your HTML file
    }





}

