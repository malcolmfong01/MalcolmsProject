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

public class LoginUI extends MainUI {

    /**
     * Prints the login options for the different types of users.
     */
    @Override
    protected void printChoice() {
        printBreadCrumbs("HMS App UI > Login Page");
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
            if ("default".equals(passwordHash)) {
                changePassword(personnel);
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
            if ("default".equals(passwordHash)) {
                changePassword(personnel);
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
            if ("default".equals(passwordHash)) {
                changePassword(personnel);
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
    private void changePassword(HMSPersonnel personnel) {
        System.out.println("It appears that you are using the default password.");
        System.out.println("Please change your password for security reasons.");

        while (true) {
            String newPassword = Helper.readString("Enter new password:");
            String confirmPassword = Helper.readString("Confirm new password:");

            if (newPassword.equals(confirmPassword) && !newPassword.isEmpty()) {
                AuthenticationController.updatePassword(personnel, newPassword);
                System.out.println("Password changed successfully!");
                break;
            } else {
                System.out.println("Passwords do not match or are empty. Try again.");
            }
        }
    }

}
