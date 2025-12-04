package controller;

import model.user.User;
import view.TerminalUI;
import view.UI;

import java.util.Map;

import static controller.UserDatabaseSaver.readInDatabaseFromFile;
import static controller.UserDatabaseSaver.writeObjectToFile;

public class MainSystem {
    private static final String DB_FILE = "src/main/resources/userDatabase.ser";

    private Map<String, User> userDatabase;
    public UI ui;
    private Menu currentMenu;
    private boolean shouldQuit = false;

    public MainSystem() {
        ui = new TerminalUI();
        userDatabase = readInDatabaseFromFile(DB_FILE);
        currentMenu = new LoginMenu(this);
    }

    public static void main(String[] args) {
        MainSystem program = new MainSystem();
        program.run();
    }

    /**
     * Saves the current user database to the serialized file
     */
    public void saveDatabase() {
        writeObjectToFile(userDatabase, DB_FILE);
    }

    /**
     * Signals to the program to fully exit
     */
    public void quit() {
        shouldQuit = true;
    }

    /**
     * Transitions to an AppMenu for the given user (ie, successfully logs in0
     */
    public void transitionToAppMenu(User loggedInUser) {
        currentMenu = new AppMenu(this, loggedInUser);
    }

    /**
     * Transitions to a LoginMenu (logs out)
     */
    public void transitionToLoginMenu() {
        currentMenu = new LoginMenu(this);
    }

    /**
     * Main run method
     */
    public void run() {
        while (!shouldQuit) {
            currentMenu.displayAndExecute();
        }
    }

    public Map<String, User> getUserDatabase() {
        return userDatabase;
    }
}
