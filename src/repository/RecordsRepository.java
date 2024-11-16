/**
 * Repository class for managing various types of records, including MedicalRecord, 
 * AppointmentRecord, and PaymentRecord. This class provides functionality for loading
 * and saving these records to and from CSV files, as well as clearing and managing
 * records in memory.
 */
package repository;

import model.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import helper.Helper;
import controller.RecordsController;
import enums.AppointmentStatus;

public class RecordsRepository extends Repository {
    /**
     * Directory for storing records CSV files.
     */
    private static final String folder = "data";
    private static final String medicalFileName = "medical_records.csv";
    private static final String appointmentFileName = "appointment_records.csv";
    private static final String paymentFileName = "payment_records.csv";
    private static Boolean isRepoLoaded = false;
    // Static data collections for different record types
    // key value = doctorID

    // key value = recordID
    public static HashMap<String, MedicalRecord> MEDICAL_RECORDS = new HashMap<>();
    public static HashMap<String, AppointmentRecord> APPOINTMENT_RECORDS = new HashMap<>();
    public static HashMap<String, PaymentRecord> PAYMENT_RECORDS = new HashMap<>();

    /**
     * Loads all record types from their respective CSV files and sets the repository as loaded.
     *
     * @return true if all records are successfully loaded; false otherwise
     */
    @Override
    public boolean loadFromCSV() {
        try {
            loadRecordsFromCSV(medicalFileName, MEDICAL_RECORDS, MedicalRecord.class);
            loadRecordsFromCSV(appointmentFileName, APPOINTMENT_RECORDS, AppointmentRecord.class);
            loadRecordsFromCSV(paymentFileName, PAYMENT_RECORDS, PaymentRecord.class);
            setRepoLoaded(true);
            return true;
        } catch (Exception e) {
            System.out.println("Error loading records repository: " + e.getMessage());
            return false;
        }
    }

    /**
     * Saves all record types (medical, appointment, and payment) to their respective CSV files.
     */
    public static void saveAllRecordFiles() {
        saveRecordsToCSV(medicalFileName, MEDICAL_RECORDS);
        saveRecordsToCSV(appointmentFileName, APPOINTMENT_RECORDS);
        saveRecordsToCSV(paymentFileName, PAYMENT_RECORDS);
    }

