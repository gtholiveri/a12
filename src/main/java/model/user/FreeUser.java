package model.user;

import model.animal.Animal;
import model.animal.Pet;

import java.util.Objects;

public class FreeUser extends User {
    private Pet myPet;

    // Constructor 1: username and password
    public FreeUser(String username, String password) {
        super(username, password);
        this.myPet = null;
    }

    // Constructor 2: username, password, and pet
    public FreeUser(String username, String password, Pet myPet) {
        super(username, password);
        this.myPet = myPet;
    }

    // Constructor 3: copy constructor
    public FreeUser(FreeUser other) {
        super(other);
        this.myPet = other.myPet;
    }

    // Constructor 4: copy from User
    public FreeUser(User other) {
        super(other);
        this.myPet = null;
    }

    // Getter
    public Pet getPet() {
        return myPet;
    }

    @Override
    public int getNumAnimals() {
        return myPet == null ? 0 : 1;
    }

    @Override
    public String getAdoptionRules() {
        return "A FreeUser can only adopt one animal of type Pet (not Animal Bots). Upgrade to adopt more.";
    }

    @Override
    protected boolean canAdopt(Animal animal) {
        return myPet == null && animal instanceof Pet;
    }

    @Override
    protected String completeAdoption(Animal animal) {
        if (canAdopt(animal)) {
            this.myPet = (Pet) animal;
            return "You have successfully adopted animal: " + animal.getFullName();
        }
        return getAdoptionRules();
    }

    @Override
    public String getAllInformation() {
        StringBuilder sb = new StringBuilder();
        sb.append(toString()).append("\n");
        if (myPet == null) {
            sb.append("\tNo pet has been adopted");
        } else {
            sb.append("\t").append(myPet.toString());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FreeUser freeUser)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(myPet, freeUser.myPet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), myPet);
    }
}
