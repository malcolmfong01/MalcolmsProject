package controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.regex.Pattern;

import enums.User;
import model.*;
import repository.UserRepository;

/**
 * This Register Controller class  is responsible for registering,
 * handling methods between repository and the Boundary classes
 */
public class RegisterController {
    public static UserSessionTracker cookie = new UserSessionTracker(null, null);
    /**
     * Authenticates a user based on username, password, and role.
     * @param username the username of the personnel
     * @param password the password of the personnel
     * @param role the role of the personnel (admin, doctor, pharmacist, or patient)
     * @return the authenticated User object if successful, otherwise null
     */
    // Method to authenticate a user based on username and password
    public static model.User login(String username, String password, enums.User role) {
        Map<String, ? extends model.User> personnelMap = null;

        switch (role.toString().toLowerCase()) {
            case "admins":
                personnelMap = UserRepository.ADMINS;
                break;
            case "doctors":
                personnelMap = UserRepository.DOCTORS;
                break;
            case "pharmacists":
                personnelMap = UserRepository.PHARMACISTS;
                break;
            case "patients":
                personnelMap = UserRepository.PATIENTS;
                break;
            default:
                System.out.println("Login failed: Invalid role provided.");
                return null;
        }

        for (model.User personnel : personnelMap.values()) {
            if (personnel.getUsername().equals(username) && verifyPassword(personnel, password)) {
                System.out.println(role + " " + personnel.getFullName() + " logged in successfully.");
                cookie.setRole(User.toEnum(personnel.getRole()));
                cookie.setUid(personnel.getUID());
                return personnel;
            }
        }

        //System.out.println("Login failed: Invalid username or password.");
        return null;
    }
    /**
     * Verifies the password by comparing the entered password with the stored password hash.
     * @param personnel the personnel whose password is being verified
     * @param password the password entered by the user
     * @return true if the password matches the stored password hash, false otherwise
     */
    // Method to verify if the password matches the stored password hash
    private static boolean verifyPassword(model.User personnel, String password) {
        return personnel.getPasswordHash().equals(password);
    }
    /**
     * Updates the password for a given personnel.
     * @param personnel the personnel whose password is to be updated
     * @param newPassword the new password to set
     * @return true if the password is updated successfully, false otherwise
     */
    // Method to update the password for a given personnel
    public static boolean updatePassword(model.User personnel, String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            System.out.println("Password update failed: New password cannot be empty.");
            return false;
        }

        personnel.setPasswordHash(newPassword);

        Map<String, ? extends model.User> personnelMap = null;
        String uid = personnel.getUID();

        switch (User.toEnum(personnel.getRole())) {
            case ADMINS:
                personnelMap = UserRepository.ADMINS;
                break;
            case DOCTORS:
                personnelMap = UserRepository.DOCTORS;
                break;
            case PHARMACISTS:
                personnelMap = UserRepository.PHARMACISTS;
                break;
            case PATIENTS:
                personnelMap = UserRepository.PATIENTS;
                break;
            default:
                System.out.println("Password update failed: Invalid role provided.");
                return false;
        }

