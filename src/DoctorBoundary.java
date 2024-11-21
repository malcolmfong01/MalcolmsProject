/**
 * User interface for managing a doctor's activities within the Hospital Management System.
 * Allows the doctor to view and update patient medical records, manage appointments,
 * set availability, and record appointment outcomes.
 */
package boundary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import Main.Main;
import controller.*;
import enums.AppointmentOutcomeStatus;
import enums.AppointmentStatus;
import enums.Record;
import utility.Validator;
import model.*;
import repository.AppointmentOutcomeRecordRepository;
import repository.PrescriptionRepository;
import repository.RecordsRepository;

/**
 * DoctorBoundary class represents the user interface for a doctor in the HMS
 * system
 * This class handles doctor-specific interactions
 */

public class DoctorBoundary extends Boundary {
	private final Doctor doctor;

	/**
	 * Constructs a DoctorBoundary for the specified doctor
	 *
	 * @param doctor the doctor interacting with the UI
	 */

	public DoctorBoundary(Doctor doctor) {
		this.doctor = doctor;
	}

	/**
	 * Displays the doctor menu options.
	 */
	@Override
	protected void printChoice() {
		System.out.printf("Welcome! Dr. --- %s ---\n", doctor.getFullName());
		System.out.println("Doctor Menu:");
		System.out.println("1. View Patient Medical Records");
		System.out.println("2. Update Patient Medical Records");
		System.out.println("3. View Personal Schedule");
		System.out.println("4. Set Availability for Appointments");
		System.out.println("5. Accept or Decline Appointment Requests");
		System.out.println("6. View Upcoming Appointments");
		System.out.println("7. Record Appointment Outcome");
		System.out.println("8. Logout");
	}

	/**
	 * Starts the doctor dashboard and processes the menu options.
	 */
	public void start() {
		showDoctorDashboard();
	}

	/**
	 * Displays the Doctor Dashboard and handles user input for menu navigation.
	 */

	public void showDoctorDashboard() {
		while (true) {
			printChoice();
			int choice = Validator.readInt("Enter your choice: ");

			switch (choice) {
				case 1 -> viewPatientMedicalRecord(doctor.getUID());
				case 2 -> updatePatientMedicalRecord(doctor.getUID());
				case 3 -> viewPersonalSchedule();
				case 4 -> setavailabilityforAppointments();
				case 5 -> manageAppointmentRequests();
				case 6 -> viewUpcomingAppointments();
				case 7 -> recordAppointmentOutcome();
				case 8 -> {
					System.out.println("Logging out...");
					Main.main(null);
					return;
				}
				default -> System.out.println("Invalid choice!");
			}
		}

	}

	/**
	 * Doctor Menu Option 1
	 * Displays all patient medical records associated with this doctor.
	 * 
	 * @param doctorID the ID of the doctor
	 */
	public void viewPatientMedicalRecord(String doctorID) {
		MainBoundary.loadHMSRepository();
		System.out.println("\n--- All Patients' Medical Records for Doctor ID: " + doctorID + " ---");
		boolean recordsFound = false;
		for (MedicalRecord record : RecordsRepository.MEDICAL_RECORDS.values()) {
			if (record.getDoctorID().equals(doctorID)) {
				recordsFound = true;
				MRBoundary medicalRecordUI = new MRBoundary(record);
				medicalRecordUI.displayMedicalRecordInBoxForDoctor();
			}
		}
		if (!recordsFound) {
			System.out.println("No medical records found for Doctor ID: " + doctorID);
		}
		System.out.println("---------------------------------------");
	}

