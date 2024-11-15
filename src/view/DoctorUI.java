/**
 * User interface for managing a doctor's activities within the Hospital Management System.
 * Allows the doctor to view and update patient medical records, manage appointments,
 * set availability, and record appointment outcomes.
 */
package view;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import HMSApp.HMSMain;
import controller.*;
import enums.AppointmentOutcomeStatus;
import enums.AppointmentStatus;
import enums.PrescriptionStatus;
import enums.RecordFileType;
import helper.Helper;
import model.*;
import repository.AppointmentOutcomeRecordRepository;
import repository.DiagnosisRepository;
import repository.PersonnelRepository;
import repository.PrescribedMedicationRepository;
import repository.PrescriptionRepository;
import repository.RecordsRepository;
import repository.TreatmentPlansRepository;

public class DoctorUI extends MainUI {
    /**
     * The doctor using this UI.
     */
	private Doctor doctor;

    /**
     * Constructs a DoctorUI for the specified doctor.
     *
     * @param doctor the doctor interacting with the UI
     */
	public DoctorUI(Doctor doctor) {
		this.doctor = doctor;

	}

    /**
     * Constructs a DoctorUI for the specified doctor.
     *
     * @param doctor the doctor interacting with the UI
     */
	@Override
	protected void printChoice() {
		System.out.printf("Welcome! Dr. --- %s ---\n", doctor.getFullName());
		printBreadCrumbs("HMS App UI > Doctor Dashboard");
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
		Scanner sc = new Scanner(System.in);
		int choice;
		do {
			printChoice();
			choice = sc.nextInt();
			switch (choice) {
				case 1 : viewPatientMedicalRecord(doctor.getUID());break;
				case 2 : selectAndUpdateMedicalRecord();break;
				case 3 : viewPersonalSchedule();break;
				case 4 : availabilityForAppointments();break; // Open new UI for setting availability
				case 5 : acceptOrDeclineAppointmentRequests();break;
				case 6 : viewUpcomingAppointments();break;
				case 7 : recordAppointmentOutcome();break;
				case 8 : System.out.println("Logging out..."); HMSMain.main(null); break;
				default : System.out.println("Invalid choice!");
			}
		} while (choice != 8);

		sc.close(); // Close the Scanner
	}

    /**
     * Displays all patient medical records associated with this doctor.
     *
     * @param doctorId the ID of the doctor
     */
	public void viewPatientMedicalRecord(String doctorId) {
		System.out.println("\n--- All Patients' Medical Records for Doctor ID: " + doctorId + " ---");
		boolean recordsFound = false;
		for (MedicalRecord record : RecordsRepository.MEDICAL_RECORDS.values()) {
			if (record.getDoctorID().equals(doctorId)) {
				recordsFound = true;
				MedicalRecordUI medicalRecordUI = new MedicalRecordUI(record);
				medicalRecordUI.displayMedicalRecordInBox();
			}
		}
		if (!recordsFound) {
			System.out.println("No medical records found for Doctor ID: " + doctorId);
		}
		System.out.println("---------------------------------------");
	}

    /**
     * Allows the doctor to select and update a patient's medical record.
     */
	public void selectAndUpdateMedicalRecord() {
		System.out.println("Enter Patient ID to select medical record:");
		String patientId = Helper.readString();

		// Retrieve the medical record by the doctor and patient ID
		String medicalRecordID = retrieveMedicalRecordID(doctor.getUID(), patientId);

		if (medicalRecordID == null) {
			System.out.println("No medical record found for the specified patient.");
			return;
		}

		MedicalRecord medicalRecord = RecordsRepository.MEDICAL_RECORDS.get(medicalRecordID);

		if (medicalRecord == null) {
			System.out.println("Error: Medical record not found in repository.");
			return;
		}

		AppointmentRecord currentAppointmentRecord = AppointmentController.retrieveEarliestConfirmedAppointmentRecord(doctor.getUID(),
				medicalRecord.getPatientID());


		if (currentAppointmentRecord == null) {
			System.out.println("Error: There are no pending appointments for the current patient.");
			System.out.println("You cannot update the medical record");
			return;
		}

		// Use UpdateMedicalRecordUI to handle the updating process
		UpdateMedicalRecordUI updateUI = new UpdateMedicalRecordUI(doctor, medicalRecord,currentAppointmentRecord);
		updateUI.start();

		// After updating, save the medical record explicitly back to the repository
		RecordsRepository.MEDICAL_RECORDS.put(medicalRecord.getRecordID(), medicalRecord);
		RecordsRepository.saveAllRecordFiles(); // Save to persist changes
	}

