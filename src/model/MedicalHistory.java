package model;

import java.util.List;
/**
 * Represents the medical history of a patient, containing a list of medical records
 * and the associated patient.
 */
public class MedicalHistory {

    private List<MedicalRecord> medicalHistoryList;
    private Patient patient;
    
    /**
     * Constructs a MedicalHistory object with the specified list of medical records and patient.
     *
     * @param medicalHistoryList the list of medical records for the patient
     * @param patient the patient associated with this medical history
     */
    public MedicalHistory(List<MedicalRecord> medicalHistoryList, Patient patient) {
        this.medicalHistoryList = medicalHistoryList;
        this.patient = patient;
    }

    /**
     * Gets the list of medical records associated with the patient.
     *
     * @return the list of medical records
     */
    public List<MedicalRecord> getMedicalHistoryList() {
        return medicalHistoryList;
    }

    /**
     * Sets the list of medical records associated with the patient.
     *
     * @param medicalHistoryList the list of medical records to set
     */
    public void setMedicalHistoryList(List<MedicalRecord> medicalHistoryList) {
        this.medicalHistoryList = medicalHistoryList;
    }

    /**
     * Gets the patient associated with this medical history.
     *
     * @return the patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Sets the patient associated with this medical history.
     *
     * @param patient the patient to set
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

}
