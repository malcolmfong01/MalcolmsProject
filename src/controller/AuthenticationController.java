package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

import enums.PersonnelFileType;
import enums.RecordFileType;
import helper.Helper;
import model.*;
import repository.PersonnelRepository;

public class AuthenticationController {
    public static SessionCookie cookie = new SessionCookie(null, null);

    // Method to authenticate a user based on username and password
    public static HMSPersonnel login(String username, String password, PersonnelFileType role) {
        Map<String, ? extends HMSPersonnel> personnelMap = null;

        switch (role.toString().toLowerCase()) {
            case "admins":
                personnelMap = PersonnelRepository.ADMINS;
                break;
            case "doctors":
                personnelMap = PersonnelRepository.DOCTORS;
                break;
            case "pharmacists":
                personnelMap = PersonnelRepository.PHARMACISTS;
                break;
            case "patients":
                personnelMap = PersonnelRepository.PATIENTS;
                break;
            default:
                System.out.println("Login failed: Invalid role provided.");
                return null;
        }

        for (HMSPersonnel personnel : personnelMap.values()) {
            if (personnel.getUsername().equals(username) && verifyPassword(personnel, password)) {
                System.out.println(role + " " + personnel.getFullName() + " logged in successfully.");
                cookie.setRole(PersonnelFileType.toEnum(personnel.getRole()));
                cookie.setUid(personnel.getUID());
                return personnel;
            }
        }

        System.out.println("Login failed: Invalid username or password.");
        return null;
    }

    // Method to verify if the password matches the stored password hash
    private static boolean verifyPassword(HMSPersonnel personnel, String password) {
        return personnel.getPasswordHash().equals(password);
    }

    // Method to update the password for a given personnel
    public static boolean updatePassword(HMSPersonnel personnel, String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            System.out.println("Password update failed: New password cannot be empty.");
            return false;
        }

        personnel.setPasswordHash(newPassword);

        Map<String, ? extends HMSPersonnel> personnelMap = null;
        String uid = personnel.getUID();

        switch (PersonnelFileType.toEnum(personnel.getRole())) {
            case ADMINS:
                personnelMap = PersonnelRepository.ADMINS;
                break;
            case DOCTORS:
                personnelMap = PersonnelRepository.DOCTORS;
                break;
            case PHARMACISTS:
                personnelMap = PersonnelRepository.PHARMACISTS;
                break;
            case PATIENTS:
                personnelMap = PersonnelRepository.PATIENTS;
                break;
            default:
                System.out.println("Password update failed: Invalid role provided.");
                return false;
        }

        if (personnelMap != null && personnelMap.containsKey(uid)) {
            ((Map<String, HMSPersonnel>) personnelMap).put(uid, personnel);
            PersonnelRepository.saveAllPersonnelFiles();
            System.out.println("Password updated successfully for " + personnel.getFullName());
            return true;
        } else {
            System.out.println("Password update failed: Personnel not found in repository.");
            return false;
        }
    }

    // Register Patient
    public static String registerPatient(String fullName, String username, String email,
            String phoneNo, String passwordHash, LocalDateTime DoB,
            String gender, String allergies,
            LocalDateTime dateOfAdmission) {

        // Register patient
        Patient patient = new Patient(fullName, username, email, phoneNo, passwordHash, DoB, gender, allergies, dateOfAdmission);
        PersonnelRepository.PATIENTS.put(patient.getUID(), patient);
        PersonnelRepository.saveAllPersonnelFiles();
        System.out.println("Patient registered successfully with username: " + username);
        return patient.getUID();
    }

    // Register Doctor
    public static boolean registerDoctor(String fullName, String username, String email,
            String phoneNo, String passwordHash, LocalDateTime DoB,
            String gender,
            LocalDateTime dateJoin) {
        // Register doctor
        Doctor doctor = new Doctor(fullName, username, email, phoneNo, passwordHash, DoB, gender, dateJoin );
        PersonnelRepository.DOCTORS.put(doctor.getUID(), doctor);
        PersonnelRepository.saveAllPersonnelFiles();
        System.out.println("Doctor registered successfully with username: " + username);
        return true;
    }

    // Register Pharmacist
    public static boolean registerPharmacist(String fullName, String username, String email,
            String phoneNo, String passwordHash, LocalDateTime DoB,
            String gender,
            LocalDateTime dateOfEmployment) {
        // Register pharmacist
        Pharmacist pharmacist = new Pharmacist(fullName, username, email, phoneNo, passwordHash, DoB, gender,
                 dateOfEmployment);
        PersonnelRepository.PHARMACISTS.put(pharmacist.getUID(), pharmacist);
        PersonnelRepository.saveAllPersonnelFiles();
        System.out.println("Pharmacist registered successfully with username: " + username);
        return true;
    }

    // Register Admin
    public static boolean registerAdmin(String fullName, String username, String email,
            String phoneNo, String passwordHash, LocalDateTime DoB,
            String gender, LocalDateTime dateOfCreation) {
        // Register admin
        Admin admin = new Admin(fullName, username, email, phoneNo, passwordHash, DoB, gender, "Admins",
                dateOfCreation);
        PersonnelRepository.ADMINS.put(admin.getUID(), admin);
        PersonnelRepository.saveAllPersonnelFiles();
        System.out.println("Admin registered successfully with username: " + username);
        return true;
    }

    // Optional: Implement a logout method if needed
    public static void logout(HMSPersonnel personnel) {
        System.out.println(personnel.getFullName() + " has been logged out.");
    }

    public static boolean isUsernameTaken(String username, Map<String, ? extends HMSPersonnel> personnelMap) {
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
