package id.ac.ui.cs.advprog.adproggameshop.service;

import id.ac.ui.cs.advprog.adproggameshop.model.User;

public interface UserService {
    User authenticate(String username, String password);
    User registerUser(User user);
    User save(User user);
    int topUp(User user, int topUpAmount);
}
