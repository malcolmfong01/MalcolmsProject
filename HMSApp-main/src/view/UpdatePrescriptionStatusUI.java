package view;

import helper.Helper;
import model.AppointmentOutcomeRecord;
import model.PrescribedMedication;
import repository.AppointmentOutcomeRecordRepository;
import repository.PrescribedMedicationRepository;
import enums.PrescriptionStatus;

import java.util.ArrayList;

import controller.PharmacistController;

/**
 * UpdatePrescriptionStatusUI handles the user interface for updating the status
 * of a specific prescription in an appointment outcome record.
 * <p>
 * This class verifies the patient ID, appointment record ID, and medicine ID before
 * allowing a pharmacist to update the prescription status.
 * </p>
 */
public class UpdatePrescriptionStatusUI extends MainUI {

    @Override
    public void start() {
        printBreadCrumbs("Hospital Management System > Update Prescription Status");
        updatePrescriptionStatus();
    }

    @Override
    protected void printChoice() {
        System.out.println("1. Try another prescription update");
        System.out.println("2. Return to main menu");
    }

    /**
     * Updates the status of a prescription after validating the patient ID,
     * appointment record ID, and medicine ID.
     */
    public void updatePrescriptionStatus() {
        // Step 1: Verify Patient ID
        System.out.print("Enter Patient ID for appointment outcome record: ");
        String patientID = Helper.readString();
        ArrayList<AppointmentOutcomeRecord> records = AppointmentOutcomeRecordRepository.patientOutcomeRecords.get(patientID);

        if (records == null) {
            printWarning("Error: No appointment outcome records found for patient ID " + patientID);
            returnToMenu();
            return;
        }

        // Step 2: Verify Appointment Outcome Record ID
        System.out.print("Enter Appointment Outcome Record ID to update: ");
        String appointmentOutcomeRecordID = Helper.readString();
        AppointmentOutcomeRecord selectedRecord = null;

        for (AppointmentOutcomeRecord record : records) {
            if (record.getAppointmentOutcomeRecordID().equals(appointmentOutcomeRecordID)) {
                selectedRecord = record;
                break;
            }
        }

        if (selectedRecord == null) {
            printWarning("Error: Appointment Outcome Record ID " + appointmentOutcomeRecordID + " not found for patient ID " + patientID);
            returnToMenu();
            return;
        }

        // Step 3: Verify Medicine ID
        System.out.print("Enter Medicine ID to update: ");
        String medicineID = Helper.readString();
        PrescribedMedication selectedMedication = null;

        for (PrescribedMedication medication : selectedRecord.getPrescription().getMedications()) {
            if (medication.getMedicineID().equals(medicineID)) {
                selectedMedication = medication;
                break;
            }
        }

        if (selectedMedication == null) {
            printWarning("Error: Prescription for medicine ID " + medicineID + " not found in appointment record.");
            returnToMenu();
            return;
        }

        // Step 4: Update Prescription Status
        System.out.print("Enter New Status (e.g., DISPENSED): ");
        PrescriptionStatus newStatus;
        try {
            newStatus = PrescriptionStatus.valueOf(Helper.readString().toUpperCase());
        } catch (IllegalArgumentException e) {
            printWarning("Invalid status entered. Please try again.");
            returnToMenu();
            return;
        }

        selectedMedication.setPrescriptionStatus(newStatus);
        System.out.println("Updated status for medicine " + medicineID + " to " + newStatus);

        PharmacistController.updatePrescribedMedicationRepository();

        // Provide navigation options
        printChoice();
        handleUserChoice();
    }

    /**
     * Handles user navigation choice for updating another prescription or returning to the main menu.
     */
    private void handleUserChoice() {
        int choice = getUserChoice(2);
        switch (choice) {
            case 1 -> updatePrescriptionStatus(); // Try another update
            case 2 -> returnToPharmacistUI(); // Return to main menu
            default -> handleInvalidInput();
        }
    }

    /**
     * Returns to the main Pharmacist UI menu.
     */
    private void returnToPharmacistUI() {
        PharmacistUI pharmacistUI = new PharmacistUI();
        pharmacistUI.start();
    }

    /**
     * Provides a way to return to the menu after an error or warning.
     */
    private void returnToMenu() {
        printChoice();
        handleUserChoice();
    }
}