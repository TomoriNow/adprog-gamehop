package id.ac.ui.cs.advprog.adproggameshop.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false, scale = 2)
    private double price;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "category", nullable = false)
    private String category;

    @ManyToOne
    @JoinColumn(name = "ownerId", referencedColumnName = "userId")
    private User owner;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    @Transient
    private MultipartFile imageFile;

    public Game() {}
    public Game(String name, double price, String description, int quantity, String category, User owner, MultipartFile imageFile) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.category = category;
        this.owner = owner;
        this.imageFile = imageFile;
    }
}
