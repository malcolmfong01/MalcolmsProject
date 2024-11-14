package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import controller.RecordsController;
import repository.RecordFileType;

public class MedicalRecord extends HMSRecords {
    private String patientID;
    private String doctorID;
    private String bloodType;
    private String allergies;
    private ArrayList<Diagnosis> Diagnosis;

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

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public ArrayList<model.Diagnosis> getDiagnosis() {
        return Diagnosis;
    }

    public void setDiagnosis(ArrayList<model.Diagnosis> diagnosis) {
        Diagnosis = diagnosis;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public void addDiagnosis(Diagnosis newDiagnosis) {
        this.Diagnosis.add(newDiagnosis);
    }
}
