package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import HMSApp.HMSMain;
import controller.AppointmentController;
import controller.AuthenticationController;
import controller.DoctorController;
import controller.HMSPersonnelController;
import controller.PatientController;
import controller.RecordsController;
import enums.AppointmentStatus;
import helper.Helper;
import model.AppointmentRecord;
import model.Diagnosis;
import model.Doctor;
import model.MedicalRecord;
import model.Patient;
import model.PrescribedMedication;
import model.TreatmentPlans;
import repository.DiagnosisRepository;
import repository.PersonnelRepository;
import repository.PrescribedMedicationRepository;
import repository.RecordsRepository;
import repository.TreatmentPlansRepository;

public class PatientUI extends MainUI {

	private static Patient patient;

	public PatientUI(Patient patient) {
		this.patient = patient;
	}

	@Override
	public void printChoice() {
		System.out.println("Patient Menu:");
		System.out.println("1. View Medical Record");
		System.out.println("2. Update Personal Information");
		System.out.println("3. View Available Appointment Slots");
		System.out.println("4. Schedule an Appointment");
		System.out.println("5. Reschedule an Appointment");
		System.out.println("6. Cancel an Appointment");
		System.out.println("7. View Scheduled Appointments");
		System.out.println("8. View Past Appointment Outcome Records");
		System.out.println("9. Acknowledge Rejected Appointment Slots"); // my added
		System.out.println("10. Logout");
	}

	public void start() {
		showPatientDashboard();
	}

	public void showPatientDashboard() {
		Scanner sc = new Scanner(System.in);
		int choice = 0;
		do {
			printChoice();
			choice = sc.nextInt();
			switch (choice) {
			case 1 -> viewPatientMedicalRecord(patient.getUID());
			case 2 -> updatePatientPrivateInfo(patient.getUID());
			case 3 -> viewAvailableAppointmentSlots();
			case 4 -> scheduleAppointment();
			case 5 -> rescheduleAppointment();
			case 6 -> cancelAppointment();
			case 7 -> viewScheduledAppointments();
			case 8 -> viewPastAppointmentOutcomes();
			case 9 -> acknowledgeRejectedAppointments();
			case 10 -> System.out.println("Logging out...");
			default -> System.out.println("Invalid choice!");
			}
		} while (choice != 10);

		sc.close();
	}

	// 1. viewPatientMedicalRecordUI
	public void viewPatientMedicalRecord(String patientID) {
		System.out.println("\n--- Patient Medical Records for Patient ID: " + patientID + " ---");
		boolean recordsFound = false;
		for (MedicalRecord record : RecordsRepository.MEDICAL_RECORDS.values()) {
			if (record.getPatientID().equals(patientID)) {
				recordsFound = true;
				MedicalRecordUI medicalRecordUI = new MedicalRecordUI(record);
				medicalRecordUI.displayMedicalRecordInBox();
			}
		}
		if (!recordsFound) {
			System.out.println("No medical records found for Patient ID: " + patientID);
		}
		System.out.println("---------------------------------------");
	}

	// 2. updatePatientContactInfo
	public void updatePatientPrivateInfo(String patientId) {
		System.out.println("\n--- Patient Personal Information for Patient ID: " + patientId + " ---");
		boolean recordsFound = false;
		for (Patient patient : PersonnelRepository.PATIENTS.values()) {
			if (patient.getUID().equals(patientId)) {
				recordsFound = true;
				UpdatePatientParticularsUI updatePatientParticularsUI = new UpdatePatientParticularsUI(patient);
				updatePatientParticularsUI.start();
			}
		}
		if (!recordsFound) {
			System.out.println("No Personal Information found for Patient ID: " + patientId);
		}
		System.out.println("---------------------------------------");
	}

