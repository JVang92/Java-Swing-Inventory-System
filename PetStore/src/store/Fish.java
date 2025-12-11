package store;


public class Fish extends Animal {
    private String patternType;
    private FishType fishType; // from FishType enum
    public Fish() {
        super();
        this.patternType = "Solid";
        this.fishType = FishType.GOLDFISH;
    }

    public Fish(String name, int age, double price, char gender, String pattern, FishType type) {
        super(name, age, price, gender);
        setPatternType(pattern);
        setFishType(type);
    }

    public void setPatternType(String pattern) {
        this.patternType = (pattern == null || pattern.isEmpty()) ? "Solid" : firstCharUpper(pattern);
    }

    public void setFishType(FishType type) {
        this.fishType = (type == null) ? FishType.OTHER : type;
    }

    public String getPattern() {
    	return this.patternType;
    }
    
    public String getFishType() {
    	return this.fishType.toString();
    }
    public String toString() {
        return super.toString() + ", Pattern: " + patternType + ", Type: " + fishType;
    }
}