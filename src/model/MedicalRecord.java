package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import controller.RecordsController;
import enums.RecordFileType;

/**
 * Represents a medical record for a patient, containing personal information, blood type,
 * allergies, and a list of diagnoses. This class extends from HMSRecords.
 */
public class MedicalRecord extends HMSRecords {
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
    public MedicalRecord( // This Constructor is for create new MedicalRecord
            LocalDateTime createdDate,
            LocalDateTime updatedDate,
            RecordStatusType recordStatus,
            String patientID,
            String doctorID,
            String bloodType,
            ArrayList<model.Diagnosis> diagnosis,
            String allergies) {

        super(RecordsController.generateRecordID(RecordFileType.MEDICAL_RECORDS), createdDate, updatedDate,
                recordStatus);
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
            LocalDateTime createdDate,
            LocalDateTime updatedDate,
            RecordStatusType recordStatus,
            String patientID,
            String doctorID,
            String bloodType,
            ArrayList<Diagnosis> diagnosis) {

        super(recordID, createdDate, updatedDate, recordStatus);
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
}
