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

import enums.AppointmentOutcomeStatus;
import model.AppointmentOutcomeRecord;
import model.Prescription;

public class AppointmentOutcomeRecordRepository extends Repository {
    private static final String folder = "data";
    private static boolean isRepoLoaded = false;
    private static final String AppointmentOutcomeRecordsfileName = "appointment_outcome_records.csv";
    //key value = patientID
    public static HashMap<String, ArrayList<AppointmentOutcomeRecord>>patientOutcomeRecords = new HashMap<>();

    @Override
    public boolean loadFromCSV() {
        loadAppoinmentOutcomeRecordsFromCSV(AppointmentOutcomeRecordsfileName, patientOutcomeRecords);
        setRepoLoaded(true);
        return true;
    }
    public static void saveAppointmentOutcomeRecordRepository() {
        saveAppoinmentOutcomeRecordsToCSV(AppointmentOutcomeRecordsfileName, patientOutcomeRecords);
    }
    public static void saveAppoinmentOutcomeRecordsToCSV(String fileName,
                                                         HashMap<String, ArrayList<AppointmentOutcomeRecord>>patientOutcomeRecords) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String patientID: patientOutcomeRecords.keySet()) {
                for (AppointmentOutcomeRecord record : patientOutcomeRecords.get(patientID)) {
                    if (record != null) {
                        writer.write(appointmentOutcomeToCSV(record));
                        writer.newLine();
                    }
                }

            }
            System.out.println("Appointment outcome records successfully saved to CSV.");
        } catch (IOException e) {
            System.out.println("Error saving appointment outcome records to CSV: " + e.getMessage());
        }
    }

    // Convert an AppointmentOutcomeRecord object to a CSV line
    private static String appointmentOutcomeToCSV( AppointmentOutcomeRecord record) {
        return String.join(",",
                record.getPatientID(), // Patient ID
                record.getDoctorID(), // Doctor ID
                record.getDiagnosisID(),
                record.getAppointmentOutcomeRecordID(),
                record.getAppointmentTime().toString(), // Appointment time
                "\"" + record.getTypeOfService() + "\"",
                "\"" + record.getConsultationNotes() + "\"", // Consultation Notes
                record.getAppointmentOutcomeStatus().toString()
        );
    }
    /**
     * Load AppointmentOutcomeRecord records from a CSV file, or create an empty
     * file if it doesn't exist
     */
    public static void loadAppoinmentOutcomeRecordsFromCSV(String fileName,
                                                           HashMap<String, ArrayList<AppointmentOutcomeRecord>>patientOutcomeRecords) {
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
                AppointmentOutcomeRecord record = csvToOutcomeRecord(line);
                String patientID = getPatientIDFromCSV(line);
                if (record != null && patientID != null) {
                    addAppointmentOutcomeRecordIntoHashMapValue( patientID,  record);
                }
            }
            System.out.println("Successfully loaded " + patientOutcomeRecords.size()
                    + " appointment outcome records from " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading appointment outcome records: " + e.getMessage());
        }
    }

    private static String getPatientIDFromCSV(String csv) {
        String[] fields = csv.split(",");
        return fields[0];
    }

    // Convert a CSV line to an AppointmentOutcomeRecord object
    private static AppointmentOutcomeRecord csvToOutcomeRecord(String csv) {
        // Split by comma, ignoring commas within quotes
        String[] fields = csv.split(",");
        try {
            String patientID = fields[0];
            String doctorID = fields[1];
            String diagnosisID = fields[2];
            String appointmentOutcomeRecordID = fields[3];
            LocalDateTime appointmentTime = LocalDateTime.parse(fields[4]);
            Prescription prescription = PrescriptionRepository.PRESCRIPTION_MAP.get(fields[2]);//diagnosisID
            String typeOfService = fields[5].replace("\"", "");
            String consultationNotes = fields[6].replace("\"", "");
            AppointmentOutcomeStatus appointmentOutcomeStatus = AppointmentOutcomeStatus.toEnumAppointmentOutcomeStatus(fields[7]);

            return new AppointmentOutcomeRecord(patientID,
                                                doctorID,
                                                diagnosisID,
                                                appointmentOutcomeRecordID,
                                                appointmentTime,
                                                prescription,
                                                typeOfService,
                                                consultationNotes,
                                                appointmentOutcomeStatus
            );
        } catch (Exception e) {
            System.out.println("Error parsing appointment outcome record data: " + e.getMessage());
        }
        return null;
    }

    /**
     * Clear all appointment outcome record data and save empty files
     */
    public static boolean clearOutcomeRecordDatabase() {
        patientOutcomeRecords.clear();
        saveAppoinmentOutcomeRecordsToCSV("appointment_outcome_records.csv", patientOutcomeRecords);
        return true;
    }

    public static boolean isRepoLoaded() {
        return isRepoLoaded;
    }

    public static void setRepoLoaded(boolean isRepoLoaded) {
        AppointmentOutcomeRecordRepository.isRepoLoaded = isRepoLoaded;
    }
    public static void addAppointmentOutcomeRecordIntoHashMapValue(String patientID, AppointmentOutcomeRecord record) {
        // Retrieve the list of records for the patient ID, or create a new list if none exists
        ArrayList<AppointmentOutcomeRecord> records = patientOutcomeRecords.getOrDefault(patientID, new ArrayList<>());

        // Add the new record to the list
        records.add(record);

        // Update the HashMap with the modified list
        patientOutcomeRecords.put(patientID, records);
        System.out.println(records);

    }
    public static void addAppointmentOutcomeRecord(String patientID, AppointmentOutcomeRecord record) {
        // Retrieve the list of records for the patient ID, or create a new list if none exists
        ArrayList<AppointmentOutcomeRecord> records = patientOutcomeRecords.getOrDefault(patientID, new ArrayList<>());
        // Add the new record to the list
        records.add(record);
        // Update the HashMap with the modified list
        patientOutcomeRecords.put(patientID, records);
        saveAppointmentOutcomeRecordRepository();
    }
//    public static void addOutcomeRecord(String patientID, AppointmentOutcomeRecord outcomeRecord) {
//        // Add the record to the repository
//    	patientOutcomeRecords.put(outcomeRecord.getAppointmentOutcomeRecordID(), outcomeRecord);
//
//    	saveAppointmentOutcomeRecordRepository();
//    }
}