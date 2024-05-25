package utility;

import id.ac.ui.cs.advprog.adproggameshop.exception.GameDoesNotExistException;
import id.ac.ui.cs.advprog.adproggameshop.exception.InsufficientFundsException;
import id.ac.ui.cs.advprog.adproggameshop.exception.NotEnoughLeftException;
import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.utility.CartBuy;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameBuyer;
import id.ac.ui.cs.advprog.adproggameshop.utility.OneClickBuy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class GameBuyerTest {
    List<Game> games;

    User seller;

    User buyer;

    @BeforeEach
    public void setup() {
        games = new ArrayList<>();

        seller = new User();
        seller.setBalance(500);
        seller.setSeller(true);
        seller.setUserId(1L);
        seller.setUsername("Seller");

        buyer = new User();
        buyer.setBalance(1000);
        buyer.setSeller(false);
        buyer.setUserId(2L);
        buyer.setUsername("Buyer");

        Game game1 = new Game();
        game1.setProductId(1L);
        game1.setPrice(60);
        game1.setName("Pokemon");
        game1.setQuantity(20);
        game1.setCategory("Switch Game");
        game1.setOwner(seller);

        games.add(game1);

        Game game2 = new Game();
        game2.setProductId(2L);
        game2.setPrice(30);
        game2.setName("Palworld");
        game2.setQuantity(400);
        game2.setCategory("Toy");
        game2.setOwner(seller);

        games.add(game2);
    }

    @Test
    public void testOneClickBuyGameValid() {
        Game game1 = games.get(1);
        assertEquals(seller, game1.getOwner());

        OneClickBuy oneClickBuy = new OneClickBuy();

        Game resultGame = oneClickBuy.buyGame(game1, buyer, 1);;

        assertEquals(game1, resultGame);
        assertEquals(game1, oneClickBuy.getGame());
        assertEquals(399, oneClickBuy.getGame().getQuantity());
        assertEquals(buyer, oneClickBuy.getBuyer());
        assertEquals(970, oneClickBuy.getBuyer().getBalance());
        assertEquals(seller, oneClickBuy.getSeller());
        assertEquals(530, oneClickBuy.getSeller().getBalance());
        assertEquals(1, oneClickBuy.getAmount());
        assertEquals(30, oneClickBuy.getBaseCost());
    }

    @Test
    public void testOneClickBuyGameDefault() {
        Game game1 = games.get(1);
        assertEquals(seller, game1.getOwner());

        OneClickBuy oneClickBuy = new OneClickBuy();

        Game resultGame = oneClickBuy.buyGame(game1, buyer);;

        assertEquals(game1, resultGame);
        assertEquals(game1, oneClickBuy.getGame());
        assertEquals(399, oneClickBuy.getGame().getQuantity());
        assertEquals(buyer, oneClickBuy.getBuyer());
        assertEquals(970, oneClickBuy.getBuyer().getBalance());
        assertEquals(seller, oneClickBuy.getSeller());
        assertEquals(530, oneClickBuy.getSeller().getBalance());
        assertEquals(1, oneClickBuy.getAmount());
        assertEquals(30, oneClickBuy.getBaseCost());
    }

    @Test
    public void testBuyGameMultipleValid() {
        Game game1 = games.get(1);
        assertEquals(seller, game1.getOwner());

        CartBuy cartBuyer = new CartBuy();

        Game resultGame = cartBuyer.buyGame(game1, buyer, 5);;

        assertEquals(game1, resultGame);
        assertEquals(game1, cartBuyer.getGame());
        assertEquals(395, cartBuyer.getGame().getQuantity());
        assertEquals(buyer, cartBuyer.getBuyer());
        assertEquals(850, cartBuyer.getBuyer().getBalance());
        assertEquals(seller, cartBuyer.getSeller());
        assertEquals(650, cartBuyer.getSeller().getBalance());
        assertEquals(5, cartBuyer.getAmount());
        assertEquals(150, cartBuyer.getBaseCost());
    }

    @Test
    public void testBuyGameInsufficientFunds() {
        Game game1 = games.get(1);
        buyer.setBalance(0);

        GameBuyer oneClickBuy = new OneClickBuy();

        assertThrows(InsufficientFundsException.class,
                () -> oneClickBuy.buyGame(game1, buyer,1));
    }

    @Test
    public void testBuyGameDoesNotExist() {
        Game game = null;

        GameBuyer oneClickBuy = new OneClickBuy();

        assertThrows(GameDoesNotExistException.class,
                () -> oneClickBuy.buyGame(game, buyer, 1));
    }

    @Test
    public void testBuyGameOutOfStock() {
        Game game = games.getFirst();
        game.setQuantity(0);

        OneClickBuy oneClickBuy = new OneClickBuy();

        assertThrows(NotEnoughLeftException.class,
                () -> oneClickBuy.buyGame(game, buyer, 1));
    }

    @Test
    public void testBuyGameNotEnoughInStock() {
        Game game = games.getFirst();
        game.setQuantity(100);

        buyer.setBalance(101 * game.getPrice());

        GameBuyer cartBuyer = new CartBuy();

        assertThrows(NotEnoughLeftException.class,
                () -> cartBuyer.buyGame(game, buyer, 101));
    }
}
