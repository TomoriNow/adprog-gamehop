package id.ac.ui.cs.advprog.adproggameshop.repository;

import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.utility.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testFindByUsernameAndPassword() {
        String username = "testuser";
        String password = "password";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.of(user));

        Optional<User> result = userRepository.findByUsernameAndPassword(username, password);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testFindFirstByUsername() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findFirstByUsername(username)).thenReturn(Optional.of(user));

        Optional<User> result = userRepository.findFirstByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testFindUserByUserId() {
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);

        when(userRepository.findUserByUserId(userId)).thenReturn(Optional.of(user));

        Optional<User> result = userRepository.findUserByUserId(userId);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testFindByUserId() {
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);

        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));

        Optional<User> result = userRepository.findByUserId(userId);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testFindByUsername() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Optional<User> result = userRepository.findByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testFindByEmail() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> result = userRepository.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }
}