    /**
     * Displays the doctor's personal schedule of appointments.
     */
	public void viewPersonalSchedule() {
		System.out.println("\n--- Appointments for Dr. " + doctor.getFullName() + " (ID: " + doctor.getUID() + ") ---");

		boolean found = false;
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
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
     * Allows the doctor to review and either accept or decline pending appointment requests.
     */
	public void acceptOrDeclineAppointmentRequests() {
		System.out.println(
				"\n--- Request Appointments for: " + doctor.getFullName() + " (UID: " + doctor.getUID() + ") ---");
		Scanner sc = new Scanner(System.in);

		boolean found = false;
		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
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
					appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
					System.out.println(
							"Appointment with Patient ID: " + appointment.getPatientID() + " has been confirmed.");
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

		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
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

	public boolean setAppointmentRecordStatus(String AppointmentRecordID, String status) {
		boolean flag = false;
		AppointmentRecord appointmentRecord = RecordsRepository.APPOINTMENT_RECORDS.get(AppointmentRecordID);
		if (appointmentRecord != null) {
			appointmentRecord.setAppointmentStatus(AppointmentStatus.toEnumAppointmentStatus(status));

		}
		return flag;

	}

    /**
     * Generates an appointment outcome record for a specific medical record and diagnosis.
     * Populates the outcome record with details such as patient ID, doctor ID, type of service,
     * and consultation notes, and marks the outcome status as incomplete.
     *
     * @param medicalRecord      the medical record associated with the appointment
     * @param diagnosisID        the ID of the diagnosis for the appointment outcome
     * @param typeOfService      the type of service provided during the appointment
     * @param consultationNotes  notes taken by the doctor during the consultation
     * @return the created AppointmentOutcomeRecord object
     */
	public AppointmentOutcomeRecord generateAppointmentOutcomeRecord(MedicalRecord medicalRecord, String diagnosisID,
			String typeOfService, String consultationNotes) {

		AppointmentOutcomeRecord outcomeRecord = new AppointmentOutcomeRecord(
				medicalRecord.getPatientID(),
				medicalRecord.getDoctorID(),
				diagnosisID,
				medicalRecord.getRecordID(),
				LocalDateTime.now(),
				PrescriptionRepository.PRESCRIPTION_MAP.get(diagnosisID),
				typeOfService,
				consultationNotes,
				AppointmentOutcomeStatus.INCOMPLETED);

		AppointmentOutcomeRecordRepository.addAppointmentOutcomeRecord(medicalRecord.getPatientID(), outcomeRecord);
		AppointmentOutcomeRecordRepository.saveAppointmentOutcomeRecordRepository();
		return outcomeRecord;

	}
	
    /**
     * Retrieves the medical record ID for a given doctor and patient.
     *
     * @param doctorId  the ID of the doctor
     * @param patientId the ID of the patient
     * @return the ID of the matching medical record, or null if no record is found
     */
	public static String getMedicalRecordID(String doctorId, String patientId) {
		// through the medical records in the repository
		for (MedicalRecord record : RecordsRepository.MEDICAL_RECORDS.values()) {
			// Check if the record matches the provided doctorId and patientId
			if (record.getDoctorID().equals(doctorId) && record.getPatientID().equals(patientId)) {
				return record.getRecordID(); // Return the matching record's ID
			}
		}
		// If no record is found, return null or an appropriate message
		return null; // Or throw an exception if preferred
	}
	
    /**
     * Retrieves a medical record ID for the specified doctor and patient.
     * Searches through records in the repository to find the one that matches
     * the doctor and patient IDs.
     *
     * @param doctorID  the ID of the doctor
     * @param patientID the ID of the patient
     * @return the ID of the medical record matching the doctor and patient, or null if none found
     */
	public String retrieveMedicalRecordID(String doctorID, String patientID) {
		for (MedicalRecord record : RecordsRepository.MEDICAL_RECORDS.values()) {
			if (record.getDoctorID().equals(doctorID) && record.getPatientID().equals(patientID)) {
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