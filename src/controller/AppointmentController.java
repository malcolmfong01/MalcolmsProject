package controller;

import java.util.*;

import enums.AppointmentOutcomeStatus;
import enums.AppointmentStatus;
import enums.RecordFileType;
import model.*;
import repository.AppointmentOutcomeRecordRepository;
import repository.RecordsRepository;

public class AppointmentController {

	private static final System.Logger logger = System.getLogger(RecordsController.class.getName());
	// Map to keep track of the next ID for each record type
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

	public static Appointment getEarliestAppointment(ArrayList<Appointment> appointments) {
		if (appointments.isEmpty()) {
			return null; // Return null if there are no pending appointments
		}

		// Sort appointments by AppointmentTime in ascending order
		appointments.sort(Comparator.comparing(Appointment::getAppointmentTime));

		// Return the first (earliest) appointment
		return appointments.get(0);
	}

	public static Appointment retrieveEarliestConfirmedAppointmentRecord(String doctorID, String patientID) {
		ArrayList<Appointment> pendingAppointments;
		pendingAppointments = AppointmentController.getAppointmentsByDoctorAndPatient(doctorID, patientID,
				AppointmentStatus.CONFIRMED);

		Appointment currentAppointment = AppointmentController.getEarliestAppointment(pendingAppointments);
		return currentAppointment;
	}

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

	public static List<Appointment> getAllAppointments(String patientID) {
		List<Appointment> confirmedAppointments = new ArrayList<>();
		for (Appointment appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (patientID.equals(appointment.getPatientID())) {
				confirmedAppointments.add(appointment);
			}
		}
		return confirmedAppointments;
	}

	public static List<Appointment> getAvailableAppointmentSlotsFromAllDoctor() {
		List<Appointment> availableSlots = new ArrayList<>();
		for (Appointment appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (appointment.getAppointmentStatus() == AppointmentStatus.AVAILABLE) {
				availableSlots.add(appointment);
			}
		}
		return availableSlots;
	}

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

	public static boolean cancelAppointment(int choice, List<Appointment> confirmedAppointments) {
		if (choice >= 1 && choice <= confirmedAppointments.size()) {
			Appointment selectedAppointment = confirmedAppointments.get(choice - 1);
			selectedAppointment.setAppointmentStatus(AppointmentStatus.AVAILABLE);
			selectedAppointment.setPatientID(null);
			RecordsRepository.saveAllRecordFiles();
			return true;
		} else {
			return false;
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
