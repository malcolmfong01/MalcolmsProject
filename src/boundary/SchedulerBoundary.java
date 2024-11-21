package boundary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import controller.DoctorController;
import enums.AppointmentStatus;
import repository.UserRepository;
import utility.Validator;
import model.Appointment;
import model.Doctor;
import model.Patient;
import repository.RecordsRepository;
/**
 * The SchedulerBoundary class provides an interface for patients to schedule
 * appointments with doctors, offering functionality to input availability and
 * display scheduled appointments.
 */
public class SchedulerBoundary extends Boundary {

	private final Patient patient;
    /**
     * Constructor to initialize the SchedulerBoundary with the given patient.
     *
     * @param patient The patient using this UI.
     */
	public SchedulerBoundary(Patient patient) {
		this.patient = patient;
	}
    /**
     * Prints the menu options available in the Schedule Appointment UI.
     */
	@Override
	protected void printChoice() {
		System.out.println("Schedule Appointment Menu:");
		System.out.println("1. Schedule Availability for Appointments");
		System.out.println("2. Back to Patient Dashboard");
	}
    /**
     * Starts the Schedule Appointment UI interaction loop,
     * allowing the user to make a choice.
     */
	@Override
	public void start() {
		int choice;
		do {
			printChoice();
			choice = Validator.readInt("Enter your choice: ");
			switch (choice) {
			case 1 -> scheduleAppointment();
			case 2 -> System.out.println("Returning to Patient Dashboard...");
			default -> System.out.println("Invalid choice! Please try again.");
			}
		} while (choice != 2);
	}
    /**
     * Schedules an appointment for the patient by allowing input for doctor ID,
     * date, and time. If an appointment slot is available, it is reserved and
     * updated accordingly.
     */
	public void scheduleAppointment() {
		System.out.println("Enter your availability. Type 'no' when finished.");

		while (true) {
			// Check if the user wants to continue rescheduling
			boolean continueScheduling = Validator.promptConfirmation("schedule appointment");

			if (!continueScheduling) {
				break;  // Exit the loop if the user does not want to continue
			}

			System.out.println("Enter the Doctor ID you want to schedule an appointment with:");
			String doctorId = Validator.readID("Doctor", "D\\d{3}");
			System.out.println("Validated Doctor ID: " + doctorId);
			Doctor doctor = UserRepository.DOCTORS.get(doctorId);
			if (doctor == null) {
				System.out.println("Doctor not found. Please enter a valid Doctor ID.");
				continue;
			}

			String dateInput = Validator.readString("Enter the date for the appointment (yyyy-MM-dd):");
			String timeInput = Validator.readString("Enter the time for the appointment (HH:mm):");

			LocalDateTime appointmentTime;

			try {
				appointmentTime = LocalDateTime.parse(dateInput + " " + timeInput,
						DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

			} catch (Exception e) {
				System.out.println("Invalid date and time format. Please try again.");
				continue;
			}

			Appointment existingAppointment = null;
			for (Appointment appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
				if (appointment.getDoctorID().equals(doctorId)
						&& appointment.getAppointmentTime().equals(appointmentTime)
						&& appointment.getAppointmentStatus() == AppointmentStatus.AVAILABLE) {
					existingAppointment = appointment;
					break;
				}
			}

			if (existingAppointment != null) {
				existingAppointment.setAppointmentStatus(AppointmentStatus.PENDING);
				existingAppointment.setPatientID(patient.getUID());
				displayScheduledSummary(existingAppointment);
				RecordsRepository.saveAllRecordFiles();
			} else {
				System.out.println("No available appointment found for the selected time. Please try again.");
			}

		}
	}
    /**
     * Displays a summary of the scheduled availability.
     *
     * @param existingAppointment The appointment record to display.
     */
	private void displayScheduledSummary(Appointment existingAppointment) {
		String doctorName = DoctorController.getDoctorNameById(existingAppointment.getDoctorID());
		System.out.println("\n--- Scheduled Availability Summary ---");
		System.out.println("Doctor ID        : " + existingAppointment.getDoctorID());
		System.out.println("Doctor           : " + doctorName);
		System.out.println("Day              : " + existingAppointment.getAppointmentTime().getDayOfWeek());
		System.out.println("Date             : "
				+ existingAppointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		System.out.println("Time             : "
				+ existingAppointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("HH:mm")));
		System.out.println("Location         : " + existingAppointment.getLocation());
		System.out.println("---------------------------------------");
	}

}
