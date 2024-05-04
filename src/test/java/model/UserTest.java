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

    @Test
    public void testEqualsAndHashCode() {
        User user1 = new User("username", "email@example.com", "password");
        User user2 = new User("username", "email@example.com", "password");

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testToString() {
        User user = new User("username", "email@example.com", "password");

        assertEquals("User{userId='null', username='username', email='email@example.com', balance=0.0, bio='null', profilePicture=null}", user.toString());
    }

}
