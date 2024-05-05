package id.ac.ui.cs.advprog.adproggameshop.model;

import jakarta.transaction.Transactional;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private static ShoppingCart instance;
    private Map<String, Integer> items;

    private ShoppingCart() {
        items = new HashMap<>();
    }

    public static ShoppingCart getInstance() {
        if (instance == null) {
            instance = new ShoppingCart();
        }
        return instance;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void setItems(Map<String, Integer> items) {
        this.items = items;
    }
    @Transactional
    public void addItem(String itemName, Integer quantity) {
        if (itemName == null) {
            throw new IllegalArgumentException("Item name must exist and cannot be null");
        }
        if (quantity == null) {
            throw new IllegalArgumentException("Quantity must exist and cannot be null");
        }
        if (items.containsKey(itemName)) {
            int currentQuantity = items.get(itemName);
            items.put(itemName, currentQuantity + quantity);
        } else {
            items.put(itemName, quantity);
        }
    }

    public void removeItem(String itemName) {
        items.remove(itemName);
    }

}



