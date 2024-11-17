/**
 * User interface for managing the outcome of appointments.
 * Allows a doctor to view incomplete appointments, select one, and record the
 * outcome, including consultation notes and type of service.
 */
package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import controller.AppointmentController;
import controller.PrescribedMedicineController;
import controller.RecordsController;
import enums.AppointmentOutcomeStatus;
import enums.RecordFileType;
import model.*;
import repository.*;
import controller.MedicineController;
import enums.AppointmentStatus;
import enums.PrescriptionStatus;
import helper.Helper;

public class AppointmentRecordOutcomeUI extends MainUI {
    /**
     * The doctor recording appointment outcomes.
     */
    private Doctor doctor;

    /**
     * Constructs an AppointmentRecordOutcomeUI for the specified doctor.
     *
     * @param doctor the doctor managing appointment outcomes
     */
    public AppointmentRecordOutcomeUI(Doctor doctor) {
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
        System.out.print("Enter your choice: ");
    }

    /**
     * Starts the appointment outcome UI, allowing the doctor to record
     * the outcome of an appointment or return to the Doctor Dashboard.
     */
    @Override
    public void start() {
        int choice = 0;
        do {
            printChoice();
            choice = Helper.readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> recordAppointmentOutcome();
                case 2 -> System.out.println("Returning to Doctor Dashboard...");
                default -> System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 2);
    }

    /**
     * Displays incomplete appointments for the doctor and allows them
     * to select one for recording the outcome.
     */
    public void recordAppointmentOutcome() {
        // prompt doctor to select appointment outcome record that is incompleted,
        // then only update service etc, then set to completed
        System.out.println("\n--- Incompleted Appointments for Dr. " + doctor.getFullName() + " ---");

        ArrayList<AppointmentOutcomeRecord> incompletedAppointments = new ArrayList<>();
        int index = 1;

        for (ArrayList<AppointmentOutcomeRecord> appointments : AppointmentOutcomeRecordRepository.patientOutcomeRecords
                .values()) {
            for (AppointmentOutcomeRecord appointment : appointments) {
                if (doctor.getUID().equals(appointment.getDoctorID())
                        && appointment.getAppointmentOutcomeStatus() == AppointmentOutcomeStatus.INCOMPLETED) {
                    incompletedAppointments.add(appointment);
                    System.out.printf("%d. Appointment on %s at %s with Patient ID: %s%n",
                            index,
                            appointment.getAppointmentTime().getDayOfWeek(),
                            appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                            appointment.getPatientID());
                    index++;
                }
            }
        }

        if (incompletedAppointments.isEmpty()) {
            System.out.println("No confirmed appointments found.");
            return;
        }

        // Prompt the user to select an appointment
        int selectedIndex = Helper.readInt("\nEnter the number of the appointment to record outcome: ");
        if (selectedIndex < 1 || selectedIndex > incompletedAppointments.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        // Process the selected appointment outcome
        AppointmentOutcomeRecord selectedAppointment = incompletedAppointments.get(selectedIndex - 1);
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

        String diagnosisDescription = Helper.readString("\n--- Enter Diagnosis for Patient (ID: " + appointment.getPatientID() + ") ---");
        String diagnosisID = AppointmentController.generateRecordID(RecordFileType.DIAGNOSIS_RECORDS);

        System.out.println("Diagnosis ID: " + diagnosisID);
        appointment.setDiagnosisID(diagnosisID);

        String medicine = Helper.readString("\n--- Enter Medicine ID for Patient (ID: " + appointment.getPatientID() + ")(Separate by ,)---");
        // Split the string by commas
        String[] medicineIDs = medicine.split(",");

        ArrayList<PrescribedMedication> medications1 = new ArrayList<>();
        for (String medicineID : medicineIDs) {
            medicineID = medicineID.trim(); // Remove any leading or trailing spaces

            if (!medicineID.isEmpty()) {
                // Gather additional details for each prescribed medication
                int quantity = Helper.readInt("Enter quantity for medicine ID " + medicineID + ": ");
                int periodDays = Helper.readInt("Enter period (days) for medicine ID " + medicineID + ": ");
                String dosage = Helper.readString("Enter dosage for medicine ID " + medicineID + ": ");
                String prescribedmedicationID = PrescribedMedicineController.generateRecordID(RecordFileType.PRESCRIBED_RECORDS);
                // Create and add a new PrescribedMedication object to the list
                PrescribedMedication medication = new PrescribedMedication(prescribedmedicationID,diagnosisID, medicineID, quantity, periodDays, PrescriptionStatus.PENDING, dosage);
                medications1.add(medication);
                PrescribedMedicationRepository.addMedication(medication.getPrescribedMedID(), medication);
                PrescribedMedicationRepository.saveAlltoCSV();

            }

        }

        // Create a Prescription object with the list of medications
        Prescription prescription1 = new Prescription(diagnosisID, LocalDateTime.now(), medications1);
        PrescriptionRepository.addPrescriptionRecord(prescription1);
        PrescriptionRepository.saveAlltoCSV();
        // Create a Diagnosis object that includes the prescription
        String MRecord = RecordsController.getMedicalRecordsByPatientID(appointment.getPatientID()).getRecordID();

        Diagnosis diagnosis = new Diagnosis(
                appointment.getPatientID(), diagnosisID, appointment.getDoctorID(), MRecord,
                LocalDateTime.now(), null,
                diagnosisDescription, prescription1);
        //say something need controller
        DiagnosisRepository.addDiagnosis(diagnosisID, diagnosis);
        DiagnosisRepository.saveAlltoCSV();


        String typeOfService = Helper.readString("Enter the type of service: ");
        String consultationNotes = Helper.readString("Enter your consultation notes: ");
        appointment.setPrescription(prescription1);
        appointment.setTypeOfService(typeOfService);
        appointment.setConsultationNotes(consultationNotes);
        appointment.setAppointmentOutcomeStatus(AppointmentOutcomeStatus.COMPLETED);

        for (AppointmentRecord app : RecordsRepository.APPOINTMENT_RECORDS.values()) {

            if (app.getAppointmentOutcomeRecordID().equals(appointment.getUID())){
                app.setAppointmentStatus(AppointmentStatus.COMPLETED);

            }}
        RecordsRepository.saveAllRecordFiles();
        AppointmentOutcomeRecordRepository.saveAppointmentOutcomeRecordRepository();
        System.out.println("Appointment outcome recorded and saved successfully.");
    }

}
