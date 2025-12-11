package store;

public class Bird extends Animal{
	private String color;
	private BirdSize birdSize;
	
	public Bird() {
		super();
		this.color = "White";
		this.birdSize = BirdSize.MEDIUM;
	}

	public Bird(String name, int age, double price, char gender, String color, BirdSize birdSize) {
		super(name, age, price, gender);
		setColor(color);
		setSize(birdSize);
		
	}
	
	public String getColor() {
		return this.color;
	}
	
	public BirdSize getBirdSize() {
		return this.birdSize;
	}
	
	public void setColor(String color) {
		if(color == null || color.isEmpty()){
			this.color = "White";
		}
		else {this.color = firstCharUpper(color); }
	}
	
	public void setSize(BirdSize size) {
		if(size == null) {
			this.birdSize = BirdSize.SMALL;
		}
		else {this.birdSize = size;}
	}
	
	public String toString() {
		return super.toString() + ", Color: " + this.color + ", Size: " + this.birdSize;
	}
}

