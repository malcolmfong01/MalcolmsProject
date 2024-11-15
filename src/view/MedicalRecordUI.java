/**
 * User interface for displaying a patient's medical record in a formatted view.
 * Allows the user to view detailed information about a patient's diagnoses,
 * prescriptions, and prescribed medications.
 */
package view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import controller.HMSPersonnelController;
import helper.Helper;
import model.MedicalRecord;
import model.Patient;
import model.Diagnosis;
import model.Prescription;
import model.PrescribedMedication;

public class MedicalRecordUI extends MainUI {

    /**
     * The medical record to be displayed.
     */
    private MedicalRecord medicalRecord;
    private Patient patient;

    /**
     * Constructs a MedicalRecordUI with the specified medical record.
     *
     * @param medicalRecord the medical record to be displayed
     */
    public MedicalRecordUI(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
        this.patient = HMSPersonnelController.getPatientById(medicalRecord.getPatientID());
    }

    /**
     * Displays the medical record in a formatted box structure.
     * Includes basic patient information, diagnoses, prescriptions, and medications.
     */
    public void displayMedicalRecordInBox() {
        String border = "+------------------------------------------+";
        System.out.println(border);
        System.out.printf("| %-38s |\n", "Medical Record");
        System.out.println(border);

        // Print basic information
        System.out.printf("| %-20s: %-20s |\n", "Doctor ID", medicalRecord.getDoctorID());
        System.out.printf("| %-20s: %-20s |\n", "Patient ID", medicalRecord.getPatientID());
        System.out.printf("| %-20s: %-20s |\n", "Patient Name", (patient != null ? patient.getFullName() : "Unknown"));
        System.out.printf("| %-20s: %-20s |\n", "Patient DOB", (patient != null ? patient.getDoB() : "Unknown"));
        System.out.printf("| %-20s: %-20s |\n", "Patient Gender", (patient != null ? patient.getGender() : "Unknown"));
        System.out.printf("| %-20s: %-20s |\n", "Blood Type", medicalRecord.getBloodType());
        System.out.println(border);

        // Diagnosis Section
        System.out.println("| Diagnoses:");
        if (medicalRecord.getDiagnosis() != null && !medicalRecord.getDiagnosis().isEmpty()) {
            Set<String> printedDiagnosisIDs = new HashSet<>();
            for (Diagnosis diagnosis : medicalRecord.getDiagnosis()) {
                String diagnosisID = diagnosis.getDiagnosisID();
                if (!printedDiagnosisIDs.contains(diagnosisID)) {
                    System.out.println(border);
                    System.out.printf("| %-20s: %-20s |\n", "Diagnosis ID", diagnosisID);
                    System.out.printf("| %-20s: %-20s |\n", "Description", diagnosis.getDiagnosisDescription());
                    printedDiagnosisIDs.add(diagnosisID); // Mark as printed
                } else {
                    continue;
                }

                Prescription prescription = diagnosis.getPrescription();
                if (prescription != null) {
                    System.out.printf("| %-20s: %-20s |\n", "Prescription Date", prescription.getPrescriptionDate());
                    System.out.println("| Medications:");

                    // Medication Section with Duplicate Check
                    if (prescription.getMedications() != null && !prescription.getMedications().isEmpty()) {
                        Set<String> printedMedicationKeys = new HashSet<>();
                        for (PrescribedMedication medication : prescription.getMedications()) {
                            // Create a unique key for each medication
                            String medicationKey = medication.getMedicineID() + "_" +
                                    medication.getMedicineQuantity() + "_" +
                                    medication.getDosage() + "_" +
                                    medication.getPeriodDays();
                            if (!printedMedicationKeys.contains(medicationKey)) {
                                printedMedicationKeys.add(medicationKey); // Mark as printed
                                System.out.printf("| %-20s: %-20s |\n", "Medicine ID", medication.getMedicineID());
                                System.out.printf("| %-20s: %-20s |\n", "Quantity", medication.getMedicineQuantity());
                                System.out.printf("| %-20s: %-20s |\n", "Dosage", medication.getDosage());
                                System.out.printf("| %-20s: %-20s |\n", "Period (days)", medication.getPeriodDays());
                                System.out.printf("| %-20s: %-20s |\n", "Status", medication.getPrescriptionStatus());
                                System.out.println(border);
                            }
                        }
                    } else {
                        System.out.println("| No prescribed medications found |");
                    }
                } else {
                    System.out.println("| No prescription found |");
                }
            }
        } else {
            System.out.println("| No diagnosis records found |");
        }
        System.out.println(border);
    }

    /**
     * Prints the available options for the MedicalRecordUI.
     */
    @Override
    protected void printChoice() {
        System.out.println("Options:");
        System.out.println("1. Back to Previous Menu");
        System.out.println("Enter your choice: ");
    }

    /**
     * Starts the MedicalRecordUI, displaying the medical record and allowing
     * the user to navigate back to the previous menu.
     */
    @Override
    public void start() {
        displayMedicalRecordInBox(); // Display the medical record

        int choice = 0;
        do {
            printChoice();
            choice = Helper.readInt("");
            switch (choice) {
                case 1:
                    System.out.println("Returning to previous menu...");
                    return; // Exits this UI and goes back to the previous one
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 1);
    }
}
