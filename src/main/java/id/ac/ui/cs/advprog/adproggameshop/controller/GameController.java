package id.ac.ui.cs.advprog.adproggameshop.controller;

import id.ac.ui.cs.advprog.adproggameshop.enums.CategoryEnums;
import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.service.GameService;
import id.ac.ui.cs.advprog.adproggameshop.service.UserService;
import id.ac.ui.cs.advprog.adproggameshop.utility.CategoryOption;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameForm;
import id.ac.ui.cs.advprog.adproggameshop.utility.OneClickBuy;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public String addGamePage(GameForm gameForm,Model model) {
        List<CategoryOption> optionsList = Arrays.stream(CategoryEnums.values())
                .map(option -> new CategoryOption(option.getLabel(), option.getLabel()))
                .collect(Collectors.toList());
        model.addAttribute("categoryOptions", optionsList);
        return "addGame";
    }

    @PostMapping("/create")
    public String addGamePost(@Valid GameForm gameForm, BindingResult bindingResult, HttpSession session, Model model) {
        if (bindingResult.hasErrors()) {
            List<CategoryOption> optionsList = Arrays.stream(CategoryEnums.values())
                    .map(option -> new CategoryOption(option.getLabel(), option.getLabel()))
                    .collect(Collectors.toList());
            model.addAttribute("categoryOptions", optionsList);
            return "addGame";
        }
        User user = (User) session.getAttribute("userLogin");
        gameForm.setOwner(user);
        Game game = gameForm.createGame();
        gameService.saveWithOwner(game, user);
        return "redirect:/personal-page";
    }

    @PostMapping("/buy")
    public String buyGame(Model model, HttpSession session, @RequestParam String gameId) {
        User buyer = (User) session.getAttribute("userLogin");
        try {
            gameService.buyGame(Long.parseLong(gameId), buyer, 1, new OneClickBuy());
        } catch (RuntimeException error) {
            model.addAttribute("error_message", error.getMessage());
            return "error_page1";
        }
        return "redirect:list";
    }

    @PostMapping("/remove")
    public String removeGame(Model model, HttpSession session, @RequestParam String gameId) {
        gameService.deleteGameById(Long.parseLong(gameId));
        return "redirect:list/personal";
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