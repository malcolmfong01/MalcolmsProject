/**
 * Repository class for managing various types of records, including MedicalRecord, 
 * Appointment, and PaymentRecord. This class provides functionality for loading
 * and saving these records to and from CSV files, as well as clearing and managing
 * records in memory.
 */
package repository;

import enums.PaymentStatus;
import enums.RecordStatus;
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
import java.util.Objects;

import enums.AppointmentStatus;

/**
 * RecordsRepository class for managing all records in the system
 * such as payment records, medical records, appointment records.
 */
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
    public static HashMap<String, Appointment> APPOINTMENT_RECORDS = new HashMap<>();
    public static HashMap<String, PaymentRecord> PAYMENT_RECORDS = new HashMap<>();

    /**
     * Loads all record types from their respective CSV files and sets the
     * repository as loaded.
     *
     * @return true if all records are successfully loaded; false otherwise
     */
    @Override
    public boolean loadFromCSV() {
        try {
            loadRecordsFromCSV(medicalFileName, MEDICAL_RECORDS, MedicalRecord.class);
            loadRecordsFromCSV(appointmentFileName, APPOINTMENT_RECORDS, Appointment.class);
            loadRecordsFromCSV(paymentFileName, PAYMENT_RECORDS, PaymentRecord.class);
            setRepoLoaded(true);
            return true;
        } catch (Exception e) {
            System.out.println("Error loading records repository: " + e.getMessage());
            return false;
        }
    }

    /**
     * Saves all record types (medical, appointment, and payment) to their
     * respective CSV files.
     */
    public static void saveAllRecordFiles() {
        saveRecordsToCSV(medicalFileName, MEDICAL_RECORDS);
        saveRecordsToCSV(appointmentFileName, APPOINTMENT_RECORDS);
        saveRecordsToCSV(paymentFileName, PAYMENT_RECORDS);
    }

    /**
     * Returns the CSV header based on the file name.
     *
     * @param fileName the name of the file
     * @return the CSV header string
     */
    private static String getCsvHeader(String fileName) {
        if (Objects.equals(fileName, medicalFileName)) {
            return "Record ID,Full Name,Phone Number,Email,Created Date,Updated Date,Record status,Patient ID,Doctor ID,Blood Type";
        } else if (Objects.equals(fileName, appointmentFileName)) {
            return "Record ID,Created Date,Updated Date,Record status,Appointment Outcome Record ID, Patient ID,Doctor ID,Appointment Time, Location, Appointment Status";
        }
        return "Record ID,Created Date,Updated Date,Record Status,Patient ID,Payment Status,Payment Amount";
    }

    /**
     * Saves a specific record map to a CSV file.
     *
     * @param fileName           the name of the file to save records to
     * @param recordsMapRecordID the map of records to save
     * @param <T>                a type parameter extending Records
     */
    private static <T extends Records> void saveRecordsToCSV(String fileName,
                                                             HashMap<String, T> recordsMapRecordID) {
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
            writer.write(getCsvHeader(fileName)); // Assuming getCsvHeader() provides a valid header
            writer.newLine();
            for (T record : recordsMapRecordID.values()) {
                writer.write(recordToCSV(record)); // Assuming recordToCSV() formats the record correctly
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
    private static String recordToCSV(Records record) {
        if (record instanceof MedicalRecord medRecord) {
            return String.join(",",
                    medRecord.getRecordID(),
                    medRecord.getPatientName(),
                    medRecord.getPatientPhoneNumber(),
                    medRecord.getPatientEmail(),
                    medRecord.getCreatedDate().toString(),
                    medRecord.getUpdatedDate().toString(),
                    medRecord.getRecordStatus().toString(),
                    medRecord.getPatientID(),
                    medRecord.getDoctorID(),
                    medRecord.getBloodType());
        } else if (record instanceof Appointment appRecord) {
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
        } else if (record instanceof PaymentRecord payRecord) {
            return String.join(",",
                    payRecord.getRecordID(),
                    payRecord.getCreatedDate().toString(),
                    payRecord.getUpdatedDate().toString(),
                    payRecord.getRecordStatus().toString(),
                    PaymentRecord.getPatientID(),
                    payRecord.getPaymentStatus().toString(),
                    String.valueOf(payRecord.getPaymentAmount()) // Payment Amount
            );
        }
        return "";
    }

    /**
     * Loads records from a CSV file into the specified records map.
     *
     * @param fileName           the name of the CSV file to load from
     * @param recordsMapRecordID the map to store the loaded records
     * @param type               the class type of record to load (e.g.,
     *                           MedicalRecord, Appointment)
     * @param <T>                a type parameter extending Records
     */
    private static <T extends Records> void loadRecordsFromCSV(String fileName,
                                                               HashMap<String, T> recordsMapRecordID,
                                                               Class<T> type) {
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
                if (fileCreated) {
                    System.out.println("Created empty file: " + filePath);
                } else {
                    System.out.println("File already exists: " + filePath);
                }
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
            return; // No data to load, as the file was just created
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true; // To skip the header row

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header row
                }
                T record = csvToRecord(line, type);
                if (record != null) {
                    recordsMapRecordID.put(record.getRecordID(), record);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading record data: " + e.getMessage());
        }
    }

    /**
     * Converts a CSV line to a record object of the specified type.
     *
     * @param csv  the CSV string representing the record
     * @param type the class type of record to create (e.g., MedicalRecord,
     *             Appointment)
     * @param <T>  a type parameter extending Records
     * @return a record object of the specified type, or null if parsing fails
     */

    private static <T extends Records> T csvToRecord(String csv, Class<T> type) {
        String[] fields = csv.split(",");
        // RecordsController rc = new RecordsController();
        try {
            if (type == MedicalRecord.class) {
                return type.cast(new MedicalRecord(
                        fields[0], // recordID
                        fields[1], // patient name
                        fields[2], // patient phone number
                        fields[3], // patient email
                        LocalDateTime.parse(fields[4]), // createdDate
                        LocalDateTime.parse(fields[5]), // updatedDate
                        RecordStatus.toEnumRecordStatusType(fields[6]), // recordStatus //ACTIVE
                        fields[7], // patientID
                        fields[8], // doctorID
                        fields[9], // bloodType
                        DiagnosisRepository.patientDiagnosisRecords.getOrDefault(fields[7], new ArrayList<>())));
            } else if (type == Appointment.class) {
                ArrayList<AppointmentOutcomeRecord> outcomeRecords = AppointmentOutcomeRecordRepository.patientOutcomeRecords
                        .get(fields[8]);
                AppointmentOutcomeRecord matchingRecord = null;
                if (outcomeRecords != null) {
                    for (AppointmentOutcomeRecord record : outcomeRecords) {
                        if (record.getUID().equals(fields[0])) {
                            matchingRecord = record;
                            break; // Exit loop once the matching record is found
                        }
                    }
                }
                return type.cast(new Appointment(
                        fields[0], // recordID (MRID)
                        LocalDateTime.parse(fields[1]), // createdDate
                        LocalDateTime.parse(fields[2]), // updatedDate
                        RecordStatus.toEnumRecordStatusType(fields[3]), // recordStatus
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
                        RecordStatus.toEnumRecordStatusType(fields[3]), // recordStatus
                        fields[4], // patientID
                        PaymentStatus.toEnumRecordStatusType(fields[5]), // recordStatus
                        Double.parseDouble(fields[6]) // paymentAmount
                ));
            }
        } catch (Exception e) {
            System.out.println("Error parsing record data: " + e.getMessage());
        }

        return null;
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

    /**
     * Updates the doctor ID in the specified medical record.
     *
     * @param medicalRecordID the ID of the medical record to update
     * @param newDoctorID     the new doctor ID to set
     * @return true if the update was successful, false otherwise
     */

    public static boolean updateDoctorIDInMedicalRecord(String medicalRecordID, String newDoctorID) {
        MedicalRecord medicalRecord = MEDICAL_RECORDS.get(medicalRecordID);
        if (medicalRecord != null) {
            medicalRecord.setDoctorID(newDoctorID); // Assuming you have a setter for doctorID
            saveAllRecordFiles(); // Save changes to the CSV
            return true;
        }
        return false;
    }
}