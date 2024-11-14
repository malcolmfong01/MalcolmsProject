package view;

import HMSApp.HMSMain;
import controller.AuthenticationController;
import controller.HMSPersonnelController;
import helper.Helper;
import model.AppointmentOutcomeRecord;
import model.Pharmacist;
import model.PrescribedMedication;
import model.Prescription;
import repository.AppointmentOutcomeRecordRepository;
import repository.PersonnelFileType;
import view.PharmacistUI;

public class ViewAppointmentOutcomeRecordUI extends MainUI {

    @Override
    public void start() {
        printBreadCrumbs("Hospital Management System > View Appointment Outcome Records");
        viewAppointmentOutcomeRecords();
    }

    @Override
    protected void printChoice() {
        System.out.println("1. View another appointment outcome record");
        System.out.println("2. Return to main menu");
    }

    public void viewAppointmentOutcomeRecords() {
        System.out.print("Enter Patient ID to view appointment outcome records: ");
        String patientID = Helper.readString();

        if (AppointmentOutcomeRecordRepository.patientOutcomeRecords.containsKey(patientID)) {
            for (AppointmentOutcomeRecord record : AppointmentOutcomeRecordRepository.patientOutcomeRecords.get(patientID)) {
                displayAppointmentOutcomeRecord(record);
            }
        } else {
            printWarning("No appointment outcome records found for patient ID: " + patientID);
        }

        // Print options for user navigation
        printChoice();
        handleUserChoice();
    }

    private void displayAppointmentOutcomeRecord(AppointmentOutcomeRecord record) {
        printSeparator();
        System.out.println("Appointment Outcome for Record ID: " + record.getAppointmentOutcomeRecordID());
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
            for (PrescribedMedication medication : prescription.getMedications()) {
                System.out.println("Medicine ID: " + medication.getMedicineID());
                System.out.println("Quantity: " + medication.getMedicineQuantity());
                System.out.println("Dosage: " + medication.getDosage());
                System.out.println("Period (days): " + medication.getPeriodDays());
                System.out.println("Prescription Status: " + medication.getPrescriptionStatus());
                System.out.println();
            }
        } else {
            System.out.println("No prescription details available for this appointment outcome record.");
        }
        printSeparator();
    }

    private void handleUserChoice() {
        int choice = getUserChoice(2);
        switch (choice) {
            case 1 -> viewAppointmentOutcomeRecords(); // View another record
            case 2 -> returnToPharmacistUI(); // Return to Pharmacist UI
            //case 2 -> exitApp(); // Return to main menu or exit
            default -> handleInvalidInput();
        }
    }

    /**
     * Returns to the Pharmacist UI menu.
     */
    private void returnToPharmacistUI() {
        Pharmacist pharmacist = (Pharmacist) HMSPersonnelController.getPersonnelByUID(
            AuthenticationController.cookie.getUid(), PersonnelFileType.PHARMACISTS);
        if (pharmacist != null) {
            PharmacistUI pharmacistUI = new PharmacistUI();
            pharmacistUI.start();
        } else {
            System.out.println("Error: Invalid pharmacist UID. Returning to main menu.");
            HMSMain.main(null); // Handle null pharmacist, returning to main menu
        }
    }

}