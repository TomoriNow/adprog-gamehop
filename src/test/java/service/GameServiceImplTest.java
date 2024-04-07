package service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
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

    @InjectMocks
    private GameServiceImpl gameService;

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
    public void testFindAllGamesByCategory() {
        String category = "Test Category";
        List<GameDTO> mockGames = new ArrayList<>();
        mockGames.add(new GameDTO());
        mockGames.add(new GameDTO());

        when(gameRepository.findAllByCategory(category)).thenReturn(mockGames);

        List<GameDTO> foundGames = gameService.findAllByCategory(category);

        assertNotNull(foundGames);
        assertEquals(2, foundGames.size());
    }

    @Test
    public void testFindAllGamesByOwner() {
        User owner = new User();
        List<GameDTO> mockGames = new ArrayList<>();
        mockGames.add(new GameDTO());
        mockGames.add(new GameDTO());

        when(gameRepository.findAllByOwner(owner)).thenReturn(mockGames);

        List<GameDTO> foundGames = gameService.findAllByOwner(owner);

        assertNotNull(foundGames);
        assertEquals(2, foundGames.size());
    }
}

