package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import enums.AppointmentOutcomeStatus;
import model.*;
import repository.*;
import controller.MedicineController;
import enums.AppointmentStatus;
import enums.PrescriptionStatus;
import helper.Helper;

public class AppointmentRecordOutcomeUI extends MainUI {
    private Doctor doctor;

    public AppointmentRecordOutcomeUI(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    protected void printChoice() {
        System.out.println("Appointment Outcome Menu:");
        System.out.println("1. Record Appointment Outcome");
        System.out.println("2. Back to Doctor Dashboard");
    }

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

    public void recordAppointmentOutcome() {
        // prompt doctor to select appointment outcome record that is not completed,
        // then only update service etc, then set to completed
        System.out.println("\n--- Confirmed Appointments for Dr. " + doctor.getFullName() + " ---");

        ArrayList<AppointmentRecord> confirmedAppointments = new ArrayList<>();
        int index = 1;

        for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
            if (doctor.getUID().equals(appointment.getDoctorID())
                    && appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
                confirmedAppointments.add(appointment);
                System.out.printf("%d. Appointment on %s at %s with Patient ID: %s%n",
                        index,
                        appointment.getAppointmentTime().getDayOfWeek(),
                        appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        appointment.getPatientID());
                index++;
            }
        }

        if (confirmedAppointments.isEmpty()) {
            System.out.println("No confirmed appointments found.");
            return;
        }

        int selectedIndex = Helper.readInt("\nEnter the number of the appointment to record outcome: ");
        if (selectedIndex < 1 || selectedIndex > confirmedAppointments.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        AppointmentRecord selectedAppointment = confirmedAppointments.get(selectedIndex - 1);
        processOutcome(selectedAppointment);
    }

    private void processOutcome(AppointmentRecord appointment) {
        System.out.println("\n--- Select Diagnosis for Patient (ID: " + appointment.getPatientID() + ") ---");
        
        //FIXME: The DiagnosisRepos SHOULD NOT handle this function getDiagnosesByPatientID!
        ArrayList<Diagnosis> diagnoses = DiagnosisRepository.getDiagnosesByPatientID(appointment.getPatientID());
        if (diagnoses.isEmpty()) {
            System.out.println("No diagnoses found for this patient.");
            return;
        }

        for (int i = 0; i < diagnoses.size(); i++) {
            Diagnosis diagnosis = diagnoses.get(i);
            System.out.printf("%d. Diagnosis ID: %s, Condition: %s%n", i + 1, diagnosis.getDiagnosisID(),
                    diagnosis.getDiagnosisDescription());
        }

        int diagnosisIndex = Helper.readInt("\nEnter the number of the diagnosis to use: ");
        if (diagnosisIndex < 1 || diagnosisIndex > diagnoses.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Diagnosis selectedDiagnosis = diagnoses.get(diagnosisIndex - 1);
        String diagnosisID = selectedDiagnosis.getDiagnosisID();

        if (!Helper.promptConfirmation("record the outcome")) {
            System.out.println("No changes made.");
            return;
        }

        appointment.setAppointmentStatus(AppointmentStatus.COMPLETED);

        String outcomeRecordID = appointment.getRecordID();
        ArrayList<PrescribedMedication> medications = new ArrayList<>();
        Prescription prescription = new Prescription(diagnosisID, LocalDateTime.now(), medications);
        PrescriptionRepository.addPrescriptionRecord(prescription);

        if (Helper.promptConfirmation("prescribe medication")) {
            while (true) {
                String medicationName = Helper.readString("Enter medication name: ");
                Medicine medicine = MedicineController.getMedicineByName(medicationName);
                if (medicine == null) {
                    System.out.println("Invalid medication name. Please try again.");
                    continue;
                }

                int quantity = Helper.readInt("Enter quantity: ");
                int periodDays = Helper.readInt("Enter period (days): ");
                String dosage = Helper.readString("Enter dosage: ");

                PrescribedMedication medication = new PrescribedMedication(diagnosisID, medicine.getMedicineID(),
                        quantity, periodDays, PrescriptionStatus.PENDING, dosage);
                medications.add(medication);
                PrescribedMedicationRepository.addMedication(diagnosisID, medication);

                if (!Helper.promptConfirmation("add another prescribed medication")) {
                    break;
                }
            }
        }

        String typeOfService = Helper.readString("Enter the type of service: ");
        String consultationNotes = Helper.readString("Enter your consultation notes: ");

        AppointmentOutcomeRecord outcomeRecord = new AppointmentOutcomeRecord(
                appointment.getPatientID(),
                doctor.getUID(),
                diagnosisID,
                outcomeRecordID,
                LocalDateTime.now(),
                prescription,
                typeOfService,
                consultationNotes,
                AppointmentOutcomeStatus.COMPLETED);

        AppointmentOutcomeRecordRepository.addAppointmentOutcomeRecord(appointment.getPatientID(), outcomeRecord);
        appointment.setAppointmentOutcomeRecordID(outcomeRecordID);

        // Save changes to the repository
        RecordsRepository.saveAllRecordFiles();
        AppointmentOutcomeRecordRepository.saveAppointmentOutcomeRecordRepository();
        DiagnosisRepository.saveAlltoCSV();
        TreatmentPlansRepository.saveAlltoCSV();
        PrescribedMedicationRepository.saveAlltoCSV();

        System.out.println("Appointment outcome recorded and saved successfully.");
    }


}
