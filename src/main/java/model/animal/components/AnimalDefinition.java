package model.animal.components;


import java.io.Serializable;

/**
 * An AnimalDefinition is a schema for a particular type of animal. It defines the unchanging / immutable qualities of
 * an animal (in contrast to things that are mutable / vary from specific to specific animal, like name, age, or happiness)
 *
 * @param category The broad category (ex: mammal, bird, fish, etc)
 * @param specificType The more specific specificType (ex: whale, gorilla, dog)
 * @param emoji The emoji for this AnimalType
 * @param sound String with the sound for this AnimalType
 * @param takesWalks true if the animal is walkable and false if it's not
 */
public record AnimalDefinition(
        GeneralCategory category,
        String specificType,
        String emoji,
        String sound,
        boolean isPet,
        boolean takesWalks
        ) implements Serializable {

    //<editor-fold> desc="Comparison"

    /**
     * Changed this method because frankly it was completely nonsensical. Previously, having the same value for isReal or takesWalks would've caused the method to return false.
     * @param o   the reference object with which to compare.
     * @return True if this and o are aliases, or if this and o have the same:<br>
     * - category<br>
     * - emoji<br>
     * - sound<br>
     * - reality<br>
     * - walkability
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnimalDefinition that = (AnimalDefinition) o;

        return this.category == that.category &&
                this.specificType.equals(that.specificType) &&
                this.emoji.equals(that.emoji) &&
                this.sound.equals(that.sound) &&
                this.takesWalks == that.takesWalks;
    }

    @Override
    public int hashCode() {
        int result = specificType.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + emoji.hashCode();
        result = 31 * result + sound.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return emoji + " " + specificType + " ( " + category + ") ";
    }
}