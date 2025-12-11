package store;

public class Dog extends Animal {
    private String breed;
    private boolean isVaccinated;

    public Dog() {
        super();
        this.breed = "Mixed";
        this.isVaccinated = false;
    }

    public Dog(String name, int age, double price, char gender, String breed, boolean isVaccinated) {
        super(name, age, price, gender);
        setBreed(breed);
        setVaccinated(isVaccinated);
    }

    public String getBreed() { return breed; }
    public boolean getIsVaccinated() { return isVaccinated; }

    public void setBreed(String breed) {
        if (breed == null || breed.isEmpty()) {
            this.breed = "Mixed";
        } else {
            this.breed = firstCharUpper(breed); // using helper from Animal
        }
    }

    public void setVaccinated(boolean vaccinated) {
        this.isVaccinated = vaccinated;
    }

    
    public String toString() {
        return super.toString() + ", Breed: " + breed + ", Vaccinated: " + isVaccinated;
    }
}