package org.lucas.controllers;

import com.google.gson.reflect.TypeToken;
import org.lucas.Globals;
import org.lucas.models.Medicine;
import org.lucas.util.JarLocation;
import org.lucas.util.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple controller class that manages the storage of pre-populated medicine variables.
 * Common medicines such as acetominophen may already have fixed dosages and come in packages.
 * Hence, help to fill in those inside a JSON file, then load them at the beginning of the application.
 */
public class MedicinesController {
    private static final String fileName = "medicines.txt";
    private static List<Medicine> availableMedicines = new ArrayList<>(); // In-memory list


    /**
     * returns a *copy* of the available medicine array.
     * This ensures that the original array is never modified.
     * @return
     */
    public static List<Medicine> getAvailableMedicines() {
        if(availableMedicines.isEmpty()) {
            loadMedicinesFromFile(); //for when add new medicines
        }
        return new ArrayList<>(availableMedicines); // Return a *copy*
    }

    /**
     * Prints a list of all available medicines to the console, including their names and prices.
     * This method is primarily for diagnostic and user interface purposes.
     */
    public static void printAvailableMedicines() {
        System.out.println("Available Medicines:");
        for (Medicine medicine : availableMedicines) {
            System.out.println("- " + medicine.getMedName() + " ($" + String.format("%.2f", medicine.getMedPrice()) + ")");
        }
    }

    /**
     * Finds a medicine by its name.  This method searches the list of available
     * medicines and returns the first medicine that matches the given name (case-insensitive).
     *
     * @param name The name of the medicine to search for.
     * @return The {@code Medicine} object if found, or {@code null} if no medicine
     * with the given name is found.
     */
    public static Medicine findAvailableMedicineByName(String name){
        if(availableMedicines.isEmpty()){
            loadMedicinesFromFile();
        }

        for(Medicine medicine: availableMedicines){
            if(medicine != null && medicine.getMedName() != null && medicine.getMedName().equalsIgnoreCase(name)){
                return medicine;
            }
        }
        return null; // Return null if not found.
    }

    /**
     * Loads the list of available medicines from the (medicines.json) file.
     * This method uses Gson to deserialize the JSON data into a list of (Medicine) objects.
     * If an IOException occurs during file reading, the stack trace is printed to the error stream.
     */
    private static void loadMedicinesFromFile(){
        availableMedicines.clear();
        StringBuilder sb = new StringBuilder();
        String basePath = "";

        // get the jar location
        try {
            basePath = JarLocation.getJarDirectory();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println("Loading Medicines from " + Paths.get(basePath, fileName));
        try (BufferedReader br = Files.newBufferedReader(Paths.get(basePath, fileName))) {
            String line;
            while((line = br.readLine()) != null){
                sb.append(line);
            }
            Type listType = new TypeToken<List<Medicine>>() {}.getType();
            availableMedicines = Util.fromJsonString(sb.toString(), listType);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * Saves the current list of available medicines to the medicines.json file.
     * This method uses Gson to serialize the list of Medicine objects into JSON format.
     * If an IOException occurs during file writing, the stack trace is printed to the error stream.
     */
    public static void saveMedicinesToFile() {
        String basePath = "";
        try {
            basePath = JarLocation.getJarDirectory();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        String path = Paths.get(basePath, fileName).toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            String json = Globals.gsonPrettyPrint.toJson(availableMedicines);
            writer.write(json);
            System.out.println("Medicines saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Populates the availableMedicines list with some sample OTC medicines.
     */
    public static void populateExampleMedicines() {
        // Only add examples if the file was empty or didn't exist
        if (availableMedicines.isEmpty()) {
            Medicine ibuprofen = new Medicine("Ibuprofen", 50, "200mg, 1-2 tablets every 4-6 hours", 8.99);
            Medicine acetaminophen = new Medicine("Acetaminophen", 100, "500mg, 1-2 tablets every 6 hours", 7.49);
            Medicine loratadine = new Medicine("Loratadine", 30, "10mg, 1 tablet daily", 12.99);
            Medicine diphenhydramine = new Medicine("Diphenhydramine", 24, "25mg, 1-2 tablets every 4-6 hours", 6.29);
            Medicine ranitidine = new Medicine("Ranitidine", 60, "150mg, 1 tablet twice daily", 9.99);
            Medicine loperamide = new Medicine("Loperamide", 12, "2mg, 2 caplets after first loose stool, then 1 caplet after each", 5.49);
            Medicine pseudoephedrine = new Medicine("Pseudoephedrine", 20, "30mg, 1-2 tablets every 4-6 hours", 7.00);
            // added more
            Medicine guaifenesin = new Medicine("Guaifenesin", 40, "200mg, 1-2 tablets every 4 hours", 8.00);
            Medicine dextromethorphan = new Medicine("Dextromethorphan", 30, "30mg, 1-2 teaspoons every 6-8 hours", 6.50);

            availableMedicines.add(ibuprofen);
            availableMedicines.add(acetaminophen);
            availableMedicines.add(loratadine);
            availableMedicines.add(diphenhydramine);
            availableMedicines.add(ranitidine);
            availableMedicines.add(loperamide);
            availableMedicines.add(pseudoephedrine);
            availableMedicines.add(guaifenesin);
            availableMedicines.add(dextromethorphan);
        }
        saveMedicinesToFile();
    }
}
