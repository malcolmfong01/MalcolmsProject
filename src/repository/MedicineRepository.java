/**
 * Repository class for managing Medicine data, including loading and saving
 * data to a CSV file. This repository maintains a HashMap where each key is a
 * medicine ID, and the corresponding value is a Medicine object representing details
 * about the medicine.
 */
package repository;

import model.Medicine;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import enums.ReplenishStatus;

public class MedicineRepository extends Repository {
    /**
     * Folder location for storing repository files.
     */
    private static final String folder = "data";
    /**
     * Name of the file used for storing medicine records.
     */
    private static final String fileName = "medicines.csv";
    /**
     * Flag indicating if the repository data has been loaded.
     */
    private static Boolean isRepoLoaded = true;
    /**
     * HashMap holding medicine records, with medicine ID as the key and a Medicine object as the value.
     */
    public static HashMap<String, Medicine> MEDICINES = new HashMap<>();

    /**
     * Specific loading logic for Medicine records from CSV.
     *
     * @return boolean indicating success or failure of the load operation
     */
    @Override
    public boolean loadFromCSV() {
        try {
            loadMedicinesFromCSV(fileName, MEDICINES);
            isRepoLoaded = true;
            return true;
        } catch (Exception e) {
            System.out.println("Error loading medicines repository: " + e.getMessage());
            return false;
        }
    }

    /**
     * Save all Medicine records to the CSV file
     */
    public static void saveAllMedicinesToCSV() {
        saveMedicinesToCSV(fileName, MEDICINES);
    }

    /**
     * Saves the provided map of Medicine records to the specified CSV file.
     *
     * @param fileName    the name of the file to save records to
     * @param medicinesMap the map of Medicine objects to save
     */
    private static void saveMedicinesToCSV(String fileName, HashMap<String, Medicine> medicinesMap) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Medicine medicine : medicinesMap.values()) {
                writer.write(medicineToCSV(medicine));
                writer.newLine();
            }
            System.out.println("Medicines successfully saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving medicine data: " + e.getMessage());
        }
    }

    /**
     * Converts a Medicine object to a CSV-formatted string.
     *
     * @param medicine the Medicine object to convert
     * @return a CSV-formatted string representing the Medicine
     */
    private static String medicineToCSV(Medicine medicine) {
        return String.join(",",
                medicine.getMedicineID(),
                medicine.getName(),
                medicine.getManufacturer(),
                medicine.getExpiryDate().toString(),
                String.valueOf(medicine.getInventoryStock()),
                String.valueOf(medicine.getLowStockLevel()),
                medicine.getReplenishStatus().toString(),
                medicine.getReplenishRequestDate().toString(),
                medicine.getApprovedDate().toString());
    }

    /**
     * Loads Medicine records from the specified CSV file, creating an empty file
     * if it does not exist. Records are added to the provided HashMap.
     *
     * @param fileName     the name of the CSV file to load from
     * @param medicinesMap the HashMap to store the loaded records
     */
    private static void loadMedicinesFromCSV(String fileName, HashMap<String, Medicine> medicinesMap) {

        String filePath = "./src/repository/" + folder + "/" + fileName;

        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }

        File file = new File(filePath);

        if (!file.exists()) {
            try {
                file.createNewFile(); // Create an empty file if it doesn't exist
                System.out.println("Created empty file: " + filePath);
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
            return; // No data to load, as the file was just created
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Medicine medicine = csvToMedicine(line);
                if (medicine != null) {
                    medicinesMap.put(medicine.getMedicineID(), medicine);
                }
            }
            System.out.println("Successfully loaded " + medicinesMap.size() + " medicines from " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading medicine data: " + e.getMessage());
        }
    }

    /**
     * Converts a CSV-formatted string to a Medicine object.
     *
     * @param csv the CSV string representing the Medicine
     * @return a Medicine object, or null if parsing fails
     */
    private static Medicine csvToMedicine(String csv) {
        String[] fields = csv.split(",");
        try {
            return new Medicine(
                    fields[0], // medicineID
                    fields[1], // name
                    fields[2], // manufacturer
                    LocalDateTime.parse(fields[3]), // expiryDate
                    Integer.parseInt(fields[4]), // inventoryStock
                    Integer.parseInt(fields[5]), // lowStockLevel
                    safeValueOf(fields[6]), // replenish status
                    LocalDateTime.parse(fields[7]), // replenishRequestDate;
                    LocalDateTime.parse(fields[8]) // approvedDate
            );
        } catch (Exception e) {
            System.out.println("Error parsing medicine data: " + e.getMessage());
        }
        return null;
    }

    /**
     * Safely parses a string to a ReplenishStatus enum value, returning null if the status is invalid.
     *
     * @param status the string representation of the ReplenishStatus
     * @return the ReplenishStatus enum value, or null if invalid
     */
    private static ReplenishStatus safeValueOf(String status) {
        try {
            return ReplenishStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid replenish status: " + status);
            return null; // or handle it as needed
        }
    }

    /**
     * Clears all medicine data in the repository and saves an empty file.
     *
     * @return true if the operation is successful
     */
    public static boolean clearMedicineDatabase() {
        MEDICINES.clear();
        saveAllMedicinesToCSV();
        isRepoLoaded = false;
        return true;
    }
    
    /**
     * Checks if the repository has been loaded.
     *
     * @return true if the repository is loaded; false otherwise
     */
    public static boolean isRepoLoaded() {
        return isRepoLoaded;
    }

    /**
     * Sets the repository load status.
     *
     * @param isRepoLoaded true to set the repository as loaded, false otherwise
     */
    public static void setRepoLoaded(boolean isRepoLoaded) {
        MedicineRepository.isRepoLoaded = isRepoLoaded;
    }
}
