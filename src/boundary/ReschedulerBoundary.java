package boundary;

import java.time.format.DateTimeFormatter;
import java.util.List;

import controller.AppointmentController;
import controller.DoctorController;
import enums.AppointmentStatus;
import utility.Validator;
import model.Appointment;
import model.Patient;
import repository.RecordsRepository;
/**
 * The ReschedulerBoundary class provides functionality to reschedule
 * appointments for a patient. It allows users to view confirmed appointments
 * and select available slots for rescheduling.
 */
public class ReschedulerBoundary extends Boundary {

	private final Patient patient;
    /**
     * Constructs a ReschedulerBoundary instance with the given patient.
     *
     * @param patient The patient using this UI to reschedule appointments.
     */
	public ReschedulerBoundary(Patient patient) {
		this.patient = patient;
	}
	 /**
     * Prints the menu for rescheduling an appointment
     */
	@Override
	protected void printChoice() {
		System.out.println("Reschedule Appointment Menu:");
		System.out.println("1. Reschedule Availability for Appointments");
		System.out.println("2. Back to Patient Dashboard");
	}

	/**
     * Starts the rescheduling interface, allowing the user to make a selection
     * from the menu options.
     */

	   @Override
	   public void start() {
		   int choice; // Initialize choice to ensure the loop runs at least once
		   while (true) {
			   printChoice();
			   choice = Validator.readInt("Enter your choice: ");
			   switch (choice) {
				   case 1 -> rescheduleAppointment();
				   case 2 -> {
					   System.out.println("Returning to Patient Dashboard...");
					   return; // Exits the method and returns to the previous menu
				   }
				   default -> System.out.println("Invalid choice! Please try again.");
			   }
		   }
	   }

	/**
     * Handles the logic for rescheduling an appointment. It allows the patient to
     * select a confirmed appointment and choose a new available slot for
     * rescheduling.
     */
	public void rescheduleAppointment() {
		System.out.println("Enter your availability. Type 'no' when finished.");

		while (true) {
			// Check if the user wants to continue rescheduling
			boolean continueRescheduling = Validator.promptConfirmation("reschedule appointment");

			if (!continueRescheduling) {
				break;  // Exit the loop if the user does not want to continue
			}

			// Retrieve the list of confirmed appointments
			List<Appointment> confirmedAppointments = AppointmentController.getConfirmedAppointments(patient.getUID());
			if (confirmedAppointments.isEmpty()) {
				System.out.println("No confirmed appointments to reschedule.");
				return; // Exit the method if there are no confirmed appointments
			}

			// Display the confirmed appointments for the user to choose from
			displayConfirmedAppointments(confirmedAppointments);

			// Get the appointment choice from the user
			int choice = Validator.readInt("Enter the number of the appointment you wish to reschedule: ");
			if (choice >= 1 && choice <= confirmedAppointments.size()) {
				Appointment selectedAppointment = confirmedAppointments.get(choice - 1);

				// Get the available slots for rescheduling
				List<Appointment> availableSlots = AppointmentController.getAvailableAppointmentSlotsFromAllDoctor();
				if (availableSlots.isEmpty()) {
					System.out.println("No available slots for rescheduling for all the doctors.");
					return; // Exit the method if no available slots
				}

				// Display the available slots for the user to choose from
				displayAvailableSlots(availableSlots);

				// Get the new slot choice from the user
				int newSlotChoice = Validator.readInt("Enter the number of the new slot: ");
				if (newSlotChoice >= 1 && newSlotChoice <= availableSlots.size()) {
					Appointment newSlot = availableSlots.get(newSlotChoice - 1);

					// Update the appointment status and assign it to the new slot
					selectedAppointment.setAppointmentStatus(AppointmentStatus.AVAILABLE);
					selectedAppointment.setPatientID(null);

					newSlot.setAppointmentStatus(AppointmentStatus.PENDING);
					newSlot.setPatientID(patient.getUID());
					System.out.println("Appointment has been successfully rescheduled.");

					// Save all the records after rescheduling
					RecordsRepository.saveAllRecordFiles();
				} else {
					System.out.println("Invalid selection. Please enter a valid number.");
				}
			} else {
				System.out.println("Invalid selection. Please enter a valid number.");
			}
		}
	}

	/**
     * Displays the list of confirmed appointments for the patient.
     *
     * @param confirmedAppointments The list of confirmed appointments.
     */
	private void displayConfirmedAppointments(List<Appointment> confirmedAppointments) {
		System.out.println("\n--- Confirmed Appointments ---");
		int index = 1;
		for (Appointment appointment : confirmedAppointments) {
			String doctorName = DoctorController.getDoctorNameById(appointment.getDoctorID());
			System.out.println(index++ + ")");
			System.out.println("Doctor ID        : " + appointment.getDoctorID());
			System.out.println("Doctor           : " + doctorName);
			System.out.println("Day              : " + appointment.getAppointmentTime().getDayOfWeek());
			System.out.println("Date             : "
					+ appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			System.out.println("Time             : "
					+ appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("HH:mm")));
			System.out.println("Location         : " + appointment.getLocation());
			System.out.println("---------------------------------------");
		}

	}
    /**
     * Displays the list of available appointment slots for rescheduling.
     *
     * @param availableSlots The list of available appointment slots.
     */
	private void displayAvailableSlots(List<Appointment> availableSlots) {
		System.out.println("\n--- Available Slots for Rescheduling ---");
		int index = 1;
		for (Appointment appointment : availableSlots) {
			String doctorName = DoctorController.getDoctorNameById(appointment.getDoctorID());
			System.out.println(index++ + ")");
			System.out.println("Doctor ID        : " + appointment.getDoctorID());
			System.out.println("Doctor           : " + doctorName);
			System.out.println("Day              : " + appointment.getAppointmentTime().getDayOfWeek());
			System.out.println("Date             : "
					+ appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			System.out.println("Time             : "
					+ appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("HH:mm")));
			System.out.println("Location         : " + appointment.getLocation());
			System.out.println("---------------------------------------");
		}
	}
}