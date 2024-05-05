package id.ac.ui.cs.advprog.adproggameshop.utility;


import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GameForm {

    @NotNull
    @Size(min=1, max = 50)
    private String name;

    @NotNull
    @Min(1)
    private int quantity;

    @NotNull
    @DecimalMin("0.01")
    private double price;

    @Size(min=0, max = 400)
    private String description;

    @NotNull
    private String category;

    private User owner;

    public Game createGame() {
        return new Game(name, price, description, quantity, category, owner);
    }
}
