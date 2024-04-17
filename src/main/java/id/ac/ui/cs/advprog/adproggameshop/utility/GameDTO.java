package id.ac.ui.cs.advprog.adproggameshop.utility;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GameDTO {
    private Long productId;
    private String name;
    private int price;
    private int quantity;
    private String category;
    private Long ownerUserId;
    private String ownerUsername;

    public GameDTO(Long productId, String name, int price,  int quantity, String category, Long ownerUserId, String ownerUsername) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.ownerUserId = ownerUserId;
        this.ownerUsername = ownerUsername;
    }
}
