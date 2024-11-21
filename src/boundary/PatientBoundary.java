package boundary;

import java.time.format.DateTimeFormatter;
import java.util.List;

import Main.Main;
import controller.AppointmentController;
import controller.DoctorController;
import enums.AppointmentStatus;
import utility.Validator;
import model.*;
import model.Appointment;
import repository.UserRepository;
import repository.RecordsRepository;


import static repository.AppointmentOutcomeRecordRepository.deleteAppointmentOutcomeRecord;


/**
 * PatientBoundary class represents the user interface for a patient in the HMS
 * system
 * This class handles patient-specific interactions
 */

public class PatientBoundary extends Boundary {

	private final Patient patient;

	/**
	 * Constructor for PatientBoundary.
	 *
	 * @param patient The patient using this user interface.
	 */

	public PatientBoundary(Patient patient) {
		this.patient = patient;
	}

	/**
	 * Displays the patient menu options.
	 */

	@Override
	public void printChoice() {
		System.out.println("Patient Menu:");
		System.out.println("1. View Medical Record");
		System.out.println("2. View Payment Record");
		System.out.println("3. Update Personal Information");
		System.out.println("4. View Available Appointment Slots");
		System.out.println("5. Schedule an Appointment");
		System.out.println("6. Reschedule an Appointment");
		System.out.println("7. Cancel an Appointment");
		System.out.println("8. View Scheduled Appointments");
		System.out.println("9. View Past Appointment Outcome Records");
		System.out.println("10. Acknowledge Rejected Appointment Slots");
		System.out.println("11. Logout");
	}

	/**
	 * Starts the patient dashboard by displaying the menu and handling user input.
	 */
	public void start() {
		showPatientDashboard();
	}

	/**
	 * Displays the patient dashboard and handles user input for menu choices.
	 */

	public void showPatientDashboard() {
		while (true) {
			printChoice();
			int choice = Validator.readInt("Enter your choice: ");

			switch (choice) {
				case 1 -> viewPatientMedicalRecord(patient.getUID());
				case 2 -> viewPatientPaymentRecord(patient.getUID());
				case 3 -> updatePatientPrivateInfo(patient.getUID());
				case 4 -> viewAvailableAppointmentSlots();
				case 5 -> scheduleAppointment();
				case 6 -> rescheduleAppointment();
				case 7 -> cancelAppointment();
				case 8 -> viewScheduledAppointments();
				case 9 -> viewPastAppointmentOutcomes();
				case 10 -> acknowledgeRejectedAppointments();
				case 11 -> {
					System.out.println("Logging out...");
					Main.main(null);
					return;
				}
				default -> System.out.println("Invalid choice!");
			}
		}
	}


	/**
	 * Patient Menu Option 1
	 * Displays the medical records for the given patient ID.
	 * @param patientID The unique ID of the patient whose medical records are to be
	 *                  viewed.
	 */
	private void viewPatientMedicalRecord(String patientID) {
		System.out.println("\n--- Patient Medical Records for Patient ID: " + patientID + " ---");
		boolean recordsFound = false;
		for (MedicalRecord record : RecordsRepository.MEDICAL_RECORDS.values()) {
			if (record.getPatientID().equals(patientID)) {
				recordsFound = true;
				MRBoundary medicalRecordUI = new MRBoundary(record);
				medicalRecordUI.displayMedicalRecordInBox();
			}
		}
		if (!recordsFound) {
			System.out.println("No medical records found for Patient ID: " + patientID);
		}
		System.out.println("---------------------------------------");
	}

	/**
	 * Patient Menu Option 2
	 * Displays the payment records for the given patient ID.
	 * @param patientID The unique ID of the patient whose payment records are to be
	 *                  viewed.
	 */
	private void viewPatientPaymentRecord(String patientID) {
		//System.out.println("\n--- Patient Payment Records for Patient ID: " + patientID + " ---");
		boolean recordsFound = false;
		for (PaymentRecord record : RecordsRepository.PAYMENT_RECORDS.values()) {
			if (PaymentRecord.getPatientID().equals(patientID)) {
				recordsFound = true;
				PaymentRecordBoundary paymentRecordUI = new PaymentRecordBoundary(record);
				paymentRecordUI.displayPaymentRecordInBox();
			}
		}
		if (!recordsFound) {
			System.out.println("No Payment records found for Patient ID: " + patientID);
		}
		System.out.println("---------------------------------------");
	}

	/**
	 * Patient Menu Option 3
	 * Updates the personal information of the patient.
	 * @param patientId The unique ID of the patient whose information is to be
	 *                  updated.
	 */
	private void updatePatientPrivateInfo(String patientId) {
		System.out.println("\n--- Patient Personal Information for Patient ID: " + patientId + " ---");
		boolean recordsFound = false;
		for (Patient patient : UserRepository.PATIENTS.values()) {
			if (patient.getUID().equals(patientId)) {
				recordsFound = true;
				UpdateDetailsBoundary PUpdateDetailsBoundary = new UpdateDetailsBoundary(patient);
				PUpdateDetailsBoundary.start();
			}
		}
		if (!recordsFound) {
			System.out.println("No Personal Information found for Patient ID: " + patientId);
		}
		System.out.println("---------------------------------------");
	}

