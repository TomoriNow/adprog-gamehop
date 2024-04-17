package id.ac.ui.cs.advprog.adproggameshop.service;

import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.utility.UserDTO;

import java.util.List;

public interface UserService {
    User registerUser(String username, String password, String email);
    User authenticate(String username, String password);
    User save(User user);
    double topUp(User user, double topUpAmount);
    User editUserProfile(Long userId, String username, String email, String password, byte[] profilePicture);
    List<UserDTO> listUsers();
}
