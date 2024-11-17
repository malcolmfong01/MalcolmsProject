package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import controller.RecordsController;
import enums.RecordFileType;

public class MedicalRecord extends HMSRecords {
    private String patientName;
    private String patientPhoneNumber;
    private String patientEmail;
    private String patientID;
    private String doctorID;
    private String bloodType;
    private String allergies;
    private ArrayList<Diagnosis> Diagnosis;

    public MedicalRecord( // This Constructor is to create a new MedicalRecord
            String patientName,
            String patientPhoneNumber,
            String patientEmail,
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
        this.patientName = patientName;
        this.patientPhoneNumber = patientPhoneNumber;
        this.patientEmail = patientEmail;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.bloodType = bloodType;
        this.Diagnosis = diagnosis;
        this.allergies = allergies;
    }

    public MedicalRecord(
            String recordID, // This Constructor is for converting CSV to a MedicalRecord
            String patientName,
            String patientPhoneNumber,
            String patientEmail,
            LocalDateTime createdDate,
            LocalDateTime updatedDate,
            RecordStatusType recordStatus,
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

    public String getPatientName() {return patientName;}
    public void setPatientName(String patientName) {this.patientName = patientName;}

    public String getPatientPhoneNumber() {return patientPhoneNumber;}
    public void setPatientPhoneNumber(String patientPhoneNumber) {this.patientPhoneNumber = patientPhoneNumber;}

    public String getPatientEmail() {return patientEmail;}
    public void setPatientEmail(String patientEmail) {this.patientEmail = patientEmail;}
}
