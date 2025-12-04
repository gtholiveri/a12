package controller;

import model.user.*;
import view.UI;

import java.util.Map;

import static controller.UserDatabaseSaver.readInDatabase;
import static controller.UserDatabaseSaver.readInDatabaseFromFile;

public class LoginMenu extends Menu {

    private Map<String, User> userDatabase;

    public LoginMenu(MainSystem system) {
        super(system);
        this.userDatabase = system.getUserDatabase();
        setupOptions();
    }

    private void setupOptions() {
        addOption("Create an account", this::createAccount);
        addOption("Login", this::login);
        addOption("Display all usernames", this::displayUsernames);
        addOption("Display all user details", this::displayUsers);
        addOption("Quit", this::quit);
    }

    //<editor-fold> desc="Menu Actions"
    private void quit() {
        system.quit();
    }

    private void createAccount() {
        String username = ui.readLnNotEmpty("Enter username: ");

        if (userDatabase.containsKey(username.toLowerCase())) {
            ui.println("Username already exists. Please login or choose a different username.");
            return;
        }

        String password = ui.readln("Enter password: ");
        String confirmPassword = ui.readln("Confirm password: ");

        if (!password.equals(confirmPassword)) {
            ui.println("Passwords do not match. Account not created.");
            return;
        }

        String[] accountTypes = {"Free", "Regular", "Premium"};
        String accountType = (String) ui.chooseFrom("Choose account type: ", (Object[]) accountTypes);

        User newUser = null;

        switch (accountType) {
            case "Free":
                newUser = new FreeUser(username, password);
                break;
            case "Regular":
                boolean confirmRegular = ui.readYesOrNo("Regular account costs $" + RegularUser.REGULAR_USER_FEE + ". Confirm payment?");
                if (confirmRegular) {
                    newUser = new RegularUser(username, password);
                } else {
                    ui.println("Account not created.");
                    return;
                }
                break;
            case "Premium":
                boolean confirmPremium = ui.readYesOrNo("Premium account costs $" + PremiumUser.PREMIUM_USER_FEE + ". Confirm payment?");
                if (confirmPremium) {
                    newUser = new PremiumUser(username, password);
                } else {
                    ui.println("Account not created.");
                    return;
                }
                break;
        }

        userDatabase.put(username.toLowerCase(), newUser);
        ui.println("Account successfully created and logged in as " + username + "!");
        system.saveDatabase();  // Save because we added a new user to the database
        system.transitionToAppMenu(newUser);
    }

    private void login() {
        String username = ui.readln("Enter username: ");
        User user = userDatabase.get(username.toLowerCase());

        if (user == null) {
            ui.println("User not found in system. Try logging in with a different username, or create an account.");
            return;
        }

        String password = ui.readln("Enter password: ");

        if (user.verifyPassword(password)) {
            ui.println("Successfully logged in as " + username + ".");
            system.transitionToAppMenu(user);
        } else {
            ui.println("Password incorrect. Try another password, or create an account.");
        }
    }

    private void displayUsernames() {
        StringBuilder sb = new StringBuilder("ALL USERS:\n");

        if (userDatabase.isEmpty()) {
            sb.append("No users currently registered");
        } else {
            int counter = 1;
            for (String username : userDatabase.keySet()) {
                sb.append(counter).append(": ").append(username).append("\n");
                counter++;
            }
        }

        ui.println(sb.toString());
    }

    private void displayUsers() {
        StringBuilder sb = new StringBuilder("ALL USER DETAILS:\n");

        if (userDatabase.isEmpty()) {
            sb.append("No users currently registered");
        } else {
            for (User user : userDatabase.values()) {
                sb.append(user.getAllInformation()).append("\n");
            }
        }

        ui.println(sb.toString());
    }

    //</editor-fold>
}
