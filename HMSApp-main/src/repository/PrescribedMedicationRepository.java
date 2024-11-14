package repository;

import enums.PrescriptionStatus;
import model.Diagnosis;
import model.PrescribedMedication;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PrescribedMedicationRepository extends Repository {
    private static final String folder = "data";
    private static final String fileName = "prescribed_medications.csv";
    private static boolean isRepoLoaded = false;
    
    
    // Static data collection for prescribed medications per diagnosis (key = diagnosisID)
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
    public static boolean saveAlltoCSV() {
    	PrescribedMedicationRepository.saveMedicationsToCSV(fileName,diagnosisToMedicationsMap);
		return true;
    }

    
    /**
     * Save prescribed medications to CSV
     */
    public static void saveMedicationsToCSV(String fileName, HashMap<String, ArrayList<PrescribedMedication>> diagnosisToMedicationsMap) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            directory.mkdirs();  // Create the directory if it doesn't exist
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String diagnosisID : diagnosisToMedicationsMap.keySet()) {
                for (PrescribedMedication medication : diagnosisToMedicationsMap.get(diagnosisID)) {
                    writer.write(medicationToCSV(diagnosisID, medication));
                    writer.newLine();
                }
            }
            System.out.println("Medications successfully saved to CSV.");
        } catch (IOException e) {
            System.out.println("Error saving medications to CSV: " + e.getMessage());
        }
    }

    // Convert a PrescribedMedication object to a CSV line
    private static String medicationToCSV(String diagnosisID, PrescribedMedication medication) {
        return String.join(",",
                medication.getDiagnosisID(),
                medication.getMedicineID(),
                String.valueOf(medication.getMedicineQuantity()),
                String.valueOf(medication.getPeriodDays()),
                medication.getPrescriptionStatus().toString(),
                "\"" + medication.getDosage() + "\""
        );
    }

    /**
     * Load medications from a CSV file or create an empty file if not found
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
                String diagnosisID = getDiagnosisIDFromCSV(line);
                if (medication != null && diagnosisID != null) {
                    addMedication(diagnosisID, medication);
                }
            }
            System.out.println("Successfully loaded medications for " + diagnosisToMedicationsMap.size() + " diagnoses from " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading medications: " + e.getMessage());
        }
    }

    public static void addMedication(String diagnosisID, PrescribedMedication medication) {
        ArrayList<PrescribedMedication> medications = diagnosisToMedicationsMap.getOrDefault(diagnosisID, new ArrayList<>());
        medications.add(medication);
        diagnosisToMedicationsMap.put(diagnosisID, medications);
    }

    private static String getDiagnosisIDFromCSV(String csv) {
        String[] fields = csv.split(",");
        return fields[0];
    }

    private static PrescribedMedication csvToMedication(String csv) {
        String[] fields = csv.split(",");
        try {
            String diagnosisID = fields[0];
            String medicineID = fields[1];
            int medicineQuantity = Integer.parseInt(fields[2]);
            int periodDays = Integer.parseInt(fields[3]);
            PrescriptionStatus prescriptionStatus = PrescriptionStatus.toEnumPrescriptionStatus(fields[4]);
            String dosage = fields[5].replace("\"", "");

            return new PrescribedMedication(diagnosisID,medicineID, medicineQuantity, periodDays, prescriptionStatus, dosage);
        } catch (Exception e) {
            System.out.println("Error parsing medication data: " + e.getMessage());
        }
        return null;
    }

    /**
     * Clear all prescribed medication data and save an empty file
     */

    public static boolean clearPrescribedMedicationDatabase() {
        diagnosisToMedicationsMap.clear();
        saveMedicationsToCSV(fileName, diagnosisToMedicationsMap);
        setRepoLoaded(false);
        return true;
    }

	public static boolean isRepoLoaded() {
		return isRepoLoaded;
	}

	public static void setRepoLoaded(boolean isRepoLoaded) {
		PrescribedMedicationRepository.isRepoLoaded = isRepoLoaded;
	}

}
