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
import java.util.Iterator;
import java.util.Map;

import enums.AppointmentOutcomeStatus;
import model.AppointmentOutcomeRecord;
import model.Prescription;

/**
 * Repository class for managing AppointmentOutcomeRecord data, including loading and saving
 * data to a CSV file. This repository maintains a HashMap where each key is a patient ID,
 * and the corresponding value is a list of appointment outcome records for that patient.
 */
public class AppointmentOutcomeRecordRepository extends Repository {
    /**
     * Indicates the data folder location for storing repository files.
     */
    private static final String folder = "data";
    /**
     * Boolean flag indicating whether the repository data has been loaded.
     */
    private static boolean isRepoLoaded = false;
    /**
     * Name of the file used for storing appointment outcome records.
     */
    private static final String AppointmentOutcomeRecordsfileName = "appointment_outcome_records.csv";
    /**
     * HashMap holding patient outcome records, with patient ID as the key and an
     * ArrayList
     * of AppointmentOutcomeRecord objects as the value.
     */
    public static HashMap<String, ArrayList<AppointmentOutcomeRecord>> patientOutcomeRecords = new HashMap<>();

    /**
     * Loads appointment outcome records from a CSV file and sets the repository as
     * loaded.
     *
     * @return true if the records are successfully loaded
     */
    @Override
    public boolean loadFromCSV() {
        loadAppoinmentOutcomeRecordsFromCSV(AppointmentOutcomeRecordsfileName, patientOutcomeRecords);
        setRepoLoaded(true);
        return true;
    }

    /**
     * Saves the current state of the appointment outcome records repository to a
     * CSV file.
     */
    public static void saveAppointmentOutcomeRecordRepository() {
        saveAppoinmentOutcomeRecordsToCSV(AppointmentOutcomeRecordsfileName, patientOutcomeRecords);
    }

