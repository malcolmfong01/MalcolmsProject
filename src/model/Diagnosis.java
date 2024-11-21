package model;

import java.time.LocalDateTime;

/**
 * Represents a Diagnosis made for a patient, which includes details such as the diagnosis description,
 * treatment plans, prescriptions, and relevant IDs for patient, doctor, and medical records.
 */

public class Diagnosis {
    private String patientID;
    private String diagnosisID;
    private String doctorID;
    private String medicalRecordID;
    private LocalDateTime diagnosisDate;
    private Treatment Treatment;
    private String diagnosisDescription;
    private Prescription prescription;

    /**
     * Constructs a Diagnosis object with the specified details.
     *
     * @param patientID the unique identifier for the patient
     * @param diagnosisID the unique identifier for the diagnosis
     * @param doctorID the unique identifier for the doctor who made the diagnosis
     * @param medicalRecordID the medical record ID associated with the diagnosis
     * @param diagnosisDate the date and time when the diagnosis was made
     * @param Treatment the treatment plans associated with the diagnosis
     * @param diagnosisDescription the description of the diagnosis
     * @param prescription the prescription given as part of the diagnosis
     */
    public Diagnosis(String patientID, String diagnosisID, String doctorID, String medicalRecordID, LocalDateTime diagnosisDate, Treatment Treatment, String diagnosisDescription, Prescription prescription) {
        this.patientID = patientID;
        this.diagnosisID = diagnosisID;
        this.doctorID = doctorID;
        this.medicalRecordID = medicalRecordID;
        this.diagnosisDate = diagnosisDate;
        this.Treatment = Treatment;
        this.diagnosisDescription = diagnosisDescription;
        this.prescription = prescription;
    }

    /**
     * Gets the unique identifier for the patient.
     *
     * @return the patient ID
     */
	public String getPatientID() {
        return patientID;
    }

    /**
     * Sets the unique identifier for the patient.
     *
     * @param patientID the patient ID to set
     */
    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    /**
     * Gets the unique identifier for the diagnosis.
     *
     * @return the diagnosis ID
     */
    public String getDiagnosisID() {
        return diagnosisID;
    }

    /**
     * Sets the unique identifier for the diagnosis.
     *
     * @param diagnosisID the diagnosis ID to set
     */
    public void setDiagnosisID(String diagnosisID) {
        this.diagnosisID = diagnosisID;
    }

    /**
     * Gets the date and time when the diagnosis was made.
     *
     * @return the diagnosis date
     */
    public LocalDateTime getDiagnosisDate() {
        return diagnosisDate;
    }

    /**
     * Sets the date and time when the diagnosis was made.
     *
     * @param diagnosisDate the diagnosis date to set
     */
    public void setDiagnosisDate(LocalDateTime diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    /**
     * Gets the treatment plans associated with the diagnosis.
     *
     * @return the treatment plans
     */
    public Treatment getTreatmentPlans() {
        return Treatment;
    }

    /**
     * Sets the treatment plans associated with the diagnosis.
     *
     * @param treatment the treatment plans to set
     */
    public void setTreatmentPlans(Treatment treatment) {
        Treatment = treatment;
    }

    /**
     * Gets the description of the diagnosis.
     *
     * @return the diagnosis description
     */
    public String getDiagnosisDescription() {
        return diagnosisDescription;
    }

    /**
     * Sets the description of the diagnosis.
     *
     * @param diagnosisDescription the diagnosis description to set
     */
    public void setDiagnosisDescription(String diagnosisDescription) {
        this.diagnosisDescription = diagnosisDescription;
    }

    /**
     * Gets the prescription associated with the diagnosis.
     *
     * @return the prescription
     */
    public Prescription getPrescription() {
        return prescription;
    }

    /**
     * Sets the prescription associated with the diagnosis.
     *
     * @param prescription the prescription to set
     */
    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    /**
     * Gets the unique identifier for the doctor who made the diagnosis.
     *
     * @return the doctor ID
     */
	public String getDoctorID() {
		return doctorID;
	}


    /**
     * Sets the unique identifier for the doctor who made the diagnosis.
     *
     * @param doctorID the doctor ID to set
     */
	public void setDoctorID(String doctorID) {
		this.doctorID = doctorID;
	}


    /**
     * Gets the medical record ID associated with the diagnosis.
     *
     * @return the medical record ID
     */
	public String getMedicalRecordID() {
		return medicalRecordID;
	}


    /**
     * Sets the medical record ID associated with the diagnosis.
     *
     * @param medicalRecordID the medical record ID to set
     */
	public void setMedicalRecordID(String medicalRecordID) {
		this.medicalRecordID = medicalRecordID;
	}




}