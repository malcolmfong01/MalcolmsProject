/**
 * Repository class for managing personnel data, including Doctors, Patients,
 * Pharmacists, and Admins. This class provides functionality for loading
 * and saving personnel data to and from CSV files, as well as clearing and managing
 * personnel records in memory.
 */
package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

import model.*;

public class UserRepository extends Repository {
    /**
     * Directory for storing user CSV files.
     */
    private static final String folder = "data";
    private static final String doctorsFileName = "doctors.csv";
    private static final String patientsFileName = "patients.csv";
    private static final String pharmacistsFileName = "pharmacists.csv";
    private static final String adminsFileName = "admins.csv";
    private static Boolean isRepoLoaded = false;

    // Static data collections for personnel
    public static HashMap<String, Doctor> DOCTORS = new HashMap<>();
    public static HashMap<String, Patient> PATIENTS = new HashMap<>();
    public static HashMap<String, Pharmacist> PHARMACISTS = new HashMap<>();
    public static HashMap<String, Admin> ADMINS = new HashMap<>();

    /**
     * Saves all personnel records to their respective CSV files.
     */
    public static void saveAllPersonnelFiles() {
        savePersonnelToCSV(doctorsFileName, DOCTORS);
        savePersonnelToCSV(patientsFileName, PATIENTS);
        savePersonnelToCSV(pharmacistsFileName, PHARMACISTS);
        savePersonnelToCSV(adminsFileName, ADMINS);
    }

    /**
     * Loads all personnel records from their respective CSV files and sets the repository as loaded.
     *
     * @return true if all records are successfully loaded; false otherwise
     */
    @Override
    public boolean loadFromCSV() {
        try {
            loadPersonnelFromCSV(doctorsFileName, DOCTORS, Doctor.class);
            loadPersonnelFromCSV(patientsFileName, PATIENTS, Patient.class);
            loadPersonnelFromCSV(pharmacistsFileName, PHARMACISTS, Pharmacist.class);
            loadPersonnelFromCSV(adminsFileName, ADMINS, Admin.class);
            setRepoLoaded(true);
            return true;
        } catch (Exception e) {
            System.out.println("Error loading records repository: " + e.getMessage());
            return false;
        }
    }

    /**
     * Saves a specified personnel map to a CSV file.
     *
     * @param fileName     the name of the file to save records to
     * @param personnelMap the map of personnel records to save
     * @param <T>          a type parameter extending Staff
     */
    private static <T extends Staff> void savePersonnelToCSV(String fileName, HashMap<String, T> personnelMap) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(getCsvHeader(fileName));
            writer.newLine();
            for (T personnel : personnelMap.values()) {
                writer.write(personnelToCSV(personnel));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving personnel data: " + e.getMessage());
        }
    }

    /**
     * Returns the CSV header based on the file name.
     *
     * @param fileName the name of the file
     * @return the CSV header string
     */
    private static String getCsvHeader(String fileName) {
        if (Objects.equals(fileName, "doctors.csv")) {
            return "ID,FullName,Username,Email,PhoneNo,PasswordHash,DoB,Gender,Role,DateJoin";
        } else if (Objects.equals(fileName, "patients.csv")) {
            return "ID,FullName,Username,Email,PhoneNo,PasswordHash,DoB,Gender,Role,Allergies,DateOfAdmission";
        } else if (Objects.equals(fileName, "pharmacists.csv")) {
            return "ID,FullName,Username,Email,PhoneNo,PasswordHash,DoB,Gender,Role,DateOfEmployment";
        } else if (Objects.equals(fileName, "admins.csv")) {
            return "ID,FullName,Username,Email,PhoneNo,PasswordHash,DoB,Gender,Role,DateOfCreation";
        }
        return "ID,FullName,Username,Email,PhoneNo,PasswordHash,DoB,Gender,Role";
    }

    /**
     * Converts an Staff object to a CSV-formatted string.
     *
     * @param personnel the personnel object to convert
     * @return a CSV-formatted string representing the personnel
     */
    private static String personnelToCSV(Staff personnel) {
        StringBuilder csvBuilder = new StringBuilder();

        // Add common fields
        csvBuilder.append(personnel.getUID()).append(",");
        csvBuilder.append(personnel.getFullName()).append(",");
        csvBuilder.append(personnel.getUsername()).append(",");
        csvBuilder.append(personnel.getEmail()).append(",");
        csvBuilder.append(personnel.getPhoneNo()).append(",");
        csvBuilder.append(personnel.getPasswordHash()).append(",");
        csvBuilder.append(personnel.getDoB().toString()).append(",");
        csvBuilder.append(personnel.getGender()).append(",");
        csvBuilder.append(personnel.getRole()).append(",");

        // Add Doctor-specific fields or empty placeholders
        if (personnel instanceof Doctor) {
            Doctor doctor = (Doctor) personnel;
            csvBuilder.append(doctor.getDateJoin().toString());
        }

        // Add Patient-specific fields or empty placeholders
        if (personnel instanceof Patient) {
            Patient patient = (Patient) personnel;
            csvBuilder.append(patient.getAllergies()).append(",");
            csvBuilder.append(patient.getDateOfAdmission().toString());
        }

        // Add Pharmacist-specific fields or empty placeholders
        if (personnel instanceof Pharmacist) {
            Pharmacist pharmacist = (Pharmacist) personnel;
            csvBuilder.append(pharmacist.getDateOfEmployment().toString());
        }

        // Add Admin-specific fields or empty placeholder
        if (personnel instanceof Admin) {
            Admin admin = (Admin) personnel;
            csvBuilder.append(admin.getDateOfCreation().toString());
        }

        return csvBuilder.toString();
    }

    /**
     * Loads personnel records from a CSV file into the specified personnel map.
     *
     * @param fileName     the name of the CSV file to load from
     * @param personnelMap the map to store the loaded personnel records
     * @param type         the class type of personnel to load (e.g., Doctor, Patient)
     * @param <T>          a type parameter extending Staff
     */
    private static <T extends Staff> void loadPersonnelFromCSV(
            String fileName, HashMap<String, T> personnelMap, Class<T> type) {
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
            boolean isFirstLine = true; // To skip the header row

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header row
                }

                T personnel = csvToPersonnel(line, type);
                if (personnel != null) {
                    personnelMap.put(personnel.getUID(), personnel);
                } else {
                    System.out.println("Warning: Failed to parse personnel in file: " + fileName);
                }
            }
