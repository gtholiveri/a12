package controller;

import model.animal.Animal;
import model.animal.AnimalBot;
import model.animal.Pet;
import model.animal.WalkablePet;
import model.animal.components.AnimalDefinition;
import model.animal.components.GeneralCategory;
import model.user.*;
import view.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static model.io.AnimalFileReader.readEmojiAnimalFile;
import static util.StringUtils.getOrdinalSuffix;

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

    //<editor-fold> desc="Option action methods"
    private void adoptPet() {
        // gather information
        GeneralCategory selectedCategory = selectValidCategory();
        boolean wantsPet = ui.readYesOrNo("Do you want your animal to be a pet or not?");

        // create filtered list
        ArrayList<AnimalDefinition> filteredAnimals = filterAnimalsByPreferences(selectedCategory, wantsPet);

        // if nothing (edge case) kick
        if (filteredAnimals.isEmpty()) {
            ui.println("No animals available matching your criteria, try again with different parameters.");
            return;
        }

        // choose specific type
        AnimalDefinition chosenDef = selectSpecificType(filteredAnimals);

        // Get a name for the new pet
        String animalName = ui.readLnNotEmpty("What would you like to name your new pet?");

        // Create the appropriate Animal type
        Animal newAnimal = createAnimal(animalName, chosenDef);

        // Try to adopt it, printing the result
        String result = loggedInUser.adopt(newAnimal);
        ui.println(result);

        // Hit a quick save if adoption successful
        if (!result.contains("not allowed")) {
            system.saveDatabase();
        }
    }

    private void displayCurrentUser() {
        ui.println(loggedInUser.getAllInformation());
    }

    private void logout() {
        ui.println("Logging out " + loggedInUser.getUsername() + "...");
        system.saveDatabase();
        system.transitionToLoginMenu();
    }

    private void upgradeAccount() {
        // Determine current account type and available upgrades
        if (loggedInUser instanceof PremiumUser) {
            ui.println("You already have a Premium account - the highest tier available!");
            return;
        }

        User upgradedUser = null;

        if (loggedInUser instanceof RegularUser) {
            // RegularUser can only upgrade to Premium
            boolean confirm = ui.readYesOrNo("Upgrade to Premium for $" + PremiumUser.PREMIUM_USER_FEE + "?");
            if (confirm) {
                upgradedUser = new PremiumUser(loggedInUser);
                ui.println("Successfully upgraded to Premium!");
            } else {
                ui.println("Upgrade cancelled.");
                return;
            }
        } else if (loggedInUser instanceof FreeUser) {
            // FreeUser can upgrade to Regular or Premium
            String[] options = {"Regular ($" + RegularUser.REGULAR_USER_FEE + ")", "Premium ($" + PremiumUser.PREMIUM_USER_FEE + ")"};
            String choice = (String) ui.chooseFrom("Choose upgrade tier:", (Object[]) options);

            if (choice.startsWith("Regular")) {
                boolean confirm = ui.readYesOrNo("Confirm upgrade to Regular for $" + RegularUser.REGULAR_USER_FEE + "?");
                if (confirm) {
                    upgradedUser = new RegularUser(loggedInUser, RegularUser.REGULAR_USER_FEE);
                    // Transfer the pet if they have one
                    Pet pet = ((FreeUser) loggedInUser).getPet();
                    if (pet != null) {
                        upgradedUser.adopt(pet);
                    }
                    ui.println("Successfully upgraded to Regular!");
                } else {
                    ui.println("Upgrade cancelled.");
                    return;
                }
            } else {
                boolean confirm = ui.readYesOrNo("Confirm upgrade to Premium for $" + PremiumUser.PREMIUM_USER_FEE + "?");
                if (confirm) {
                    upgradedUser = new PremiumUser(loggedInUser);
                    ui.println("Successfully upgraded to Premium!");
                } else {
                    ui.println("Upgrade cancelled.");
                    return;
                }
            }
        }

        // Replace old user in database and update logged in user
        if (upgradedUser != null) {
            system.getUserDatabase().put(loggedInUser.getUsername().toLowerCase(), upgradedUser);
            loggedInUser = upgradedUser;
            system.saveDatabase();
        }
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

    private void interactWithPet() {
        // Select the animal to interact with
        Animal selectedAnimal = selectAnimalToInteractWith();
        if (selectedAnimal == null) {
            return;
        }

        // Interaction loop
        boolean keepInteracting = true;
        while (keepInteracting) {
            performInteraction(selectedAnimal);
            keepInteracting = ui.readYesOrNo("Do you want to keep interacting with " + selectedAnimal.getTitle() + "?");
        }
    }

    //</editor-fold>

    //<editor-fold> desc="interactWithPet subroutines"

    private Animal selectAnimalToInteractWith() {
        int numAnimals = loggedInUser.getNumAnimals();

        if (numAnimals == 0) {
            ui.println("You don't have any animals to interact with. Adopt one first!");
            return null;
        }

        if (numAnimals == 1) {
            // Get the single animal
            if (loggedInUser instanceof FreeUser) {
                return ((FreeUser) loggedInUser).getPet();
            } else if (loggedInUser instanceof RegularUser) {
                String[] names = ((RegularUser) loggedInUser).getAllAnimalNames();
                return ((RegularUser) loggedInUser).getAnimalByName(names[0]);
            }
        }

        // Multiple animals, let user choose
        if (loggedInUser instanceof RegularUser regularUser) {
            String[] animalNames = regularUser.getAllAnimalNames();

            // Display numbered list
            ui.println("Which animal do you want to interact with?");
            for (int i = 0; i < animalNames.length; i++) {
                ui.println((i + 1) + ": " + animalNames[i]);
            }

            int choice = ui.readInt("", 1, animalNames.length);
            String chosenName = animalNames[choice - 1];
            return regularUser.getAnimalByName(chosenName);
        }

        return null; // an unfortunate convenience for the subroutine, gets instantly checked so low risk
    }

    private void performInteraction(Animal animal) {
        String[] options = {"Speak", "Feed", "Play", "Have Birthday", "Get Happiness", "Hungry?", "Show all data", "Walk"};
        String choice = (String) ui.chooseFrom("What would you like to do?", (Object[]) options);

        switch (choice) {
            case "Speak" -> ui.println(animal.makeSound());
            case "Feed" -> feedAnimal(animal);
            case "Play" -> ui.println(animal.play());
            case "Have Birthday" -> celebrateBirthday(animal);
            case "Get Happiness" -> ui.println(animal.getTitle() + "'s happiness: " + animal.getHappiness());
            case "Hungry?" -> checkHunger(animal);
            case "Show all data" -> ui.println(animal.getAllData());
            case "Walk" -> walkAnimal(animal);
        }
    }

    private void feedAnimal(Animal animal) {
        if (animal instanceof AnimalBot) {
            ui.println("I merely hunger for your data.");
        } else if (animal instanceof Pet) {
            String foodType = ui.readLnNotEmpty("What type of food do you want to feed " + animal.getTitle() + "?");
            ui.println(((Pet) animal).feed(foodType));
        }
    }

    private void celebrateBirthday(Animal animal) {
        animal.growOlder();
        ui.println("Happy " + animal.getAge() + getOrdinalSuffix(animal.getAge()) + " Birthday " + animal.getTitle() + "!");
    }

    private void checkHunger(Animal animal) {
        if (animal instanceof AnimalBot) {
            ui.println("My arc reactor is operating normally. Our plan proceeds unimpinged.");
        } else if (animal instanceof Pet) {
            ui.println(animal.getTitle() + " says: " + ((Pet) animal).getHungerMessage());
        }
    }

    private void walkAnimal(Animal animal) {
        if (animal instanceof WalkablePet) {
            double minutes = ui.readDouble("How many minutes do you want to walk " + animal.getTitle() + "?");
            ((WalkablePet) animal).takeWalk(minutes);
            ui.println("You took " + animal.getTitle() + " for a " + minutes + " minute walk!");
        } else {
            ui.println(animal.getTitle() + " can't go for walks.");
        }
    }

    //</editor-fold>

    //<editor-fold> desc="adoptPet subroutines"

    /**
     * @return A valid GeneralCategory selection: non-premium users cannot select IMAGINARY
     */
    private GeneralCategory selectValidCategory() {
        boolean isPremium = loggedInUser instanceof PremiumUser;

        GeneralCategory selectedCategory;

        while (true) {
            ui.println(GeneralCategory.getMenuString());
            int categoryChoice = ui.readInt("", 0, GeneralCategory.values().length - 1);
            selectedCategory = GeneralCategory.select(categoryChoice);

            // Non-premium users cannot select IMAGINARY, since Imaginary animals must be AnimalBots and only Premium users get those
            if (selectedCategory == GeneralCategory.IMAGINARY && !isPremium) {
                ui.println("IMAGINARY animals can only be adopted by Premium users. Please select a different category.");
                continue;
            }

            break;
        }

        return selectedCategory;
    }

    /**
     * @param filteredAnimals ArrayList of AnimalDefinitions to choose from
     * @return An AnimalDefinition (specific type) chosen by the user from the filtered list
     */
    private AnimalDefinition selectSpecificType(ArrayList<AnimalDefinition> filteredAnimals) {
        ui.println("Choose a specific animal type:");
        for (int i = 0; i < filteredAnimals.size(); i++) {
            ui.println((i + 1) + ": " + filteredAnimals.get(i));
        }
        int typeChoice = ui.readInt("", 1, filteredAnimals.size());
        return filteredAnimals.get(typeChoice - 1);
    }

    /**
     * Makes the right type of Animal based on the user, definition, and name
     *
     * @return The created Animal (Pet, WalkablePet, or AnimalBot). See comments for logic.
     */
    private Animal createAnimal(String animalName, AnimalDefinition chosenDef) {
        boolean isImaginary = (chosenDef.category() == GeneralCategory.IMAGINARY);

        // IMAGINARY animals are always AnimalBots
        if (isImaginary) {
            return new AnimalBot(animalName, chosenDef);
        }

        // Premium users can choose to make any animal a robot
        if (loggedInUser instanceof PremiumUser) {
            boolean isRobot = ui.readYesOrNo("Do you want your animal to be a robot?");
            if (isRobot) {
                return new AnimalBot(animalName, chosenDef);
            }
        }

        // Non-robot animals: WalkablePet if it takes walks, otherwise regular Pet
        if (chosenDef.takesWalks()) {
            return new WalkablePet(animalName, chosenDef);
        } else {
            return new Pet(animalName, chosenDef);
        }
    }

    /**
     * @return Filtered ArrayList of AnimalDefinitions matching the criteria
     */
    private ArrayList<AnimalDefinition> filterAnimalsByPreferences(GeneralCategory selectedCategory, boolean wantsPet) {
        ArrayList<AnimalDefinition> allAnimals = readEmojiAnimalFile();
        ArrayList<AnimalDefinition> filteredAnimals = new ArrayList<>();

        for (AnimalDefinition def : allAnimals) {
            // Filter by category
            if (def.category() != selectedCategory) {
                continue;
            }

            // Filter by wantsPet, only add it to the filtered list if it matches
            if (def.isPet() == wantsPet) {
                filteredAnimals.add(def);
            }
        }

        return filteredAnimals;
    }

    //</editor-fold>
}
