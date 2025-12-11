package store;

public class Product {
    private String name;
    private String manufacturer;
    private double price;
    private int quantity;

    // default constructor
    public Product() {
        this.name = "Unknown";
        this.manufacturer = "Unknown";
        this.price = 9.99;
        this.quantity = 0;
    }

    // parameterized constructor
    public Product(String name, String manufacturer, double price) {
        setName(name);
        setManufacturer(manufacturer);
        setPrice(price);
        this.quantity = 0;
    }

    // accessors
    public String getName() { return this.name; }
    public String getManufacturer() { return this.manufacturer; }
    public double getPrice() { return this.price; }
    public int getQuantity() { return quantity; }

    // mutators
    public void setName(String name) {
        if(name == null || name.isEmpty()) {
            this.name = "Unknown";
        } else {
            this.name = name;
        }
    }

    public void setManufacturer(String manufacturer) {
        if(manufacturer == null || manufacturer.isEmpty()) {
            this.manufacturer = "Unknown";
        } else {
            this.manufacturer = manufacturer;
        }
    }
    
    public void setQuantity(int quantity) {
        if(quantity < 0) {
        	this.quantity = 0;
        }
        else {this.quantity = quantity;}
    }
    
    // helpers
    public void adjustQuantity(int amount) {
        this.quantity += amount;
        if (this.quantity < 0) this.quantity = 0;
    }

    public void setPrice(double price) {
        if(price < 0.0) {
            this.price = 0.0;
        } else {
            this.price = price;
        }
    }


    public String toString() {
    	return "Manufacturer: " + manufacturer + " " +"| Product: " + name + " | Qty: " + quantity + " | Price: $" + price;
    }
}