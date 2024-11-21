/**
 * User interface for managing the outcome of appointments.
 * Allows a doctor to view incomplete appointments, select one, and record the
 * outcome, including consultation notes and type of service.
 */
package boundary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import controller.AppointmentController;
import controller.PrescribedMedicineController;
import controller.RecordsController;
import enums.*;
import enums.Record;
import utility.Validator;
import model.*;
import repository.*;

/**
 * Represents the Boundary for recording appointment outcome
 */
public class RecordOutcomeBoundary extends Boundary {
    /**
     * The doctor recording appointment outcomes.
     */
    private final Doctor doctor;

    /**
     * Constructs an RecordOutcomeBoundary for the specified doctor.
     *
     * @param doctor the doctor managing appointment outcomes
     */
    public RecordOutcomeBoundary(Doctor doctor) {
        this.doctor = doctor;
    }

    /**
     * Prints the menu options for the Appointment Outcome UI.
     */
    @Override
    protected void printChoice() {
        System.out.println("Appointment Outcome Menu:");
        System.out.println("1. Record Appointment Outcome");
        System.out.println("2. Back to Doctor Dashboard");
    }

    /**
     * Starts the appointment outcome UI, allowing the doctor to record
     * the outcome of an appointment or return to the Doctor Dashboard.
     */
    @Override
    public void start() {
        int choice;
        while (true) {
            printChoice();
            choice = Validator.readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> recordAppointmentOutcome();
                case 2 -> {
                    System.out.println("Returning to Doctor Dashboard...");
                    return; // Exit the loop and method
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    /**
     * Displays incomplete appointments for the doctor and allows them
     * to select one for recording the outcome.
     */
    public void recordAppointmentOutcome() {
        // prompt doctor to select appointment outcome record that is incomplete,
        // then only update service and then set to completed
        System.out.println("\n--- Incomplete Appointments for Dr. " + doctor.getFullName() + " ---");

        ArrayList<AppointmentOutcomeRecord> incompleteAppointments = new ArrayList<>();
        int index = 1;

        for (ArrayList<AppointmentOutcomeRecord> appointments : AppointmentOutcomeRecordRepository.patientOutcomeRecords
                .values()) {
            for (AppointmentOutcomeRecord appointment : appointments) {
                if (doctor.getUID().equals(appointment.getDoctorID())
                        && appointment.getAppointmentOutcomeStatus() == AppointmentOutcomeStatus.INCOMPLETED) {
                    incompleteAppointments.add(appointment);
                    System.out.printf("%d. Appointment on %s at %s with Patient ID: %s%n",
                            index,
                            appointment.getAppointmentTime().getDayOfWeek(),
                            appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                            appointment.getPatientID());
                    index++;
                }
            }
        }

        if (incompleteAppointments.isEmpty()) {
            System.out.println("No confirmed appointments found.");
            return;
        }

        // Prompt the user to select an appointment
        int selectedIndex = Validator.readInt("\nEnter the number of the appointment to record outcome: ");
        if (selectedIndex < 1 || selectedIndex > incompleteAppointments.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        // Process the selected appointment outcome
        AppointmentOutcomeRecord selectedAppointment = incompleteAppointments.get(selectedIndex - 1);
        processOutcome(selectedAppointment);
    }

    /**
     * Processes the selected appointment by allowing the doctor to enter
     * details such as type of service and consultation notes, then marks
     * the appointment as completed.
     *
     * @param appointment the AppointmentOutcomeRecord to process
     */
    private void processOutcome(AppointmentOutcomeRecord appointment) {

        String diagnosisDescription = Validator.readString("\n--- Enter Diagnosis for Patient (ID: " + appointment.getPatientID() + ") ---");
        String diagnosisID = AppointmentController.generateRecordID(Record.DIAGNOSIS_RECORDS);
        System.out.println("Diagnosis ID: " + diagnosisID);

        String medicine = Validator.readString("\n--- Enter Medicine ID for Patient (ID: " + appointment.getPatientID() + ")(Separate by ,: e.g M000,M001)---");
        // Split the string by commas
        String[] medicineIDs = medicine.split(",");

        ArrayList<PrescribedMedication> medications1 = new ArrayList<>();
        for (String medicineID : medicineIDs) {
            medicineID = medicineID.trim(); // Remove any leading or trailing spaces

            if (!medicineID.isEmpty()) {
                // Gather additional details for each prescribed medication
                int quantity = Validator.readInt("Enter quantity for medicine ID " + medicineID + ": ");
                int periodDays = Validator.readInt("Enter period (days) for medicine ID " + medicineID + ": ");
                String dosage = Validator.readString("Enter dosage for medicine ID " + medicineID + ": ");
                String prescribedmedicationID = PrescribedMedicineController.generateRecordID(Record.PRESCRIBED_RECORDS);
                // Create and add a new PrescribedMedication object to the list
                PrescribedMedication medication = new PrescribedMedication(prescribedmedicationID,diagnosisID, medicineID, quantity, periodDays, PrescriptionStatus.PENDING, dosage);
                medications1.add(medication);
                PrescribedMedicationRepository.addMedication(medication.getDiagnosisID(), medication);
                PrescribedMedicationRepository.saveAlltoCSV();

            }

        }


        // Create a Prescription object with the list of medications
        Prescription prescription1 = new Prescription(diagnosisID, LocalDateTime.now(), medications1);
        PrescriptionRepository.addPrescriptionRecord(prescription1);
        PrescriptionRepository.saveAlltoCSV();
        // Create a Diagnosis object that includes the prescription
        String MRecord;
        MedicalRecord medicalRecord = RecordsController.getMedicalRecordsByPatientID(appointment.getPatientID());

        if (medicalRecord != null) {
            MRecord = medicalRecord.getRecordID();
        } else {
            System.out.println("No medical record found for patient ID: " + appointment.getPatientID());
            System.out.println("Cannot proceed with diagnosis creation. Exiting...");
            return; // Exit the method if no medical record is found
        }
        Diagnosis diagnosis = new Diagnosis(
                appointment.getPatientID(), diagnosisID, appointment.getDoctorID(), MRecord,
                LocalDateTime.now(), null,
                diagnosisDescription, prescription1);
        //say something need controller
        DiagnosisRepository.addDiagnosis(diagnosisID, diagnosis);
        DiagnosisRepository.saveAlltoCSV();


        String typeOfService = Validator.readString("Enter the type of service: ");
        String consultationNotes = Validator.readString("Enter your consultation notes: ");
        appointment.setDiagnosisID(diagnosisID);
        appointment.setPrescription(prescription1);
        appointment.getPrescription().setMedications(medications1);
        appointment.setTypeOfService(typeOfService);
        appointment.setConsultationNotes(consultationNotes);
        appointment.setAppointmentOutcomeStatus(AppointmentOutcomeStatus.COMPLETED);

        // Null check to avoid potential NPE in the following loop
        if (appointment.getUID() != null) {
            for (Appointment app : RecordsRepository.APPOINTMENT_RECORDS.values()) {
                if (app.getAppointmentOutcomeRecordID().equals(appointment.getUID())) {
                    app.setAppointmentStatus(AppointmentStatus.COMPLETED);
                }
            }
        } else {
            System.out.println("Appointment outcome record ID is null. Cannot update appointment status.");
        }
        RecordsRepository.saveAllRecordFiles();
        AppointmentOutcomeRecordRepository.saveAppointmentOutcomeRecordRepository();
        System.out.println("Appointment outcome recorded and saved successfully.");
        // Enter amount to be paid
        int paymentAmount = Validator.readInt("Enter Payment Amount: ");
        String billingID = RecordsController.generateRecordID(Record.PAYMENT_RECORDS);
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime updateDate = LocalDateTime.now();
        PaymentStatus paymentStatus =  PaymentStatus.OUTSTANDING;
        // Generate the next medicine ID
        PaymentRecord record;
        System.out.println(appointment.getPatientID());
        record = new PaymentRecord(billingID,createdDate,updateDate, RecordStatus.ACTIVE,paymentStatus,appointment.getPatientID(),paymentAmount);
        RecordsRepository.PAYMENT_RECORDS.put(appointment.getPatientID(), record);
        RecordsRepository.saveAllRecordFiles();
    }

}
