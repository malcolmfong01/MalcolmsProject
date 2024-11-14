package model;

import java.util.List;

public class MedicalHistory {

    private List<MedicalRecord> medicalHistoryList;
    private Patient patient;

    public MedicalHistory(List<MedicalRecord> medicalHistoryList, Patient patient) {
        this.medicalHistoryList = medicalHistoryList;
        this.patient = patient;
    }

    public List<MedicalRecord> getMedicalHistoryList() {
        return medicalHistoryList;
    }

    public void setMedicalHistoryList(List<MedicalRecord> medicalHistoryList) {
        this.medicalHistoryList = medicalHistoryList;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

}