	/**
	 * Doctor Menu Option 2
	 * Allows the doctor to select and update a patient's medical record.
	 */
	public void updatePatientMedicalRecord(String doctorID) {
		// check whether the doctor has any medical record to update
		boolean recordsFound = false;
		for (MedicalRecord record : RecordsRepository.MEDICAL_RECORDS.values()) {
			if (record.getDoctorID().equals(doctorID)) {
				recordsFound = true;
				System.out.println("Enter Patient ID to select medical record:");
				String patientId = Validator.readString();
				// Retrieve the medical record by the doctor and patient ID
				String medicalRecordID = retrieveMedicalRecordID(patientId);
				if (medicalRecordID == null) {
					System.out.println("No medical record found for the specified patient.");
					return;
				}

				MedicalRecord medicalRecord = RecordsRepository.MEDICAL_RECORDS.get(medicalRecordID);
				if (medicalRecord == null) {
					System.out.println("Error: Medical record not found in repository.");
					return;
				}

				List<Appointment> currentAppointment = AppointmentController
						.getCompletedAppointmentsByDoctorID(medicalRecord.getDoctorID());
				// Check if there are no completed appointments for the patient
				if (currentAppointment.isEmpty()) {
					System.out.println("Error: There are no completed appointments for the current patient.");
					System.out.println("You cannot update the medical record.");
					return;
				}
				// Loop through each appointment record and perform the necessary actions
				for (Appointment appointment : currentAppointment) {
					// Check if the appointment is relevant (e.g., still pending or needs update)
					if (appointment.getAppointmentStatus() == AppointmentStatus.COMPLETED) {
						// Use UpdateMRBoundary to handle the updating process
						UpdateMRBoundary updateUI = new UpdateMRBoundary(doctor, medicalRecord,
								appointment, this);
						updateUI.start();
					} else {
						System.out.println("Appointment with ID " + appointment.getRecordID()
								+ " is not pending and cannot be updated.");
					}
				}

				// After updating, save the medical record explicitly back to the repository
				RecordsRepository.MEDICAL_RECORDS.put(medicalRecord.getRecordID(), medicalRecord);
				RecordsRepository.saveAllRecordFiles(); // Save to persist changes

			}
		}
		if (!recordsFound) {
			System.out.println("No Patient medical records found under Doctor ID: " + doctorID);
		}

	}

	/**
	 * Doctor Menu Option 3
	 * Displays the doctor's personal schedule of appointments.
	 */
	public void viewPersonalSchedule() {
		System.out.println("\n--- Appointments for Dr. " + doctor.getFullName() + " (ID: " + doctor.getUID() + ") ---");

		boolean found = false;
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		for (Appointment appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (doctor.getUID().equals(appointment.getDoctorID())) {
				found = true;
				System.out.println("Appointment Record:");
				System.out.println("  - Appointment ID: " + appointment.getRecordID());
				System.out.println("  - Date & Time: " + appointment.getAppointmentTime().format(dateTimeFormatter));
				System.out.println("  - Location: " + appointment.getLocation());
				System.out.println("  - Status: " + appointment.getAppointmentStatus());
				System.out.println(
						"  - Patient ID: " + (appointment.getPatientID() != null ? appointment.getPatientID() : "N/A"));
				System.out.println("---------------------------------------");
			}
		}

		if (!found) {
			System.out.println("No appointments found for this doctor.");
		}
		System.out.println("---------------------------------------");
	}

	/**
	 * Doctor Menu Option 4
	 * Opens the UI for setting the doctor's availability for appointments.
	 */
	public void setavailabilityforAppointments() {
		AvailabilitySetterBoundary availabilityUI = new AvailabilitySetterBoundary(doctor);
		availabilityUI.start();
	}

	/**
	 * Doctor Menu Option 5
	 * Allows the doctor to review and either accept or decline pending appointment
	 * requests.
	 */
	public void manageAppointmentRequests() {
		System.out.println(
				"\n--- Request Appointments for: " + doctor.getFullName() + " (UID: " + doctor.getUID() + ") ---");
		Scanner sc = new Scanner(System.in);

		boolean found = false;
		for (Appointment appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (doctor.getUID().equals(appointment.getDoctorID())
					&& appointment.getAppointmentStatus().equals(AppointmentStatus.PENDING)) {
				found = true;
				System.out.println("Day: " + appointment.getAppointmentTime().getDayOfWeek() + ", Time: "
						+ appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
						+ ", Location: " + appointment.getLocation() + ", Patient ID: " + appointment.getPatientID()
						+ ", Patient Name: " + PatientController.getPatientNameById(appointment.getPatientID()));

				System.out.println("Do you want to accept or decline this appointment? (Type 'accept' or 'decline'): ");
				String choice = sc.nextLine().trim().toLowerCase();

				if ("accept".equals(choice)) {
					String patientID = appointment.getPatientID();
					String doctorID = appointment.getDoctorID();
					String medicalRecordID = retrieveMedicalRecordID(patientID);

					appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
					// Update the doctor ID in the medical record
					RecordsRepository.updateDoctorIDInMedicalRecord(medicalRecordID, doctorID);
					System.out.println(
							"Appointment with Patient ID: " + appointment.getPatientID() + " has been confirmed.");

					// Now we will generate the appointment outcome record
					if (medicalRecordID != null) {
						MedicalRecord medicalRecord = RecordsRepository.MEDICAL_RECORDS.get(medicalRecordID);

						// Assuming you have a way to get the diagnosis ID, type of service, and
						// consultation notes
						String diagnosisID = ""; // Retrieve the diagnosis ID (you may need to implement a way to get this)
						String typeOfService = ""; // Define the type of service provided
						String consultationNotes = ""; // Get consultation notes from the doctor

						// Generate the appointment outcome record
						String AppOutcomeID = generateAppointmentOutcomeRecord(medicalRecord,
								diagnosisID, typeOfService, consultationNotes, appointment.getAppointmentTime());
						appointment.setAppointmentOutcomeRecordID(AppOutcomeID);
						break;
					} else {
						System.out.println("No medical record found for Patient ID: " + patientID);
					}

				} else if ("decline".equals(choice)) {
					appointment.setAppointmentStatus(AppointmentStatus.CANCELED);
					System.out.println("Appointment with Patient ID: " + appointment.getPatientID()
							+ " has been declined and is waiting for patient to acknowledge.");
				} else {
					System.out.println("Invalid choice. Please enter 'accept' or 'decline'.");
				}
			}
		}

		if (!found) {
			System.out.println("No pending appointments found for this doctor.");
		}

		System.out.println("---------------------------------------");

		RecordsRepository.saveAllRecordFiles();
	}

