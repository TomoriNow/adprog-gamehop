package id.ac.ui.cs.advprog.adproggameshop.model;

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

    public void addItem(String itemName, int quantity) {
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

    public void updateQuantity(String itemName, int quantity) {
        items.put(itemName, quantity);
    }

    public void clear() {
        items.clear();
    }
}



