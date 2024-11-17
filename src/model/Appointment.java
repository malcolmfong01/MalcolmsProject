package model;

import java.time.LocalDateTime;

import enums.AppointmentStatus;

public class Appointment extends HMSRecords {
	private String appointmentOutcomeRecordID;
	private String patientID;
	private String doctorID;
	private LocalDateTime appointmentTime;
	private String location;
	private AppointmentStatus appointmentStatus;
	private AppointmentOutcomeRecord appointmentOutcomeRecord;

	// Constructor when retrieving CSV to an object
	// delete kc constructor dy

	public Appointment(String recordID,
					   LocalDateTime createdDate,
					   LocalDateTime updatedDate,
					   RecordStatusType recordStatus,
					   String appointmentOutcomeRecordID,
					   String patientID,
					   String doctorID,
					   LocalDateTime appointmentTime,
					   String location,
					   AppointmentStatus appointmentStatus,
					   AppointmentOutcomeRecord appointmentOutcomeRecord) {
		super(recordID, createdDate, updatedDate, recordStatus);
		this.appointmentOutcomeRecordID = appointmentOutcomeRecordID;
		this.patientID = patientID;
		this.doctorID = doctorID;
		this.appointmentTime = appointmentTime;
		this.location = location;
		this.appointmentStatus = appointmentStatus;
		this.appointmentOutcomeRecord = appointmentOutcomeRecord;
	}

	public String getAppointmentOutcomeRecordID() {
		return appointmentOutcomeRecordID;
	}

	public void setAppointmentOutcomeRecordID(String appointmentOutcomeRecordID) {
		this.appointmentOutcomeRecordID = appointmentOutcomeRecordID;
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

	public LocalDateTime getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(LocalDateTime appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public AppointmentStatus getAppointmentStatus() {
		return appointmentStatus;
	}

	public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}

	public AppointmentOutcomeRecord getAppointmentOutcomeRecord() {
		return appointmentOutcomeRecord;
	}

	public void setAppointmentOutcomeRecord(AppointmentOutcomeRecord appointmentOutcomeRecord) {
		this.appointmentOutcomeRecord = appointmentOutcomeRecord;
	}

}

// Constructor when generating a new Appointment
