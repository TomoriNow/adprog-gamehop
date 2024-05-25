package utility;

import id.ac.ui.cs.advprog.adproggameshop.utility.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class UserDTOTest {

    @Test
    public void testConstructor() {
        UserDTO userDTO = new UserDTO(1L, "email", "username");

        assertEquals(1L, userDTO.getUserId());
        assertEquals("email", userDTO.getEmail());
        assertEquals("username", userDTO.getUsername());
    }

    @Test
    public void testSetUserId() {
        UserDTO userDTO = new UserDTO(1L, "email", "username");

        userDTO.setUserId(2L);

        assertEquals(2L, userDTO.getUserId());
        assertEquals("email", userDTO.getEmail());
        assertEquals("username", userDTO.getUsername());
    }

    @Test
    public void testSetEmail() {
        UserDTO userDTO = new UserDTO(1L, "email", "username");

        userDTO.setEmail("email2");

        assertEquals(1L, userDTO.getUserId());
        assertEquals("email2", userDTO.getEmail());
        assertEquals("username", userDTO.getUsername());
    }

    @Test
    public void testSetUsername() {
        UserDTO userDTO = new UserDTO(1L, "email", "username");

        userDTO.setUsername("username2");

        assertEquals(1L, userDTO.getUserId());
        assertEquals("email", userDTO.getEmail());
        assertEquals("username2", userDTO.getUsername());
    }
}