	/**
	 * Doctor Menu Option 6
	 * Displays all upcoming confirmed appointments for the doctor.
	 */
	public void viewUpcomingAppointments() {
		System.out.println(
				"\n--- Upcoming Appointments for: " + doctor.getFullName() + " (UID: " + doctor.getUID() + ") ---");

		boolean found = false;

		for (Appointment appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (doctor.getUID().equals(appointment.getDoctorID())
					&& appointment.getAppointmentStatus().equals(AppointmentStatus.CONFIRMED)) {
				found = true;
				System.out.println("Day: " + appointment.getAppointmentTime().getDayOfWeek() + ", Time: "
						+ appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
						+ ", Location: " + appointment.getLocation() + ", Patient ID: " + appointment.getPatientID()
						+ "\n" + PatientController.getPatientInfoById(appointment.getPatientID())

				);
			}
		}

		if (!found) {
			System.out.println("No upcoming appointments found for this doctor.");
		}

		System.out.println("---------------------------------------");
	}

	/**
	 * Doctor Menu Option 7
	 * Launches the appointment outcome recording interface for the doctor,
	 * allowing the doctor to record outcomes for completed appointments.
	 */
	public void recordAppointmentOutcome() {
		RecordOutcomeBoundary outcomeUI = new RecordOutcomeBoundary(doctor);
		outcomeUI.start();
	}

	/**
	 * Generates an appointment outcome record for a specific medical record and
	 * diagnosis.
	 * Populates the outcome record with details such as patient ID, doctor ID, type
	 * of service,
	 * and consultation notes, and marks the outcome status as incomplete.
	 *
	 * @param medicalRecord     the medical record associated with the appointment
	 * @param diagnosisID       the ID of the diagnosis for the appointment outcome
	 * @param typeOfService     the type of service provided during the appointment
	 * @param consultationNotes notes taken by the doctor during the consultation
	 * @return the created AppointmentOutcomeRecord object
	 */
	public String generateAppointmentOutcomeRecord(MedicalRecord medicalRecord, String diagnosisID,
			String typeOfService, String consultationNotes, LocalDateTime appointmentTime) {
		String UID = AppointmentController.generateRecordID(Record.APPOINTMENT_OUTCOME_RECORDS);
		AppointmentOutcomeRecord outcomeRecord = new AppointmentOutcomeRecord(
				UID,
				medicalRecord.getPatientID(),
				medicalRecord.getDoctorID(),
				diagnosisID,
				appointmentTime,
				PrescriptionRepository.PRESCRIPTION_MAP.get(diagnosisID),
				typeOfService,
				consultationNotes,
				AppointmentOutcomeStatus.INCOMPLETED);

		AppointmentOutcomeRecordRepository.addAppointmentOutcomeRecord(medicalRecord.getPatientID(), outcomeRecord);
		// AppointmentOutcomeRecordRepository.saveAppointmentOutcomeRecordRepository();
		return UID;

	}

	/**
	 * Retrieves a medical record ID for the specified doctor and patient.
	 * Searches through records in the repository to find the one that matches
	 * the doctor and patient IDs.
	 *
	 * @param patientID the ID of the patient
	 * @return the ID of the medical record matching the doctor and patient, or null
	 *         if none found
	 */
	public String retrieveMedicalRecordID(String patientID) {
		for (MedicalRecord record : RecordsRepository.MEDICAL_RECORDS.values()) {
			if (record.getPatientID().equals(patientID)) {
				return record.getRecordID(); // Return the matching record's ID
			}
		}
		return null; // No matching record found
	}

}