/**
 * User interface for handling login functionality in the Hospital Management System (HMS).
 * Allows users to log in as different personnel types (Patient, Doctor, Pharmacist, Administrator)
 * and redirects them to the appropriate UI after successful authentication.
 */
package view;

import helper.Helper;
import model.*;
import controller.*;
import enums.PersonnelFileType;
import repository.PersonnelRepository;

import java.util.regex.Pattern;

public class LoginUI extends MainUI {

    /**
     * Prints the login options for the different types of users.
     */
    @Override
    protected void printChoice() {
        System.out.println("You would like to login as:");
        System.out.println("1. Patient");
        System.out.println("2. Doctor");
        System.out.println("3. Pharmacist");
        System.out.println("4. Administrator");
        System.out.println("5. Back to Main Menu");
    }

    /**
     * Starts the login UI, allowing the user to select a login type
     * and proceed with authentication.
     */
    @Override
    public void start() {
        while (true) {
            printChoice();
            int role = Helper.readInt("", 1, 5);

            switch (role) {
                case 1 -> patientLogin();
                case 2 -> doctorLogin();
                case 3 -> pharmacistLogin();
                case 4 -> administratorLogin();
                case 5 -> {
                    System.out.println("Returning to main menu...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    /**
     * Handles the login process for a patient.
     * If authentication is successful, redirects the patient to the Patient UI.
     */
    public void patientLogin() {
        String username = Helper.readString("Please enter your username:");
        String passwordHash = Helper.readString("Please enter your password:");

        // Call the controller to verify login
        HMSPersonnel personnel = AuthenticationController.login(username, passwordHash, PersonnelFileType.PATIENTS);

        if (personnel != null && personnel instanceof Patient) {
            if ("password".equals(passwordHash)) {
                promptChangePassword(personnel);
            }
            Patient retrievedPatient = (Patient) personnel; // Cast to Patient
            PatientUI patUI = new PatientUI(retrievedPatient);
            patUI.start();

        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
    }

    /**
     * Handles the login process for a doctor.
     * If authentication is successful, redirects the doctor to the Doctor UI.
     */
    public void doctorLogin() {
        String username = Helper.readString("Please enter your username:");
        String passwordHash = Helper.readString("Please enter your password:");

        HMSPersonnel personnel = AuthenticationController.login(username, passwordHash, PersonnelFileType.DOCTORS);

        if (personnel instanceof Doctor) {
            if ("password".equals(passwordHash)) {
                promptChangePassword(personnel);
            }
            DoctorUI docUI = new DoctorUI((Doctor) personnel);
            docUI.start();
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
    }

    /**
     * Handles the login process for a pharmacist.
     * If authentication is successful, redirects the pharmacist to the Pharmacist UI.
     */
    public void pharmacistLogin() {
        String username = Helper.readString("Please enter your username:");
        String passwordHash = Helper.readString("Please enter your password:");

        HMSPersonnel personnel = AuthenticationController.login(username, passwordHash, PersonnelFileType.PHARMACISTS);

        if (personnel instanceof Pharmacist) {
            if ("password".equals(passwordHash)) {
                promptChangePassword(personnel);
            }
            System.out.println("Login Successful!");
            // TODO:
            PharmacistUI patUI = new PharmacistUI();
            patUI.showPharmacistDashboard();

        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
    }

    /**
     * Handles the login process for an administrator.
     * If authentication is successful, redirects the administrator to the Admin UI.
     */
    public void administratorLogin() {
        String username = Helper.readString("Please enter your username:");
        String passwordHash = Helper.readString("Please enter your password:");

        HMSPersonnel personnel = AuthenticationController.login(username, passwordHash, PersonnelFileType.ADMINS);

        if (personnel instanceof Admin) {
            if ("password".equals(passwordHash)) {
                promptChangePassword(personnel);
            }
            AdminUI adminUI = new AdminUI((Admin) personnel);
            adminUI.start();
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
    }

    /**
     * Prompts the user to change their password if they are using the default password.
     * Ensures that the new password is confirmed before updating.
     *
     * @param personnel the HMSPersonnel object representing the user changing their password
     */
    public static boolean changePassword(HMSPersonnel personnel, String oldPassword, String newPassword) {
        // Check if the old password matches the stored one
        if (newPassword.equals(oldPassword)) {
            System.out.println("Please use a new password.");
            return false;
        }

        // Validate the new password strength
        if (!isValidPassword(newPassword)) {
            System.out.println("Password must be at least 8 characters long, contain an uppercase letter, a number, and a special character.");
            return false;
        }


        return true;
    }

    private static boolean isValidPassword(String password) {
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

    private void promptChangePassword(HMSPersonnel personnel) {
        System.out.println("It appears that you are using the default password.");
        System.out.println("Please change your password for security reasons.");

        while (true) {
            String oldPassword = personnel.getPasswordHash();
            String newPassword = Helper.readString("Enter new password:");
            String confirmPassword = Helper.readString("Confirm new password:");

            if (newPassword.equals(confirmPassword) && !newPassword.isEmpty()) {
                if (changePassword(personnel,oldPassword, newPassword)) {
                    AuthenticationController.updatePassword(personnel, newPassword);
                    System.out.println("Password changed successfully!");
                    break;
                }
            } else {
                System.out.println("Passwords do not match or are empty. Try again.");
            }

        }
    }


}
