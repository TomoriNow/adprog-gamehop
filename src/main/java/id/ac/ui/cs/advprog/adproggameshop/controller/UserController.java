package id.ac.ui.cs.advprog.adproggameshop.controller;

import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.service.GameService;
import id.ac.ui.cs.advprog.adproggameshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

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
    public String login(@ModelAttribute User user, Model model) {
        System.out.println("Register request: " + user);
        User authenticated = userService.authenticate(user.getUsername(), user.getPassword());
        if (authenticated != null) {
            model.addAttribute("userLogin", authenticated.getUsername());
            return "personal_page";
        } else {
            return "error_page";
        }
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
        List<Game> games = gameService.findAll();
        model.addAttribute("games", games);
        return "gameList";
    }
}