        if (personnelMap != null && personnelMap.containsKey(uid)) {
            ((Map<String, model.User>) personnelMap).put(uid, personnel);
            UserRepository.saveAllPersonnelFiles();
            System.out.println("Password updated successfully for " + personnel.getFullName());
            return true;
        } else {
            System.out.println("Password update failed: Personnel not found in repository.");
            return false;
        }
    }
    /**
     * Registers a new patient.
     * @param fullName the full name of the patient
     * @param username the username for the patient
     * @param email the email address of the patient
     * @param phoneNo the phone number of the patient
     * @param passwordHash the hashed password for the patient
     * @param DoB the date of birth of the patient
     * @param gender the gender of the patient
     * @param allergies the allergies of the patient
     * @param dateOfAdmission the date the patient was admitted
     * @return the UID of the newly registered patient
     */
    // Register Patient
    public static String registerPatient(String fullName, String username, String email,
            String phoneNo, String passwordHash, LocalDateTime DoB,
            String gender, String allergies,
            LocalDateTime dateOfAdmission) {

        // Register patient
        Patient patient = new Patient(fullName, username, email, phoneNo, passwordHash, DoB, gender, allergies, dateOfAdmission);
        UserRepository.PATIENTS.put(patient.getUID(), patient);
        UserRepository.saveAllPersonnelFiles();
        System.out.println("Patient registered successfully with username: " + username);
        return patient.getUID();
    }
    /**
     * Registers a new doctor.
     * @param fullName the full name of the doctor
     * @param username the username for the doctor
     * @param email the email address of the doctor
     * @param phoneNo the phone number of the doctor
     * @param passwordHash the hashed password for the doctor
     * @param DoB the date of birth of the doctor
     * @param gender the gender of the doctor
     * @param dateJoin the date the doctor joined
     * @return true if the doctor is registered successfully, false otherwise
     */
    // Register Doctor
    public static boolean registerDoctor(String fullName, String username, String email,
            String phoneNo, String passwordHash, LocalDateTime DoB,
            String gender,
            LocalDateTime dateJoin) {
        // Register doctor
        Doctor doctor = new Doctor(fullName, username, email, phoneNo, passwordHash, DoB, gender, dateJoin );
        UserRepository.DOCTORS.put(doctor.getUID(), doctor);
        UserRepository.saveAllPersonnelFiles();
        System.out.println("Doctor registered successfully with username: " + username);
        return true;
    }
    /**
     * Registers a new pharmacist.
     * @param fullName the full name of the pharmacist
     * @param username the username for the pharmacist
     * @param email the email address of the pharmacist
     * @param phoneNo the phone number of the pharmacist
     * @param passwordHash the hashed password for the pharmacist
     * @param DoB the date of birth of the pharmacist
     * @param gender the gender of the pharmacist
     * @param dateOfEmployment the date the pharmacist was employed
     * @return true if the pharmacist is registered successfully, false otherwise
     */
    // Register Pharmacist
    public static boolean registerPharmacist(String fullName, String username, String email,
            String phoneNo, String passwordHash, LocalDateTime DoB,
            String gender,
            LocalDateTime dateOfEmployment) {
        // Register pharmacist
        Pharmacist pharmacist = new Pharmacist(fullName, username, email, phoneNo, passwordHash, DoB, gender,
                 dateOfEmployment);
        UserRepository.PHARMACISTS.put(pharmacist.getUID(), pharmacist);
        UserRepository.saveAllPersonnelFiles();
        System.out.println("Pharmacist registered successfully with username: " + username);
        return true;
    }
    /**
     * Registers a new admin.
     * @param fullName the full name of the admin
     * @param username the username for the admin
     * @param email the email address of the admin
     * @param phoneNo the phone number of the admin
     * @param passwordHash the hashed password for the admin
     * @param DoB the date of birth of the admin
     * @param gender the gender of the admin
     * @param dateOfCreation the date the admin account was created
     * @return true if the admin is registered successfully, false otherwise
     */
    // Register Administrator
    public static boolean registerAdmin(String fullName, String username, String email,
            String phoneNo, String passwordHash, LocalDateTime DoB,
            String gender, LocalDateTime dateOfCreation) {
        // Register administrator
        Administrator administrator = new Administrator(fullName, username, email, phoneNo, passwordHash, DoB, gender, "Admins",
                dateOfCreation);
        UserRepository.ADMINS.put(administrator.getUID(), administrator);
        UserRepository.saveAllPersonnelFiles();
        System.out.println("Administrator registered successfully with username: " + username);
        return true;
    }
    /**
     * Logs out the currently logged in personnel.
     * @param personnel the personnel to log out
     */
    // Optional: Implement a logout method if needed
    public static void logout(model.User personnel) {
        System.out.println(personnel.getFullName() + " has been logged out.");
    }
    /**
     * Checks if a username is already taken in the given personnel map.
     * @param username the username to check
     * @param personnelMap the map of personnel to check the username against
     * @return true if the username is taken, false otherwise
     */
    public static boolean isUsernameTaken(String username, Map<String, ? extends model.User> personnelMap) {
        return personnelMap.values().stream().anyMatch(personnel -> personnel.getUsername().equals(username));
    }
    public static boolean isValidPassword(String password) {
        // Check password length
        if (password.length() < 8) {
            return false;
        }
        // Check for uppercase letter
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            return false;
        }
        // Check for lowercase letter
        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            return false;
        }
        // Check for a digit
        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            return false;
        }
        // Check for special character
        if (!Pattern.compile("[!@#$%^&*(),.?\":{}|<>]").matcher(password).find()) {
            return false;
        }
        return true;
    }

}
