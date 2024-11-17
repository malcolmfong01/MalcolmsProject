package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import enums.AppointmentOutcomeStatus;
import enums.AppointmentStatus;
import enums.RecordFileType;
import model.*;
import repository.AppointmentOutcomeRecordRepository;
import repository.RecordsRepository;
import java.util.Comparator;
/**
 * The AppointmentController class provides methods to handle appointment records,
 * such as retrieving, filtering, and modifying appointments for doctors and patients.
 */
public class AppointmentController {

	private static final System.Logger logger = System.getLogger(RecordsController.class.getName());
	// Map to keep track of the next ID for each record type
	/**
	 * Generates a unique record ID for a specific record type using UUID.
	 *
	 * @param recType The type of the record for which the ID is generated.
	 * @return A unique record ID string.
	 */
	private static final Map<RecordFileType, Integer> recordCounters = new HashMap<>();

	static {
		// Initialize counters for each record type starting at 0
		for (RecordFileType recType : RecordFileType.values()) {
			recordCounters.put(recType, 0);
		}
	}
	public static String generateRecordID(RecordFileType recType) {
		// Get the current counter for the specified record type
		int nextId = recordCounters.get(recType);
		// Format the ID with a prefix and leading zeros (e.g., "A-000")
		String prefix = "";
		switch (recType) {
		case APPOINTMENT_OUTCOME_RECORDS:
			prefix = "AO-";
			break;
		case DIAGNOSIS_RECORDS:
			prefix = "DIAG-";
			break;
		case MEDICINE_RECORDS:
			prefix= "MR-";
		default:
			prefix = "R-";
		}
		// Return the formatted ID
		return String.format("%s%03d", prefix, nextId);
	}
    /**
     * Retrieves appointments filtered by doctor ID, patient ID, and appointment status.
     *
     * @param doctorID The ID of the doctor.
     * @param patientID The ID of the patient.
     * @param status The status of the appointment to filter by.
     * @return A list of filtered appointment records.
     */
	public static ArrayList<AppointmentRecord> getAppointmentsByDoctorAndPatient(String doctorID, String patientID,
			AppointmentStatus status) {
		ArrayList<AppointmentRecord> filteredAppointments = new ArrayList<>();

		// Loop through all appointment records
		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			// Check if appointment matches both the doctorID and patientID, and has a
			// CONFIRMED status
			if (doctorID.equals(appointment.getDoctorID()) && patientID.equals(appointment.getPatientID())
					&& appointment.getAppointmentStatus() == status) {

				// Add to filtered list
				filteredAppointments.add(appointment);
			}
		}

		return filteredAppointments;

		// If no appointments match the criteria
	}
    /**
     * Retrieves the earliest appointment from a list of appointments based on appointment time.
     *
     * @param appointments A list of appointment records.
     * @return The earliest appointment record, or null if no appointments are provided.
     */
	public static AppointmentRecord getEarliestAppointment(ArrayList<AppointmentRecord> appointments) {
		if (appointments.isEmpty()) {
			return null; // Return null if there are no pending appointments
		}

		// Sort appointments by AppointmentTime in ascending order
		appointments.sort(Comparator.comparing(AppointmentRecord::getAppointmentTime));

		// Return the first (earliest) appointment
		return appointments.get(0);
	}
    /**
     * Retrieves the earliest confirmed appointment record for a specific doctor and patient.
     *
     * @param doctorID The ID of the doctor.
     * @param patientID The ID of the patient.
     * @return The earliest confirmed appointment record, or null if none are found.
     */
	public static AppointmentRecord retrieveEarliestConfirmedAppointmentRecord(String doctorID, String patientID) {
		ArrayList<AppointmentRecord> pendingAppointments;
		pendingAppointments = AppointmentController.getAppointmentsByDoctorAndPatient(doctorID, patientID,
				AppointmentStatus.CONFIRMED);

		AppointmentRecord currentAppointmentRecord = AppointmentController.getEarliestAppointment(pendingAppointments);
		return currentAppointmentRecord;
	}
    /**
     * Retrieves all confirmed appointments for a given patient ID.
     *
     * @param patientID The ID of the patient.
     * @return A list of confirmed appointment records for the specified patient.
     */
	public static List<AppointmentRecord> getConfirmedAppointments(String patientID) {
		List<AppointmentRecord> confirmedAppointments = new ArrayList<>();
		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (patientID.equals(appointment.getPatientID())
					&& appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
				confirmedAppointments.add(appointment);
			}
		}
		return confirmedAppointments;
	}
    /**
     * Retrieves all appointments (regardless of status) for a given patient ID.
     *
     * @param patientID The ID of the patient.
     * @return A list of all appointment records for the specified patient.
     */
	public static List<AppointmentRecord> getAllAppointments(String patientID) {
		List<AppointmentRecord> confirmedAppointments = new ArrayList<>();
		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (patientID.equals(appointment.getPatientID())) {
				confirmedAppointments.add(appointment);
			}
		}
		return confirmedAppointments;
	}
	   /**
     * Retrieves all available appointment slots from all doctors.
     *
     * @return A list of available appointment records.
     */
	public static List<AppointmentRecord> getAvailableAppointmentSlotsFromAllDoctor() {
		List<AppointmentRecord> availableSlots = new ArrayList<>();
		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (appointment.getAppointmentStatus() == AppointmentStatus.AVAILABLE) {
				availableSlots.add(appointment);
			}
		}
		return availableSlots;
	}
    /**
     * Retrieves all canceled appointment slots for a given patient ID.
     *
     * @param patientID The ID of the patient.
     * @return A list of canceled appointment records for the specified patient.
     */
	public static List<AppointmentRecord> getCancelledAppointmentSlots(String patientID) {
		List<AppointmentRecord> canceledSlots = new ArrayList<>();
		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (patientID.equals(appointment.getPatientID())
					&& appointment.getAppointmentStatus() == AppointmentStatus.CANCELED) {
				canceledSlots.add(appointment);
			}
		}
		return canceledSlots;
	}
    /**
     * Cancels an appointment from the confirmed appointments list based on the user's choice.
     *
     * @param choice The index of the appointment to be canceled.
     * @param confirmedAppointments A list of confirmed appointment records.
     * @return appointmentOutcomeRecordID if the appointment was successfully canceled, null otherwise.
     */
	public static String cancelAppointment(int choice, List<AppointmentRecord> confirmedAppointments) {
		if (choice >= 1 && choice <= confirmedAppointments.size()) {
			AppointmentRecord selectedAppointment = confirmedAppointments.get(choice - 1);
			selectedAppointment.setAppointmentStatus(AppointmentStatus.AVAILABLE);
			selectedAppointment.setPatientID(null);
			RecordsRepository.saveAllRecordFiles();
			return selectedAppointment.getAppointmentOutcomeRecordID();
		} else {
			return null;
		}
	}

	public static List<AppointmentOutcomeRecord> getPastAppointmentOutcomes(String patientId) {
		List<AppointmentOutcomeRecord> pastOutcomes = new ArrayList<>();
		// Retrieve past appointment outcomes for the given patient ID
		for (List<AppointmentOutcomeRecord> outcomes : AppointmentOutcomeRecordRepository.patientOutcomeRecords
				.values()) {
			for (AppointmentOutcomeRecord outcome : outcomes) {
				if (outcome.getPatientID().equals(patientId)
						&& outcome.getAppointmentOutcomeStatus() == AppointmentOutcomeStatus.COMPLETED) {
					pastOutcomes.add(outcome);
				}
			}
		}
		return pastOutcomes;
	}

}
