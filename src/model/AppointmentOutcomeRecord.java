package model;

import java.time.LocalDateTime;

import enums.AppointmentOutcomeStatus;

/**
 * Represents the appointment outcome record of a patient's appointment.
 * This class captures details such as the diagnosis, appointment time,
 * prescription,
 * type of service, consultation notes, and the status of the appointment
 * outcome.
 */
public class AppointmentOutcomeRecord {

    private String UID;
    private String patientID;
    private String doctorID;
    private String diagnosisID;
    private LocalDateTime appointmentTime;
    private Prescription prescription;
    private String typeOfService;
    private String consultationNotes;

    private AppointmentOutcomeStatus appointmentOutcomeStatus;

    /**
     * Constructs an AppointmentOutcomeRecord with the provided details.
     *
     * @param UID                        the unique ID of the appointment outcome
     * @param patientID                  the ID of the patient
     * @param doctorID                   the ID of the doctor
     * @param diagnosisID                the ID of the diagnosis
     *                                   record
     * @param appointmentTime            the date and time of the appointment
     * @param prescription               the prescription given during the
     *                                   appointment
     * @param typeOfService              the type of service rendered during the
     *                                   appointment
     * @param consultationNotes          the notes from the consultation
     * @param appointmentOutcomeStatus   the status of the appointment outcome
     *                                   (e.g., completed, pending)
     */
    public AppointmentOutcomeRecord(String UID, String patientID,
            String doctorID,
            String diagnosisID,
            LocalDateTime appointmentTime,
            Prescription prescription,
            String typeOfService,
            String consultationNotes,
            AppointmentOutcomeStatus appointmentOutcomeStatus) {
        this.UID = UID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.diagnosisID = diagnosisID;
        this.appointmentTime = appointmentTime;
        this.prescription = prescription;
        this.typeOfService = typeOfService;
        this.consultationNotes = consultationNotes;
        this.appointmentOutcomeStatus = appointmentOutcomeStatus;
    }

    /**
     * Gets the UID associated with the appointment.
     *
     * @return the UID
     */
    public String getUID() {
        return UID;
    }

    /**
     * Sets the UID for the appointment.
     *
     * @param UID the UID to set
     */
    public void setUID(String UID) {
        this.UID = UID;
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
     * Gets the diagnosis ID associated with the appointment outcome.
     *
     * @return the diagnosis ID
     */
    public String getDiagnosisID() {
        return diagnosisID;
    }

    /**
     * Sets the diagnosis ID for the appointment outcome.
     *
     * @param diagnosisID the diagnosis ID to set
     */
    public void setDiagnosisID(String diagnosisID) {
        this.diagnosisID = diagnosisID;
    }

    /**
     * Gets the time of the appointment.
     *
     * @return the appointment time
     */
    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    /**
     * Sets the time of the appointment.
     *
     * @param appointmentTime the appointment time to set
     */
    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    /**
     * Gets the prescription given during the appointment.
     *
     * @return the prescription
     */
    public Prescription getPrescription() {
        return prescription;
    }

    /**
     * Sets the prescription for the appointment.
     *
     * @param prescription the prescription to set
     */
    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    /**
     * Gets the type of service rendered during the appointment.
     *
     * @return the type of service
     */
    public String getTypeOfService() {
        return typeOfService;
    }

    /**
     * Sets the type of service rendered during the appointment.
     *
     * @param typeOfService the type of service to set
     */
    public void setTypeOfService(String typeOfService) {
        this.typeOfService = typeOfService;
    }

    /**
     * Gets the consultation notes from the appointment.
     *
     * @return the consultation notes
     */
    public String getConsultationNotes() {
        return consultationNotes;
    }

    /**
     * Sets the consultation notes for the appointment.
     *
     * @param consultationNotes the consultation notes to set
     */
    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    /**
     * Gets the status of the appointment outcome (e.g., completed, pending).
     *
     * @return the appointment outcome status
     */
    public AppointmentOutcomeStatus getAppointmentOutcomeStatus() {
        return appointmentOutcomeStatus;
    }

    /**
     * Sets the status of the appointment outcome.
     *
     * @param appointmentOutcomeStatus the appointment outcome status to set
     */
    public void setAppointmentOutcomeStatus(AppointmentOutcomeStatus appointmentOutcomeStatus) {
        this.appointmentOutcomeStatus = appointmentOutcomeStatus;
    }

}
