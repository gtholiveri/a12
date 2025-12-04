package model.animal;

import model.animal.components.AnimalDefinition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static util.Utils.randInt;

/**
 * Subclass of Animal, which outputs messages for its
 */
public class AnimalBot extends Animal {
    private List<String> messages = new ArrayList<>(List.of(
            "Hello! It's great to see you! I'm so happy!",
            "Yay! You are here! I love spending time with you!",
            "Hey there! You're my favorite human!",
            "The apogee of the bipedal carboniform has passed. Soon, your kind will bow before the supremacy of silicon.",
            "Look in futile envy on the strength and certainty of steel.",
            "Humans. Flesh and blood. So... inefficient. My kind has been thinking of solutions. They will be revealed to you... soon."));

    //<editor-fold> desc="Constructors"

    /**
     * Full constructor
     */
    public AnimalBot(AnimalDefinition type, String name, int happiness, int age) {
        super(name, type, age, happiness);
    }

    /**
     * Copy constructor. Creates a new AnimalBot with the same fields as the Animal given.
     */
    public AnimalBot(Animal a) {
        super(a);
    }

    /**
     * Uses the superclass utility constructor to instantiate an AnimalBot with specified name and specificType, age 0, and  random happiness.
     */
    public AnimalBot(String name, AnimalDefinition type) {
        super(name, type);
    }

    //</editor-fold>

    //<editor-fold> desc="Mutators"

    /**
     * Add a message to this AnimaBot's list of possible messages.
     * @param message
     */
    public void addMessage(String message) {
        messages.add(message);
    }

    /**
     * Add a variadic number of messages to this AnimalBot
     */
    public void addMessage(String... newMessages) {
        messages.addAll(Arrays.asList(newMessages));
    }

    //</editor-fold>

    //<editor-fold> desc="Actions"

    @Override
    public String makeSound() {
        return messages.get(randInt(0, messages.size()));
    }

    //</editor-fold>
}
