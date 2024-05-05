package id.ac.ui.cs.advprog.adproggameshop.factory;

import id.ac.ui.cs.advprog.adproggameshop.enums.CategoryEnums;
import id.ac.ui.cs.advprog.adproggameshop.repository.GameRepository;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;

import java.util.List;

public class XboxSeriesXGameHandler implements CategoryHandler{
    private final GameRepository gameRepository;
    public XboxSeriesXGameHandler(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
    @Override
    public List<GameDTO> getGames() {
        return gameRepository.findAllByCategory(CategoryEnums.XBOXSERIESX.getLabel());
    }

}
