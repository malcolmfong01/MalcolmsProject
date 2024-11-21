/**
 * Repository class for managing prescribed medication data, including loading and saving
 * data to a CSV file. This repository maintains a HashMap where each key is a
 * diagnosis ID, and the corresponding value is a list of prescribed medications for that diagnosis.
 */
package repository;

import model.Prescription;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Prescription Repository reads, loads,stores  and updates data for
 * all csv files for prescription
 */
public class PrescriptionRepository extends Repository {

    /**
     * Directory for storing prescribed Prescription CSV files.
     */

	private static final String folder = "data";
	
    private static final String fileName = "prescriptions_records.csv";
    private static boolean isRepoLoaded = false;
    
    // Static data collection for Prescription records (key: diagnosis ID)
    public static HashMap<String, Prescription> PRESCRIPTION_MAP = new HashMap<>();

    /**
     * Loads prescription records from a CSV file and sets the repository as loaded.
     *
     * @return true if the records are successfully loaded; false otherwise
     */
    @Override
    public boolean loadFromCSV() {
        try {
            loadPrescriptionsFromCSV(fileName, PRESCRIPTION_MAP);
            PrescriptionRepository.setRepoLoaded(true);
            return true;
        } catch (Exception e) {
            System.out.println("Error loading prescription repository: " + e.getMessage());
            return false;
        }
    }
    /**
     * Saves all prescription records in the repository to the CSV file.
     *
     * @return true if the save operation is successful
     */
    public static boolean saveAlltoCSV() {
    	PrescriptionRepository.savePrescriptionsToCSV(fileName, PRESCRIPTION_MAP);
		return true;
    }

    /**
     * Saves the provided prescription records to the specified CSV file.
     *
     * @param fileName       the name of the file to save records to
     * @param prescriptionMap the map of prescription records to save
     */
    public static void savePrescriptionsToCSV(String fileName, HashMap<String, Prescription> prescriptionMap) {
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

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String diagnosisID : prescriptionMap.keySet()) {
                Prescription prescription = prescriptionMap.get(diagnosisID);
                if (prescription != null) {
                    writer.write(prescriptionToCSV(diagnosisID, prescription)); // Assuming prescriptionToCSV() formats the prescription correctly
                    writer.newLine();
                }
            }
            System.out.println("Prescriptions successfully saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving prescriptions to CSV: " + e.getMessage());
        }
    }

    /**
     * Converts a Prescription object to a CSV-formatted string.
     *
     * @param diagnosisID   the ID of the diagnosis associated with the prescription
     * @param prescription  the Prescription object to convert
     * @return a CSV-formatted string representing the prescription
     */
    private static String prescriptionToCSV(String diagnosisID, Prescription prescription) {
        return String.join(",",
                prescription.getDiagnosisID(),                 // Diagnosis ID
                prescription.getPrescriptionDate().toString()  // Prescription date
        );
    }

    /**
     * Loads prescription records from the specified CSV file, creating an empty file
     * if not found. Records are added to the provided map.
     *
     * @param fileName              the name of the CSV file to load from
     * @param diagnosisPrescriptionMap the map to store the loaded prescriptions
     */

    private static void loadPrescriptionsFromCSV(String fileName, HashMap<String, Prescription> diagnosisPrescriptionMap) {
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
                return; // Exit if there was an error creating the file
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Prescription prescription = csvToPrescription(line);
                String diagnosisID = getDiagnosisIDFromCSV(line);
                if (prescription != null && diagnosisID != null) {
                    diagnosisPrescriptionMap.put(diagnosisID, prescription);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading prescriptions: " + e.getMessage());
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
     * Converts a CSV-formatted string to a Prescription object.
     *
     * @param csv the CSV string representing the Prescription
     * @return a Prescription object, or null if parsing fails
     */

    private static Prescription csvToPrescription(String csv) {
        String[] fields = csv.split(",");
        try {
            return new Prescription(
                    fields[0],                               // diagnosisID
                    LocalDateTime.parse(fields[1]),          // Prescription date
                    PrescribedMedicationRepository.diagnosisToMedicationsMap.getOrDefault(fields[0], new ArrayList<>())
            );
        } catch (Exception e) {
            System.out.println("Error parsing prescription data: " + e.getMessage());
        }
        return null;
    }

    /**
     * Sets the repository load status.
     *
     * @param isRepoLoaded true to set the repository as loaded, false otherwise
     */

	public static void setRepoLoaded(boolean isRepoLoaded) {
		PrescriptionRepository.isRepoLoaded = isRepoLoaded;
	}

    /**
     * Adds a new Prescription record to the repository and immediately saves the repository
     * state to the CSV file.
     *
     * @param prescription the Prescription object to add
     */

    public static void addPrescriptionRecord(Prescription prescription) {
        // Add the record to the repository
    	PRESCRIPTION_MAP.put(prescription.getDiagnosisID(), prescription);

    	saveAlltoCSV();
    }
}


