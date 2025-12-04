package model.user;

import model.animal.Animal;

import java.io.Serializable;
import java.util.Objects;

public abstract class User implements Serializable {
    private final String username;
    private String password;

    //<editor-fold> desc="Constructors"

    /**
     * Full constructor
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Copy constructor
     */
    public User(User o) {
        this.username = o.username;
        this.password = o.password;
    }

    //</editor-fold>

    //<editor-fold> desc="Password Methods"
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

    //</editor-fold>

    //<editor-fold> desc="Property Getters"

    public abstract int getNumAnimals();
    protected abstract boolean canAdopt(Animal animal);

    public abstract String getAdoptionRules();

    public abstract String getAllInformation();
    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + username;
    }

    public String getUsername() {
        return username;
    }

    //</editor-fold>

    //<editor-fold> desc="Actions"

    /**
     *
      * @param animal The {@code Animal} to be adopted
     * @return String containing the to-be-displayed results of the adoption attempt
     */
    public final String adopt(Animal animal) {
        String message;
        if (canAdopt(animal)) {
            message = completeAdoption(animal);
        } else {
            message = "Adoption is not allowed: " + getAdoptionRules();
        }
        return message;
    }

    /**
     * Subroutine to carry out the actual adoption action.
     */
    protected abstract String completeAdoption(Animal animal);


    //</editor-fold>

    //<editor-fold> desc="Comparison"

    /**
     * @param o   the reference object with which to compare.
     * @return True if this and o are aliases, or if this and o are the same class and have the same:<br>
     * - username (case-insensitive)<br>
     * - password (case-sensitive)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return username.equalsIgnoreCase(user.username) && 
                password.equals(user.password);
    }

    /**
     * @return Hash code based on:<br>
     * - username (case-insensitive)<br>
     * - password (case-sensitive)
     */
    @Override
    public int hashCode() {
        return Objects.hash(username.toLowerCase(), password);
    }

    //</editor-fold>
}
