package controller;

import model.AppointmentRecord;
import repository.*;

import java.time.format.DateTimeFormatter;

public class dummytestaor {
    public static void listAllAppointments() {

        System.out.println("\n--- All Appointment Records ---");

        boolean found = false;

        // Iterate through all appointment records
        for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {


            // Output appointment details (Doctor, Appointment Time, Location, Status, etc.)
            System.out.println("Appointment ID: " + appointment.getRecordID());
            System.out.println("Patient ID: " + appointment.getPatientID());
            System.out.println("Doctor ID: " + appointment.getDoctorID() );
            System.out.println("Appointment Time: " + appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            System.out.println("Location: " + appointment.getLocation());
            System.out.println("Status: " + appointment.getAppointmentStatus());


            // Check if there's an outcome record for this appointment
            //AppointmentOutcomeRecord outcome = AppointmentController.getAppointmentOutcomeByDoctorAndPatient(appointment.getDoctorID(), appointment.getPatientID(), appointment.getAppointmentTime());
            // AppointmentOutcomeRecord outcome = appointment.getAppointmentOutcomeRecord();
            //if (outcome != null) {
            System.out.println("Outcome Record ID: " + appointment.getAppointmentOutcomeRecord().getAppointmentOutcomeRecordID());
            System.out.println(appointment.getAppointmentOutcomeRecord().getPrescription()==null);
            //System.out.println("Outcome: " + outcome.getDiagnosisID());
            //System.out.println("Outcome Date: " + outcome.getOutcomeDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            //} else {
            // System.out.println("No Outcome Record available.");
            // }

            System.out.println("---------------------------------------");
            found = true;
        }

        if (!found) {
            System.out.println("No appointments found.");
        }

        System.out.println("---------------------------------------");
    }
    public static void main(String[] args) {
        Repository.loadRepository(new PersonnelRepository());
        Repository.loadRepository(new PrescribedMedicationRepository());
        Repository.loadRepository(new TreatmentPlansRepository());
        Repository.loadRepository(new PrescriptionRepository());
        Repository.loadRepository(new DiagnosisRepository());
        Repository.loadRepository(new AppointmentOutcomeRecordRepository());
        Repository.loadRepository(new RecordsRepository());
        Repository.loadRepository(new MedicineRepository());






        listAllAppointments();
    }
}
