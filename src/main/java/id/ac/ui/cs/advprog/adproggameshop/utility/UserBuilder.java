package id.ac.ui.cs.advprog.adproggameshop.utility;
import id.ac.ui.cs.advprog.adproggameshop.model.User;

public class UserBuilder {
    private String username;
    private String email;
    private String password;
    private int balance;
    private String bio;
    private byte[] profilePicture;
    private boolean isSeller;

    public UserBuilder(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserBuilder balance(int balance) {
        this.balance = balance;
        return this;
    }

    public UserBuilder bio(String bio) {
        this.bio = bio;
        return this;
    }

    public UserBuilder profilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public UserBuilder isSeller(boolean isSeller) {
        this.isSeller = isSeller;
        return this;
    }

    public User build() {
        return new User(username, email, password, balance, bio, profilePicture, isSeller);
    }
}
