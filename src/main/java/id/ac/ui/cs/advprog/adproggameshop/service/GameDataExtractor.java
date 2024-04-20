package id.ac.ui.cs.advprog.adproggameshop.service;

import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameDataExtractor implements DataExtractor<Game> {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public List<Game> extractData() {
        return gameRepository.findAll();
    }
}
