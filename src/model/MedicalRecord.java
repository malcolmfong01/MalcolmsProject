package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import controller.RecordsController;
import enums.Record;
import enums.RecordStatus;

/**
 * Represents a medical record for a patient, containing personal information, blood type,
 * allergies, and a list of diagnoses. This class extends from Records.
 */

public class MedicalRecord extends Records {
    private String patientName;
    private String patientPhoneNumber;
    private String patientEmail;
    private String patientID;
    private String doctorID;
    private String bloodType;
    private String allergies;
    private ArrayList<Diagnosis> Diagnosis;

    /**
     * Constructor for creating a new medical record.
     *
     * @param createdDate the date when the medical record was created
     * @param updatedDate the date when the medical record was last updated
     * @param recordStatus the status of the medical record
     * @param patientID the ID of the patient
     * @param doctorID the ID of the doctor
     * @param bloodType the patient's blood type
     * @param diagnosis the list of diagnoses associated with the patient
     * @param allergies the allergies of the patient
     */
    public MedicalRecord( // This Constructor is to create a new MedicalRecord
            String patientName,
            String patientPhoneNumber,
            String patientEmail,
            LocalDateTime createdDate,
            LocalDateTime updatedDate,
            RecordStatus recordStatus,
            String patientID,
            String doctorID,
            String bloodType,
            ArrayList<model.Diagnosis> diagnosis,
            String allergies) {

        super(RecordsController.generateRecordID(Record.MEDICAL_RECORDS), createdDate, updatedDate,
                recordStatus);
        this.patientName = patientName;
        this.patientPhoneNumber = patientPhoneNumber;
        this.patientEmail = patientEmail;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.bloodType = bloodType;
        this.Diagnosis = diagnosis;
        this.allergies = allergies;
    }
    /**
     * Constructor for converting a CSV file into a MedicalRecord object.
     *
     * @param recordID the unique ID for the medical record
     * @param createdDate the date when the medical record was created
     * @param updatedDate the date when the medical record was last updated
     * @param recordStatus the status of the medical record
     * @param patientID the ID of the patient
     * @param doctorID the ID of the doctor
     * @param bloodType the patient's blood type
     * @param diagnosis the list of diagnoses associated with the patient
     */
    public MedicalRecord(
            String recordID, // This Constructor is for converting CSV to a MedicalRecord
            String patientName,
            String patientPhoneNumber,
            String patientEmail,
            LocalDateTime createdDate,
            LocalDateTime updatedDate,
            RecordStatus recordStatus,
            String patientID,
            String doctorID,
            String bloodType,
            ArrayList<Diagnosis> diagnosis) {

        super(recordID, createdDate, updatedDate, recordStatus);
        this.patientName = patientName;
        this.patientPhoneNumber = patientPhoneNumber;
        this.patientEmail = patientEmail;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.bloodType = bloodType;
        this.Diagnosis = diagnosis;

    }

    /**
     * Gets the patient ID associated with the medical record.
     *
     * @return the patient ID
     */
    public String getPatientID() {
        return patientID;
    }

    /**
     * Sets the patient ID for the medical record.
     *
     * @param patientID the patient ID to set
     */
    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    /**
     * Gets the doctor ID associated with the medical record.
     *
     * @return the doctor ID
     */
    public String getDoctorID() {
        return doctorID;
    }

    /**
     * Sets the doctor ID for the medical record.
     *
     * @param doctorID the doctor ID to set
     */
    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    /**
     * Gets the blood type of the patient.
     *
     * @return the blood type
     */
    public String getBloodType() {
        return bloodType;
    }

    /**
     * Sets the blood type for the patient.
     *
     * @param bloodType the blood type to set
     */
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    /**
     * Gets the list of diagnoses associated with the medical record.
     *
     * @return the list of diagnoses
     */
    public ArrayList<model.Diagnosis> getDiagnosis() {
        return Diagnosis;
    }

    /**
     * Sets the list of diagnoses for the medical record.
     *
     * @param diagnosis the list of diagnoses to set
     */
    public void setDiagnosis(ArrayList<model.Diagnosis> diagnosis) {
        Diagnosis = diagnosis;
    }

    /**
     * Gets the allergies associated with the patient.
     *
     * @return the allergies
     */
    public String getAllergies() {
        return allergies;
    }

    /**
     * Sets the allergies for the patient.
     *
     * @param allergies the allergies to set
     */
    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    /**
     * Adds a new diagnosis to the list of diagnoses in the medical record.
     *
     * @param newDiagnosis the new diagnosis to add
     */
    public void addDiagnosis(Diagnosis newDiagnosis) {
        this.Diagnosis.add(newDiagnosis);
    }

    /**
     * Gets the patient's name associated with the medical record.
     *
     * @return the patient's name
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * Sets the patient's name for the medical record.
     *
     * @param patientName the patient's name to set
     */
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    /**
     * Gets the patient's phone number associated with the medical record.
     *
     * @return the patient's phone number
     */
    public String getPatientPhoneNumber() {
        return patientPhoneNumber;
    }

    /**
     * Sets the patient's phone number for the medical record.
     *
     * @param patientPhoneNumber the patient's phone number to set
     */
    public void setPatientPhoneNumber(String patientPhoneNumber) {
        this.patientPhoneNumber = patientPhoneNumber;
    }

    /**
     * Gets the patient's email address associated with the medical record.
     *
     * @return the patient's email address
     */
    public String getPatientEmail() {
        return patientEmail;
    }

    /**
     * Sets the patient's email address for the medical record.
     *
     * @param patientEmail the patient's email address to set
     */
    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

}
