package id.ac.ui.cs.advprog.adproggameshop.factory;

import id.ac.ui.cs.advprog.adproggameshop.enums.CategoryEnums;

import java.util.HashMap;
import java.util.Map;

public class CategoryFactory {
    private static final Map<String, CategoryEnums> categoryMap = new HashMap<>();

    static {
        for (CategoryEnums categoryEnum : CategoryEnums.values()) {
            categoryMap.put(categoryEnum.getLabel().toLowerCase(), categoryEnum);
        }
    }

    public static CategoryEnums getCategory(String category) {
        return categoryMap.get(category.toLowerCase());
    }
}