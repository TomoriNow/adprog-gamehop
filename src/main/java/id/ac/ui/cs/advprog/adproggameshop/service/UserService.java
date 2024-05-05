package id.ac.ui.cs.advprog.adproggameshop.service;

import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.utility.UserDTO;

import java.util.List;

public interface UserService {
    User authenticate(String username, String password);
    User registerUser(User user);
    User save(User user);
    double topUp(User user, double topUpAmount);
    List<UserDTO> listUsers();
    User findUserById(Long userId);
}
