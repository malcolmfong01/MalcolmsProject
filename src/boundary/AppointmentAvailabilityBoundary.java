/**
 * User interface for managing a doctor's appointment availability.
 * Allows a doctor to set available appointment time slots, specify
 * appointment locations, and view a summary of their availability.
 */
package boundary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import controller.RecordsController;
import enums.AppointmentStatus;
import enums.RecordFileType;
import utility.DateTimePicker;
import utility.Validator;
import model.Appointment;
import model.Doctor;
import enums.RecordStatusType;
import repository.RecordsRepository;

public class AppointmentAvailabilityBoundary extends Boundary {
    /**
     * The doctor for whom appointment availability is being managed.
     */
	private Doctor doctor;

    /**
     * Constructs an AppointmentAvailabilityBoundary with the specified doctor.
     *
     * @param doctor the doctor whose availability is being set
     */
    public AppointmentAvailabilityBoundary(Doctor doctor) {
        this.doctor = doctor;
    }

    /**
     * Prints the menu options for the Appointment Availability UI.
     */
    @Override
    protected void printChoice() {
        System.out.println("Appointment Availability Menu:");
        System.out.println("1. Set Availability for Appointments");
        System.out.println("2. Back to Doctor Dashboard");
        System.out.print("Enter your choice: ");
    }

    /**
     * Starts the appointment availability UI, allowing the doctor to set
     * available time slots or return to the Doctor Dashboard.
     */
    @Override
    public void start() {
        int choice;
        do {
            printChoice();
            choice = Validator.readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> setAvailabilityForAppointments();
                case 2 -> System.out.println("Returning to Doctor Dashboard...");
                default -> System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 2);
    }

    /**
     * Allows the doctor to set available appointment time slots by entering
     * details such as location and appointment date and time.
     * Appointments are saved and a summary of availability is displayed.
     */
    public void setAvailabilityForAppointments() {
        List<Appointment> availableAppointments = new ArrayList<>();
        AppointmentStatus status = AppointmentStatus.AVAILABLE;

        System.out.println("Enter your availability. Type 'done' when finished.");

        boolean addMoreAppointments = true;

        while (addMoreAppointments) {
            // Ask user if they want to add another available time slot
            addMoreAppointments = Validator.promptConfirmation("add another available time slot");

            if (!addMoreAppointments) {
                break; // Exit the loop if the user doesn't want to add more appointments
            }

            // Prompt for location
            String location = Validator.readString("Enter the location (e.g., 'Level 2 - Heart Clinic'): ");

            LocalDateTime appointmentDateTime = DateTimePicker.pickDateTime("Enter the appointment date and time:");

            // Create appointment record
            Appointment appointment = new Appointment(
                    RecordsController.generateRecordID(RecordFileType.APPOINTMENT_RECORDS),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    RecordStatusType.ACTIVE,
                    null,
                    null,
                    doctor.getUID(),
                    appointmentDateTime,
                    location,
                    status,
                    null
            );

            // Add and save the appointment
            availableAppointments.add(appointment);
            RecordsRepository.APPOINTMENT_RECORDS.put(appointment.getRecordID(), appointment);

            System.out.printf("Created and saved appointment for %s at %s\n",
                    appointmentDateTime.toLocalDate(), appointmentDateTime.toLocalTime());
        }

        // Display the summary of all available appointments
        displayAvailabilitySummary(availableAppointments);

        // Save all appointment records
        RecordsRepository.saveAllRecordFiles();
    }

    /**
     * Displays a summary of the available appointments set by the doctor,
     * showing details such as date, time, location, and record IDs.
     *
     * @param availableAppointments the list of available appointments
     */
    private void displayAvailabilitySummary(List<Appointment> availableAppointments) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("\n--- Appointment Availability Summary ---");

        for (Appointment appointment : availableAppointments) {
            if(appointment.getAppointmentStatus()==AppointmentStatus.AVAILABLE) {
                System.out.printf("Date: %s, Time: %s, Location: %s, Record ID: %s, Doctor ID: %s\n",
                        appointment.getAppointmentTime().toLocalDate(),
                        appointment.getAppointmentTime().format(dateTimeFormatter),
                        appointment.getLocation(),
                        appointment.getRecordID(),
                        appointment.getDoctorID());
            }
        }

        System.out.println("---------------------------------------");
    }
}