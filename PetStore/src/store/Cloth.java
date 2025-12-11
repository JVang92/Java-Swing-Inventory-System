package store;

public class Cloth extends Accessory {
    private String type; // like "Sweater", "Bandana", or "Pants"

    public Cloth() {
        super();
        this.type = "None";
    }

    public Cloth(String name, String manufacturer, double price, String type) {
        super(name, manufacturer, price);
        setType(type);
    }

    public String getType() { return type; }

    public void setType(String type) {
        this.type = (type == null || type.isEmpty()) ? "None" : type;
    }


    public String toString() {
        return super.toString() + ", Cloth Type: " + type;
    }
}