package repository;

import model.Prescription;
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

public class PrescriptionRepository extends Repository {
    private static final String folder = "data";
    private static final String fileName = "prescriptions_records.csv";
    private static boolean isRepoLoaded = false;
    
    // Static data collection for Prescription records (key: diagnosis ID)
    public static HashMap<String, Prescription> PRESCRIPTION_MAP = new HashMap<>();

    /**
     * Specific loading logic for Prescription records from CSV.
     *
     * @return boolean indicating success or failure of the load operation
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
    
    public static boolean saveAlltoCSV() {
    	PrescriptionRepository.savePrescriptionsToCSV(fileName, PRESCRIPTION_MAP);
		return true;
    }

    /**
     * Save Prescription records map to a CSV file
     */
    public static void savePrescriptionsToCSV(String fileName, HashMap<String, Prescription> prescriptionMap) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            directory.mkdirs();  // Create the directory if it doesn't exist
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String diagnosisID : prescriptionMap.keySet()) {
                Prescription prescription = prescriptionMap.get(diagnosisID);
                if (prescription != null) {
                    writer.write(prescriptionToCSV(diagnosisID, prescription));
                    writer.newLine();
                }
            }
            System.out.println("Prescriptions successfully saved to CSV.");
        } catch (IOException e) {
            System.out.println("Error saving prescriptions to CSV: " + e.getMessage());
        }
    }

    // Convert a Prescription object to a CSV line
    private static String prescriptionToCSV(String diagnosisID, Prescription prescription) {
        return String.join(",",
                prescription.getDiagnosisID(),                 // Diagnosis ID
                prescription.getPrescriptionDate().toString()  // Prescription date
        );
    }

    /**
     * Load prescriptions from a CSV file or create an empty file if it doesn't exist
     */
    private static void loadPrescriptionsFromCSV(String fileName, HashMap<String, Prescription> diagnosisPrescriptionMap) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            directory.mkdirs();  // Create the directory if it doesn't exist
        }

        File file = new File(filePath);

        if (!file.exists()) {
            try {
                file.createNewFile();  // Create an empty file if it doesn't exist
                System.out.println("Created empty file: " + filePath);
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
            return;  // No data to load, as the file was just created
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
            System.out.println("Successfully loaded " + diagnosisPrescriptionMap.size() + " prescriptions from " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading prescriptions: " + e.getMessage());
        }
    }

    // Helper to extract Diagnosis ID from CSV line
    private static String getDiagnosisIDFromCSV(String csv) {
        String[] fields = csv.split(",");
        return fields[0];
    }

    // Convert a CSV line to a Prescription object
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
     * Clear all prescription data and save an empty file
     */
    public static boolean clearPrescriptionDatabase() {
    	PRESCRIPTION_MAP.clear();
        savePrescriptionsToCSV(fileName, PRESCRIPTION_MAP);
        setRepoLoaded(false);
        return true;
    }

	public static boolean isRepoLoaded() {
		return isRepoLoaded;
	}

	public static void setRepoLoaded(boolean isRepoLoaded) {
		PrescriptionRepository.isRepoLoaded = isRepoLoaded;
	}
	
    public static void addPrescriptionRecord(Prescription prescription) {
        // Add the record to the repository
    	PRESCRIPTION_MAP.put(prescription.getDiagnosisID(), prescription);

    	saveAlltoCSV();
    }
}


