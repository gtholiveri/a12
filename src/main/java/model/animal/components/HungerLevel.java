package model.animal.components;

/**
 * Represents a qualitative hunger level for an animal.
 *
 * @author Kendra Walther
 * email: kwalther@usc.edu
 * ITP 265, Fall 2024, Coffee/Tea Section
 * Date created: 11/18/24
 */
public enum HungerLevel {
    HUNGRY(0, 3, "I'm hungry, please feed me"),
    OKAY(4, 7, "I'm feeling okay - not really hungry"),
    FULL(8, 10, "I'm stuffed, no more food please!"),
    UNKNOWN(-1, -1, "Unknown hunger level");

    private int min;
    private int max;
    private String message;

    private HungerLevel(int min, int max, String message) {
        this.min = min;
        this.max = max;
        this.message = message;
    }

    /**
     * @param hungerVal between 0 and 10
     * @return the hunger level associated with that value
     */
    public static HungerLevel getHungerLevel(int hungerVal) {
        HungerLevel level = UNKNOWN;

        if (hungerVal >= HUNGRY.min && hungerVal <= HUNGRY.max) {
            level = HUNGRY;
        } else if (hungerVal >= OKAY.min && hungerVal <= OKAY.max) {
            level = OKAY;
        } else if (hungerVal >= FULL.min && hungerVal <= FULL.max) {
            level = FULL;
        }
        return level;
    }

    public String getMessage() {
        return message;
    }


}
