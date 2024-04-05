package id.ac.ui.cs.advprog.adproggameshop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Objects;

@Entity
@Getter @Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "balance", nullable = false)
    private int balance;

    @Column(name = "bio")
    private String bio;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] profilePicture;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return balance == user.balance && Objects.equals(userId, user.userId) && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(bio, user.bio) && Arrays.equals(profilePicture, user.profilePicture);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(userId, username, email, password, balance, bio);
        result = 31 * result + Arrays.hashCode(profilePicture);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                ", bio='" + bio + '\'' +
                ", profilePicture=" + Arrays.toString(profilePicture) +
                '}';
    }

    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String username, String email, String password, int balance, String bio, byte[] profilePicture) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.bio = bio;
        this.profilePicture = profilePicture;
    }
}
