package id.ac.ui.cs.advprog.adproggameshop.repository;

import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.Review;
import id.ac.ui.cs.advprog.adproggameshop.utility.ReviewDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<ReviewDTO> findByGame(Game game);
}
