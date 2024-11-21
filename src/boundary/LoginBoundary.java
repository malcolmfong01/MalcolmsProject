package boundary;

import enums.User;
import utility.Validator;
import model.*;
import controller.*;

import java.util.regex.Pattern;

/**
 * This is the login interface for handling login functionality in the Hospital Management System (HMS).
 * Allows users to log in as different personnel types (Patient, Doctor, Pharmacist, Administrator)
 * and redirects them to the appropriate UI after successful authentication.
 */
public class LoginBoundary extends Boundary {

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
        System.out.print("Enter your choice: ");
    }

    /**
     * Starts the login UI, allowing the user to select a login type
     * and proceed with authentication.
     */
    @Override
    public void start() {
        while (true) {
            printChoice();
            int role = Validator.readInt("", 1, 5);

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
        String username = Validator.readString("Please enter your username:");
        String passwordHash = Validator.readString("Please enter your password:");

        // Call the controller to verify login
        model.User personnel = RegisterController.login(username, passwordHash, User.PATIENTS);

        if (personnel != null && personnel instanceof Patient) {
            if ("password".equals(passwordHash)) {
                promptChangePassword(personnel);
            }
            Patient retrievedPatient = (Patient) personnel; // Cast to Patient
            PatientBoundary patUI = new PatientBoundary(retrievedPatient);
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
        String username = Validator.readString("Please enter your username:");
        String passwordHash = Validator.readString("Please enter your password:");

        model.User personnel = RegisterController.login(username, passwordHash, User.DOCTORS);

        if (personnel instanceof Doctor) {
            if ("password".equals(passwordHash)) {
                promptChangePassword(personnel);
            }
            DoctorBoundary docUI = new DoctorBoundary((Doctor) personnel);
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
        String username = Validator.readString("Please enter your username:");
        String passwordHash = Validator.readString("Please enter your password:");

        model.User personnel = RegisterController.login(username, passwordHash, User.PHARMACISTS);

        if (personnel instanceof Pharmacist) {
            if ("password".equals(passwordHash)) {
                promptChangePassword(personnel);
            }
            System.out.println("Login Successful!");
            // TODO:
            PharmacistBoundary patUI = new PharmacistBoundary();
            patUI.showPharmacistDashboard();

        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
    }

    /**
     * Handles the login process for an administrator.
     * If authentication is successful, redirects the administrator to the Administrator UI.
     */
    public void administratorLogin() {
        String username = Validator.readString("Please enter your username:");
        String passwordHash = Validator.readString("Please enter your password:");

        model.User personnel = RegisterController.login(username, passwordHash, User.ADMINS);

        if (personnel instanceof Administrator) {
            if ("password".equals(passwordHash)) {
                promptChangePassword(personnel);
            }
            AdministratorBoundary administratorBoundary = new AdministratorBoundary((Administrator) personnel);
            administratorBoundary.start();
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
    }

    /**
     * Change Password Method
     * @param user the User object representing the user changing their password
     */
    public static boolean changePassword(model.User user, String oldPassword, String newPassword) {
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

    /**
     * Method to check
     * @param password  if the password set by the user is a strong password
     */

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

    /**
     * Prompts the user to change their password if they are using the default password.
     * Ensures that the new password is confirmed before updating.
     *
     * @param user the User object representing the user changing their password
     */

    private void promptChangePassword(model.User user) {
        System.out.println("It appears that you are using the default password.");
        System.out.println("Please change your password for security reasons.");

        while (true) {
            String oldPassword = user.getPasswordHash();
            String newPassword = Validator.readString("Enter new password:");
            String confirmPassword = Validator.readString("Confirm new password:");

            if (newPassword.equals(confirmPassword) && !newPassword.isEmpty()) {
                if (changePassword(user,oldPassword, newPassword)) {
                    RegisterController.updatePassword(user, newPassword);
                    System.out.println("Password changed successfully!");
                    break;
                }
            } else {
                System.out.println("Passwords do not match or are empty. Try again.");
            }

        }
    }


}
