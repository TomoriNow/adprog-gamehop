package id.ac.ui.cs.advprog.adproggameshop.service;

import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.User;

import java.util.List;

public interface GameService {
    Game save(Game game);
    List<Game> findAll();

    List<Game> findAllByCategory(String category);

    List<Game> findAllByOwnerId(Long ownerid);

    List<Game> findAllByOwner(User owner);

    Game saveWithOwner(Game game, Long owner_id);

    Game saveWithOwner(Game game, User owner);
}
