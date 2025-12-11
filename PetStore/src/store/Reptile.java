package store;

public class Reptile extends Animal {
    private String species; // "Snake" or "Lizard"

    // default constructor
    public Reptile() {
        super();
        this.species = "Lizard";
    }

    // parameterized constructor
    public Reptile(String name, int age, double price, char gender, String species) {
        super(name, age, price, gender);
        setSpecies(species);
    }

    public void setSpecies(String species) {
        this.species = (species == null || species.isEmpty()) ? "Lizard" : firstCharUpper(species);
    }
    
    public String getSpecies() {
    	return this.species;
    }

    
    public String toString() {
        return super.toString() + ", Species: " + species;
    }
}