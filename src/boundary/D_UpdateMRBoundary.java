package boundary;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import controller.PrescribedMedicineController;
import enums.AppointmentOutcomeStatus;
import enums.AppointmentStatus;
import enums.PrescriptionStatus;
import enums.RecordFileType;
import model.*;
import repository.*;
import controller.AppointmentController;
import controller.MedicineController;

/**
 * This class provides a UI to update the medical records of a patient during a
 * doctor's appointment.
 * It allows adding diagnoses, treatment plans, and prescriptions.
 */
public class D_UpdateMRBoundary {
    private final Doctor doctor;
    private final MedicalRecord medicalRecord;
    private final Appointment currentAppointment;
    private final Scanner sc;
    private final DoctorBoundary doctorBoundary;

    /**
     * Constructor to initialize the UI with the doctor, medical record, and the
     * current appointment.
     *
     * @param doctor             The doctor updating the medical record.
     * @param medicalRecord      The medical record to be updated.
     * @param currentAppointment The current appointment record being worked
     *                           on.
     */
    // Constructor to initialize with the doctor and medical record to be updated
    public D_UpdateMRBoundary(Doctor doctor, MedicalRecord medicalRecord,
                              Appointment currentAppointment, DoctorBoundary doctorBoundary) {
        this.doctor = doctor;
        this.medicalRecord = medicalRecord;
        this.currentAppointment = currentAppointment;
        this.sc = new Scanner(System.in);
        this.doctorBoundary = doctorBoundary;
    }

    /**
     * Starts the UI, displaying the current appointment details and guiding the
     * doctor
     * through adding new diagnoses, treatment plans, and prescriptions.
     */
    public void start() {
        // displaying currentAppointment that the doctor is working on

        // currentAppointment =
        // AppointmentController.retrieveEarliestConfirmedAppointmentRecord(doctor.getUID(),
        // medicalRecord.getPatientID());

        System.out.println("\n-- Current Appointment Details --");
        System.out.println("Patient ID: " + medicalRecord.getPatientID());
        System.out.println("Appointment DateTime: " +
                currentAppointment.getAppointmentTime());
        System.out.println("Doctor: " + doctor.getFullName());
        System.out.println("Status: " +
                currentAppointment.getAppointmentStatus().toString());
        System.out.println("=========================================");

        System.out.println("Existing Diagnosis");
        ArrayList<Diagnosis> diagnosis = DiagnosisRepository.getDiagnosesByPatientID(medicalRecord.getPatientID());
        if (diagnosis.isEmpty()) {
            System.out.println("No existing diagnosis found for this patient.");
            return; // Exit if no diagnosis are available
        }
        for (int i = 0; i < diagnosis.size(); i++) {
            System.out.println((i + 1) + ". Diagnosis ID: " + diagnosis.get(i).getDiagnosisID() +
                    ", Description: " + diagnosis.get(i).getDiagnosisDescription());
        }
        System.out.print("Select a diagnosis by number (Key in other integers to return): ");
        int diagnosisChoice = sc.nextInt();
        sc.nextLine();
        if (diagnosisChoice < 1 || diagnosisChoice > diagnosis.size()) {
            System.out.println("Invalid choice.");
            doctorBoundary.showDoctorDashboard(); // Exit if the choice is invalid
            return;
        }
        // Get the selected diagnosis
        Diagnosis selectedDiagnosis = diagnosis.get(diagnosisChoice - 1);
        System.out.println("Selected Diagnosis ID: " + selectedDiagnosis.getDiagnosisID());

        // Prompt to add treatment plan and prescription
        System.out.println("\nChoose an option to add for the new diagnosis:");
        System.out.println("1. Add Treatment Plan");
        System.out.println("2. Add Prescription");
        System.out.println("3. Add Both Treatment Plan and Prescription");
        System.out.print("Enter your choice: ");

        int updateChoice = sc.nextInt();
        sc.nextLine(); // Consume newline left-over
        switch (updateChoice) {
            case 1 -> addTreatmentPlan(selectedDiagnosis);
            case 2 -> addPrescriptions(selectedDiagnosis);
            case 3 -> {
                addTreatmentPlan(selectedDiagnosis);
                addPrescriptions(selectedDiagnosis);
            }
            default -> System.out.println("Invalid choice.");
        }

        // Save updated medical record back to repository
        RecordsRepository.MEDICAL_RECORDS.put(medicalRecord.getRecordID(), medicalRecord);
        currentAppointment.setAppointmentStatus(AppointmentStatus.COMPLETED);
        generateAppointmentOutcome(selectedDiagnosis.getPrescription(), selectedDiagnosis);
        RecordsRepository.saveAllRecordFiles();

    }

    /**
     * Generates an appointment outcome record when the appointment status is
     * completed.
     *
     * @param prescription The prescription associated with the appointment.
     * @param diagnosis    The diagnosis associated with the appointment.
     */
    private void generateAppointmentOutcome(Prescription prescription, Diagnosis diagnosis) {
        String UID = AppointmentController.generateRecordID(RecordFileType.APPOINTMENT_OUTCOME_RECORDS);
        if (currentAppointment.getAppointmentStatus() == AppointmentStatus.COMPLETED) {
            AppointmentOutcomeRecord outcomeRecord = new AppointmentOutcomeRecord(
                    UID,
                    currentAppointment.getPatientID(),
                    currentAppointment.getDoctorID(),
                    diagnosis.getDiagnosisID(),
                    LocalDateTime.now(),
                    prescription,
                    null, // type of service
                    null, // consultation notes
                    AppointmentOutcomeStatus.INCOMPLETED);

            AppointmentOutcomeRecordRepository.addAppointmentOutcomeRecord(currentAppointment.getPatientID(),
                    outcomeRecord);
            AppointmentOutcomeRecordRepository.saveAppointmentOutcomeRecordRepository();

        }

    }

