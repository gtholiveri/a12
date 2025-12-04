package model.user;

import model.animal.Animal;
import model.animal.Pet;

import java.util.Map;

public class PremiumUser extends RegularUser implements Premium {
    public static final double PREMIUM_USER_FEE = 25.99;

    // Constructor 1: upgrade from any User
    public PremiumUser(User user) {
        super(user, PREMIUM_USER_FEE);
        // If upgrading from RegularUser, transfer animals
        if (user instanceof RegularUser) {
            this.getAdoptedAnimals().putAll(((RegularUser) user).getAdoptedAnimals());
        }
        // If upgrading from FreeUser, transfer the single pet
        else if (user instanceof FreeUser) {
            Pet pet = ((FreeUser) user).getPet();
            if (pet != null) {
                this.getAdoptedAnimals().put(pet.getTitle(), pet);
            }
        }
    }

    // Constructor 2: username and password
    public PremiumUser(String username, String password) {
        super(username, password);
        super.getAdoptedAnimals().clear(); // Ensure empty map
    }

    // Constructor 3: username, password, and map
    public PremiumUser(String username, String password, Map<String, Animal> adoptedAnimals) {
        super(username, password, adoptedAnimals, PREMIUM_USER_FEE);
    }

    @Override
    public boolean isPremium() {
        return true;
    }

    @Override
    public double getPremiumFee() {
        return PREMIUM_USER_FEE;
    }

    @Override
    public String getAdoptionRules() {
        return "A PremiumUser can adopt as many animals as they like.";
    }

    @Override
    protected boolean canAdopt(Animal animal) {
        return true; // Unlimited adoptions
    }
}
