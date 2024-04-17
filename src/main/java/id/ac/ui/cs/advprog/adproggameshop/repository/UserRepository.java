package id.ac.ui.cs.advprog.adproggameshop.repository;

import id.ac.ui.cs.advprog.adproggameshop.utility.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import id.ac.ui.cs.advprog.adproggameshop.model.User;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsernameAndPassword(String username, String password);
    Optional<User> findFirstByUsername(String username);

    Optional<User> findUserByUserId(Long userId);
  
    Optional<User> findByUserId(long userId);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<UserDTO> findAllBy();
}
