package id.ac.ui.cs.advprog.adproggameshop.service;

import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.repository.UserRepository;
import id.ac.ui.cs.advprog.adproggameshop.utility.UserDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public User registerUser(User user) {
        if (user.getUsername() != null && user.getPassword() != null && user.getEmail() != null) {
            if (userRepository.findFirstByUsername(user.getUsername()).isPresent()) {
                System.out.println("Duplicate user, please enter a different username");
                return null;
            }
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public User authenticate(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password).orElse(null);
    }

    @Override
    public User save(User user){
        return userRepository.save(user);
    }

    @Override @Transactional
    public byte[] findProfilePictureByUserId(Long userId) {
        return userRepository.findProfilePictureByUserId(userId).orElse(null);
    }

    @Override
    public double topUp(User user, double topUpAmount) {
        double newBalance = user.getBalance() + topUpAmount;
        user.setBalance(newBalance);
        User user1 = userRepository.save(user);
        return user1.getBalance();
    }

    @Override
    public List<UserDTO> listUsers() {
        return userRepository.findAllBy();
    }

    @Override
    @Transactional
    public User findUserById(Long userId) {
        return userRepository.findUserByUserId(userId).orElse(null);
    }
}
