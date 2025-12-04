package model.io;

import model.animal.components.GeneralCategory;
import model.animal.components.AnimalDefinition;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Reads data from the emojiAnimals.csv file
 *
 * @author Kendra Walther
 * @Email: kwalther@usc.edu
 * @Class: ITP 265,
 * @Assignment: 12
 */
public class AnimalFileReader {

    private static final String FILE_NAME_SIMPLE = "src/main/resources/emojiAnimals.csv";

    /**
     * @return An ArrayList containing all parsed AnimalDefinitions found in the source file
     */
    public static ArrayList<AnimalDefinition> readEmojiAnimalFile() {
        ArrayList<AnimalDefinition> data = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(FILE_NAME_SIMPLE);
             Scanner scan = new Scanner(fis)) {
            scan.nextLine(); // skip the header
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (!line.isEmpty()) { //skip empty lines
                    AnimalDefinition a = parseLine(line);
                    data.add(a);
                }
            }
        } catch (IOException e) {
            System.err.println("Emoji Animals File not found, fix the file location and re-run the program");
            System.exit(1);
        }
        return data;
    }

    /**
     * Walks through the CSV-like data structure for a single line in the emojiAnimals.csv file.<br>
     *
     * @param line The line of the file being examined
     * @return The specific AnimalType object contained in that line
     */
    private static AnimalDefinition parseLine(String line) {
        Scanner ls = new Scanner(line);
        // we assume for our simplified CSV format that every comma is a column delimiter
        ls.useDelimiter(",");


        //Column structure:
        // TypeName,Category,Emoji,canBePet,(for Pet)Sound,walkable
        String typeName = ls.next();
        String categoryStr = ls.next();
        String emoji = ls.next();
        String pet = ls.next(); // yes or no
        boolean isPet = false;
        String sound = "";
        boolean walkable = false;
        if (pet.equalsIgnoreCase("yes")) {
            // if it's a pet, set isPet to true and get the sound
            isPet = true;
            sound = ls.next();
            if (ls.hasNext()) {
                // if there's something after the isPet, it's walkable
                walkable = true;
            }
        }
        GeneralCategory category = GeneralCategory.select(categoryStr);

        return new AnimalDefinition(category, typeName, emoji, sound, isPet, walkable);
    }
}