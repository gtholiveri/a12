package model.user;

import model.animal.Animal;

import java.util.HashMap;
import java.util.Map;

public class PremiumUser extends RegularUser implements Premium {
    public static final double PREMIUM_USER_FEE = 25.99;

    // Constructor 1: username and password
    public PremiumUser(String username, String password) {
        super(username, password, PREMIUM_USER_FEE);
    }

    // Constructor 2: upgrade from RegularUser (copy constructor)
    public PremiumUser(RegularUser other) {
        super(other.getUsername(), other.getPassword(), PREMIUM_USER_FEE, other.getAdoptedAnimals());
    }

    // Constructor 3: upgrade from FreeUser
    public PremiumUser(FreeUser other) {
        super(other.getUsername(), other.getPassword(), PREMIUM_USER_FEE);
        // Transfer the single pet if they have one
        if (other.getPet() != null) {
            getAdoptedAnimals().put(other.getPet().getUniqueName(), other.getPet());
        }
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
