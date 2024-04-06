package id.ac.ui.cs.advprog.adproggameshop.service;

import id.ac.ui.cs.advprog.adproggameshop.model.User;

public interface UserService {
    User registerUser(String username, String password, String email);
    User authenticate(String username, String password);
    User save(User user);

    int topUp(User user, int topUpAmount);
}
