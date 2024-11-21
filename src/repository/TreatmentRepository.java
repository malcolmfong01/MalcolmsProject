package repository;

import model.Treatment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Repository class for managing Treatment data, including loading and saving
 * data to a CSV file. This repository maintains a HashMap where each key is a
 * diagnosis ID, and the corresponding value is a Treatment object associated with that diagnosis.
 */
public class TreatmentRepository extends Repository {
    /**
     * Directory for storing treatment plans CSV files.
     */
	private static final String folder = "data";
    private static final String fileName = "treatment_plans_records.csv";
    private static boolean isRepoLoaded = false;

    // Static data collection for Treatment Plan records (key: diagnosisID)
    public static HashMap<String, Treatment> diagnosisToTreatmentPlansMap = new HashMap<>();

    /**
     * Specific loading logic for Treatment Plans from CSV.
     *
     * @return boolean indicating success or failure of the load operation
     */
    @Override
    public boolean loadFromCSV() {
        try {
            loadTreatmentPlansFromCSV(fileName, diagnosisToTreatmentPlansMap);
            setRepoLoaded(true);
            return true;
        } catch (Exception e) {
            System.out.println("Error loading treatment plans repository: " + e.getMessage());
            return false;
        }
    }

    /**
     * Saves the provided treatment plans records to the specified CSV file.
     *
     * @param fileName              the name of the file to save records to
     * @param diagnosisTreatmentPlansMap the map of treatment plans records to save
     */
    public static void saveTreatmentPlansToCSV(String fileName,
                                               HashMap<String, Treatment> diagnosisTreatmentPlansMap) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            boolean dirsCreated = directory.mkdirs(); // Create the directory if it doesn't exist
            if (!dirsCreated) {
                System.out.println("Error: Failed to create directory: " + directory.getAbsolutePath());
                return; // Exit if directory creation fails
            }
        }

        // Use a HashSet to track unique treatments
        HashSet<Treatment> uniqueTreatments = new HashSet<>();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String diagnosisID : diagnosisTreatmentPlansMap.keySet()) {
                Treatment treatmentPlan = diagnosisTreatmentPlansMap.get(diagnosisID);
                if (treatmentPlan != null && uniqueTreatments.add(treatmentPlan)) {
                    // Add the treatment to the HashSet and write it to the file if unique
                    writer.write(treatmentPlanToCSV(diagnosisID, treatmentPlan));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving treatment plans to CSV: " + e.getMessage());
        }
    }

    /**
     * Saves all treatment plans records in the repository to the CSV file.
     *
     * @return true if the save operation is successful
     */
    public static boolean saveAlltoCSV() {
        TreatmentRepository.saveTreatmentPlansToCSV(fileName, diagnosisToTreatmentPlansMap);
        return true;
    }

    /**
     * Converts a Treatment object to a CSV-formatted string.
     *
     * @param diagnosisID   the ID of the diagnosis associated with the treatment plan
     * @param treatmentPlan the Treatment object to convert
     * @return a CSV-formatted string representing the treatment plan
     */
    private static String treatmentPlanToCSV(String diagnosisID, Treatment treatmentPlan) {
        return String.join(",",
                treatmentPlan.getDiagnosisID(), // Diagnosis ID
                treatmentPlan.getTreatmentDate().toString(), // Treatment date
                "\"" + treatmentPlan.getTreatmentDescription() + "\"" // Treatment description
        );
    }

    /**
     * Loads treatment plans from the specified CSV file, creating an empty file
     * if not found. Records are added to the provided map.
     *
     * @param fileName              the name of the CSV file to load from
     * @param diagnosisTreatmentPlansMap the map to store the loaded treatment plans
     */
    private static void loadTreatmentPlansFromCSV(String fileName,
                                                  HashMap<String, Treatment> diagnosisTreatmentPlansMap) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            boolean dirsCreated = directory.mkdirs(); // Create the directory if it doesn't exist
            if (!dirsCreated) {
                System.out.println("Error: Failed to create directory: " + directory.getAbsolutePath());
                return; // Exit if directory creation fails
            }
        }

        File file = new File(filePath);

        if (!file.exists()) {
            try {
                boolean fileCreated = file.createNewFile(); // Create an empty file if it doesn't exist
                if (!fileCreated) {
                    System.out.println("Error: Failed to create file: " + filePath);
                    return; // Exit if file creation fails
                }
                System.out.println("Created empty file: " + filePath);
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
                return; // Exit if there is an error during file creation
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Treatment treatmentPlan = csvToTreatmentPlan(line);
                String diagnosisID = getDiagnosisIDFromCSV(line);
                if (diagnosisID != null) {
                    diagnosisTreatmentPlansMap.put(diagnosisID, treatmentPlan);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading treatment plans: " + e.getMessage());
        }
    }

    /**
     * Extracts the diagnosis ID from a CSV-formatted string.
     *
     * @param csv the CSV string containing the diagnosis ID
     * @return the diagnosis ID extracted from the CSV string
     */
    private static String getDiagnosisIDFromCSV(String csv) {
        String[] fields = csv.split(",");
        return fields[0];
    }

    /**
     * Converts a CSV-formatted string to a Treatment object.
     *
     * @param csv the CSV string representing the Treatment
     * @return a Treatment object, or null if parsing fails
     */
    private static Treatment csvToTreatmentPlan(String csv) {
        String[] fields = csv.split(",");
        try {
            return new Treatment(
                    fields[0], // diagnosisID
                    LocalDateTime.parse(fields[1]), // Treatment date
                    fields[2].replace("\"", "") // Treatment description
            );
        } catch (Exception e) {
            System.out.println("Error parsing treatment plan data: " + e.getMessage());
        }
        return null;
    }

    /**
     * Sets the repository load status.
     *
     * @param isRepoLoaded true to set the repository as loaded, false otherwise
     */

    public static void setRepoLoaded(boolean isRepoLoaded) {
        TreatmentRepository.isRepoLoaded = isRepoLoaded;
    }

}
