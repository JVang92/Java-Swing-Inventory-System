package store;

public class Food extends Product {
    private String type; // like "Dry Kibble", "Wet Food"

    public Food() {
        super(); // calling Product()
        this.type = "Generic";
    }

    public Food(String name, String manufacturer, double price, String type) {
        super(name, manufacturer, price); 
        setType(type);
    }

    public String getType() { return type; }

    public void setType(String type) {
        this.type = (type == null || type.isEmpty()) ? "Generic" : type;
    }

    
    public String toString() {
        return super.toString() + ", Type: " + type;
    }
}