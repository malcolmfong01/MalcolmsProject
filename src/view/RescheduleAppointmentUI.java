package view;

import java.time.format.DateTimeFormatter;
import java.util.List;

import controller.AppointmentController;
import controller.DoctorController;
import enums.AppointmentStatus;
import helper.Helper;
import model.AppointmentRecord;
import model.Patient;
import repository.RecordsRepository;
/**
 * The RescheduleAppointmentUI class provides functionality to reschedule
 * appointments for a patient. It allows users to view confirmed appointments
 * and select available slots for rescheduling.
 */
public class RescheduleAppointmentUI extends MainUI {

	private Patient patient;
    /**
     * Constructs a RescheduleAppointmentUI instance with the given patient.
     *
     * @param patient The patient using this UI to reschedule appointments.
     */
	public RescheduleAppointmentUI(Patient patient) {
		this.patient = patient;
	}
	 /**
     * Prints the reschedule appointment menu options.
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
		int choice;
		do {
			printChoice();
			choice = Helper.readInt("Enter your choice: ");
			switch (choice) {
			case 1 -> rescheduleAppointment();
			case 2 -> System.out.println("Returning to Patient Dashboard...");
			default -> System.out.println("Invalid choice! Please try again.");
			}
		} while (choice != 2);
	}
    /**
     * Handles the logic for rescheduling an appointment. It allows the patient to
     * select a confirmed appointment and choose a new available slot for
     * rescheduling.
     */
	public void rescheduleAppointment() {
		System.out.println("Enter your availability. Type 'done' when finished.");

		while (true) {
			if (!Helper.promptConfirmation("reschedule appointment")) {
				break;
			}

			List<AppointmentRecord> confirmedAppointments = AppointmentController.getConfirmedAppointments(patient.getUID());
			if (confirmedAppointments.isEmpty()) {
				System.out.println("No confirmed appointments to reschedule.");
				return;
			}

			displayConfirmedAppointments(confirmedAppointments);

			int choice = Helper.readInt("Enter the number of the appointment you wish to reschedule: ");
			if (choice >= 1 && choice <= confirmedAppointments.size()) {
				AppointmentRecord selectedAppointment = confirmedAppointments.get(choice - 1);

				List<AppointmentRecord> availableSlots = AppointmentController.getAvailableAppointmentSlotsFromAllDoctor();
				if (availableSlots.isEmpty()) {
					System.out.println("No available slots for rescheduling for all the doctors.");
					return;
				}

				displayAvailableSlots(availableSlots);

				int newSlotChoice = Helper.readInt("Enter the number of the new slot: ");
				if (newSlotChoice >= 1 && newSlotChoice <= availableSlots.size()) {
					AppointmentRecord newSlot = availableSlots.get(newSlotChoice - 1);

					selectedAppointment.setAppointmentStatus(AppointmentStatus.AVAILABLE);
					selectedAppointment.setPatientID(null);

					newSlot.setAppointmentStatus(AppointmentStatus.PENDING);
					newSlot.setPatientID(patient.getUID());
					System.out.println("Appointment has been successfully rescheduled.");

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
	private void displayConfirmedAppointments(List<AppointmentRecord> confirmedAppointments) {
		System.out.println("\n--- Confirmed Appointments ---");
		int index = 1;
		for (AppointmentRecord appointment : confirmedAppointments) {
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
	private void displayAvailableSlots(List<AppointmentRecord> availableSlots) {
		System.out.println("\n--- Available Slots for Rescheduling ---");
		int index = 1;
		for (AppointmentRecord appointment : availableSlots) {
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