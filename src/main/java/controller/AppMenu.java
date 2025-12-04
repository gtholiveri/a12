package controller;

import model.animal.Animal;
import model.animal.AnimalBot;
import model.animal.Pet;
import model.animal.WalkablePet;
import model.user.*;
import view.UI;

import java.util.List;
import java.util.Map;

public class AppMenu extends Menu {
    private User loggedInUser;

    public AppMenu(MainSystem system, User loggedInUser) {
        super(system);
        this.loggedInUser = loggedInUser;

        setupOptions();
    }

    private void setupOptions() {
        addOption("Adopt a pet", this::adoptPet);
        addOption("Display current user and their pets", this::displayCurrentUser);
        addOption("Interact with an adopted pet", this::interactWithPet);
        addOption("Upgrade account type", this::upgradeAccount);
        addOption("Change password", this::changePassword);
        addOption("Logout", this::logout);
    }

    private void logout() {
        ui.println("Logging out " + loggedInUser.getUsername() + "...");
        system.saveDatabase();
        system.transitionToLoginMenu();
    }

    private void changePassword() {
        String oldPassword = ui.readln("Enter current password: ");
        String newPassword = ui.readln("Enter new password: ");

        if (loggedInUser.updatePassword(oldPassword, newPassword)) {
            ui.println("Password successfully updated!");
        } else {
            ui.println("Current password incorrect. Password not changed.");
        }
    }

    private void displayCurrentUser() {
        ui.println(loggedInUser.getAllInformation());
    }

    private void adoptPet() {
        // TODO: Implement pet adoption flow
        ui.println("Adopt pet feature - TODO");
    }

    private void interactWithPet() {
        // TODO: Implement pet interaction flow
        ui.println("Interact with pet feature - TODO");
    }

    private void upgradeAccount() {
        // TODO: Implement account upgrade flow
        ui.println("Upgrade account feature - TODO");
    }
}
