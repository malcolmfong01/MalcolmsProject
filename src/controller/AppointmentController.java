package controller;

import java.util.*;

import enums.AppointmentOutcomeStatus;
import enums.AppointmentStatus;
import enums.Record;
import model.*;
import repository.AppointmentOutcomeRecordRepository;
import repository.DiagnosisRepository;
import repository.RecordsRepository;

/**
 * The AppointmentController class provides methods to handle appointment
 * records,
 * such as retrieving, filtering, and modifying appointments for doctors and
 * patients.
 */
public class AppointmentController {

	/** Map to keep track of the next ID for each record type
	 * Generates a unique record ID for a specific record type using UUID.
	 */
	private static final Map<Record, Integer> recordCounters = new HashMap<>();

	static {
		// Initialize counters for each record type starting at 0
		for (Record recType : Record.values()) {
			recordCounters.put(recType, 0);
		}
	}

	public static String generateRecordID(Record recType) {
		String prefix;
		int nextId = 0;

		// Set the prefix based on the record type
		switch (recType) {
			case APPOINTMENT_OUTCOME_RECORDS:
				prefix = "AO-";
				break;
			case DIAGNOSIS_RECORDS:
				prefix = "DIAG-";
				break;
			case MEDICINE_RECORDS:
				prefix = "MR-";
				break;
			default:
				prefix = "R-";
				break;
		}

		// Find the highest ID currently in the repository based on the record type
		switch (recType) {
			case APPOINTMENT_OUTCOME_RECORDS:
				HashMap<String, ArrayList<AppointmentOutcomeRecord>> outcomeRepository = AppointmentOutcomeRecordRepository.patientOutcomeRecords;
				for (ArrayList<AppointmentOutcomeRecord> records : outcomeRepository.values()) {
					for (AppointmentOutcomeRecord record : records) {
						String id = record.getUID();
						if (id.startsWith(prefix)) {
							try {
								// Extract the numeric part after the prefix and parse it
								int currentId = Integer.parseInt(id.substring(prefix.length()));
								nextId = Math.max(nextId, currentId + 1); // Increment for the next ID
							} catch (NumberFormatException e) {
								System.out.println("Invalid ID format: " + id);
							}
						}
					}
				}
				break;

			case DIAGNOSIS_RECORDS:
				HashMap<String, ArrayList<Diagnosis>> diagnosisRepository = DiagnosisRepository.patientDiagnosisRecords;
				for (ArrayList<Diagnosis> records : diagnosisRepository.values()) {
					for (Diagnosis record : records) {
						String id = record.getDiagnosisID();
						if (id.startsWith(prefix)) {
							try {
								// Extract the numeric part after the prefix and parse it
								int currentId = Integer.parseInt(id.substring(prefix.length()));
								nextId = Math.max(nextId, currentId + 1); // Increment for the next ID
							} catch (NumberFormatException e) {
								System.out.println("Invalid ID format: " + id);
							}
						}
					}
				}
				break;

			default:
				System.out.println("Invalid record type.");
				break;
		}

		// Format the next ID with leading zeros (e.g., "AO001")
		return String.format("%s%03d", prefix, nextId);
	}

	/**
	 * Retrieves appointments filtered by doctor ID, patient ID, and appointment
	 * status.
	 *
	 * @param doctorID  The ID of the doctor.
	 * @param patientID The ID of the patient.
	 * @param status    The status of the appointment to filter by.
	 * @return A list of filtered appointment records.
	 */
	public static ArrayList<Appointment> getAppointmentsByDoctorAndPatient(String doctorID, String patientID,
																		   AppointmentStatus status) {
		ArrayList<Appointment> filteredAppointments = new ArrayList<>();

		// Loop through all appointment records
		for (Appointment appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
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
	 * Retrieves all confirmed appointments for a given patient ID.
	 *
	 * @param patientID The ID of the patient.
	 * @return A list of confirmed appointment records for the specified patient.
	 */
	public static List<Appointment> getConfirmedAppointments(String patientID) {
		List<Appointment> confirmedAppointments = new ArrayList<>();
		for (Appointment appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (patientID.equals(appointment.getPatientID())
					&& appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
				confirmedAppointments.add(appointment);
			}
		}
		return confirmedAppointments;
	}
	/**
	 * Retrieves all confirmed appointments for a given patient ID.
	 *
	 * @param doctorID The ID of the doctor.
	 * @return A list of confirmed appointment records for the specified patient.
	 */
	public static List<Appointment> getCompletedAppointmentsByDoctorID(String doctorID) {
		List<Appointment> confirmedAppointments = new ArrayList<>();
		for (Appointment appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (doctorID.equals(appointment.getDoctorID())
					&& appointment.getAppointmentStatus() == AppointmentStatus.COMPLETED) {
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
	public static List<Appointment> getAllAppointments(String patientID) {
		List<Appointment> confirmedAppointments = new ArrayList<>();
		for (Appointment appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
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
	public static List<Appointment> getAvailableAppointmentSlotsFromAllDoctor() {
		List<Appointment> availableSlots = new ArrayList<>();
		for (Appointment appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
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
	public static List<Appointment> getCancelledAppointmentSlots(String patientID) {
		List<Appointment> canceledSlots = new ArrayList<>();
		for (Appointment appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
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
	public static String cancelAppointment(int choice, List<Appointment> confirmedAppointments) {

		if (choice >= 1 && choice <= confirmedAppointments.size()) {
			Appointment selectedAppointment = confirmedAppointments.get(choice - 1);
			selectedAppointment.setAppointmentStatus(AppointmentStatus.AVAILABLE);
			selectedAppointment.setPatientID(null);
			String appointmentOutcomeRecordID = selectedAppointment.getAppointmentOutcomeRecordID();
			selectedAppointment.setAppointmentOutcomeRecordID(null);
			RecordsRepository.saveAllRecordFiles();
			return appointmentOutcomeRecordID;
		} else {
			return null;
		}
	}

	/**
	 * Gets the list of Past Appointment Outcomes as specific patient has
	 * @param patientId the current patient
	 * @return the list
	 */
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