	/**
	 * Patient Menu Option 4
	 * Displays all available appointment slots from all doctors.
	 */
	private static boolean viewAvailableAppointmentSlots() {
		System.out.println("\n--- Available Appointment Slots ---");
		List<Appointment> availableSlots = AppointmentController.getAvailableAppointmentSlotsFromAllDoctor();

		if (availableSlots.isEmpty()) {
			System.out.println("No appointments found");
			System.out.println("---------------------------------------");
			return false;
		}

		for (Appointment appointment : availableSlots) {
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

		return true;
	}

	/**
	 * Patient Menu Option 5
	 * Schedules a new appointment for the patient.
	 */
	private void scheduleAppointment() {
		if(viewAvailableAppointmentSlots()) {
			System.out.println("\n--- Schedule Appointment for Patient ID: " + patient.getUID() + " ---");
			SchedulerBoundary scheduleAppointmentUI = new SchedulerBoundary(patient);
			scheduleAppointmentUI.start();
		}
	}

	/**
	 * Patient Menu Option 6
	 * Reschedules an existing appointment for the patient.
	 */
	private void rescheduleAppointment() {
		if(viewAvailableAppointmentSlots()) {
		System.out.println("\n--- Reschedule an Appointment ---");
		ReschedulerBoundary rescheduleAppointmentUI = new ReschedulerBoundary(patient);
		rescheduleAppointmentUI.start();
		}
	}

	/**
	 * Patient Menu Option 7
	 * Cancels an existing confirmed appointment for the patient.
	 */
	private void cancelAppointment() {
		System.out.println("\n--- Scheduled Appointments ---");

		List<Appointment> confirmedAppointments = AppointmentController
				.getConfirmedAppointments(patient.getUID());
		if (confirmedAppointments.isEmpty()) {
			System.out.println("No confirmed appointments to cancel.");
			System.out.println("---------------------------------------");
			return;
		}
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

		try {
			int choice = Validator.readInt("Enter the number of the appointment you wish to cancel: ");

			String success = AppointmentController.cancelAppointment(choice, confirmedAppointments);
			// add deleteAppointmentOutcome
			if (success != null) {
				deleteAppointmentOutcomeRecord(success);
				System.out.println("The appointment has been successfully cancelled.");
			} else {
				System.out.println("Invalid selection. Please enter a valid number.");
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid input. Please enter a valid number.");
		}

		System.out.println("----------------------------------");

	}

	/**
	 * Patient Menu Option 8
	 * Displays the scheduled appointments for the patient.
	 */
	private void viewScheduledAppointments() {
		System.out.println("\n--- Scheduled Appointments ---");

		List<Appointment> scheduledSlots = AppointmentController.getAllAppointments(patient.getUID());

		if (scheduledSlots.isEmpty()) {
			System.out.println("No appointments found");
			System.out.println("---------------------------------------");
			return;
		}

		for (Appointment appointment : scheduledSlots) {
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

	/**
	 * Patient Menu Option 9
	 * Displays past appointment outcome records for the patient.
	 */
	private void viewPastAppointmentOutcomes() {
		System.out.println("\n--- Past Appointment Outcomes ---");

		// Get the past appointment outcomes for the patient
		List<AppointmentOutcomeRecord> pastOutcomes = AppointmentController
				.getPastAppointmentOutcomes(patient.getUID());

		if (pastOutcomes.isEmpty()) {
			System.out.println("No past appointment outcomes found.");
			System.out.println("---------------------------------------");
			return;
		}

		for (AppointmentOutcomeRecord outcome : pastOutcomes) {
			System.out.println("Appointment Outcome ID: " + outcome.getUID());
			System.out.println("Patient ID: " + outcome.getPatientID());
			System.out.println("Doctor ID: " + outcome.getDoctorID());
			System.out.println("Appointment Time: "
					+ outcome.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
			System.out.println("Type of Service: " + outcome.getTypeOfService());
			System.out.println("Consultation Notes: " + outcome.getConsultationNotes());
			System.out.println("Appointment Outcome Status: " + outcome.getAppointmentOutcomeStatus());

			// Display prescription details if available
			Prescription prescription = outcome.getPrescription();
			if (prescription != null && prescription.getMedications() != null) {
				System.out.println("Prescription Details:");
				for (PrescribedMedication medication : prescription.getMedications()) {
					System.out.println("Medicine ID: " + medication.getMedicineID());
					System.out.println("Quantity: " + medication.getMedicineQuantity());
					System.out.println("Dosage: " + medication.getDosage());
					System.out.println("Period (days): " + medication.getPeriodDays());
					System.out.println("Prescription Status: " + medication.getPrescriptionStatus());
					System.out.println();
				}
			} else {
				System.out.println("No prescription details available for this appointment outcome record.");
			}
			System.out.println("---------------------------------------");
		}
	}

	/**
	 * Patient Menu Option 10
	 * Allows the patient to acknowledge rejected appointment slots.
	 */
	private void acknowledgeRejectedAppointments() {
		System.out.println("\n--- Acknowledge Rejected Appointments ---");

		List<Appointment> canceledAppointments = AppointmentController
				.getCancelledAppointmentSlots(patient.getUID());
		if (canceledAppointments.isEmpty()) {
			System.out.println("No canceled appointments found.");
			System.out.println("---------------------------------------");
			return;
		}

		for (Appointment appointment : canceledAppointments) {
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

			String userResponse = Validator.readString("Do you acknowledge this cancellation? (yes/no): ");

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
