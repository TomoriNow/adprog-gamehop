package id.ac.ui.cs.advprog.adproggameshop.controller;


import id.ac.ui.cs.advprog.adproggameshop.service.GameServiceImpl;
import id.ac.ui.cs.advprog.adproggameshop.service.TransactionServiceImpl;
import id.ac.ui.cs.advprog.adproggameshop.service.*;
import id.ac.ui.cs.advprog.adproggameshop.model.ShoppingCart;
import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
import id.ac.ui.cs.advprog.adproggameshop.utility.TransactionDTO;
import id.ac.ui.cs.advprog.adproggameshop.utility.UserBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
@Controller
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TransactionServiceImpl transactionService;

    @Autowired
    private GameServiceImpl gameService;

    private final static String FETCHED = "picture_fetched";
    private final static String GAMES_STRING = "games";
    private final static String USER_LOGIN_SESSION = "userLogin";
    private final static String ERROR_PAGE = "error_page";
    private final static String REDIRECT_LOGIN = "redirect:/login";
    private final static String CART_SUFFIX = "cart_";
    private final static String REDIRECT_PERSONAL_PAGE = "redirect:/personal-page";

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
        return registeredUser == null ? ERROR_PAGE : REDIRECT_LOGIN;
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session, Model model) {
        System.out.println("Login request: " + user);
        User authenticated = userService.authenticate(user.getUsername(), user.getPassword());
        if (authenticated != null) {
            model.addAttribute(USER_LOGIN_SESSION, authenticated.getUsername());
            session.setAttribute(USER_LOGIN_SESSION, authenticated);
            session.setAttribute(FETCHED, false);
            ShoppingCart cart = new ShoppingCart();
            session.setAttribute(CART_SUFFIX + authenticated.getUserId(), cart);

            return REDIRECT_PERSONAL_PAGE;
        } else {
            return ERROR_PAGE;
        }
    }

    @GetMapping("/login-picture/{userId}")
    @ResponseBody
    public ResponseEntity<String> getLoginPicture(@PathVariable Long userId, HttpSession session) {
        byte[] profilePicture = userService.findProfilePictureByUserId(userId);
        System.out.println("payload collected");
        session.setAttribute(FETCHED, true);
        if (profilePicture != null) {
            String base64Image = Base64.encodeBase64String(profilePicture);
            User user = (User) session.getAttribute(USER_LOGIN_SESSION);
            user.setProfilePicture(profilePicture);
            return ResponseEntity.ok(base64Image);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/personal-page")
    public  String personalPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute(USER_LOGIN_SESSION);
        boolean fetched = (boolean) session.getAttribute(FETCHED);
        model.addAttribute("authenticated", user);
        model.addAttribute(FETCHED, fetched);
        return "personal_page";
    }

    @GetMapping("/profile-page")
    public  String profilePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute(USER_LOGIN_SESSION);
        boolean fetched = (boolean) session.getAttribute(FETCHED);
        if (fetched) {
            String base64Image = Base64.encodeBase64String(user.getProfilePicture());
            model.addAttribute("profilePictureBase64", base64Image);
        }
        model.addAttribute("authenticated", user);
        return "profile_page";
    }

    @GetMapping("/profile-picture/{userId}")
    @ResponseBody
    public ResponseEntity<String> getProfilePicture(@PathVariable Long userId) {
        byte[] profilePicture = userService.findProfilePictureByUserId(userId);
        System.out.println("called");
        System.out.println(userId);
        if (profilePicture != null) {
            String base64Image = Base64.encodeBase64String(profilePicture);
            return ResponseEntity.ok(base64Image);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/edit-profile")
    public String editProfilePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute(USER_LOGIN_SESSION);
        model.addAttribute("user", user);
        return "edit_profile";
    }

    @PostMapping("/edit-profile")
    public String editProfile(@ModelAttribute User user, HttpSession session) {
        User currentUser = (User) session.getAttribute(USER_LOGIN_SESSION);
        boolean fetched = (boolean) session.getAttribute(FETCHED);

        if (user.getProfilePictureFile() != null && !user.getProfilePictureFile().isEmpty()) {
            try {
                BufferedImage originalImage = ImageIO.read(user.getProfilePictureFile().getInputStream());

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(compressImage(originalImage), "jpg", baos);
                byte[] profilePictureBytes = baos.toByteArray();

                user.setProfilePicture(profilePictureBytes);
                session.setAttribute(FETCHED, true);
            } catch (IOException e) {
                return ERROR_PAGE;
            }
        } else {
            if (fetched){
                user.setProfilePicture(currentUser.getProfilePicture());
            } else {
                byte[] profilePicture = userService.findProfilePictureByUserId(user.getUserId());
                user.setProfilePicture(profilePicture);
                session.setAttribute(FETCHED, true);
            }
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            user.setPassword(currentUser.getPassword());
        }
        user.setSeller(currentUser.isSeller());

        userService.save(user);
        session.setAttribute(USER_LOGIN_SESSION, user);
        return REDIRECT_PERSONAL_PAGE;
    }

    private BufferedImage compressImage(BufferedImage originalImage) {
        int targetWidth = 200;
        int targetHeight = 200;

        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        resizedImage.createGraphics().drawImage(originalImage.getScaledInstance(targetWidth, targetHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);

        return resizedImage;
    }

    @GetMapping("/profile/{userId}")
    public String getProfilePage(@PathVariable Long userId, Model model) {
        User user = userService.findUserById(userId);

        if (user == null) {
            return ERROR_PAGE;
        }

        if (user.isSeller()) {
            List<GameDTO> games = gameService.findAllByOwner(user);
            model.addAttribute(GAMES_STRING, games);
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
        return REDIRECT_LOGIN;
    }

    @PostMapping("/change-role-seller")
    public String changeRoleSeller(HttpSession session, Model model) {
        User user = (User) session.getAttribute(USER_LOGIN_SESSION);
        user.setSeller(true);
        userService.save(user);
        return REDIRECT_PERSONAL_PAGE;
    }

    @GetMapping("/topUp")
    public String topUp(HttpSession session, Model model) {
        User user = (User) session.getAttribute(USER_LOGIN_SESSION);
        model.addAttribute(USER_LOGIN_SESSION, user);
        return "topUp";
    }

    @PostMapping("/topUp")
    public String submitInteger(@RequestParam("topUpAmount") int topUpAmount, HttpSession session) {
        User user = (User) session.getAttribute(USER_LOGIN_SESSION);
        userService.topUp(user, topUpAmount);
        return REDIRECT_PERSONAL_PAGE;
    }

    @GetMapping("/listUsers")
    public String listUsers(Model model, HttpSession httpSession) {
        model.addAttribute("usersList", userService.listUsers());
        return "usersList";
    }

    @GetMapping("/transaction-history")
    public String transactionHistory(HttpSession session, Model model) {
        User user = (User) session.getAttribute(USER_LOGIN_SESSION);
        List<TransactionDTO> transactions = transactionService.findAllByBuyerOrSeller(user, user);
        transactions.sort(Comparator.comparing(TransactionDTO::getTransactionId).reversed());
        model.addAttribute("user", user);
        model.addAttribute("transactions", transactions);
        return "transactionHistory";
    }

    @GetMapping("/extract")
    public String extractGameData(Model model) {
        List<Game> games = gameService.extractGameData();
        model.addAttribute(GAMES_STRING, games);
        return "gameList";
    }

    @GetMapping("/shopping-cart")
    public String viewShoppingCart(HttpSession session, Model model) {
        User user = (User) session.getAttribute(USER_LOGIN_SESSION);
        if (user == null) {
            return REDIRECT_LOGIN;
        }

        ShoppingCart cart = (ShoppingCart) session.getAttribute(CART_SUFFIX + user.getUserId());
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute(CART_SUFFIX + user.getUserId(), cart);
        }

        System.out.println("Cart contents:");
        for (Map.Entry<Game, Integer> entry : cart.getItems().entrySet()) {
            System.out.println("Item: " + entry.getKey().getName() + ", Quantity: " + entry.getValue());
        }

        double total = cart.calculateTotal();
        model.addAttribute("cart", cart.getItems());
        model.addAttribute("total", total);
        model.addAttribute(USER_LOGIN_SESSION, user);
        model.addAttribute("gameService", gameService);

        return "shoppingCart";
    }

    @PostMapping("/shopping-cart/delete")
    public String deleteFromCart(@RequestParam Long gameId, HttpSession session) {
        User user = (User) session.getAttribute(USER_LOGIN_SESSION);
        if (user == null) {
            return REDIRECT_LOGIN;
        }

        ShoppingCart cart = (ShoppingCart) session.getAttribute(CART_SUFFIX + user.getUserId());
        if (cart != null) {
            Game game = gameService.findByProductId(gameId);
            if (game != null) {
                cart.removeItem(game);
            }
        }
        return "redirect:/shopping-cart";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long gameId, HttpSession session) {
        User user = (User) session.getAttribute(USER_LOGIN_SESSION);
        if (user == null) {
            return REDIRECT_LOGIN;
        }

        Game game = gameService.findByProductId(gameId);
        ShoppingCart cart = (ShoppingCart) session.getAttribute(CART_SUFFIX + user.getUserId());

        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute(CART_SUFFIX + user.getUserId(), cart);
        }

        cart.addItem(game, 1);

        return "redirect:/game/list";
    }
    @PostMapping("/shopping-cart/buy")
    public String buyFromCart(HttpSession session, Model model) {
        User buyer = (User) session.getAttribute(USER_LOGIN_SESSION);
        if (buyer == null) {
            return REDIRECT_LOGIN;
        }

        ShoppingCart cart = (ShoppingCart) session.getAttribute(CART_SUFFIX + buyer.getUserId());
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute(CART_SUFFIX + buyer.getUserId(), cart);
        } else {
            try {
                gameService.cartBuyGames(cart, buyer);
            } catch (RuntimeException error) {
                cart.getItems().clear();
                model.addAttribute("error_message", error.getMessage());
                return "error_page1";
            }
        }
        return "redirect:/shopping-cart";
    }
}


