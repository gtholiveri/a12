package model.animal;

import model.animal.components.AnimalDefinition;
import model.animal.components.HungerLevel;

import static java.lang.Math.clamp;
import static util.Utils.randInt;

public class Pet extends Animal {
    private int hungerVal; // must be 0-10

    private String sound;

    /**
     * Full constructor
     */
    public Pet(String name, AnimalDefinition type, int happiness, int age, int hungerVal) {
        super(name, type, happiness, age);
        setPetVariables(hungerVal);
    }

    /**
     * Copy constructor that takes in a hungerLevel
     */
    public Pet(Animal a, int hungerVal) {
        super(a);
        setPetVariables(hungerVal);
    }

    /**
     * Copy constructor that initializes the hunger to a random value on [0, 10]
     */
    public Pet(Animal a) {
        super(a);
        setPetVariables();
    }

    /**
     * Creates an Animal using a definition and name, and gives it a random starting hunger on [0, 10]
     */
    public Pet(String name, AnimalDefinition def) {
        super(name, def);
        setPetVariables();
    }


    //<editor-fold> desc="Getters"

    public int getHungerVal() {
        return hungerVal;
    }

    //</editor-fold>

    //<editor-fold> desc="Mutators"

    /**
     * Overloaded subroutine that initializes the hunger level to a random value on [0, 10]
     */
    private void setPetVariables() {
        sound = getDef().sound();
        setPetVariables(randInt(0, 11));
    }

    /**
     * Subroutine that initializes the hunger level
     */
    private void setPetVariables(int hungerLevel) {
        setHungerVal(hungerLevel);
    }

    /**
     * Sets the hunger level, clamped to [0, 10]
     */
    public void setHungerVal(int hungerVal) {
        this.hungerVal = Math.clamp(hungerVal, 0, 10);
    }

    //</editor-fold>

    //<editor-fold> desc="Actions"

    // happiness goes down if pet is fed and not hungry...

    /**
     * Feeds the pet and adjusts its happiness level based on hunger level after feeding.
     *
     * @param foodType Cosmetic string with the type of food being given
     * @return A message with the pet's response
     */
    public String feed(String foodType) {
        String message = this.getFullName() + " \" says:\" ";

        HungerLevel lvl = HungerLevel.getHungerLevel(this.hungerVal);
        if (lvl == HungerLevel.HUNGRY) {
            increaseHappiness((int) (Math.random() * 5) + 1);
            message += " I was hungry, getting " + foodType + " made me happy. Happiness level is now: " + getHappiness();
        } else if (lvl == HungerLevel.OKAY) {
            message += " I wasn't actually hungry, but thanks for the " + foodType;
        } else if (lvl == HungerLevel.FULL) {// full
            decreaseHappiness((int) (Math.random() * 5) + 1);
            message += " I was already full, giving me " + foodType + " made me feel sick. Happiness level is now: " + getHappiness();
        } else {
            message = this.getFullName() + " rejected the food because they had an UNKNOWN hunger level";
        }
        return message;
    }


    @Override
    public String makeSound() {
        return getFullName() + " says: " + sound;
    }

    //</editor-fold>

    //<editor-fold> desc="Comparison"

    /**
     * @param o The other Animal being compared
     * @return True if this and o are aliases, or if this and o are the same class and have the same:<br>
     * - superclass attributes (name, definition, age, happiness)<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- note that definition comparison accounts for  category, emoji, sound, reality, and walkability<br>
     * - hunger value<br>
     * And false otherwise
     *
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Pet pet = (Pet) o;
        return hungerVal == pet.hungerVal;
            // && sound.equals(pet.sound); // this is unnecessary because our sound instance is just an alias of
            // the redundant sound instance in AnimalDefinition, which is already checked by the call to super()
    }

    /**
     * @return Hash code based on superclass hash, hunger value, and sound
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + hungerVal;
        result = 31 * result + sound.hashCode();
        return result;
    }

    //</editor-fold>

    //<editor-fold> desc="String Output"

    /**
     * @return String with the hunger message<br>
     * ex: "I'm feeling okay - not really hungry for hunger value of 5"
     */
    public String getHungerMessage() {
        HungerLevel lvl = HungerLevel.getHungerLevel(hungerVal);
        return lvl.getMessage() + " for hunger value of " + hungerVal;
    }

    /**
     * Overriden to include Pet-specific data (hunger level)
     *
     * @return Debug String with all data in this object
     */
    @Override
    public String getAllData() {
        HungerLevel lvl = HungerLevel.getHungerLevel(hungerVal);
        return super.getAllData() + ", hunger: " + hungerVal + "(Level: " + lvl + ")";
    }

    //</editor-fold>

}
