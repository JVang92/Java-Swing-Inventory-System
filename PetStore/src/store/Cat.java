package store;

public class Cat extends Animal {
    private String breed;

    // default constructor
    public Cat() {
        super();
        this.breed = "Domestic Shorthair";
    }

    // paramatized constructor
    public Cat(String name, int age, double price, char gender, String breed) {
        super(name, age, price, gender);
        setBreed(breed);
    }

    public String getBreed() { return breed; }

    public void setBreed(String breed) {
        if (breed == null || breed.isEmpty()) {
            this.breed = "Domestic Shorthair";
        } else {
            this.breed = firstCharUpper(breed);
        }
    }

    
    public String toString() {
        return super.toString() + ", Breed: " + breed;
    }
}