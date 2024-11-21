package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import model.Diagnosis;
import model.Prescription;
import model.Treatment;

/**
 * Repository class for managing Diagnosis data, including loading and saving
 * data to a CSV file. This repository maintains a HashMap where each key is a
 * patient ID, and the corresponding value is a list of Diagnosis records for that patient.
 */
public class DiagnosisRepository extends Repository {
    /**
     * Folder location for storing repository files.
     */
    private static final String folder = "data";
    /**
     * Name of the file used for storing diagnosis records.
     */
    private static final String fileName = "diagnosis_records.csv";
    /**
     * Flag indicating if the repository data has been loaded.
     */
    private static boolean isRepoLoaded = false;
    /**
     * HashMap holding patient diagnosis records, with patient ID as the key and an
     * ArrayList of Diagnosis objects as the value.
     */
    public static HashMap<String, ArrayList<Diagnosis>> patientDiagnosisRecords = new HashMap<>();

    /**
     * Specific loading logic for Diagnosis records.
     *
     * @return boolean indicating success or failure of the load operation
     */
    @Override
    public boolean loadFromCSV() {
        try {
            loadDiagnosisRecordsFromCSV(fileName, patientDiagnosisRecords);
            setRepoLoaded(true);
            return true;
        } catch (Exception e) {
            System.out.println("Error loading diagnosis repository: " + e.getMessage());
        }
        return false;
    }
    /**
     * Saves the current state of the diagnosis records repository to a CSV file.
     *
     * @return true if the save operation is successful
     */
    public static boolean saveAlltoCSV() {
        DiagnosisRepository.saveDiagnosisRecordsToCSV(fileName, patientDiagnosisRecords);
        return true;
    }

    /**
     * Saves the provided diagnosis records to the specified CSV file.
     *
     * @param fileName             the name of the file to save records to
     * @param patientDiagnosisRecords the records to save
     */
    public static void saveDiagnosisRecordsToCSV(String fileName,
                                                 HashMap<String, ArrayList<Diagnosis>> patientDiagnosisRecords) {
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
            Set<String> writtenDiagnosisIDs = new HashSet<>();
            for (String patientID : patientDiagnosisRecords.keySet()) {
                for (Diagnosis record : patientDiagnosisRecords.get(patientID)) {
                    String diagnosisID = record.getDiagnosisID();
                    if (!writtenDiagnosisIDs.contains(diagnosisID)) {
                        writer.write(diagnosisToCSV(record));
                        writer.newLine();

                        // Mark this diagnosisID as written
                        writtenDiagnosisIDs.add(diagnosisID);
                    }
                }
            }
            System.out.println("Diagnosis records successfully saved to " + filePath);
        } catch (IOException e) {
            System.out.println("Error saving diagnosis records to CSV: " + e.getMessage());
        }
    }

    /**
     * Converts a Diagnosis object to a CSV-formatted string.
     *
     * @param record the Diagnosis to convert
     * @return a CSV-formatted string representing the record
     */
    private static String diagnosisToCSV(Diagnosis record) {
        return String.join(",",
                record.getPatientID(), // Patient ID
                record.getDiagnosisID(), // Diagnosis ID
                record.getDoctorID(), // Doctor ID
                record.getMedicalRecordID(), // MedicalRecord ID
                record.getDiagnosisDate().toString(), // Diagnosis date
                // record.getTreatmentPlans().toString(), // FIXME You get Treatment plans from
                // medical Record...
                "\"" + record.getDiagnosisDescription() + "\"" // Diagnosis description
        );
    }
    /**
     * Loads diagnosis records from the specified CSV file, creating an empty file 
     * if it does not exist. Records are added to the provided HashMap.
     *
     * @param fileName             the name of the CSV file to load from
     * @param patientDiagnosisRecords the HashMap to store the loaded records
     */
    public static void loadDiagnosisRecordsFromCSV(String fileName,
                                                   HashMap<String, ArrayList<Diagnosis>> patientDiagnosisRecords) {
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
                    System.out.println("Error: Failed to create the file: " + filePath);
                    return; // Exit if file creation fails
                }
                System.out.println("Created empty file: " + filePath);
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
                return; // Exit on file creation failure
            }
        }

        // Read the data from the CSV file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Diagnosis record = csvToDiagnosisRecord(line);
                if (record != null) {
                    addDiagnosis(record.getPatientID(), record);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading diagnosis records: " + e.getMessage());
        }
    }

    /**
     * Adds a diagnosis record to the HashMap for the specified patient ID.
     *
     * @param diagnosisID the patient ID associated with the diagnosis
     * @param diagnosis the Diagnosis to add
     */
    public static void addDiagnosis(String diagnosisID, Diagnosis diagnosis) {
        ArrayList<Diagnosis> diagnoses = patientDiagnosisRecords.getOrDefault(diagnosisID, new ArrayList<>());
        diagnoses.add(diagnosis);
        patientDiagnosisRecords.put(diagnosisID, diagnoses);

    }
    /**
     * Converts a CSV-formatted string to a Diagnosis object.
     *
     * @param csv the CSV string representing the Diagnosis
     * @return a Diagnosis object, or null if parsing fails
     */
    private static Diagnosis csvToDiagnosisRecord(String csv) {
        String[] fields = csv.split(",");
        try {
            String patientID = fields[0];
            String diagnosisID = fields[1];
            String doctorID = fields[2];
            String medicalRecordID = fields[3];
            LocalDateTime diagnosisDate = LocalDateTime.parse(fields[4]);
            Treatment treatmentPlan = TreatmentRepository.diagnosisToTreatmentPlansMap.get(fields[1]);
            String diagnosisDescription = fields[5].replace("\"", "");
            Prescription prescription = PrescriptionRepository.PRESCRIPTION_MAP.get(fields[1]);

            return new Diagnosis(patientID, diagnosisID, doctorID, medicalRecordID, diagnosisDate,treatmentPlan, diagnosisDescription,
                    prescription);
        } catch (Exception e) {
            System.out.println("Error parsing diagnosis record data: " + e.getMessage());
        }
        return null;
    }

    /**
     * Sets the repository load status.
     *
     * @param isRepoLoaded true to set the repository as loaded, false otherwise
     */

    public static void setRepoLoaded(boolean isRepoLoaded) {
        DiagnosisRepository.isRepoLoaded = isRepoLoaded;
    }

    /**
     * Retrieves a list of diagnoses for the specified patient ID.
     *
     * @param patientID the patient ID for which diagnoses are requested
     * @return an ArrayList of Diagnosis objects for the specified patient ID
     */

    public static ArrayList<Diagnosis> getDiagnosesByPatientID(String patientID) {
        ArrayList<Diagnosis> diagnosesForPatient = new ArrayList<>();
        Set<String> addedDiagnosisIDs = new HashSet<>(); // Set to track unique DiagnosisIDs
        for (ArrayList<Diagnosis> diagnoses : patientDiagnosisRecords.values()) {
            for (Diagnosis diagnosis : diagnoses) {
                if (diagnosis.getPatientID().equals(patientID)) {
                    // Check if this DiagnosisID is already in the set
                    if (!addedDiagnosisIDs.contains(diagnosis.getDiagnosisID())) {
                        diagnosesForPatient.add(diagnosis); // Add diagnosis if it's not a duplicate
                        addedDiagnosisIDs.add(diagnosis.getDiagnosisID()); // Mark this DiagnosisID as added
                    }
                }
            }
        }

        return diagnosesForPatient;
    }


}


