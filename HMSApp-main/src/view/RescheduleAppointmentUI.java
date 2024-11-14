package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import controller.AppointmentController;
import controller.DoctorController;
import enums.AppointmentStatus;
import helper.Helper;
import model.AppointmentRecord;
import model.Doctor;
import model.Patient;
import repository.PersonnelRepository;
import repository.RecordsRepository;

public class RescheduleAppointmentUI extends MainUI {

	private Patient patient;

	public RescheduleAppointmentUI(Patient patient) {
		this.patient = patient;
	}

	@Override
	protected void printChoice() {
		System.out.println("Reschedule Appointment Menu:");
		System.out.println("1. Reschedule Availability for Appointments");
		System.out.println("2. Back to Patient Dashboard");
	}

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