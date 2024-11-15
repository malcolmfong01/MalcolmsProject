package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import controller.DoctorController;
import enums.AppointmentStatus;
import helper.Helper;
import model.AppointmentRecord;
import model.Doctor;
import model.Patient;
import repository.PersonnelRepository;
import repository.RecordsRepository;

public class ScheduleAppointmentUI extends MainUI {

	private Patient patient;

	public ScheduleAppointmentUI(Patient patient) {
		this.patient = patient;
	}

	@Override
	protected void printChoice() {
		System.out.println("Schedule Appointment Menu:");
		System.out.println("1. Schedule Availability for Appointments");
		System.out.println("2. Back to Patient Dashboard");
	}

	@Override
	public void start() {
		int choice;
		do {
			printChoice();
			choice = Helper.readInt("Enter your choice: ");
			switch (choice) {
			case 1 -> scheduleAppointment();
			case 2 -> System.out.println("Returning to Patient Dashboard...");
			default -> System.out.println("Invalid choice! Please try again.");
			}
		} while (choice != 2);
	}

	public void scheduleAppointment() {
		System.out.println("Enter your availability. Type 'done' when finished.");

		while (true) {
			if (!Helper.promptConfirmation("schedule appointment")) {
				break;
			}

			String doctorId = Helper.readString("Enter the Doctor ID you want to schedule an appointment with:");
			Doctor doctor = PersonnelRepository.DOCTORS.get(doctorId);
			if (doctor == null) {
				System.out.println("Doctor not found. Please enter a valid Doctor ID.");
				continue;
			}

			String dateInput = Helper.readString("Enter the date for the appointment (yyyy-MM-dd):");
			String timeInput = Helper.readString("Enter the time for the appointment (HH:mm):");

			LocalDateTime appointmentTime;

			try {
				appointmentTime = LocalDateTime.parse(dateInput + " " + timeInput,
						DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

			} catch (Exception e) {
				System.out.println("Invalid date and time format. Please try again.");
				continue;
			}

			AppointmentRecord existingAppointment = null;
			for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
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

	private void displayScheduledSummary(AppointmentRecord existingAppointment) {
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
