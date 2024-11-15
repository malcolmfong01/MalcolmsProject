/**
 * Repository class for managing TreatmentPlans data, including loading and saving
 * data to a CSV file. This repository maintains a HashMap where each key is a
 * diagnosis ID, and the corresponding value is a TreatmentPlans object associated with that diagnosis.
 */
package repository;

import model.AppointmentOutcomeRecord;
import model.Diagnosis;
import model.TreatmentPlans;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TreatmentPlansRepository extends Repository {
    /**
     * Directory for storing treatment plans CSV files.
     */
	private static final String folder = "data";
    private static final String fileName = "treatment_plans_records.csv";
    private static boolean isRepoLoaded = false;

    // Static data collection for Treatment Plan records (key: diagnosisID)
    public static HashMap<String, TreatmentPlans> diagnosisToTreatmentPlansMap = new HashMap<>();

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
            HashMap<String, TreatmentPlans> diagnosisTreatmentPlansMap) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String diagnosisID : diagnosisTreatmentPlansMap.keySet()) {
                TreatmentPlans treatmentPlan = diagnosisTreatmentPlansMap.get(diagnosisID);
                if (treatmentPlan != null) {
                    writer.write(treatmentPlanToCSV(diagnosisID, treatmentPlan));
                    writer.newLine();
                }
            }
            System.out.println("Treatment plans successfully saved to CSV.");
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
        TreatmentPlansRepository.saveTreatmentPlansToCSV(fileName, diagnosisToTreatmentPlansMap);
        return true;
    }

    /**
     * Converts a TreatmentPlans object to a CSV-formatted string.
     *
     * @param diagnosisID   the ID of the diagnosis associated with the treatment plan
     * @param treatmentPlan the TreatmentPlans object to convert
     * @return a CSV-formatted string representing the treatment plan
     */
    private static String treatmentPlanToCSV(String diagnosisID, TreatmentPlans treatmentPlan) {
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
            HashMap<String, TreatmentPlans> diagnosisTreatmentPlansMap) {
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
                TreatmentPlans treatmentPlan = csvToTreatmentPlan(line);
                String diagnosisID = getDiagnosisIDFromCSV(line);
                if (treatmentPlan != null && diagnosisID != null) {
                    diagnosisTreatmentPlansMap.put(diagnosisID, treatmentPlan);
                }
            }
            System.out.println(
                    "Successfully loaded " + diagnosisTreatmentPlansMap.size() + " treatment plans from " + fileName);
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
     * Converts a CSV-formatted string to a TreatmentPlans object.
     *
     * @param csv the CSV string representing the TreatmentPlans
     * @return a TreatmentPlans object, or null if parsing fails
     */
    private static TreatmentPlans csvToTreatmentPlan(String csv) {
        String[] fields = csv.split(",");
        try {
            return new TreatmentPlans(
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
     * Clears all treatment plan data in the repository and saves an empty file.
     *
     * @return true if the operation is successful
     */
    public static boolean clearTreatmentPlanDatabase() {
        diagnosisToTreatmentPlansMap.clear();
        saveTreatmentPlansToCSV(fileName, diagnosisToTreatmentPlansMap);
        setRepoLoaded(false);
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
        TreatmentPlansRepository.isRepoLoaded = isRepoLoaded;
    }

    // public static TreatmentPlans getTreatmentPlansByDiagnosisID(String
    // diagnosisID) {
    // // Retrieve the treatment plan for the given diagnosisID
    // TreatmentPlans treatmentPlan = diagnosisToTreatmentPlansMap.get(diagnosisID);
    //
    // if (treatmentPlan != null) {
    // return treatmentPlan; // Return the treatment plan if found
    // } else {
    // System.out.println("No treatment plan found for Diagnosis ID: " +
    // diagnosisID);
    // return null; // Return null if no treatment plan exists for the given
    // diagnosis ID
    // }
    // }
    
    /**
     * Retrieves the TreatmentPlans object for the specified diagnosis ID.
     *
     * @param diagnosisID the diagnosis ID for which the treatment plan is requested
     * @return the TreatmentPlans object for the specified diagnosis ID, or null if not found
     */
    public static TreatmentPlans getTreatmentPlansByDiagnosisID(String diagnosisID) {
        // Retrieve the treatment plan for the given diagnosisID
        TreatmentPlans treatmentPlan = diagnosisToTreatmentPlansMap.get(diagnosisID);

        if (treatmentPlan != null) {
            return treatmentPlan; // Return the treatment plan if found
        } else {
            System.out.println("No treatment plan found for Diagnosis ID: " + diagnosisID);
            return null; // Return null if no treatment plan exists for the given diagnosis ID
        }
    }

    /**
     * Adds a new TreatmentPlans record to the repository.
     *
     * @param treatmentplans the TreatmentPlans object to add
     */
    public static void addTreatmentPlansRecord(TreatmentPlans treatmentplans) {
        // Add the record to the repository
        diagnosisToTreatmentPlansMap.put(treatmentplans.getDiagnosisID(), treatmentplans);

        // saveAlltoCSV();
    }

}
