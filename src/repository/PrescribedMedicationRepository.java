package repository;

import enums.PrescriptionStatus;
import model.Diagnosis;
import model.PrescribedMedication;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PrescribedMedicationRepository extends Repository {
    private static final String folder = "data";
    private static final String fileName = "prescribed_medications.csv";
    private static boolean isRepoLoaded = false;
    
    
    // Static data collection for prescribed medications per diagnosis (key = PrescribedMedication ID)
    public static HashMap<String, ArrayList<PrescribedMedication>> diagnosisToMedicationsMap = new HashMap<>();

    /**
     * Specific loading logic for Prescribed Medications from CSV.
     *
     * @return boolean indicating success or failure of the load operation
     */
    @Override
	public boolean loadFromCSV() {
        try {
            loadMedicationsFromCSV(fileName, diagnosisToMedicationsMap);
            setRepoLoaded(true);
            return true;
        } catch (Exception e) {
            System.out.println("Error loading prescribed medications repository: " + e.getMessage());
            return false;
        }
    }

    /**
     * Saves all prescribed medication records in the repository to the CSV file.
     *
     * @return true if the save operation is successful
     */
    public static boolean saveAlltoCSV() {
    	PrescribedMedicationRepository.saveMedicationsToCSV(fileName,diagnosisToMedicationsMap);
		return true;
    }

    
    /**
     * Saves the provided prescribed medication records to the specified CSV file.
     *
     * @param fileName the name of the file to save records to
     * @param diagnosisToMedicationsMap the map of prescribed medications to save
     */
    public static void saveMedicationsToCSV(String fileName, HashMap<String, ArrayList<PrescribedMedication>> diagnosisToMedicationsMap) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            directory.mkdirs();  // Create the directory if it doesn't exist
        }

        // Use a Set to track existing medication IDs to prevent duplicates
        Set<String> existingMedicationIDs = new HashSet<>();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Iterate over all prescribed medications in the map
            for (String prescribedMedID : diagnosisToMedicationsMap.keySet()) {
                for (PrescribedMedication medication : diagnosisToMedicationsMap.get(prescribedMedID)) {
                    String medicineID = medication.getMedicineID();

                    // If this medication ID has already been processed, skip it
                    if (existingMedicationIDs.contains(medicineID)) {
                        continue;  // Skip duplicate
                    }

                    // Add the medication ID to the set of existing IDs
                    existingMedicationIDs.add(medicineID);

                    // Write the medication to the file
                    writer.write(medicationToCSV(prescribedMedID, medication));
                    writer.newLine();
                }
            }
//            System.out.println("Medications successfully saved to CSV.");
        } catch (IOException e) {
            System.out.println("Error saving medications to CSV: " + e.getMessage());
        }
    }

    /**
     * Converts a prescribed medication to a CSV-formatted string.
     *
     * @param prescribedMedID the ID of the diagnosis associated with the medication
     * @param medication the PrescribedMedication object to convert
     * @return a CSV-formatted string representing the prescribed medication
     */
    private static String medicationToCSV(String prescribedMedID, PrescribedMedication medication) {
        return String.join(",",
                medication.getPrescribedMedID(),
                medication.getDiagnosisID(),
                medication.getMedicineID(),
                String.valueOf(medication.getMedicineQuantity()),
                String.valueOf(medication.getPeriodDays()),
                medication.getPrescriptionStatus().toString(),
                "\"" + medication.getDosage() + "\""
        );
    }

    /**
     * Loads prescribed medications from the specified CSV file, creating an empty file if not found.
     * Records are added to the provided map.
     *
     * @param fileName the name of the CSV file to load from
     * @param diagnosisToMedicationsMap the map to store the loaded prescribed medications
     */
    private static void loadMedicationsFromCSV(String fileName, HashMap<String, ArrayList<PrescribedMedication>> diagnosisToMedicationsMap) {
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
                System.out.println("CSV file not found. Created new file: " + filePath);
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
            return;  // No data to load, as the file was just created
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                PrescribedMedication medication = csvToMedication(line);
                String prescribedMedID = getprescribedMedIDFromCSV(line);
                if (medication != null && prescribedMedID != null) {
                    addMedication(prescribedMedID, medication);
                }
            }
//            System.out.println("Successfully loaded medications for " + diagnosisToMedicationsMap.size() + " diagnoses from " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading medications: " + e.getMessage());
        }
    }
    /**
     * Adds a prescribed medication to the map for the specified diagnosis ID.
     *
     * @param prescribedMedID the ID of the Prescribed Medication associated with the medication
     * @param medication the PrescribedMedication to add
     */
    public static void addMedication(String prescribedMedID, PrescribedMedication medication) {
        ArrayList<PrescribedMedication> medications = diagnosisToMedicationsMap.getOrDefault(prescribedMedID, new ArrayList<>());
        medications.add(medication);
        diagnosisToMedicationsMap.put(prescribedMedID, medications);
    }
    /**
     * Extracts the prescribedMed ID from a CSV-formatted string.
     *
     * @param csv the CSV string containing the diagnosis ID
     * @return the diagnosis ID extracted from the CSV string
     */
    private static String getprescribedMedIDFromCSV(String csv) {
        String[] fields = csv.split(",");
        return fields[0];
    }
    /**
     * Converts a CSV-formatted string to a PrescribedMedication object.
     *
     * @param csv the CSV string representing the PrescribedMedication
     * @return a PrescribedMedication object, or null if parsing fails
     */
    private static PrescribedMedication csvToMedication(String csv) {
        String[] fields = csv.split(",");
        try {
            String prescribedMedID = fields[0];
            String diagnosisID = fields[1];
            String medicineID = fields[2];
            int medicineQuantity = Integer.parseInt(fields[3]);
            int periodDays = Integer.parseInt(fields[4]);
            PrescriptionStatus prescriptionStatus = PrescriptionStatus.toEnumPrescriptionStatus(fields[5]);
            String dosage = fields[6].replace("\"", "");

            return new PrescribedMedication(prescribedMedID,diagnosisID,medicineID, medicineQuantity, periodDays, prescriptionStatus, dosage);
        } catch (Exception e) {
            System.out.println("Error parsing medication data: " + e.getMessage());
        }
        return null;
    }

    /**
     * Clears all Medication data in the repository and saves empty files.
     *
     * @return true if the operation is successful
     */
    public static boolean clearPrescribedMedicationDatabase() {
        diagnosisToMedicationsMap.clear();
        saveMedicationsToCSV(fileName, diagnosisToMedicationsMap);
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
		PrescribedMedicationRepository.isRepoLoaded = isRepoLoaded;
	}

}