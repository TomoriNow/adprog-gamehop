package id.ac.ui.cs.advprog.adproggameshop.factory;

import id.ac.ui.cs.advprog.adproggameshop.enums.CategoryEnums;
import id.ac.ui.cs.advprog.adproggameshop.repository.GameRepository;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;

import java.util.List;

public class Ps4GameHandler implements CategoryHandler{
    private final GameRepository gameRepository;
    public Ps4GameHandler(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
    @Override
    public List<GameDTO> getGames() {
        return gameRepository.findAllByCategory(CategoryEnums.PS4.getLabel());
    }

}
