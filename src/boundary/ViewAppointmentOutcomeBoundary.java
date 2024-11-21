package boundary;

import Main.Main;
import controller.RegisterController;
import controller.UserController;
import enums.User;
import model.AppointmentOutcomeRecord;
import model.Pharmacist;
import model.PrescribedMedication;
import model.Prescription;
import repository.AppointmentOutcomeRecordRepository;
import utility.Validator;

import java.util.ArrayList;
import java.util.List;

import static repository.PrescribedMedicationRepository.diagnosisToMedicationsMap;

/**
 * The ViewAppointmentOutcomeBoundary class provides a user interface for
 * viewing
 * appointment outcome records for patients within the Hospital Management
 * System.
 */
public class ViewAppointmentOutcomeBoundary extends Boundary {
    /**
     * Starts the menu for viewing appointment outcome records and displays the
     * appropriate breadcrumbs.
     */
    @Override
    public void start() {
        viewAppointmentOutcomeRecords();
    }

    /**
     * Prints the available choices for the user when interacting with the
     * appointment outcome record UI.
     */
    @Override
    protected void printChoice() {
        System.out.println("1. View another appointment outcome record");
        System.out.println("2. Return to main menu");
        System.out.print("Enter your choice: ");
    }

    /**
     * Retrieves and displays appointment outcome records for a specified patient
     * based on their patient ID. If no records are found, an appropriate warning is
     * displayed.
     */
    public void viewAppointmentOutcomeRecords() {
        System.out.print("Enter Patient ID to view appointment outcome records: ");
        String patientID = Validator.readID("Patient", "P\\d{3}");
        System.out.println("Validated Patient ID: " + patientID);

        boolean found = false;
        for (List<AppointmentOutcomeRecord> records : AppointmentOutcomeRecordRepository.patientOutcomeRecords.values()) {
            for (AppointmentOutcomeRecord record : records) {
                if (record.getPatientID().equals(patientID)) {
                    displayAppointmentOutcomeRecord(record);
                    found = true;
                }
            }
        }

        if (!found) {
            printWarning("No appointment outcome records found for patient ID: " + patientID);
        }


        // Print options for user navigation
        printChoice();
        viewmoreOrexit();
    }

    /**
     * Displays the details of a given AppointmentOutcomeRecord, including
     * prescription information if available.
     *
     * @param record The AppointmentOutcomeRecord to be displayed.
     */
    private void displayAppointmentOutcomeRecord(AppointmentOutcomeRecord record) {
        printline();
        System.out.println("Appointment Outcome for Record ID: " + record.getUID());
        System.out.println("Patient ID: " + record.getPatientID());
        System.out.println("Doctor ID: " + record.getDoctorID());
        System.out.println("Diagnosis ID: " + record.getDiagnosisID());
        System.out.println("Appointment Time: " + record.getAppointmentTime());
        System.out.println("Type of Service: " + record.getTypeOfService());
        System.out.println("Consultation Notes: " + record.getConsultationNotes());
        System.out.println("Appointment Outcome Status: " + record.getAppointmentOutcomeStatus());

        Prescription prescription = record.getPrescription();
        if (prescription != null && prescription.getMedications() != null) {
            System.out.println("Prescription Details:");

            // Display all medicine IDs in the prescription if present
            String currentdiagnosisID = record.getDiagnosisID();
            List<PrescribedMedication> medicationsList = new ArrayList<>();
            // Loop through the map and find the medications for the matching diagnosis ID
            ArrayList<PrescribedMedication> medications = diagnosisToMedicationsMap.get(currentdiagnosisID);
            if (medications != null && !medications.isEmpty()) {
                // Add all medications for this diagnosis
                medicationsList.addAll(medications);

                // Display the medication IDs
                for (PrescribedMedication medication : medicationsList) {
                    System.out.println("Medicine ID: " + medication.getMedicineID());
                    System.out.println("Quantity: " + medication.getMedicineQuantity());
                    System.out.println("Dosage: " + medication.getDosage());
                    System.out.println("Period (days): " + medication.getPeriodDays());
                    System.out.println("Prescription Status: " + medication.getPrescriptionStatus());
                    System.out.println();
                }
                System.out.println(); // New line after listing all medicine IDs
            }

        } else {
            System.out.println("No prescription details available for this appointment outcome record.");
        }
        printline();
    }

    /**
     * Handles the user's choice to view another appointment outcome record or
     * return to the Pharmacist UI.
     */
    private void viewmoreOrexit() {
        int choice = Validator.readInt("");
        switch (choice) {
            case 1 -> viewAppointmentOutcomeRecords(); // View another record
            case 2 -> returnToPharmacistMenu();
            default -> handleInvalidInput();
        }
    }

    /**
     * Returns the user to the Pharmacist menu. If the authenticated user is not
     * a valid, it returns to the main menu.
     */
    private void returnToPharmacistMenu() {
        Pharmacist pharmacist = (Pharmacist) UserController.getUserbyUID(
                RegisterController.cookie.getUid(), User.PHARMACISTS);
        if (pharmacist != null) {
            PharmacistBoundary pharmacistBoundary = new PharmacistBoundary();
            pharmacistBoundary.start();
        } else {
            //This will handle the case if pharmacist is null and return the user back to the main menu
            System.out.println("Error: Invalid pharmacist ID. Returning to main menu.");
            Main.main(null);
        }
    }

}