    /**
     * Saves the provided appointment outcome records to the specified CSV file.
     *
     * @param fileName              the name of the file to save records to
     * @param patientOutcomeRecords the records to save
     */
    public static void saveAppoinmentOutcomeRecordsToCSV(String fileName,
                                                         HashMap<String, ArrayList<AppointmentOutcomeRecord>> patientOutcomeRecords) {
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
            writer.write(getCsvHeader());
            writer.newLine();
            for (String patientID : patientOutcomeRecords.keySet()) {
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


    private static String getCsvHeader() {
        return "Appointment Outcome ID,PatientID,DoctorID,DiagnosisID,AppointmentTime,TypeOfService,ConsultationNotes,AppointmentOutcomeStatus";
    }

    /**
     * Converts an AppointmentOutcomeRecord object to a CSV-formatted string.
     *
     * @param record the AppointmentOutcomeRecord to convert
     * @return a CSV-formatted string representing the record
     */
    private static String appointmentOutcomeToCSV(AppointmentOutcomeRecord record) {
        return String.join(",",
                record.getUID(),
                record.getPatientID(), // Patient ID
                record.getDoctorID(), // Doctor ID
                record.getDiagnosisID(),
                record.getAppointmentTime().toString(), // Appointment time
                "\"" + record.getTypeOfService() + "\"",
                "\"" + record.getConsultationNotes() + "\"", // Consultation Notes
                record.getAppointmentOutcomeStatus().toString());
    }

    /**
     * Loads appointment outcome records from the specified CSV file, creating an empty file
     * if it does not exist. Records are added to the provided HashMap.
     *
     * @param fileName              the name of the CSV file to load from
     * @param patientOutcomeRecords the HashMap to store the loaded records
     */
    public static void loadAppoinmentOutcomeRecordsFromCSV(String fileName,
                                                           HashMap<String, ArrayList<AppointmentOutcomeRecord>> patientOutcomeRecords) {
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
                return; // Exit if file creation throws an exception
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true; // To skip the header row

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header row
                }
                AppointmentOutcomeRecord record = csvToOutcomeRecord(line);
                String patientID = getPatientIDFromCSV(line);
                if (record != null && patientID != null) {
                    addAppointmentOutcomeRecordIntoHashMapValue(patientID, record);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading appointment outcome records: " + e.getMessage());
        }
    }



    /**
     * Extracts the patient ID from a CSV-formatted string.
     *
     * @param csv the CSV string containing the patient ID
     * @return the patient ID extracted from the CSV string
     */
    private static String getPatientIDFromCSV(String csv) {
        String[] fields = csv.split(",");
        return fields[0];
    }

    /**
     * Converts a CSV-formatted string to an AppointmentOutcomeRecord object.
     *
     * @param csv the CSV string representing the AppointmentOutcomeRecord
     * @return an AppointmentOutcomeRecord object, or null if parsing fails
     */
    private static AppointmentOutcomeRecord csvToOutcomeRecord(String csv) {
        // Split by comma, ignoring commas within quotes
        String[] fields = csv.split(",");
        try {
            String UID = fields[0];
            String patientID = fields[1];
            String doctorID = fields[2];
            String diagnosisID = fields[3];
            LocalDateTime appointmentTime = LocalDateTime.parse(fields[4]);
            Prescription prescription = PrescriptionRepository.PRESCRIPTION_MAP.get(fields[3]);// diagnosisID
            String typeOfService = fields[5].replace("\"", "");
            String consultationNotes = fields[6].replace("\"", "");
            AppointmentOutcomeStatus appointmentOutcomeStatus = AppointmentOutcomeStatus
                    .toEnumAppointmentOutcomeStatus(fields[7]);

            return new AppointmentOutcomeRecord(UID, patientID,
                    doctorID,
                    diagnosisID,
                    appointmentTime,
                    prescription,
                    typeOfService,
                    consultationNotes,
                    appointmentOutcomeStatus);
        } catch (Exception e) {
            System.out.println("Error parsing appointment outcome record data: " + e.getMessage());
        }
        return null;
    }

    /**
     * Sets the repository load status.
     *
     * @param isRepoLoaded true to set the repository as loaded, false otherwise
     */
    public static void setRepoLoaded(boolean isRepoLoaded) {
        AppointmentOutcomeRecordRepository.isRepoLoaded = isRepoLoaded;
    }

    /**
     * Adds an appointment outcome record to the HashMap for the specified patient
     * ID.
     *
     * @param patientID the patient ID associated with the record
     * @param record    the AppointmentOutcomeRecord to add
     */
    public static void addAppointmentOutcomeRecordIntoHashMapValue(String patientID, AppointmentOutcomeRecord record) {
        // Retrieve the list of records for the patient ID, or create a new list if none
        // exists
        ArrayList<AppointmentOutcomeRecord> records = patientOutcomeRecords.getOrDefault(patientID, new ArrayList<>());

        // Check if the record already exists based on a unique identifier (e.g.,
        // appointmentOutcomeRecordID)
        boolean exists = records.stream()
                .anyMatch(r -> r.getUID().equals(record.getUID()));

        if (!exists) {
            // Add the new record to the list
            records.add(record);
            // Update the HashMap with the modified list
            patientOutcomeRecords.put(patientID, records);
        }
    }

    /**
     * Adds an appointment outcome record to the repository and immediately saves
     * the repository
     * state to a CSV file.
     *
     * @param patientID the patient ID associated with the record
     * @param record    the AppointmentOutcomeRecord to add
     */
    public static void addAppointmentOutcomeRecord(String patientID, AppointmentOutcomeRecord record) {
        // Retrieve the list of records for the patient ID, or create a new list if none
        // exists
        ArrayList<AppointmentOutcomeRecord> records = patientOutcomeRecords.getOrDefault(patientID, new ArrayList<>());
        // Check if the record already exists based on a unique identifier (e.g.,
        // appointmentOutcomeRecordID)
        boolean exists = records.stream()
                .anyMatch(r -> r.getUID().equals(record.getUID())); // Check for duplicates by UID

        if (!exists) {
            // Add the new record to the list
            records.add(record);
            // Update the HashMap with the modified list
            patientOutcomeRecords.put(record.getUID(), records);
            saveAppointmentOutcomeRecordRepository();
        }

    }


    /**
     * Deletes an appointment outcome record by its record ID.
     *
     * @param recordID the unique ID of the appointment outcome record to delete
     * @return true if the record was successfully deleted, false otherwise
     */
    public static boolean deleteAppointmentOutcomeRecord(String recordID) {
        // Iterate through all entries in the HashMap
        for (Map.Entry<String, ArrayList<AppointmentOutcomeRecord>> entry : patientOutcomeRecords.entrySet()) {
            ArrayList<AppointmentOutcomeRecord> records = entry.getValue();

            if (records != null) {
                // Use an iterator to safely remove while iterating
                Iterator<AppointmentOutcomeRecord> iterator = records.iterator();
                while (iterator.hasNext()) {
                    AppointmentOutcomeRecord record = iterator.next();
                    if (record.getUID().equals(recordID)) {
                        // Remove the record
                        iterator.remove();

                        // If the list becomes empty, remove the patientID entry
                        if (records.isEmpty()) {
                            patientOutcomeRecords.remove(entry.getKey());
                        }

                        // Save changes to the CSV
                        saveAppointmentOutcomeRecordRepository();
                        System.out.println("Appointment outcome record deleted successfully.");
                        return true;
                    }
                }
            }
        }

        System.out.println("No matching record found for deletion.");
        return false;
    }

}