	// 3. viewAvailableAppointmentSlots
	public static void viewAvailableAppointmentSlots() {
		System.out.println("\n--- Available Appointment Slots ---");
		List<AppointmentRecord> availableSlots = AppointmentController.getAvailableAppointmentSlotsFromAllDoctor();

		if (availableSlots.isEmpty()) {
			System.out.println("No appointments found");
			System.out.println("---------------------------------------");
			return;
		}

		for (AppointmentRecord appointment : availableSlots) {
			String doctorName = DoctorController.getDoctorNameById(appointment.getDoctorID());
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

	// 4. scheduleAppointment
	public void scheduleAppointment() {
		System.out.println("\n--- Schedule Appointment for Patient ID: " + patient.getUID() + " ---");
		ScheduleAppointmentUI scheduleAppointmentUI = new ScheduleAppointmentUI(patient);
		scheduleAppointmentUI.start();
	}

	// 5. rescheduleAppointment
	public void rescheduleAppointment() {
		System.out.println("\n--- Reschedule an Appointment ---");
		RescheduleAppointmentUI rescheduleAppointmentUI = new RescheduleAppointmentUI(patient);
		rescheduleAppointmentUI.start();
	}

	// 6. cancelAppointment
	public void cancelAppointment() {
		System.out.println("\n--- Scheduled Appointments ---");

		List<AppointmentRecord> confirmedAppointments = AppointmentController
				.getConfirmedAppointments(patient.getUID());
		if (confirmedAppointments.isEmpty()) {
			System.out.println("No confirmed appointments to cancel.");
			System.out.println("---------------------------------------");
			return;
		}
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

		try {
			int choice = Helper.readInt("Enter the number of the appointment you wish to cancel: ");

			boolean success = AppointmentController.cancelAppointment(choice, confirmedAppointments);
			if (success) {
				System.out.println("The appointment has been successfully cancelled.");
			} else {
				System.out.println("Invalid selection. Please enter a valid number.");
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid input. Please enter a valid number.");
		}

		System.out.println("----------------------------------");

	}

	// 7. viewScheduledAppointments
	public static void viewScheduledAppointments() {
		System.out.println("\n--- Scheduled Appointments ---");

		List<AppointmentRecord> scheduledSlots = AppointmentController.getAllAppointments(patient.getUID());

		if (scheduledSlots.isEmpty()) {
			System.out.println("No appointments found");
			System.out.println("---------------------------------------");
			return;
		}

		for (AppointmentRecord appointment : scheduledSlots) {
			String doctorName = DoctorController.getDoctorNameById(appointment.getDoctorID());
			System.out.println("Doctor ID        : " + appointment.getDoctorID());
			System.out.println("Doctor           : " + doctorName);
			System.out.println("Day              : " + appointment.getAppointmentTime().getDayOfWeek());
			System.out.println("Date             : "
					+ appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			System.out.println("Time             : "
					+ appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("HH:mm")));
			System.out.println("Location         : " + appointment.getLocation());
			System.out.println("Status           : " + appointment.getAppointmentStatus());
			System.out.println("---------------------------------------");
		}
	}

	// 8. viewPastAppointmentOutcomes
	public void viewPastAppointmentOutcomes() {

	}

	// 9. acknowledgeRejectedAppointments
	public void acknowledgeRejectedAppointments() {
		System.out.println("\n--- Acknowledge Rejected Appointments ---");

		List<AppointmentRecord> canceledAppointments = AppointmentController
				.getCancelledAppointmentSlots(patient.getUID());
		if (canceledAppointments.isEmpty()) {
			System.out.println("No canceled appointments found.");
			System.out.println("---------------------------------------");
			return;
		}

		for (AppointmentRecord appointment : canceledAppointments) {
			String doctorName = DoctorController.getDoctorNameById(appointment.getDoctorID());
			System.out.println("Doctor ID        : " + appointment.getDoctorID());
			System.out.println("Doctor           : " + doctorName);
			System.out.println("Day              : " + appointment.getAppointmentTime().getDayOfWeek());
			System.out.println("Date             : "
					+ appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			System.out.println("Time             : "
					+ appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("HH:mm")));
			System.out.println("Location         : " + appointment.getLocation());
			System.out.println("---------------------------------------");

			String userResponse = Helper.readString("Do you acknowledge this cancellation? (yes/no): ");

			if ("yes".equalsIgnoreCase(userResponse)) {
				appointment.setAppointmentStatus(AppointmentStatus.AVAILABLE);
				appointment.setPatientID(null);
				System.out.println("Thank you for acknowledging the cancelled slots.");
				RecordsRepository.saveAllRecordFiles();

			} else {
				System.out.println("The appointment is not acknowledged");
			}
		}

		System.out.println("-----------------------------------------");
	}

}