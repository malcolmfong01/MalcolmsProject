
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
/**
 * Repository class for managing personnel data, including Doctors, Patients,
 * Pharmacists, and Admins. This class provides functionality for loading
 * and saving personnel data to and from CSV files, as well as clearing and managing
 * personnel records in memory.
 */
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
    public static HashMap<String, Administrator> ADMINS = new HashMap<>();

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
            loadPersonnelFromCSV(adminsFileName, ADMINS, Administrator.class);
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
     * @param <T>          a type parameter extending User
     */
    private static <T extends User> void savePersonnelToCSV(String fileName, HashMap<String, T> personnelMap) {
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
     * Converts User object to a CSV-formatted string.
     *
     * @param user the user object to convert
     * @return a CSV-formatted string representing the user
     */
    private static String personnelToCSV(User user) {
        StringBuilder csvBuilder = new StringBuilder();

        // Add common fields
        csvBuilder.append(user.getUID()).append(",");
        csvBuilder.append(user.getFullName()).append(",");
        csvBuilder.append(user.getUsername()).append(",");
        csvBuilder.append(user.getEmail()).append(",");
        csvBuilder.append(user.getPhoneNo()).append(",");
        csvBuilder.append(user.getPasswordHash()).append(",");
        csvBuilder.append(user.getDoB().toString()).append(",");
        csvBuilder.append(user.getGender()).append(",");
        csvBuilder.append(user.getRole()).append(",");

        // Use switch with instanceof pattern matching and default case
        switch (user) {
            case Doctor doctor -> csvBuilder.append(doctor.getDateJoin().toString());
            case Patient patient -> {
                csvBuilder.append(patient.getAllergies()).append(",");
                csvBuilder.append(patient.getDateOfAdmission().toString());
            }
            case Pharmacist pharmacist -> csvBuilder.append(pharmacist.getDateOfEmployment().toString());
            case Administrator administrator -> csvBuilder.append(administrator.getDateOfCreation().toString());
            default -> System.out.println("Warning: Unrecognized user type: " + user.getClass().getSimpleName());
        }

        return csvBuilder.toString();
    }



    /**
     * Loads personnel records from a CSV file into the specified personnel map.
     *
     * @param fileName     the name of the CSV file to load from
     * @param personnelMap the map to store the loaded personnel records
     * @param type         the class type of personnel to load (e.g., Doctor, Patient)
     * @param <T>          a type parameter extending User
     */
    private static <T extends User> void loadPersonnelFromCSV(
            String fileName, HashMap<String, T> personnelMap, Class<T> type) {
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
                return; // Exit if there is an error during file creation
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

                T personnel = csvToPersonnel(line, type);
                if (personnel != null) {
                    personnelMap.put(personnel.getUID(), personnel);
                } else {
                    System.out.println("Warning: Failed to parse personnel in file: " + fileName);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading personnel data: " + e.getMessage());
        }
    }


    /**
     * Converts a CSV line to a personnel object of the specified type.
     *
     * @param csv  the CSV string representing the personnel
     * @param type the class type of personnel to create (e.g., Doctor, Patient)
     * @param <T>  a type parameter extending User
     * @return a personnel object of the specified type, or null if parsing fails
     */
    private static <T extends User> T csvToPersonnel(String csv, Class<T> type) {
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
            } else if (type == Administrator.class) {
                return type.cast(new Administrator(
                        fields[0], // UID
                        fields[1], // fullName
                        fields[2], // username
                        fields[3], // email
                        fields[4], // phoneNo
                        fields[5], // passwordHash
                        LocalDateTime.parse(fields[6]), // DoB (LocalDateTime)
                        fields[7], // gender
                        fields[8], // role (e.g., Administrator)
                        LocalDateTime.parse(fields[9]) // dateOfCreation (LocalDateTime)
                ));
            }
        } catch (Exception e) {
            System.out.println("Error parsing personnel data: " + e.getMessage());
        }

        return null;
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