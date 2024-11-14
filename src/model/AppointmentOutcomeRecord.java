package model;

import java.time.LocalDateTime;

import enums.AppointmentOutcomeStatus;
import enums.AppointmentStatus;

public class AppointmentOutcomeRecord {

	private String patientID;
	private String doctorID;
	private String diagnosisID;
	private String appointmentOutcomeRecordID;
	private LocalDateTime appointmentTime;
	private Prescription prescription;
	private String typeOfService;
	private String consultationNotes;

	private AppointmentOutcomeStatus appointmentOutcomeStatus;

	public AppointmentOutcomeRecord(String patientID,
			String doctorID,
			String diagnosisID,
			String appointmentOutcomeRecordID,
			LocalDateTime appointmentTime,
			Prescription prescription,
			String typeOfService,
			String consultationNotes,
			AppointmentOutcomeStatus appointmentOutcomeStatus) {
		this.patientID = patientID;
		this.doctorID = doctorID;
		this.diagnosisID = diagnosisID;
		this.appointmentOutcomeRecordID = appointmentOutcomeRecordID;
		this.appointmentTime = appointmentTime;
		this.prescription = prescription;
		this.typeOfService = typeOfService;
		this.consultationNotes = consultationNotes;
		this.appointmentOutcomeStatus = appointmentOutcomeStatus;
	}

	public String getPatientID() {
		return patientID;
	}

	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}

	public String getDoctorID() {
		return doctorID;
	}

	public void setDoctorID(String doctorID) {
		this.doctorID = doctorID;
	}

	public String getDiagnosisID() {
		return diagnosisID;
	}

	public void setDiagnosisID(String diagnosisID) {
		this.diagnosisID = diagnosisID;
	}

	public String getAppointmentOutcomeRecordID() {
		return appointmentOutcomeRecordID;
	}

	public void setAppointmentOutcomeRecordID(String appointmentOutcomeRecordID) {
		this.appointmentOutcomeRecordID = appointmentOutcomeRecordID;
	}

	public LocalDateTime getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(LocalDateTime appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public Prescription getPrescription() {
		return prescription;
	}

	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}

	public String getTypeOfService() {
		return typeOfService;
	}

	public void setTypeOfService(String typeOfService) {
		this.typeOfService = typeOfService;
	}

	public String getConsultationNotes() {
		return consultationNotes;
	}

	public void setConsultationNotes(String consultationNotes) {
		this.consultationNotes = consultationNotes;
	}

	public AppointmentOutcomeStatus getAppointmentOutcomeStatus() {
		return appointmentOutcomeStatus;
	}

	public void setAppointmentOutcomeStatus(AppointmentOutcomeStatus appointmentOutcomeStatus) {
		this.appointmentOutcomeStatus = appointmentOutcomeStatus;
	}

}
