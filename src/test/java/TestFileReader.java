import model.animal.components.AnimalDefinition;
import view.TerminalUI;

import java.util.ArrayList;

import static model.io.AnimalFileReader.readEmojiAnimalFile;

/**
 * Quick test to see if the file reader works
 */
public class TestFileReader {

    public static void main(String[] args) {
        TerminalUI ui = new TerminalUI();

        ArrayList<AnimalDefinition> types = readEmojiAnimalFile();
        for (AnimalDefinition a : types) {
            ui.println(a);
        }
    }

}
