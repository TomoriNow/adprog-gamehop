package id.ac.ui.cs.advprog.adproggameshop.controller;


import id.ac.ui.cs.advprog.adproggameshop.enums.CategoryEnums;
import id.ac.ui.cs.advprog.adproggameshop.utility.CategoryOption;
import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.service.GameService;
import id.ac.ui.cs.advprog.adproggameshop.service.UserService;
import id.ac.ui.cs.advprog.adproggameshop.service.UserServiceImpl;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
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
        User registeredUser = userService.registerUser(user.getUsername(), user.getPassword(), user.getEmail());
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
}

@Controller
@RequestMapping("/game")
class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        byte[] byteArray = {1, 2, 3, 4, 5};
        User owner = new User("username", "email", "password", 100, "bio", byteArray, false);
        User owner1 = userService.save(owner);
        Game game = new Game("name", 10, "description", 5, "category", owner1);
        Game game1 = gameService.save(game);
        return ResponseEntity.ok(game1.toString());
    }

    @GetMapping("/list")
    public String gameListPage(Model model) {
        List<GameDTO> games = gameService.findAllBy();
        List<String> categories = Arrays.stream(CategoryEnums.values())
                .map(CategoryEnums::getLabel)
                .collect(Collectors.toList());
        model.addAttribute("categories", categories);
        model.addAttribute("games", games);
        return "gameList";
    }

    @GetMapping("/list/personal")
    public String personalGameListPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("userLogin");
        List<GameDTO> games = gameService.findAllByOwner(user);
        model.addAttribute("games", games);
        return "personalGameList";
    }

    @GetMapping("/create")
    public String addGamePage(Model model) {
        Game game = new Game();
        List<CategoryOption> optionsList = Arrays.stream(CategoryEnums.values())
                .map(option -> new CategoryOption(option.getLabel(), option.getLabel()))
                .collect(Collectors.toList());
        model.addAttribute("categoryOptions", optionsList);
        model.addAttribute("game", game);
        return "addGame";
    }

    @PostMapping("/create")
    public String addGamePost(@ModelAttribute Game game, HttpSession session, Model model) {
        User user = (User) session.getAttribute("userLogin");
        gameService.saveWithOwner(game, user);
        return "redirect:/personal-page";
    }

    @PostMapping("/buy")
    public String buyGame(Model model, HttpSession session, @RequestParam String gameId) {
        User buyer = (User) session.getAttribute("userLogin");
        gameService.buyGame(Long.parseLong(gameId), buyer);
        return "redirect:list";
    }
  
    @GetMapping("/category/{category}")
    public String gamesByCategory(@PathVariable String category, Model model) {
        List<GameDTO> games = gameService.findAllByCategory(category);
        List<String> categories = Arrays.stream(CategoryEnums.values())
                .map(CategoryEnums::getLabel)
                .collect(Collectors.toList());
        model.addAttribute("categories", categories);
        model.addAttribute("games", games);
        return "gameList"; // Assuming you have a view named "gameList" to display the filtered games
    }
}