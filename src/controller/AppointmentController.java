package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import enums.AppointmentStatus;
import enums.RecordFileType;
import model.AppointmentOutcomeRecord;
import model.AppointmentRecord;
import model.Doctor;
import model.MedicalRecord;
import model.PaymentRecord;
import model.RecordStatusType;
import repository.AppointmentOutcomeRecordRepository;
import repository.RecordsRepository;
import java.util.Comparator;

public class AppointmentController {

	private static final System.Logger logger = System.getLogger(RecordsController.class.getName());

	public static String generateRecordID(RecordFileType recType) {
		UUID uuid = UUID.randomUUID();
		String uuidAsString = uuid.toString();
		switch (recType) {
		case APPOINTMENT_OUTCOME_RECORDS:
			return "AO-" + uuidAsString;
		case DIAGNOSIS_RECORDS:
			return "DIAG-" + uuidAsString;
		case MEDICINE_RECORDS:
			return "MR-" + uuidAsString;
		default:
			return "R-" + uuidAsString;
		}
	}

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

	public static AppointmentRecord getEarliestAppointment(ArrayList<AppointmentRecord> appointments) {
		if (appointments.isEmpty()) {
			return null; // Return null if there are no pending appointments
		}

		// Sort appointments by AppointmentTime in ascending order
		appointments.sort(Comparator.comparing(AppointmentRecord::getAppointmentTime));

		// Return the first (earliest) appointment
		return appointments.get(0);
	}

	public static AppointmentRecord retrieveEarliestConfirmedAppointmentRecord(String doctorID, String patientID) {
		ArrayList<AppointmentRecord> pendingAppointments;
		pendingAppointments = AppointmentController.getAppointmentsByDoctorAndPatient(doctorID, patientID,
				AppointmentStatus.CONFIRMED);

		AppointmentRecord currentAppointmentRecord = AppointmentController.getEarliestAppointment(pendingAppointments);
		return currentAppointmentRecord;
	}

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

	public static List<AppointmentRecord> getAllAppointments(String patientID) {
		List<AppointmentRecord> confirmedAppointments = new ArrayList<>();
		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (patientID.equals(appointment.getPatientID())) {
				confirmedAppointments.add(appointment);
			}
		}
		return confirmedAppointments;
	}

	public static List<AppointmentRecord> getAvailableAppointmentSlotsFromAllDoctor() {
		List<AppointmentRecord> availableSlots = new ArrayList<>();
		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (appointment.getAppointmentStatus() == AppointmentStatus.AVAILABLE) {
				availableSlots.add(appointment);
			}
		}
		return availableSlots;
	}

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

	public static boolean cancelAppointment(int choice, List<AppointmentRecord> confirmedAppointments) {
		if (choice >= 1 && choice <= confirmedAppointments.size()) {
			AppointmentRecord selectedAppointment = confirmedAppointments.get(choice - 1);
			selectedAppointment.setAppointmentStatus(AppointmentStatus.AVAILABLE);
			selectedAppointment.setPatientID(null);
			RecordsRepository.saveAllRecordFiles();
			return true;
		} else {
			return false;
		}
	}

}