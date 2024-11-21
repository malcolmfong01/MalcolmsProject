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
import enums.Record;
import enums.RecordStatus;
import utility.DateTime;
import utility.Validator;
import model.Appointment;
import model.Doctor;
import repository.RecordsRepository;

import static utility.Validator.sc;

/**
 * AvailabilitySetter Boundary class represents the user interface for
 * a doctor setting their availability.
 */
public class AvailabilitySetterBoundary extends Boundary {
    /**
     * The doctor for whom appointment availability is being managed.
     */
	private final Doctor doctor;

    /**
     * Constructs an AvailabilitySetterBoundary with the specified doctor.
     *
     * @param doctor the doctor whose availability is being set
     */
    public AvailabilitySetterBoundary(Doctor doctor) {
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
        while (true) {
            printChoice();
            choice = Validator.readInt("");
            switch (choice) {
                case 1 -> setAvailabilityForAppointments();
                case 2 -> {
                    System.out.println("Returning to Doctor Dashboard...");
                    return; // Exit the loop and method
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
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

        boolean addavailability = true; // Flag to handle the first run of the loop

        while (true) {
            if (!addavailability) { // Skip the confirmation prompt on the first iteration
                System.out.flush(); // Clear any stray output
                sc.nextLine(); // Clear residual input from the stream

                boolean addMoreAppointments = Validator.promptConfirmation("add another available time slot");
                if (!addMoreAppointments) {
                    break; // Exit the loop if the user doesn't want to add more appointments
                }
            }
            addavailability = false; // Reset flag after the first iteration

            // Prompt for location
            String location = Validator.readString("Enter the location (e.g., 'Level 2 - Heart Clinic'): ");

            // Prompt for appointment date and time
            LocalDateTime appointmentDateTime = DateTime.pickDateTime("Enter the appointment date and time:");

            // Create appointment record
            Appointment appointment = new Appointment(
                    RecordsController.generateRecordID(Record.APPOINTMENT_RECORDS),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    RecordStatus.ACTIVE,
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
