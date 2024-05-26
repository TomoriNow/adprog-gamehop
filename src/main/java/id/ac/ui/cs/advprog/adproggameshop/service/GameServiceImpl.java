package id.ac.ui.cs.advprog.adproggameshop.service;

import id.ac.ui.cs.advprog.adproggameshop.exception.InsufficientFundsException;
import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.Review;
import id.ac.ui.cs.advprog.adproggameshop.model.ShoppingCart;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.repository.GameRepository;
import id.ac.ui.cs.advprog.adproggameshop.repository.ReviewRepository;
import id.ac.ui.cs.advprog.adproggameshop.repository.TransactionRepository;
import id.ac.ui.cs.advprog.adproggameshop.repository.UserRepository;
import id.ac.ui.cs.advprog.adproggameshop.utility.CartBuy;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameBuyer;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DataExtractor<Game> dataExtractor; // Inject DataExtractor
    
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Game save(Game game){
        gameRepository.save(game);
        return game;
    }

    public Game saveWithOwner(Game game, Long owner_id) {
        User owner = userRepository.findUserByUserId(owner_id).orElse(null);
        game.setOwner(owner);
        return gameRepository.save(game);
    }

    public Game saveWithOwner(Game game, User owner) {
        game.setOwner(owner);
        return gameRepository.save(game);
    }

    @Override
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public List<GameDTO> findAllBy() {
        return gameRepository.findAllBy();
    }

    @Override
    public List<GameDTO> findAllByCategory(String category) {
        return gameRepository.findAllByCategory(category);
    }

    @Override
    public List<GameDTO> findAllByOwnerId(Long ownerid) {
        User owner = userRepository.findUserByUserId(ownerid).orElse(null);
        return gameRepository.findAllByOwner(owner);
    }

    @Override
    public List<GameDTO> findAllByOwner(User owner) {
        return gameRepository.findAllByOwner(owner);
    }

    @Override
    public List<Game> extractGameData() {
        return dataExtractor.extractData();
    }

    @Override @Transactional
    public Game buyGame(Long gameId, User buyer, int amount, GameBuyer gameBuyer){
        Game game = gameRepository.findByProductId(gameId);
        gameBuyer.buyGame(game, buyer, amount);

        Game result = gameRepository.save(gameBuyer.getGame());
        userRepository.save(gameBuyer.getSeller());
        userRepository.save(gameBuyer.getBuyer());
        transactionRepository.save(gameBuyer.createTransactionRecord());
        return result;
    }

    @Override @Transactional
    public ShoppingCart cartBuyGames(ShoppingCart shoppingCart, User buyer) {
        CartBuy cartBuyer = new CartBuy();
        Map<Game, Integer> items = shoppingCart.getItems();
        if (shoppingCart.calculateTotal() > buyer.getBalance()) {
            throw new InsufficientFundsException(shoppingCart.calculateTotal(), buyer.getBalance());
        }
        for (Map.Entry<Game, Integer> entry: items.entrySet()) {
            cartBuyer.buyGame(entry.getKey(), buyer, entry.getValue());
            gameRepository.save(cartBuyer.getGame());
            userRepository.save(cartBuyer.getSeller());
            userRepository.save(cartBuyer.getBuyer());
            transactionRepository.save(cartBuyer.createTransactionRecord());
        }
        shoppingCart.getItems().clear();
        return shoppingCart;
    }


    @Override
    public GameRepository getGameRepository() {
        return gameRepository;
    }
  
    @Override @Transactional
    public void deleteGameById(Long gameId) {
        gameRepository.deleteByProductId(gameId);
    }
    @Override @Transactional
    public Game findByProductId(Long productId) {
        return gameRepository.findByProductId(productId);
    }

    @Override @Transactional
    public List<Review> getReviewsByGame(Game game) {
        return reviewRepository.findByGame(game);
    }

    @Override @Transactional
    public void saveReview(Review review) {
        if (review == null) {
            throw new IllegalArgumentException("Review cannot be null");
        }
        reviewRepository.save(review);
    }
}
