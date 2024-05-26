package service;

import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.repository.GameRepository;
import id.ac.ui.cs.advprog.adproggameshop.service.GameDataExtractor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class DataExtractorTest {
    @Mock
    GameRepository gameRepository;

    @InjectMocks
    GameDataExtractor gameDataExtractor;

    @Test
    public void testExtractData() {
        List<Game> games = new ArrayList<>();

        when(gameRepository.findAll()).thenReturn(games);
        List<Game> resultGames = gameDataExtractor.extractData();

        assertEquals(games, resultGames);
        verify(gameRepository, times(1)).findAll();

    }

}
