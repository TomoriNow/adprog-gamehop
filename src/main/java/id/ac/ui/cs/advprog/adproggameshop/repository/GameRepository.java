package id.ac.ui.cs.advprog.adproggameshop.repository;

import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {
    List<Game> findAllByCategory(String category);

    List<Game> findAllByOwner(User owner);

    Game findByProductId(Long gameId);
}
