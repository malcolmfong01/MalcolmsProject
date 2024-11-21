package model;

import java.time.LocalDateTime;

import enums.AppointmentStatus;
import enums.RecordStatus;

/**
 * Appointment class extends Records
 */

public class Appointment extends Records {
	private String appointmentOutcomeRecordID;
	private String patientID;
	private String doctorID;
	private LocalDateTime appointmentTime;
	private String location;
	private AppointmentStatus appointmentStatus;
	private AppointmentOutcomeRecord appointmentOutcomeRecord;

    /**
     * Constructor when retrieving CSV data to an object.
     *
     * @param recordID the unique ID of the appointment record
     * @param createdDate the date when the appointment record was created
     * @param updatedDate the date when the appointment record was last updated
     * @param recordStatus the status of the appointment record
     * @param appointmentOutcomeRecordID the ID of the associated appointment outcome record
     * @param patientID the ID of the patient
     * @param doctorID the ID of the doctor
     * @param appointmentTime the date and time of the appointment
     * @param location the location of the appointment
     * @param appointmentStatus the status of the appointment
     * @param appointmentOutcomeRecord the associated appointment outcome record
     */

	public Appointment(String recordID,
					   LocalDateTime createdDate,
					   LocalDateTime updatedDate,
					   RecordStatus recordStatus,
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

    /**
     * Gets the ID of the associated appointment outcome record.
     *
     * @return the appointment outcome record ID
     */

	public String getAppointmentOutcomeRecordID() {
		return appointmentOutcomeRecordID;
	}

    /**
     * Sets the ID of the associated appointment outcome record.
     *
     * @param appointmentOutcomeRecordID the appointment outcome record ID to set
     */

	public void setAppointmentOutcomeRecordID(String appointmentOutcomeRecordID) {
		this.appointmentOutcomeRecordID = appointmentOutcomeRecordID;
	}

    /**
     * Gets the patient ID associated with the appointment.
     *
     * @return the patient ID
     */

	public String getPatientID() {
		return patientID;
	}

    /**
     * Sets the patient ID for the appointment.
     *
     * @param patientID the patient ID to set
     */

	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}

    /**
     * Gets the doctor ID associated with the appointment.
     *
     * @return the doctor ID
     */

	public String getDoctorID() {
		return doctorID;
	}

    /**
     * Sets the doctor ID for the appointment.
     *
     * @param doctorID the doctor ID to set
     */

	public void setDoctorID(String doctorID) {
		this.doctorID = doctorID;
	}

    /**
     * Gets the appointment time.
     *
     * @return the appointment time
     */

	public LocalDateTime getAppointmentTime() {
		return appointmentTime;
	}

    /**
     * Sets the appointment time.
     *
     * @param appointmentTime the appointment time to set
     */

	public void setAppointmentTime(LocalDateTime appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

    /**
     * Gets the location of the appointment.
     *
     * @return the appointment location
     */

	public String getLocation() {
		return location;
	}

    /**
     * Sets the location for the appointment.
     *
     * @param location the location to set
     */

	public void setLocation(String location) {
		this.location = location;
	}

    /**
     * Gets the current status of the appointment.
     *
     * @return the appointment status
     */

	public AppointmentStatus getAppointmentStatus() {
		return appointmentStatus;
	}

    /**
     * Sets the status for the appointment.
     *
     * @param appointmentStatus the status to set
     */

	public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}

    /**
     * Gets the associated appointment outcome record.
     *
     * @return the appointment outcome record
     */

	public AppointmentOutcomeRecord getAppointmentOutcomeRecord() {
		return appointmentOutcomeRecord;
	}

    /**
     * Sets the appointment outcome record.
     *
     * @param appointmentOutcomeRecord the appointment outcome record to set
     */

	public void setAppointmentOutcomeRecord(AppointmentOutcomeRecord appointmentOutcomeRecord) {
		this.appointmentOutcomeRecord = appointmentOutcomeRecord;
	}

}

