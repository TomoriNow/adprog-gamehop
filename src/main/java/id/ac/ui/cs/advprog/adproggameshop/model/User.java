package id.ac.ui.cs.advprog.adproggameshop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

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

    public User(String userId, String username, String email, String password, int balance, String bio, byte[] profilePicture) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.bio = bio;
        this.profilePicture = profilePicture;

    }
}
