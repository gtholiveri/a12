package model.animal;

import model.animal.components.AnimalDefinition;

import java.io.Serializable;

import static util.Utils.randInt;

/**
 * Abstract base class representing an Animal. Mostly a POJO, besides some formatting methods and mutator logic.
 */
public abstract class Animal implements Serializable {
    private final String name;
    private final AnimalDefinition def;
    private int age;
    private int happiness;

    //<editor-fold> desc="Constructors"

    /**
     * Full constructor
     */
    public Animal(String name, AnimalDefinition def, int age, int happiness) {
        this.name = name;
        this.def = def;
        this.age = age;
        this.happiness = happiness;
    }

    /**
     * Copy constructor
     */
    public Animal(Animal a) {
        this(a.name, a.def, a.age, a.happiness);
    }

    /**
     * Utility constructor: age of 0 and random happiness.
     */
    public Animal(String name, AnimalDefinition def) {
        this(name, def, 0, randInt(1, 101)); // Random happiness on [1, 100]
    }
    //</editor-fold>

    //<editor-fold> desc="Comparison"

    /**
     * @param o The other Animal being compared
     * @return True if this and o are aliases or are the same class and have the same:<br>
     * - name (case-insensitive)<br>
     * - definition<br>
     * - age<br>
     * - happiness<br>
     * And false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Animal that = (Animal) o;
        return name.equalsIgnoreCase(that.name) &&
                def.equals(that.def) &&
                age == that.age &&
                happiness == that.happiness;
    }

    /**
     * @return The hash code for this Animal based on the:<br>
     * - name (case-insensitive)<br>
     * - specificType<br>
     * - age<br>
     * - happiness<br>
     */
    @Override
    public int hashCode() {
        int result = name.toLowerCase().hashCode();
        result = 31 * result + def.hashCode();
        result = 31 * result + age;
        result = 31 * result + happiness;
        return result;
    }

    //</editor-fold>

    //<editor-fold> desc="Actions"

    /**
     * @return The String with this animal's distinctive sound
     */
    public abstract String makeSound();

    /**
     * Increase happiness by random integer amount [1, 5]
     *
     * @return The display String for the play
     */
    public String play() {
        increaseHappiness(randInt(1, 6)); // increase happiness by random int on [1, 5]
        return this.name + " is so happy you decided to play with them!";
    }

    //</editor-fold>

    //<editor-fold> desc="String Formatting"

    /**
     * @return Full name for display
     */
    public String getTitle() {
        return name + " the " + def.specificType() + " " + def.emoji();

    }

    /**
     * @return The full display name: The animal class, and the full name (name + the + emoji)<br>
     * ex: "Pet Harambe the 🦍"
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + getTitle();
    }

    /**
     * @return A debug string with all the information in this Animal
     */
    public String getAllData() {
        return toString() + " age: " + this.age + ", happiness: " + this.happiness + ", category: " + this.getDef().category();
    }

    //</editor-fold>

    //<editor-fold> desc="Getters"


    public String getName() {
        return name;
    }

    public AnimalDefinition getDef() {
        return def;
    }

    public int getAge() {
        return age;
    }

    public int getHappiness() {
        return happiness;
    }

    //</editor-fold>

    //<editor-fold> desc="Mutators"

    /**
     * Sets age to new value, floored at 0
     */
    public void setAge(int age) {
        this.age = Math.max(age, 0);
    }

    /**
     * Increment age by 1
     */
    public void growOlder() {
        age++;
    }

    /**
     * Increase happiness by {@code amount}, capped at 100
     */
    public void increaseHappiness(int amount) {
        happiness = Math.min(100, happiness + amount); // Ensure happiness does not exceed 100
    }

    /**
     * Decrease happiness by {@code amount}, floored at 0
     */
    public void decreaseHappiness(int amount) {
        happiness = Math.max(0, happiness - amount); // Ensure happiness does not go below 0
    }


    //</editor-fold>
}
