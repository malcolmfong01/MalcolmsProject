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
import model.TreatmentPlans;

public class DiagnosisRepository extends Repository {
    private static final String folder = "data";
    private static final String fileName = "diagnosis_records.csv";
    private static boolean isRepoLoaded = false;
    // Static data collection for Diagnosis records (key = patientID)
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

    public static boolean saveAlltoCSV() {
        DiagnosisRepository.saveDiagnosisRecordsToCSV(fileName, patientDiagnosisRecords);
        return true;
    }

    // Save and load methods, and any other methods in DiagnosisRepository
    public static void saveDiagnosisRecordsToCSV(String fileName,
            HashMap<String, ArrayList<Diagnosis>> patientDiagnosisRecords) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
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
            System.out.println("Diagnosis records successfully saved to CSV.");
        } catch (IOException e) {
            System.out.println("Error saving diagnosis records to CSV: " + e.getMessage());
        }
    }

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

    public static void loadDiagnosisRecordsFromCSV(String fileName,
            HashMap<String, ArrayList<Diagnosis>> patientDiagnosisRecords) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

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
                Diagnosis record = csvToDiagnosisRecord(line);
                if (record != null) {
                    addDiagnosis(record.getPatientID(), record);
                }
            }
            System.out.println(
                    "Successfully loaded " + patientDiagnosisRecords.size() + " diagnosis records from " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading diagnosis records: " + e.getMessage());
        }
    }

    public static void addDiagnosis(String patientID, Diagnosis diagnosis) {
        ArrayList<Diagnosis> diagnoses = patientDiagnosisRecords.getOrDefault(patientID, new ArrayList<>());
//        System.out.println(diagnoses);
//        System.out.println(patientDiagnosisRecords.size());
        diagnoses.add(diagnosis);
//        System.out.println(diagnoses);
//        System.out.println(patientDiagnosisRecords.size());
        patientDiagnosisRecords.put(patientID, diagnoses);
//        System.out.println(diagnoses);
//        System.out.println(patientDiagnosisRecords.size());

        //System.out.println(diagnoses);

    }

    private static Diagnosis csvToDiagnosisRecord(String csv) {
        String[] fields = csv.split(",");
        try {
            String patientID = fields[0];
            String diagnosisID = fields[1];
            String doctorID = fields[2];
            String medicalRecordID = fields[3];
            LocalDateTime diagnosisDate = LocalDateTime.parse(fields[4]);
            TreatmentPlans treatmentPlan = TreatmentPlansRepository.diagnosisToTreatmentPlansMap.get(fields[1]);
            String diagnosisDescription = fields[5].replace("\"", "");
            Prescription prescription = PrescriptionRepository.PRESCRIPTION_MAP.get(fields[1]);

            return new Diagnosis(patientID, diagnosisID, doctorID, medicalRecordID, diagnosisDate,treatmentPlan, diagnosisDescription,
                    prescription);
        } catch (Exception e) {
            System.out.println("Error parsing diagnosis record data: " + e.getMessage());
        }
        return null;
    }

    public static boolean isRepoLoaded() {
        return isRepoLoaded;
    }

    public static void setRepoLoaded(boolean isRepoLoaded) {
        DiagnosisRepository.isRepoLoaded = isRepoLoaded;
    }
    
    public static ArrayList<Diagnosis> getDiagnosesByPatientID(String patientID) {
        ArrayList<Diagnosis> diagnosesForPatient = new ArrayList<>();
        
        for (ArrayList<Diagnosis> diagnoses : patientDiagnosisRecords.values()) {
            for (Diagnosis diagnosis : diagnoses) {
                if (diagnosis.getPatientID().equals(patientID)) {
                    diagnosesForPatient.add(diagnosis);
                }
            }
        }

        return diagnosesForPatient;
    }


}


