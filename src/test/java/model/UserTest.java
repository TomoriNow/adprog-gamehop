package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.adproggameshop.model.User;

public class UserTest {

    @Test
    public void testUserConstructor() {
        String username = "testuser";
        String email = "test@example.com";
        String password = "testpassword";
        int balance = 0;
        String bio = "Test bio";
        byte[] profilePicture = null;

        User user = new User(username, email, password);

        assertNotNull(user);
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(balance, user.getBalance());
        assertNull(user.getBio());
        assertArrayEquals(profilePicture, user.getProfilePicture());
    }

}
