package id.ac.ui.cs.advprog.adproggameshop.model;


import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Game, Integer> items;

    public ShoppingCart() {
        items = new HashMap<>();
    }

    public Map<Game, Integer> getItems() {
        return items;
    }

    public void addItem(Game game, Integer quantity) {
        if (game == null || game.getProductId() == null) {
            throw new IllegalArgumentException("Game and productId must not be null");
        }
        if (quantity == null) {
            quantity = 0;
        }
        for (Game existingGame : items.keySet()) {
            if (game.getProductId().equals(existingGame.getProductId())) {
                items.put(existingGame, items.get(existingGame) + quantity);
                return;
            }
        }
        items.put(game, quantity);
    }


    public void removeItem(Game game) {
        items.remove(game);
    }

    public double calculateTotal() {
        double total = 0.0;
        for (Map.Entry<Game, Integer> entry : items.entrySet()) {
            Game game = entry.getKey();
            int quantity = entry.getValue();
            double price = game.getPrice();
            total += price * quantity;
        }
        return total;
    }
}





