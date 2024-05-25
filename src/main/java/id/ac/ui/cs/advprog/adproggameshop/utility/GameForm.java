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

    @NotNull(message = "Name can not be null")
    @Size(min=1, max = 50, message = "Name must be between 1 and 50 characters long")
    private String name;

    @NotNull(message = "Quantity can not be null")
    @Min(value = 1, message = "You can not add a game that is already out of stock")
    private int quantity;

    @NotNull(message = "Price can not be null")
    @DecimalMin(value = "0.01", message = "You can not add a game that costs less than $0.01")
    private double price;

    @Size(min=0, max = 400, message = "The description can be no longer than 400 characters")
    private String description;

    @NotNull(message = "Category can not be null")
    private String category;

    private User owner;

    public Game createGame() {
        return new Game(name, price, description, quantity, category, owner);
    }
}
