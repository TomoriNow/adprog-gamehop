package id.ac.ui.cs.advprog.adproggameshop.enums;

import java.util.HashMap;
import java.util.Map;

public enum CategoryEnums {
    TOY("Toy"),
    BOARDGAME("Board Game"),
    SWITCH("Switch Game"),
    XBOXSERIESX("Xbox Series X Game"),
    XBOXONE("Xbox One Game"),
    PS5("PS5 Game"),
    PS4("PS4 Game");


    private String label;

    private static final Map<String, CategoryEnums> lookup = new HashMap<>();

    static {
        for (CategoryEnums category : CategoryEnums.values()) {
            lookup.put(category.getLabel(), category);
        }
    }

    CategoryEnums(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static CategoryEnums fromString(String label) {
        return lookup.get(label);
    }
}
