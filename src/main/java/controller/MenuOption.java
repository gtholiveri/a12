package controller;

public class MenuOption {
    private final String displayText;
    private final Runnable action;

    public MenuOption(String displayText, Runnable action) {
        this.displayText = displayText;
        this.action = action;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void execute() {
        action.run();
    }

    @Override
    public String toString() {
        return displayText;
    }
}
