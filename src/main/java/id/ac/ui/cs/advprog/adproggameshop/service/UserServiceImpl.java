package id.ac.ui.cs.advprog.adproggameshop.service;

import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import id.ac.ui.cs.advprog.adproggameshop.service.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(String username, String password, String email) {
        if (username != null && password != null && email != null) {
            if (userRepository.findFirstByUsername(username).isPresent()) {
                System.out.println("Duplicate user, please enter a different username");
                return null;
            }
            User newUser = new User(username, email, password);
            return userRepository.save(newUser);
        } else {
            return null;
        }
    }

    @Override
    public User authenticate(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password).orElse(null);
    }

    @Override
    public User save(User user){
        return userRepository.save(user);
    }

    @Override
    public int topUp(User user, int topUpAmount) {
        int newBalance = user.getBalance() + topUpAmount;
        user.setBalance(newBalance);
        User user1 = userRepository.save(user);
        return user1.getBalance();
    }
}
