package controller;
public enum AllMenuOptions {
    CREATE_ACCOUNT("Create an account"),
    LOGIN("Login"),
    LOGOUT("Logout"),
    CHANGE_PASSWORD("Change password"),
    SHOW_USERNAMES("Display all usernames"),
    DISPLAY_USERS("Display all user details"),

    ADOPT_PET("Adopt a pet (requires login)"),
    DISPLAY_USER("Display current user and their pets (requires login)"),
    INTERACT_WITH_PET("Interact with an adopted pet (requires login)"),
    UPGRADE_ACCOUNT("Upgrade account type"),
    QUIT("Quit");

    private final String option;

    AllMenuOptions(String description) {
        this.option = description;
    }

    @Override
    public String toString() {
        return option;
    }
}
