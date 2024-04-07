package id.ac.ui.cs.advprog.adproggameshop.enums;

public enum CategoryEnums {
    TOY("Toy"),
    BOARDGAME("Board Game"),
    SWITCH("Switch Game"),
    XBOXSERIESX("Xbox Series X Game"),
    XBOXONE("Xbox One Game"),
    PS5("PS5 Game"),
    PS4("PS4 Game");


    private String label;

    CategoryEnums(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
