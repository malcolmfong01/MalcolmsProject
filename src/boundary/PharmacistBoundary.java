package boundary;

import Main.Main;
import enums.User;
import model.Pharmacist;
import controller.*;
import utility.Validator;

/**
 * PharmacistBoundary class represents the user interface for a pharmacist in the HMS
 * system
 * This class handles pharmacist-specific interactions
 */

public class PharmacistBoundary extends Boundary {
    private final Pharmacist pharmacist;

    /**
     * Constructor for Pharmacist Boundary.
     * pharmacist means the current pharamcist for this user interface.
     */

    public PharmacistBoundary() {
        this.pharmacist = (Pharmacist) UserController.getUserbyUID(RegisterController.cookie.getUid(), User.PHARMACISTS);
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
                    Main.main(null); // Return to the main application
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
        ViewAppointmentOutcomeBoundary outcomeRecordUI = new ViewAppointmentOutcomeBoundary();
        outcomeRecordUI.viewAppointmentOutcomeRecords();
    }

    /**
     * Pharmacist Menu Option 2
     * To enable the pharmacist to update the prescriptions when a patient has completed an appointment
     */
    public static void updatePrescriptionStatus() {
        UpdatePrescriptionBoundary updateStatusUI = new UpdatePrescriptionBoundary();
        updateStatusUI.start();
    }

    /**
     * Pharmacist Menu Option 3
     * To enable the pharmacist to monitor the medication inventory
     */
    public static void monitorInventory() {
        MonitorInventoryBoundary monitorInventoryUI = new MonitorInventoryBoundary();
        monitorInventoryUI.start();
    }

    /**
     * Pharmacist Menu Option 4
     * For Pharmacists to submit replenishment requests to the administrator for approval
     */
    public static void submitReplenishmentRequests() {
        ReplenishRequestBoundary submitRequestUI = new ReplenishRequestBoundary();
        submitRequestUI.start();
    }

}

