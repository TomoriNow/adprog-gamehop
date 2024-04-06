package id.ac.ui.cs.advprog.adproggameshop.enums;

public enum CategoryEnums {
    TOY("Toy"),
    BOARDGAME("Board Game");

    private String label;

    CategoryEnums(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
