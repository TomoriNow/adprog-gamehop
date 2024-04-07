package id.ac.ui.cs.advprog.adproggameshop.utility;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryOption {
    private String value;
    private String label;

    public CategoryOption(String value, String label) {
        this.value = value;
        this.label = label;
    }
}