package boundary;

import HMSApp.HMSMain;
import model.Pharmacist;
import controller.*;
import enums.PersonnelFileType;
import utility.Validator;

/**
 * PharmacistBoundary class represents the user interface for a pharmacist in the HMS
 * system.
 * This class handles pharmacist-specific interactions.
 */
public class PharmacistBoundary extends Boundary {
    private final Pharmacist pharmacist;

    /**
     * Constructor for PatientBoundary.
     *
     * @param //pharmacist The pharmacist using this user interface.
     */
    public PharmacistBoundary() {
        this.pharmacist = (Pharmacist) StaffController.getPersonnelByUID(RegisterController.cookie.getUid(), PersonnelFileType.PHARMACISTS);
    }

    /**
     * Displays the pharmacist menu options.
     */
    @Override
    protected void printChoice() {
        System.out.printf("Welcome! Pharmacist --- %s ---\n", pharmacist.getFullName());
        System.out.println("Pharmacist Menu:");
        System.out.println("1. View Appointment Outcome Record");
        System.out.println("2. Update Prescription Status");
        System.out.println("3. View Medication Inventory");
        System.out.println("4. Submit Replenishment Request");
        System.out.println("5. Logout");

    }

    /**
     * Starts the Pharmacist UI by showing the Pharmacist Dashboard.
     */
    @Override
    public void start() {
        showPharmacistDashboard();
    }

    /**
     * Displays the Pharmacist Dashboard and handles the Pharmacist menu choices.
     */

    public void showPharmacistDashboard() {
        while (true) {
            printChoice();
            int choice = Validator.readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> viewAppointmentOutcomeRecords();
                case 2 -> updatePrescriptionStatus();
                case 3 -> monitorInventory();
                case 4 -> submitReplenishmentRequests();
                case 5 -> {
                    System.out.println("Logging out...");
                    HMSMain.main(null); // Return to the main application
                    return; // Exit the method
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    /**
     * Pharmacist Menu Option 1
     * To allow the pharmacists to view patient appointment outcome records
     */
    public static void viewAppointmentOutcomeRecords() {
        PH_ViewAppointmentOutcomeBoundary outcomeRecordUI = new PH_ViewAppointmentOutcomeBoundary();
        outcomeRecordUI.viewAppointmentOutcomeRecords();
    }

    /**
     * Pharmacist Menu Option 2
     * To enable the pharmacist to update the prescriptions when a patient has completed an appointment
     */
    public static void updatePrescriptionStatus() {
        PH_UpdatePrescriptionBoundary updateStatusUI = new PH_UpdatePrescriptionBoundary();
        updateStatusUI.start();
    }

    /**
     * Pharmacist Menu Option 3
     * To enable the pharmacist to monitor the medication inventory
     */
    public static void monitorInventory() {
        PH_MonitorInventoryBoundary monitorInventoryUI = new PH_MonitorInventoryBoundary();
        monitorInventoryUI.start();
    }

    /**
     * Pharmacist Menu Option 4
     * For Pharmacists to submit replenishment requests to the administrator for approval
     */
    public static void submitReplenishmentRequests() {
        PH_ReplenishRequestBoundary submitRequestUI = new PH_ReplenishRequestBoundary();
        submitRequestUI.start();
    }

}

