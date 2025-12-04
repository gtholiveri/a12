package model.user;

import model.animal.Animal;

import java.io.Serializable;
import java.util.Objects;

public abstract class User implements Serializable {
    private final String username;
    private String password;

    // Full constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Copy constructor
    public User(User other) {
        this.username = other.username;
        this.password = other.password;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    // Password methods (no accessor/mutator)
    public boolean verifyPassword(String passwordAttempt) {
        return this.password.equals(passwordAttempt);
    }

    public boolean updatePassword(String password, String newPassword) {
        if (this.password.equals(password)) {
            this.password = newPassword;
            return true;
        }
        return false;
    }

    // Abstract methods for subclasses to implement
    public abstract int getNumAnimals();
    protected abstract String completeAdoption(Animal animal);
    public abstract String getAdoptionRules();
    protected abstract boolean canAdopt(Animal animal);
    public abstract String getAllInformation();

    // Template method pattern - final so it can't be overridden
    public final String adopt(Animal animal) {
        String message;
        if (canAdopt(animal)) {
            message = completeAdoption(animal);
        } else {
            message = "Adoption is not allowed: " + getAdoptionRules();
        }
        return message;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        // Username case-insensitive, password case-sensitive
        return username.equalsIgnoreCase(user.username) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        // Username case-insensitive, password case-sensitive
        return Objects.hash(username.toLowerCase(), password);
    }
}