    /**
     * Saves a specific record map to a CSV file.
     *
     * @param fileName        the name of the file to save records to
     * @param recordsMapRecordID the map of records to save
     * @param <T>             a type parameter extending HMSRecords
     */
    private static <T extends HMSRecords> void saveRecordsToCSV(String fileName,
            HashMap<String, T> recordsMapRecordID) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (T record : recordsMapRecordID.values()) {
                writer.write(recordToCSV(record));
                writer.newLine();
            }
            System.out.println("Records successfully saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving record data: " + e.getMessage());
        }
    }
    
    /**
     * Converts a record object to a CSV-formatted string.
     *
     * @param record the record object to convert
     * @return a CSV-formatted string representing the record
     */
    private static String recordToCSV(HMSRecords record) {
        if (record instanceof MedicalRecord) {
            MedicalRecord medRecord = (MedicalRecord) record;
            return String.join(",",
                    medRecord.getRecordID(),
                    medRecord.getCreatedDate().toString(),
                    medRecord.getUpdatedDate().toString(),
                    medRecord.getRecordStatus().toString(),
                    medRecord.getPatientID(),
                    medRecord.getDoctorID(),
                    medRecord.getBloodType());
        } else if (record instanceof AppointmentRecord) {
            AppointmentRecord appRecord = (AppointmentRecord) record;
            AppointmentOutcomeRecord outcome = appRecord.getAppointmentOutcomeRecord();
            return String.join(",",
                    appRecord.getRecordID(),
                    appRecord.getCreatedDate().toString(),
                    appRecord.getUpdatedDate().toString(),
                    appRecord.getRecordStatus().toString(),
                    appRecord.getAppointmentOutcomeRecordID(),
                    appRecord.getPatientID(),
                    appRecord.getDoctorID(),
                    appRecord.getAppointmentTime().toString(),
                    appRecord.getLocation(),
                    appRecord.getAppointmentStatus().toString());
            // AppointmentOutcomeRecord appointmentOutcomeRecord,
        } else if (record instanceof PaymentRecord) {
            PaymentRecord payRecord = (PaymentRecord) record;
            return String.join(",",
                    payRecord.getRecordID(),
                    payRecord.getCreatedDate().toString(),
                    payRecord.getUpdatedDate().toString(),
                    payRecord.getRecordStatus().toString(),
                    payRecord.getPatientID(),
                    String.valueOf(payRecord.getPaymentAmount()) // Payment Amount
            );
        }
        return "";
    }

    /**
     * Loads records from a CSV file into the specified records map.
     *
     * @param fileName        the name of the CSV file to load from
     * @param recordsMapRecordID the map to store the loaded records
     * @param type            the class type of record to load (e.g., MedicalRecord, AppointmentRecord)
     * @param <T>             a type parameter extending HMSRecords
     */
    private static <T extends HMSRecords> void loadRecordsFromCSV(String fileName,
            HashMap<String, T> recordsMapRecordID,
            Class<T> type) {
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
                T record = csvToRecord(line, type);
                // MedicalRecord medicalrecord = csvToRecord(line, type);
                // String[] fields = line.split(",");

                if (record != null) {
                    // recordsMapPatientID.put(fields[4], record);
                    // recordsMapDoctorID.put(fields[4], record);
                    recordsMapRecordID.put(record.getRecordID(), record);
                }
            }
            System.out.println("Successfully loaded " + recordsMapRecordID.size() + " records from " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading record data: " + e.getMessage());
        }
    }

    /**
     * Converts a CSV line to a record object of the specified type.
     *
     * @param csv  the CSV string representing the record
     * @param type the class type of record to create (e.g., MedicalRecord, AppointmentRecord)
     * @param <T>  a type parameter extending HMSRecords
     * @return a record object of the specified type, or null if parsing fails
     */
    private static <T extends HMSRecords> T csvToRecord(String csv, Class<T> type) {
        String[] fields = csv.split(",");
        // RecordsController rc = new RecordsController();
        try {
            if (type == MedicalRecord.class) {
                return type.cast(new MedicalRecord(
                        fields[0], // recordID
                        LocalDateTime.parse(fields[1]), // createdDate
                        LocalDateTime.parse(fields[2]), // updatedDate
                        RecordStatusType.toEnumRecordStatusType(fields[3]), // recordStatus //ACTIVE
                        fields[4], // patientID
                        fields[5], // doctorID
                        fields[6], // bloodType
                        DiagnosisRepository.patientDiagnosisRecords.getOrDefault(fields[4], new ArrayList<>())));
            } else if (type == AppointmentRecord.class) {
                ArrayList<AppointmentOutcomeRecord> outcomeRecords = AppointmentOutcomeRecordRepository.patientOutcomeRecords
                        .get(fields[5]);
                AppointmentOutcomeRecord matchingRecord = null;
                if (outcomeRecords != null) {
                    for (AppointmentOutcomeRecord record : outcomeRecords) {
                        if (record.getAppointmentOutcomeRecordID().equals(fields[4])) {
                            matchingRecord = record;
                            break; // Exit loop once the matching record is found
                        }
                    }
                }
                return type.cast(new AppointmentRecord(
                        fields[0], // recordID (MRID)
                        LocalDateTime.parse(fields[1]), // createdDate
                        LocalDateTime.parse(fields[2]), // updatedDate
                        RecordStatusType.toEnumRecordStatusType(fields[3]), // recordStatus
                        fields[4], // appointmentOutcomeRecordID
                        fields[5], // patientID
                        fields[6], // doctorID
                        LocalDateTime.parse(fields[7]), // appointmentTime
                        fields[8],
                        AppointmentStatus.toEnumAppointmentStatus(fields[9]), // appointmentStatus
                        matchingRecord // appointmentOutcome, look up for appointment outcome ID
                )); // doctorID

            } else if (type == PaymentRecord.class) {
                return type.cast(new PaymentRecord(
                        fields[0], // recordID
                        LocalDateTime.parse(fields[1]), // createdDate
                        LocalDateTime.parse(fields[2]), // updatedDate
                        RecordStatusType.toEnumRecordStatusType(fields[3]), // recordStatus
                        fields[4], // patientID
                        Double.parseDouble(fields[5]) // paymentAmount
                ));
            }
        } catch (Exception e) {
            System.out.println("Error parsing record data: " + e.getMessage());
        }

        return null;
    }

    /**
     * Clears all records in the repository and saves empty files.
     *
     * @return true if the operation is successful
     */
    public static boolean clearRecordDatabase() {
        MEDICAL_RECORDS.clear();
        APPOINTMENT_RECORDS.clear();
        PAYMENT_RECORDS.clear();
        saveAllRecordFiles();
        setRepoLoaded(false);
        return true;
    }
    /**
     * Checks if the repository has been loaded.
     *
     * @return true if the repository is loaded; false otherwise
     */
    public static Boolean isRepoLoaded() {
        return isRepoLoaded;
    }
    /**
     * Sets the repository load status.
     *
     * @param isRepoLoaded true to set the repository as loaded, false otherwise
     */
    public static void setRepoLoaded(Boolean isRepoLoaded) {
        RecordsRepository.isRepoLoaded = isRepoLoaded;
    }
}