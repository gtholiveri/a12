package controller;

import view.UI;

import java.util.ArrayList;
import java.util.List;

public abstract class Menu {
    protected MainSystem system;
    protected UI ui;
    protected List<MenuOption> options;

    public Menu(MainSystem system) {
        this.system = system;
        this.ui = system.ui;
        this.options = new ArrayList<>();
    }

    protected void addOption(String displayText, Runnable action) {
        options.add(new MenuOption(displayText, action));
    }

    public void displayAndExecute() {
        // Display numbered menu
        for (int i = 0; i < options.size(); i++) {
            ui.println((i + 1) + ": " + options.get(i).getDisplayText());
        }

        // Get validated selection
        int choice = ui.readInt("", 1, options.size());

        // Execute
        options.get(choice - 1).execute();
    }
}
