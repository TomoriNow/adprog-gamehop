package utility;

import id.ac.ui.cs.advprog.adproggameshop.utility.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class UserDTOTest {
    UserDTO userDTO;
    @BeforeEach
    public void setup() {
        userDTO = new UserDTO(1L, "email", "username");
    }

    @Test
    public void testConstructor() {
        assertEquals(1L, userDTO.getUserId());
        assertEquals("email", userDTO.getEmail());
        assertEquals("username", userDTO.getUsername());
    }

    @Test
    public void testSetUserId() {
        userDTO.setUserId(2L);

        assertEquals(2L, userDTO.getUserId());
        assertEquals("email", userDTO.getEmail());
        assertEquals("username", userDTO.getUsername());
    }

    @Test
    public void testSetEmail() {
        userDTO.setEmail("email2");

        assertEquals(1L, userDTO.getUserId());
        assertEquals("email2", userDTO.getEmail());
        assertEquals("username", userDTO.getUsername());
    }

    @Test
    public void testSetUsername() {
        userDTO.setUsername("username2");

        assertEquals(1L, userDTO.getUserId());
        assertEquals("email", userDTO.getEmail());
        assertEquals("username2", userDTO.getUsername());
    }
}
