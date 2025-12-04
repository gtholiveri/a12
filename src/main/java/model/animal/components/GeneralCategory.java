package model.animal.components;

import java.io.Serializable;

/**
 * Enum representing a more general category for an animal (class/order)
 */
public enum GeneralCategory implements Serializable {
    AQUATIC,
    BIRD,
    IMAGINARY,
    INSECT,
    MAMMAL,
    REPTILE;

    /**
     * @return A big selection menu string for the user to look at, with all ofo the categories and a prompt
     */
    public static String getMenuString() {
        StringBuilder menu = new StringBuilder("Let's start by choosing a category of animal:\n");
        for (GeneralCategory op : GeneralCategory.values()) {
            menu.append(op.ordinal()).append(": ").append(op).append("\n");
        }
        menu.append("Which category number do you want?\n");
        return menu.toString();
    }

    /**
     * @param typeStr A String representing a particular category
     * @return The GeneralCategory corresponding to that String
     */
    public static GeneralCategory select(String typeStr) {
        return GeneralCategory.valueOf(typeStr.toUpperCase());
    }

    /**
     *
     * @param n The index of the category to select
     * @return The GeneralCategory corresponding to that index
     */
    public static GeneralCategory select(int n) {
        return GeneralCategory.values()[n];
    }
}