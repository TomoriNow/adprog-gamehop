package id.ac.ui.cs.advprog.adproggameshop.service;

import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testRegisterUser() {
        String username = "testuser";
        String email = "test@example.com";
        String password = "password";
        User user = new User(username, email, password);
        User savedUser = new User();
        savedUser.setUserId(1L);
        savedUser.setUsername(username);
        savedUser.setEmail(email);
        savedUser.setPassword(password);
        savedUser.setBalance(0);
        savedUser.setSeller(false);

        when(userRepository.findFirstByUsername(username)).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(savedUser);

        User result = userServiceImpl.registerUser(user);

        assertEquals(savedUser, result);
    }

    @Test
    void testAuthenticate() {
        String username = "testuser";
        String password = "password";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.of(user));

        User result = userServiceImpl.authenticate(username, password);

        assertEquals(user, result);
    }

    @Test
    void testSave() {
        User user = new User();
        user.setUserId(1L);

        when(userRepository.save(user)).thenReturn(user);

        User result = userServiceImpl.save(user);

        assertEquals(user, result);
    }

    @Test
    void testTopUp() {
        User user = new User();
        user.setBalance(100.0);
        double topUpAmount = 50.0;

        when(userRepository.save(user)).thenReturn(user);

        double result = userServiceImpl.topUp(user, topUpAmount);

        assertEquals(150.0, result);
    }

    @Test
    void testFindUserById() {
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);

        when(userRepository.findUserByUserId(userId)).thenReturn(Optional.of(user));

        User result = userServiceImpl.findUserById(userId);

        assertEquals(user, result);
    }
}
