package model.user;

import model.animal.Animal;
import model.animal.Pet;

import java.util.*;

public class RegularUser extends User {
    public static final int MAX_ANIMALS = 5;
    public static final double REGULAR_USER_FEE = 10.99;

    private Map<String, Animal> adoptedAnimals;
    private double subscriptionFee;

    /**
     * Specified fee, empty starting animal map
     * @param user Copies their username and password
     */
    public RegularUser(User user, double subscriptionFee) {
        super(user);
        this.adoptedAnimals = new HashMap<>();
        this.subscriptionFee = subscriptionFee;
    }

    /**
     * Most default constructor: default fee, empty starting animal map
     */
    public RegularUser(String username, String password) {
        super(username, password);
        this.adoptedAnimals = new HashMap<>();
        this.subscriptionFee = REGULAR_USER_FEE;
    }

    /**
     * Specified starting animal map and fee
     * @param user Copies their username and password
     */
    public RegularUser(User user, Map<String, Animal> adoptedAnimals, double subscriptionFee) {
        super(user);
        this.adoptedAnimals = new HashMap<>(adoptedAnimals);
        this.subscriptionFee = subscriptionFee;
    }

    /**
     * Everything specified except fee
     */
    public RegularUser(String username, String password, Map<String, Animal> adoptedAnimals) {
        super(username, password);
        this.adoptedAnimals = new HashMap<>(adoptedAnimals);
        this.subscriptionFee = REGULAR_USER_FEE;
    }

    /**
     * Full constructor, all fields specified
     */
    public RegularUser(String username, String password, Map<String, Animal> adoptedAnimals, double subscriptionFee) {
        super(username, password);
        this.adoptedAnimals = new HashMap<>(adoptedAnimals);
        this.subscriptionFee = subscriptionFee;
    }

    // Constructor 6: Copy from User (upgrade/copy constructor)
    public RegularUser(User other) {
        super(other);
        this.adoptedAnimals = new HashMap<>();
        this.subscriptionFee = REGULAR_USER_FEE;

        // Transfer animals based on the type of user
        if (other instanceof RegularUser o) {
            // Copy from RegularUser or PremiumUser (both have adoptedAnimals)
            Map<String, Animal> incomingAnimals = o.getAdoptedAnimals();
            this.adoptedAnimals.putAll(incomingAnimals);
        } else if (other instanceof FreeUser) {
            // Transfer single pet from FreeUser
            Pet pet = ((FreeUser) other).getPet();
            if (pet != null) {
                this.adoptedAnimals.put(pet.getTitle(), pet);
            }
        }
    }

    //<editor-fold> desc="Getters"
    public Map<String, Animal> getAdoptedAnimals() {
        return adoptedAnimals;
    }

    public double getSubscriptionFee() {
        return subscriptionFee;
    }

    public Animal getAnimalByName(String name) {
        return adoptedAnimals.get(name);
    }

    public String[] getAllAnimalNames() {
        return adoptedAnimals.keySet().toArray(new String[0]);
    }

    @Override
    public int getNumAnimals() {
        return adoptedAnimals.size();
    }

    @Override
    public String getAdoptionRules() {
        return "A RegularUser can adopt up to 5 animals. Upgrade to adopt more.";
    }


    @Override
    protected boolean canAdopt(Animal animal) {
        return adoptedAnimals.size() < MAX_ANIMALS;
    }
    //</editor-fold>


    @Override
    protected String completeAdoption(Animal animal) {
        if (canAdopt(animal)) {
            adoptedAnimals.put(animal.getTitle(), animal);
            return "You have successfully adopted animal: " + animal.getTitle();
        }
        return getAdoptionRules();
    }

    @Override
    public String getAllInformation() {
        StringBuilder sb = new StringBuilder();
        sb.append(toString()).append("\n");
        if (adoptedAnimals.isEmpty()) {
            sb.append("\tYou have not yet adopted any pets");
        } else {
            for (Animal animal : adoptedAnimals.values()) {
                sb.append("\t").append(animal.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    //<editor-fold> desc="Comparison"

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegularUser that)) return false;
        if (!super.equals(o)) return false;
        return Double.compare(subscriptionFee, that.subscriptionFee) == 0 
                && Objects.equals(adoptedAnimals, that.adoptedAnimals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), adoptedAnimals, subscriptionFee);
    }

    //</editor-fold>
}