    /**
     * Adds a new diagnosis for the given patient.
     *
     * @param patientId            The ID of the patient.
     * @param diagnosisDescription The description of the diagnosis.
     * @return The newly created Diagnosis object.
     */
    private Diagnosis addNewDiagnosis(String patientId, String diagnosisDescription) {
        String diagnosisID = AppointmentController.generateRecordID(RecordFileType.DIAGNOSIS_RECORDS);
        Diagnosis diagnosis = new Diagnosis(patientId, diagnosisID, doctor.getUID(),
                medicalRecord.getRecordID(), LocalDateTime.now(), null,
                diagnosisDescription, null);

        DiagnosisRepository.addDiagnosis(patientId, diagnosis); // Add to the Diagnosis Repository
        medicalRecord.addDiagnosis(diagnosis); // Add the diagnosis to the current medical record
        DiagnosisRepository.saveAlltoCSV();
        return diagnosis;
    }

    /**
     * Adds a treatment plan for the given diagnosis.
     *
     * @param diagnosis The diagnosis to which the treatment plan will be added.
     */
    private void addTreatmentPlan(Diagnosis diagnosis) {
        System.out.println("Enter Treatment Description:");
        String treatmentDescription = sc.nextLine();
        Treatment treatmentPlan = new Treatment(diagnosis.getDiagnosisID(), LocalDateTime.now(),
                treatmentDescription);
        diagnosis.setTreatmentPlans(treatmentPlan);
        TreatmentRepository.diagnosisToTreatmentPlansMap.put(diagnosis.getDiagnosisID(), treatmentPlan);
        TreatmentRepository.saveAlltoCSV();
        // RecordsRepository.saveAllRecordFiles(); // Save changes
        System.out.println("Treatment plan added successfully for Diagnosis ID: " + diagnosis.getDiagnosisID());
    }

    /**
     * Adds prescriptions for the given diagnosis, allowing the doctor to specify
     * multiple medications.
     *
     * @param newDiagnosis The diagnosis for which prescriptions will be added.
     */
    private void addPrescriptions(Diagnosis newDiagnosis) {
        boolean addMore = true;
        while (addMore) {
            System.out.println("\n--- Add Prescribed Medication ---");
            System.out.print("Enter Medication Name: ");
            String medicationName = sc.nextLine();
            Medicine medicine = MedicineController.getMedicineByName(medicationName);

            if (medicine == null) {
                System.out.println("\n--- Invalid Prescribed Medication ---");
                continue;
            }

            System.out.print("Enter Quantity: ");
            int quantity = sc.nextInt();
            System.out.print("Enter Period (Days): ");
            int periodDays = sc.nextInt();
            sc.nextLine(); // Consume newline left-over
            System.out.print("Enter Dosage: ");
            String dosage = sc.nextLine();

            // Add the prescribed medication
            String medicineID = medicine.getMedicineID();
            String prescribedmedicationID = PrescribedMedicineController
                    .generateRecordID(RecordFileType.PRESCRIBED_RECORDS);
            PrescribedMedication prescribedMedication = new PrescribedMedication(prescribedmedicationID,
                    newDiagnosis.getDiagnosisID(),
                    medicineID,
                    quantity, periodDays, PrescriptionStatus.PENDING, dosage);
            Prescription prescription = addPrescription(newDiagnosis, prescribedMedication);
            PrescriptionRepository.PRESCRIPTION_MAP.put(newDiagnosis.getDiagnosisID(), prescription);

            System.out.println("Medication prescribed successfully for Diagnosis ID: " + newDiagnosis.getDiagnosisID());

            // Prompt to add more medication or finish
            System.out.print("Would you like to add another prescribed medication? (yes/no): ");
            addMore = sc.nextLine().trim().equalsIgnoreCase("yes");
        }
        PrescriptionRepository.saveAlltoCSV();
        // RecordsRepository.saveAllRecordFiles();

        System.out.println("Finished adding prescribed medications for Diagnosis ID: " + newDiagnosis.getDiagnosisID());
    }

    /**
     * Adds a prescription for the given diagnosis and prescribed medication.
     *
     * @param diagnosis            The diagnosis for which the prescription is
     *                             created.
     * @param prescribedMedication The prescribed medication details.
     * @return The created Prescription object.
     */
    private Prescription addPrescription(Diagnosis diagnosis, PrescribedMedication prescribedMedication) {
        Prescription prescription = diagnosis.getPrescription();
        if (prescription == null) {
            prescription = new Prescription(diagnosis.getDiagnosisID(), LocalDateTime.now(), new ArrayList<>());
            diagnosis.setPrescription(prescription);
        }

        prescription.addPrescribedMedication(prescribedMedication);
        PrescribedMedicationRepository.addMedication(diagnosis.getDiagnosisID(), prescribedMedication);
        PrescribedMedicationRepository.saveAlltoCSV();
        // RecordsRepository.saveAllRecordFiles();
        return prescription;
    }
}
