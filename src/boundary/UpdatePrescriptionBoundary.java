package boundary;

import controller.DoctorController;
import controller.MedicineController;
import controller.PatientController;
import controller.PharmacistController;
import enums.AppointmentOutcomeStatus;
import enums.PrescriptionStatus;
import utility.Validator;
import model.AppointmentOutcomeRecord;
import model.Medicine;
import model.PrescribedMedication;
import repository.AppointmentOutcomeRecordRepository;

import java.util.ArrayList;
import java.util.List;

import static repository.PrescribedMedicationRepository.diagnosisToMedicationsMap;

/**
 * UpdatePrescriptionBoundary handles the user interface for updating the status
 * of a specific prescription in an appointment outcome record.
 * <p>
 * This class verifies the patient ID, appointment record ID, and medicine ID
 * before
 * allowing a pharmacist to update the prescription status.
 * </p>
 */
public class UpdatePrescriptionBoundary extends Boundary {

    /**
     * Starts the user menu
     */
    @Override
    public void start() {
        updatePrescriptionStatus();
    }

    @Override
    protected void printChoice() {
        System.out.println("1. Make another prescription update");
        System.out.println("2. Return to main menu");
        System.out.print("Enter your choice: ");
    }

    /**
     * Updates the status of a prescription after validating the patient ID,
     * appointment record ID, and medicine ID.
     */
    public void updatePrescriptionStatus() {
        // Step 1: Verify Patient ID
        System.out.print("Enter Patient ID for appointment outcome record: ");
        String patientID = Validator.readString();
        int index = 1;
        ArrayList<AppointmentOutcomeRecord> records = new ArrayList<>();
        for (ArrayList<AppointmentOutcomeRecord> appointments : AppointmentOutcomeRecordRepository.patientOutcomeRecords
                .values()) {
            for (AppointmentOutcomeRecord appointment : appointments) {
                if (appointment.getPatientID().equals(patientID) && appointment.getAppointmentOutcomeStatus().equals(AppointmentOutcomeStatus.COMPLETED)) {
                    records.add(appointment);
                    System.out.printf("%d. Appointment Outcome %s for Doctor %s with Patient ID: %s%n",
                            index,
                            appointment.getUID(),
                            DoctorController.getDoctorNameById(appointment.getDoctorID()),
                            PatientController.getPatientNameById(appointment.getPatientID()));
                    // Display all medicine IDs in the prescription if present
                    String currentdiagnosisID = appointment.getDiagnosisID();
                    List<PrescribedMedication> medicationsList = new ArrayList<>();
                    // Loop through the map and find the medications for the matching diagnosis ID
                    ArrayList<PrescribedMedication> medications = diagnosisToMedicationsMap.get(currentdiagnosisID);
                    if (medications != null && !medications.isEmpty()) {
                        // Add all medications for this diagnosis
                        medicationsList.addAll(medications);

                        // Display the medication IDs
                        System.out.print("   Medicine IDs: ");
                        for (PrescribedMedication medication : medicationsList) {
                            System.out.print(medication.getMedicineID() + " ");
                        }
                        System.out.println(); // New line after listing all medicine IDs
                    } else {
                        System.out.println("   No prescribed medications found.");
                    }
                    index++;
                }
            }
        }
        if (records.isEmpty()) {
            printWarning("Error: No appointment outcome records found for patient ID " + patientID);
            returnToMenu();
            return;
        }
        // Prompt the user to select an appointment
        // Step 2: Verify Appointment Outcome Record ID
        int selectedIndex = Validator.readInt("\nEnter Appointment Outcome Record ID to update: ");
        if (selectedIndex < 1 || selectedIndex > records.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        if (records.get(selectedIndex - 1) == null) {
            printWarning("Error: Appointment Outcome Record ID ");
            returnToMenu();
            return;
        }

        // Step 3: Verify Medicine ID
        String medicineID = Validator.readID("Medicine", "M\\d{3}");
        System.out.println("Validated Medicine ID: " + medicineID);

        // If the prescription is not null, proceed to find the medication
        ArrayList<PrescribedMedication> selectedMedication = records.get(selectedIndex - 1).getPrescription().getMedications();
        PrescribedMedication foundMedication = null;
        //looping through each medication in the list
        for (PrescribedMedication medication : selectedMedication) {
            if (medication.getMedicineID().equals(medicineID)) {
                foundMedication = medication;
                break;
            }
        }
        //Exception Handling
        if (foundMedication == null) {
            printWarning("Error: Prescription for medicine ID " + medicineID + " not found in appointment record.");
            returnToMenu();
            return;
        }
        System.out.println("Medicine ID: " + foundMedication.getMedicineID());

        // Step 4: Update Prescription Status
        System.out.print("Enter New Status (e.g., DISPENSED): ");
        PrescriptionStatus newStatus;
        try {
            newStatus = PrescriptionStatus.valueOf(Validator.readString().toUpperCase());
            foundMedication.setPrescriptionStatus(newStatus);
            PharmacistController.updatePrescribedMedicationRepository();

        } catch (IllegalArgumentException e) {
            printWarning("Invalid status entered. Please try again.");
            returnToMenu();
            return;
        }

        // Step 5: If status is DISPENSED, reduce the inventory using MedicineController
        if (newStatus == PrescriptionStatus.DISPENSED) {
            Medicine medicine = MedicineController.getMedicineByUID(foundMedication.getMedicineID());
            if (medicine != null) {
                int quantity = foundMedication.getMedicineQuantity();
                if (medicine.getInventoryStock() >= quantity) {
                    // Reduce inventory
                    medicine.setInventoryStock(medicine.getInventoryStock() - quantity);
                    System.out.println("Reduced inventory for Medicine ID " + medicineID + " by " + quantity);
                    // Step 7: Call MedicineController to update the medicine's data
                    if (!MedicineController.updateMedicine(medicineID, medicine)) {
                        printWarning("Error updating the medicine inventory.");
                        returnToMenu();
                        return;
                    }
                } else {
                    System.out.println("Not enough stock to dispense " + quantity + " of Medicine ID " + medicineID);
                    returnToMenu();
                    return;
                }
            } else {
                System.out.println("Error: Medicine not found for ID " + medicineID);
                returnToMenu();
                return;
            }
        }

        //decrease the inventory amount
        System.out.println("Updated status for medicine " + medicineID + " to " + newStatus);

        // Provide navigation options
        printChoice();
        viewmoreOrexit();
    }

    /**
     * Handles user navigation choice for updating another prescription or returning
     * to the main menu.
     */
    private void viewmoreOrexit() {
        int choice = Validator.readInt("");
        switch (choice) {
            case 1 -> updatePrescriptionStatus(); // Make another prescription
            case 2 -> returnToPharmacistMenu();
            default -> handleInvalidInput();
        }
    }

    /**
     * Returns to the main Pharmacist Boundary Menu
     */
    private void returnToPharmacistMenu() {
        PharmacistBoundary pharmacistBoundary = new PharmacistBoundary();
        pharmacistBoundary.start();
    }

    /**
     * Provides a way to return to the menu after an error or warning.
     */
    private void returnToMenu() {
        printChoice();
        viewmoreOrexit();
    }
}