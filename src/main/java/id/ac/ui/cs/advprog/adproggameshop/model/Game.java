package id.ac.ui.cs.advprog.adproggameshop.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Getter @Setter
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String productId;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "category", nullable = false)
    private String category;

    @ManyToOne
    @JoinColumn(name = "ownerId", referencedColumnName = "userId")
    private User owner;

    public Game(String productId, int price, String description, int quantity, String category, User owner) {
        this.productId = productId;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.category = category;
        this.owner = owner;
    }
}
