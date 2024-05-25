package id.ac.ui.cs.advprog.adproggameshop.utility;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {
    private Long userId;
    private String email;
    private String username;

    public UserDTO(Long userId, String email, String username) {
        this.userId = userId;
        this.email = email;
        this.username = username;
    }
}
