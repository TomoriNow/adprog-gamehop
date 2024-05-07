package id.ac.ui.cs.advprog.adproggameshop.model;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<String, Integer> items;

    public ShoppingCart() {
        items = new HashMap<>();
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void addItem(String itemName, Integer quantity) {
        if (itemName == null || quantity == null) {
            throw new IllegalArgumentException("Item name and quantity must not be null");
        }
        items.put(itemName, items.getOrDefault(itemName, 0) + quantity);
    }

    public void removeItem(String itemName) {
        items.remove(itemName);
    }
}




