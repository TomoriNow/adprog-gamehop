package id.ac.ui.cs.advprog.adproggameshop.repository;

import id.ac.ui.cs.advprog.adproggameshop.utility.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT new User(u.userId, u.username, u.email, u.password, u.balance, u.bio, u.isSeller) FROM User u WHERE u.username = :username AND u.password = :password")
    Optional<User> findByUsernameAndPassword(String username, String password);

    @Query("SELECT u.profilePicture FROM User u WHERE u.userId = :userId")
    Optional<byte[]> findProfilePictureByUserId(@Param("userId") Long userId);

    @Query("SELECT new User(u.userId, u.username, u.email, u.password, u.balance, u.bio, u.isSeller) FROM User u WHERE u.username = :username")
    Optional<User> findFirstByUsername(String username);

    @Query("SELECT new User(u.userId, u.username, u.email, u.password, u.balance, u.bio, u.isSeller) FROM User u WHERE u.userId = :userId")
    Optional<User> findUserByUserId(Long userId);

    @Query("SELECT new User(u.userId, u.username, u.email, u.password, u.balance, u.bio, u.isSeller) FROM User u WHERE u.userId = :userId")
    Optional<User> findByUserId(long userId);

    @Query("SELECT new User(u.userId, u.username, u.email, u.password, u.balance, u.bio, u.isSeller) FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(String username);

    @Query("SELECT new User(u.userId, u.username, u.email, u.password, u.balance, u.bio, u.isSeller) FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);

    List<UserDTO> findAllBy();
}
