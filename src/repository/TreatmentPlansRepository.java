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
     * Save TreatmentPlans records map to a CSV file
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

    public static boolean saveAlltoCSV() {
        TreatmentPlansRepository.saveTreatmentPlansToCSV(fileName, diagnosisToTreatmentPlansMap);
        return true;
    }

    // Convert a TreatmentPlans object to a CSV line
    private static String treatmentPlanToCSV(String diagnosisID, TreatmentPlans treatmentPlan) {
        return String.join(",",
                treatmentPlan.getDiagnosisID(), // Diagnosis ID
                treatmentPlan.getTreatmentDate().toString(), // Treatment date
                "\"" + treatmentPlan.getTreatmentDescription() + "\"" // Treatment description
        );
    }

    /**
     * Load treatment plans from a CSV file or create an empty file if it doesn't
     * exist
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

    // Helper to extract Diagnosis ID from CSV line
    private static String getDiagnosisIDFromCSV(String csv) {
        String[] fields = csv.split(",");
        return fields[0];
    }

    // Convert a CSV line to a TreatmentPlans object
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
     * Clear all treatment plan data and save an empty file
     */
    public static boolean clearTreatmentPlanDatabase() {
        diagnosisToTreatmentPlansMap.clear();
        saveTreatmentPlansToCSV(fileName, diagnosisToTreatmentPlansMap);
        setRepoLoaded(false);
        return true;
    }

    public static boolean isRepoLoaded() {
        return isRepoLoaded;
    }

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

    public static void addTreatmentPlansRecord(TreatmentPlans treatmentplans) {
        // Add the record to the repository
        diagnosisToTreatmentPlansMap.put(treatmentplans.getDiagnosisID(), treatmentplans);

        // saveAlltoCSV();
    }

}
