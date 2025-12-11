package store;

public class Shelter extends Accessory {
    private String type; // ex "Cage" or "Aquarium"
    private double width;
    private double height;

    public Shelter() {
        super();
        this.type = "Box";
        this.width = 0.0;
        this.height = 0.0;
    }

    public Shelter(String name, String manufacturer, double price, String type, double width, double height) {
        super(name, manufacturer, price);
        setType(type);
        setDimensions(width, height);
    }

    public void setType(String type) {
        this.type = (type == null || type.isEmpty()) ? "Box" : type;
    }

    public void setDimensions(double width, double height) {
        if(width < 0) {
        	this.width = 0.0;
        }
        else {this.width = width;}
        
        if(height < 0) {
        	this.height = 0.0;
        }
        else {this.height = height;}
    }
    
    public String displayDimensions(){
    	return this.width + "IN, " + this.height + "IN";
    	}
    

    public String toString() {
        return super.toString() + ", Type: " + type + " (" + width + "x" + height + ")";
    }
}