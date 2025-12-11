package store;

public class Accessory extends Product {
    // Accessory is just a specific type of Product
    // currently has no fields as it is a category of Product
    
    public Accessory() {
        super();
    }

    public Accessory(String name, String manufacturer, double price) {
        super(name, manufacturer, price);
    }
}