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

import HMSApp.HMSMain;
import controller.*;
import enums.AppointmentOutcomeStatus;
import enums.AppointmentStatus;
import enums.RecordFileType;
import helper.Validator;
import model.*;
import repository.AppointmentOutcomeRecordRepository;
import repository.PrescriptionRepository;
import repository.RecordsRepository;

public class DoctorBoundary extends MainUI {
	/**
	 * The doctor using this UI.
	 */
	private Doctor doctor;

	/**
	 * Constructs a DoctorBoundary for the specified doctor.
	 *
	 * @param doctor the doctor interacting with the UI
	 */
	public DoctorBoundary(Doctor doctor) {
		this.doctor = doctor;

	}

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
		System.out.print("Enter your choice: ");
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
		Scanner sc = new Scanner(System.in);
		int choice;
		do {
			printChoice();
			choice = Validator.readInt("");
			switch (choice) {
				case 1:
					viewPatientMedicalRecord(doctor.getUID());
					break;
				case 2:
					selectAndUpdateMedicalRecord(doctor.getUID());
					break;
				case 3:
					viewPersonalSchedule();
					break;
				case 4:
					availabilityForAppointments();
					break; // Open new UI for setting availability
				case 5:
					acceptOrDeclineAppointmentRequests();
					break;
				case 6:
					viewUpcomingAppointments();
					break;
				case 7:
					recordAppointmentOutcome();
					break;
				case 8:
					System.out.println("Logging out...");
					HMSMain.main(null);
					break;
				default:
					System.out.println("Invalid choice!");
			}
		} while (choice != 8);

		sc.close(); // Close the Scanner
	}

	/**
	 * Displays all patient medical records associated with this doctor.
	 *
	 * @param doctorID the ID of the doctor
	 */
	public void viewPatientMedicalRecord(String doctorID) {
		System.out.println("\n--- All Patients' Medical Records for Doctor ID: " + doctorID + " ---");
		boolean recordsFound = false;
		for (MedicalRecord record : RecordsRepository.MEDICAL_RECORDS.values()) {
			if (record.getDoctorID().equals(doctorID)) {
				recordsFound = true;
				MedicalRecordUI medicalRecordUI = new MedicalRecordUI(record);
				medicalRecordUI.displayMedicalRecordInBox();
			}
		}
		if (!recordsFound) {
			System.out.println("No medical records found for Doctor ID: " + doctorID);
		}
		System.out.println("---------------------------------------");
	}

	/**
	 * Allows the doctor to select and update a patient's medical record.
	 */
	public void selectAndUpdateMedicalRecord(String doctorID) {
		//check whether the doctor has any medical record to update
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

				List<Appointment> currentAppointment = AppointmentController.getCompletedAppointmentsByDoctorID(medicalRecord.getDoctorID());
				if (currentAppointment == null || currentAppointment.isEmpty()) {
					System.out.println("Error: There are no completed appointments for the current patient.");
					System.out.println("You cannot update the medical record");
					return;
				}
				// Loop through each appointment record and perform the necessary actions
				for (Appointment appointment : currentAppointment) {
					// Check if the appointment is relevant (e.g., still pending or needs update)
					if (appointment.getAppointmentStatus() == AppointmentStatus.COMPLETED) {
						// Use UpdateMedicalRecordUI to handle the updating process
						UpdateMedicalRecordUI updateUI = new UpdateMedicalRecordUI(doctor, medicalRecord, appointment);
						updateUI.start();
					} else {
						System.out.println("Appointment with ID " + appointment.getRecordID() + " is not pending and cannot be updated.");
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
	 * Opens the UI for setting the doctor's availability for appointments.
	 */
	public void availabilityForAppointments() {
		AppointmentAvailabilityUI availabilityUI = new AppointmentAvailabilityUI(doctor);
		availabilityUI.start();
	}

	/**
	 * Allows the doctor to review and either accept or decline pending appointment
	 * requests.
	 */
	public void acceptOrDeclineAppointmentRequests() {
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
					System.out.println(doctorID);
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
	 * Updates the status of a specific appointment record.
	 *
	 * @param AppointmentRecordID the ID of the appointment record
	 * @param status              the new status to set
	 * @return true if the status update was successful, false otherwise
	 */
	public boolean setAppointmentRecordStatus(String AppointmentRecordID, String status) {
		boolean flag = false;
		Appointment appointment = RecordsRepository.APPOINTMENT_RECORDS.get(AppointmentRecordID);
		if (appointment != null) {
			appointment.setAppointmentStatus(AppointmentStatus.toEnumAppointmentStatus(status));

		}
		return flag;

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
		String UID = AppointmentController.generateRecordID(RecordFileType.APPOINTMENT_OUTCOME_RECORDS);
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
				String recordID = record.getRecordID();
				System.out.println(recordID);
				return record.getRecordID(); // Return the matching record's ID
			}
		}
		return null; // No matching record found
	}

	/**
	 * Launches the appointment outcome recording interface for the doctor,
	 * allowing the doctor to record outcomes for completed appointments.
	 */
	public void recordAppointmentOutcome() {
		AppointmentRecordOutcomeUI outcomeUI = new AppointmentRecordOutcomeUI(doctor);
		outcomeUI.start();
	}

}