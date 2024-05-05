package id.ac.ui.cs.advprog.adproggameshop.factory;

import id.ac.ui.cs.advprog.adproggameshop.enums.CategoryEnums;
import id.ac.ui.cs.advprog.adproggameshop.repository.GameRepository;


public class CategoryFactory {
    public static CategoryHandler createCategoryHandler(CategoryEnums category, GameRepository gameRepository) {
        switch (category) {
            case TOY:
                return new ToyCategoryHandler(gameRepository);
            case BOARDGAME:
                return new BoardGameHandler(gameRepository);
            case SWITCH:
                return new SwitchGameHandler(gameRepository);
            case XBOXSERIESX:
                return new XboxSeriesXGameHandler(gameRepository);
            case XBOXONE:
                return new XboxOneGameHandler(gameRepository);
            case PS5:
                return new Ps5GameHandler(gameRepository);
            case PS4:
                return new Ps4GameHandler(gameRepository);
            default:
                throw new IllegalArgumentException("Unsupported category: " + category);
        }
    }
}