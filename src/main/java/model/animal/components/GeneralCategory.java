package model.animal.components;

import java.io.Serializable;

public enum GeneralCategory implements Serializable {
    AQUATIC,
    BIRD,
    IMAGINARY,
    INSECT,
    MAMMAL,
    REPTILE;

    public static String getMenuString() {
        StringBuilder menu = new StringBuilder("Let's start by choosing a category of animal:\n");
        for (GeneralCategory op : GeneralCategory.values()) {
            menu.append(op.ordinal()).append(": ").append(op).append("\n");
        }
        menu.append("Which category number do you want?\n");
        return menu.toString();

    }

    public static GeneralCategory select(String typeStr) {
        return GeneralCategory.valueOf(typeStr.toUpperCase());
    }

    public static GeneralCategory getCategoryNumber(int n) {
        return GeneralCategory.values()[n];
    }
}