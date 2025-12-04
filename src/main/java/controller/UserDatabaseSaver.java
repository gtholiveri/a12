package controller;

import model.user.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserDatabaseSaver {
    public static Map<String, User> readInDatabaseFromFile(String filename) {

        Map<String, User> db = new HashMap<>();
        try (FileInputStream fs = new FileInputStream(filename);
             ObjectInputStream os = new ObjectInputStream(fs)){
            Object o = os.readObject();
            if(o instanceof Map) {
                db = (Map<String, User> )o; //cast
            }
        } catch (Exception e) {
            //can ignore exception if no file found
           // System.err.println("Error caught in readInDatabaseFromFile: " + e);
        }

        if(db == null) {
            System.out.println("No database file to be read, will need to create new empty database :-(");
            db = new HashMap<>(); //make an empty database instead of having a null
        }

        return db;
    }
    public static void writeObjectToFile(Map<String, User> thing, String fileName) {
        //for this to work, the thing  MUST (implement) Serializable, which means it can be written to a file
        // anything with a Scanner or BFF as instance variable can not be serializable.

        System.out.println("** demo serializable. which is basically writing data to machine-readable data");

        try (FileOutputStream fs = new FileOutputStream(fileName)){
            System.out.println("\t writing to file: " + fileName); //message
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(thing); //write the thing to a file in a machine-readable way
            os.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

