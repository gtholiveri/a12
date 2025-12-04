package model.user;

import model.animal.Animal;

import java.util.*;

public class RegularUser extends User {
    public static final int MAX_ANIMALS = 5;
    public static final double REGULAR_USER_FEE = 10.99;

    private Map<String, Animal> adoptedAnimals;
    private double subscriptionFee;

    // Constructor 1: username and password
    public RegularUser(String username, String password) {
        super(username, password);
        this.adoptedAnimals = new HashMap<>();
        this.subscriptionFee = REGULAR_USER_FEE;
    }

    // Constructor 2: username, password, and fee
    public RegularUser(String username, String password, double subscriptionFee) {
        super(username, password);
        this.adoptedAnimals = new HashMap<>();
        this.subscriptionFee = subscriptionFee;
    }

    // Constructor 3: username, password, and map
    public RegularUser(String username, String password, Map<String, Animal> adoptedAnimals) {
        super(username, password);
        this.adoptedAnimals = new HashMap<>(adoptedAnimals);
        this.subscriptionFee = REGULAR_USER_FEE;
    }

    // Constructor 4: username, password, fee, and map
    public RegularUser(String username, String password, double subscriptionFee, Map<String, Animal> adoptedAnimals) {
        super(username, password);
        this.adoptedAnimals = new HashMap<>(adoptedAnimals);
        this.subscriptionFee = subscriptionFee;
    }

    // Constructor 5: copy constructor
    public RegularUser(RegularUser other) {
        super(other);
        this.adoptedAnimals = new HashMap<>(other.adoptedAnimals);
        this.subscriptionFee = other.subscriptionFee;
    }

    // Constructor 6: upgrade from FreeUser
    public RegularUser(FreeUser other) {
        super(other);
        this.adoptedAnimals = new HashMap<>();
        this.subscriptionFee = REGULAR_USER_FEE;
        // Transfer the single pet if they have one
        if (other.getPet() != null) {
            this.adoptedAnimals.put(other.getPet().getUniqueName(), other.getPet());
        }
    }

    // Getters
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

    @Override
    protected String completeAdoption(Animal animal) {
        if (canAdopt(animal)) {
            adoptedAnimals.put(animal.getUniqueName(), animal);
            return "You have successfully adopted animal: " + animal.getFullName();
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
}
