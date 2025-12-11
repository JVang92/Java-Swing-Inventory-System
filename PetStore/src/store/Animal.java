package store;

public class Animal {
	
	private String name;
	private int age;
	private double price;
	private char gender;
	
	public Animal() {
		this.name = "none";
		this.age = 0;
		this.price = 19.99;
		this.gender = 'M';
		
	}
	
	public Animal(String name, int age, double price, char gender) {
		this.setName(name);
		this.setAge(age);
		this.setPrice(price);
		this.setGender(gender);
		
	}
	
	// accessors
	public String getName() {return this.name;}
	
	public int getAge() {return this.age;}
	
	public double getPrice() {return this.price;}
	
	public char getGender() {return this.gender;}
	
	
	
	//mutators
	public void setName(String name) {
		if(name != null)
		{
			this.name = firstCharUpper(name);
		}	
		else {this.name = "none";}
	}
	
	public void setAge(int age) {
		if(age > 0) {
			this.age = age;
		}
		else {this.age = 0;}
	}
	
	public void setPrice(double price) {
		if(price >= 0.0) {
			this.price = price;
		}
		else {this.price = 0.0;}
	}
	
	public void setGender(char gender) {
		Character.toUpperCase(gender);
		if(gender == 'M' || gender == 'F') {
			this.gender = gender;
		}
		else {this.gender = 'M';}
	}
	
	
	// return string with its first letter capitalized
	public String firstCharUpper(String string) {
		if(string == null || string.isEmpty()) {
			return string;
		}
		
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}
	
	public String toString() {
		return "Name: " + this.name + ", Age: " + this.age + ", Price: $" + this.price + ", Gender: " + this.gender;
	}

}
