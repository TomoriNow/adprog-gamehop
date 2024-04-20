package service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import id.ac.ui.cs.advprog.adproggameshop.utility.UserBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.repository.UserRepository;
import id.ac.ui.cs.advprog.adproggameshop.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testRegisterUser_SuccessfulRegistration() {
        String username = "testUser";
        String password = "testPassword";
        String email = "test@example.com";

        User newUser = new UserBuilder(username, email, password).build();

        when(userRepository.findFirstByUsername(username)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User registeredUser = userService.registerUser(newUser);

        assertNotNull(registeredUser);
        assertEquals(username, registeredUser.getUsername());
        assertEquals(email, registeredUser.getEmail());
        assertEquals(password, registeredUser.getPassword());
    }

    @Test
    public void testRegisterUser_DuplicateUsername() {
        String username = "existingUser";
        String password = "testPassword";
        String email = "test@example.com";

        when(userRepository.findFirstByUsername(username)).thenReturn(Optional.of(new User()));

        User registeredUser = userService.registerUser(new UserBuilder(username, email, password).build());

        assertNull(registeredUser);
    }

    @Test
    public void testAuthenticate_SuccessfulAuthentication() {
        String username = "existingUser";
        String password = "testPassword";
        User existingUser = new User(username, "test@example.com", password);

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.of(existingUser));

        User authenticatedUser = userService.authenticate(username, password);

        assertNotNull(authenticatedUser);
        assertEquals(username, authenticatedUser.getUsername());
        assertEquals(password, authenticatedUser.getPassword());
    }

    @Test
    public void testAuthenticate_AuthenticationFailed() {
        String username = "existingUser";
        String password = "wrongPassword";

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.empty());

        User authenticatedUser = userService.authenticate(username, password);

        assertNull(authenticatedUser);
    }
}