//            System.out.println("Successfully loaded from " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading personnel data: " + e.getMessage());
        }
    }

    /**
     * Converts a CSV line to a personnel object of the specified type.
     *
     * @param csv  the CSV string representing the personnel
     * @param type the class type of personnel to create (e.g., Doctor, Patient)
     * @param <T>  a type parameter extending Staff
     * @return a personnel object of the specified type, or null if parsing fails
     */
    private static <T extends Staff> T csvToPersonnel(String csv, Class<T> type) {
        String[] fields = csv.split(",");

        try {
            if (type == Doctor.class) {
                return type.cast(new Doctor(
                        fields[0], // UID
                        fields[1], // fullName
                        fields[2], // username
                        fields[3], // email
                        fields[4], // phoneNo
                        fields[5], // passwordHash
                        LocalDateTime.parse(fields[6]), // DoB (LocalDateTime)
                        fields[7], // gender
                        fields[8], // role (e.g., Doctor)
                        LocalDateTime.parse(fields[9]) // dateJoin (LocalDateTime)
                ));
            } else if (type == Patient.class) {
                return type.cast(new Patient(
                        fields[0], // UID
                        fields[1], // fullName
                        fields[2], // username
                        fields[3], // email
                        fields[4], // phoneNo
                        fields[5], // passwordHash
                        LocalDateTime.parse(fields[6]), // DoB (LocalDateTime)
                        fields[7], // gender
                        fields[8], // role (e.g., Patient)
                        fields[9], // allergies
                        LocalDateTime.parse(fields[10]) // dateOfAdmission (LocalDateTime)
                ));
            } else if (type == Pharmacist.class) {
                return type.cast(new Pharmacist(
                        fields[0], // UID
                        fields[1], // fullName
                        fields[2], // username
                        fields[3], // email
                        fields[4], // phoneNo
                        fields[5], // passwordHash
                        LocalDateTime.parse(fields[6]), // DoB (LocalDateTime)
                        fields[7], // gender
                        fields[8], // role (e.g., Pharmacist)
                        LocalDateTime.parse(fields[9]) // dateOfEmployment (LocalDateTime)
                ));
            } else if (type == Admin.class) {
                return type.cast(new Admin(
                        fields[0], // UID
                        fields[1], // fullName
                        fields[2], // username
                        fields[3], // email
                        fields[4], // phoneNo
                        fields[5], // passwordHash
                        LocalDateTime.parse(fields[6]), // DoB (LocalDateTime)
                        fields[7], // gender
                        fields[8], // role (e.g., Admin)
                        LocalDateTime.parse(fields[9]) // dateOfCreation (LocalDateTime)
                ));
            }
        } catch (Exception e) {
            System.out.println("Error parsing personnel data: " + e.getMessage());
        }

        return null;
    }

    /**
     * Clears all personnel data in the repository and saves empty files.
     *
     * @return true if the operation is successful
     */
    public static boolean clearPersonnelDatabase() {
        DOCTORS.clear();
        PATIENTS.clear();
        PHARMACISTS.clear();
        ADMINS.clear();
        saveAllPersonnelFiles();
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
        UserRepository.isRepoLoaded = isRepoLoaded;
    }
}