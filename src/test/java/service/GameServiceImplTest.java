package service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import id.ac.ui.cs.advprog.adproggameshop.model.Transaction;
import id.ac.ui.cs.advprog.adproggameshop.repository.TransactionRepository;
import id.ac.ui.cs.advprog.adproggameshop.repository.UserRepository;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameBuyer;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
import id.ac.ui.cs.advprog.adproggameshop.exception.InsufficientFundsException;
import id.ac.ui.cs.advprog.adproggameshop.utility.OneClickBuy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.repository.GameRepository;
import id.ac.ui.cs.advprog.adproggameshop.service.GameServiceImpl;

@ExtendWith(MockitoExtension.class)
public class GameServiceImplTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GameBuyer gameBuyer;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private GameServiceImpl gameService;

    List<Game> games;

    List<GameDTO> gameDTOS;

    User seller;

    User buyer;

    @BeforeEach
    public void setup() {
        games = new ArrayList<>();
        gameDTOS = new ArrayList<>();

        seller = new User();
        seller.setBalance(500);
        seller.set_seller(true);
        seller.setUserId(1L);
        seller.setUsername("Seller");

        buyer = new User();
        buyer.setBalance(1000);
        buyer.set_seller(false);
        buyer.setUserId(2L);
        buyer.setUsername("Buyer");

        Game game1 = new Game();
        game1.setProductId(1L);
        game1.setPrice(60);
        game1.setName("Pokemon");
        game1.setQuantity(20);
        game1.setCategory("Switch Game");
        game1.setOwner(seller);
        GameDTO gameDTO1 = new GameDTO(1L, "Pokemon", 60, 20, "Switch", seller.getUserId(), seller.getUsername());

        games.add(game1);
        gameDTOS.add(gameDTO1);

        Game game2 = new Game();
        game2.setProductId(2L);
        game2.setPrice(30);
        game2.setName("Palworld");
        game2.setQuantity(400);
        game2.setCategory("Toy");
        game2.setOwner(seller);
        GameDTO gameDTO2 = new GameDTO(2L, "Palworld", 30, 400, "Toy", seller.getUserId(), seller.getUsername());

        games.add(game2);
        gameDTOS.add(gameDTO2);
    }

    @Test
    public void testSaveGame() {
        Game gameToSave = new Game();
        gameToSave.setName("Test Game");
        gameToSave.setCategory("Test Category");
        gameToSave.setOwner(new User());

        when(gameRepository.save(gameToSave)).thenReturn(gameToSave);

        Game savedGame = gameService.save(gameToSave);

        assertNotNull(savedGame);
        assertEquals("Test Game", savedGame.getName());
        assertEquals("Test Category", savedGame.getCategory());
        assertNotNull(savedGame.getOwner());
    }

    @Test
    public void testFindAllGames() {
        List<Game> mockGames = new ArrayList<>();
        mockGames.add(new Game());
        mockGames.add(new Game());

        when(gameRepository.findAll()).thenReturn(mockGames);

        List<Game> foundGames = gameService.findAll();

        assertNotNull(foundGames);
        assertEquals(2, foundGames.size());
    }

    @Test
    public void testFindAllGamesByOwner() {
        doReturn(gameDTOS).when(gameRepository).findAllByOwner(seller);

        List<GameDTO> result = gameService.findAllByOwner(seller);

        assertEquals(result, gameDTOS);
        verify(gameRepository, times(1)).findAllByOwner(seller);
    }

    @Test
    public void testFindAllGamesByValidOwnerId() {
        when(userRepository.findUserByUserId(seller.getUserId())).thenReturn(Optional.of(seller));

        when(gameRepository.findAllByOwner(seller)).thenReturn(gameDTOS);

        List<GameDTO> result = gameService.findAllByOwnerId(seller.getUserId());

        assertEquals(gameDTOS, result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findUserByUserId(seller.getUserId());
        verify(gameRepository, times(1)).findAllByOwner(seller);
    }

    @Test
    public void testFindAllGamesByInvalidOwnerId() {
        when(userRepository.findUserByUserId(-1L)).thenReturn(Optional.empty());

        when(gameRepository.findAllByOwner(null)).thenReturn(new ArrayList<>());

        List<GameDTO> result = gameService.findAllByOwnerId(-1L);

        assertEquals(new ArrayList<>(), result);
        assertEquals(0, result.size());
        verify(userRepository, times(1)).findUserByUserId(-1L);
        verify(gameRepository, times(1)).findAllByOwner(null);
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
    }

    @Test
    public void testOneClickBuyGameInvalid() {
        Game game1 = games.get(1);
        buyer.setBalance(0);

        OneClickBuy oneClickBuy = new OneClickBuy();

        assertThrows(InsufficientFundsException.class,
                () -> oneClickBuy.buyGame(game1, buyer,1));
    }

    @Test
    public void testBuyGameWithGameBuyer() {
        Game game1 = games.get(1);
        Game game2 = new Game(game1.getName(), game1.getPrice(), game1.getDescription(), game1.getQuantity()-1, game1.getCategory(), game1.getOwner());
        User buyer1 = new User(buyer.getUsername(), buyer.getEmail(), buyer.getPassword(),buyer.getBalance()-30, buyer.getBio(), buyer.getProfilePicture(), buyer.is_seller());
        seller.setBalance(seller.getBalance()+30);

        when(gameRepository.findByProductId(game1.getProductId())).thenReturn(game1);
        when(gameBuyer.buyGame(game1, buyer,1)).thenReturn(game2);
        when(gameBuyer.getGame()).thenReturn(game2);
        when(gameBuyer.getSeller()).thenReturn(seller);
        when(gameBuyer.getBuyer()).thenReturn(buyer1);
        Transaction transaction = new Transaction(buyer1, seller, game2.getName(), new Date(), 1, game2.getPrice());
        when(gameBuyer.createTransactionRecord()).thenReturn(transaction);
        when(gameRepository.save(game2)).thenReturn(game2);

        Game resultGame = gameService.buyGame(game1.getProductId(), buyer, 1, gameBuyer);



        assertEquals(game2, resultGame);
        verify(gameRepository, times(1)).findByProductId(game1.getProductId());
        verify(gameBuyer, times(1)).buyGame(game1,buyer,1);
        verify(gameBuyer, times(1)).getGame();
        verify(gameBuyer, times(1)).getBuyer();
        verify(gameBuyer, times(1)).getSeller();
        verify(gameBuyer, times(1)).createTransactionRecord();
        verify(gameRepository, times(1)).save(game2);
        verify(userRepository, times(1)).save(seller);
        verify(userRepository, times(1)).save(buyer1);
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    public void testFindAllByCategory() {
        when(gameRepository.findAllByCategory(anyString())).thenReturn(Arrays.asList(
                new GameDTO(1L, "Game1", 19.99, 10, "Category1", 1L, "User1"),
                new GameDTO(2L, "Game2", 29.99, 5, "Category1", 2L, "User2")));

        List<GameDTO> games = gameService.findAllByCategory("Category1");

        assertEquals(2, games.size());
        assertEquals("Game1", games.get(0).getName());
        assertEquals("Game2", games.get(1).getName());
    }

}

