package model.animal;

import model.animal.components.AnimalDefinition;

/**
 * TODO: Class description
 *
 * @author Kendra Walther
 * email: kwalther@usc.edu
 * ITP 265, Spring 2025, Coffee/Tea Section
 * Date created: 4/12/25
 */
public class WalkablePet extends Pet {

    public WalkablePet(AnimalDefinition def, String name, int happiness, int age, int hungerLevel) {
        super(name, def, happiness, age, hungerLevel);
    }

    public WalkablePet(Animal a, int hungerLevel) {
        super(a, hungerLevel);
    }

    public WalkablePet(Animal a) {
        super(a);
    }

    public WalkablePet(String name, AnimalDefinition def) {
        super(name, def);
    }

    public void takeWalk(double minutes) {
        increaseHappiness(1);
    }


}
