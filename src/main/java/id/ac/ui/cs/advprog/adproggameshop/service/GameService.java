package id.ac.ui.cs.advprog.adproggameshop.service;

import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.Review;
import id.ac.ui.cs.advprog.adproggameshop.model.ShoppingCart;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.repository.GameRepository;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameBuyer;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
import id.ac.ui.cs.advprog.adproggameshop.utility.ReviewDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface GameService {
    Game save(Game game);
    List<Game> findAll();
    List<GameDTO> findAllBy();

    List<GameDTO> findAllByCategory(String category);

    List<GameDTO> findAllByOwnerId(Long ownerid);

    List<GameDTO> findAllByOwner(User owner);

    Game saveWithOwner(Game game, Long owner_id);

    Game saveWithOwner(Game game, User owner);

    GameRepository getGameRepository();
  
    List<Game> extractGameData();

    Game buyGame(Long gameId, User buyer, int amount, GameBuyer gameBuyer);

    void deleteGameById(Long gameId);

    Game findByProductId(Long productId);

    List<ReviewDTO> getReviewsByGame(Game game);
  
    void saveReview(Review review);

    ShoppingCart cartBuyGames(ShoppingCart shoppingCart, User buyer);
}
