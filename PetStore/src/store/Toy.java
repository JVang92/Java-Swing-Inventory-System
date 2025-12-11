package store;

public class Toy extends Product {
    private String material; // EX: "Rubber" or "Plush"

    public Toy() {
        super();
        this.material = "Plastic";
    }

    public Toy(String name, String manufacturer, double price, String material) {
        super(name, manufacturer, price);
        setMaterial(material);
    }

    public String getMaterial() { return material; }

    public void setMaterial(String material) {
        this.material = (material == null || material.isEmpty()) ? "Unknown" : material;
    }


    public String toString() {
        return super.toString() + ", Material: " + material;
    }
}