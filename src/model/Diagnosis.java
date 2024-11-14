package model;


import java.time.LocalDate;
import java.time.LocalDateTime;

public class Diagnosis {
    private String patientID;
    private String diagnosisID;
    private String doctorID;
    private String medicalRecordID;
    private LocalDateTime diagnosisDate;
    private TreatmentPlans TreatmentPlans;
    private String diagnosisDescription;
    private Prescription prescription;


    public Diagnosis(String patientID, String diagnosisID, String doctorID, String medicalRecordID, LocalDateTime diagnosisDate, TreatmentPlans TreatmentPlans,String diagnosisDescription, Prescription prescription) {
        this.patientID = patientID;
        this.diagnosisID = diagnosisID;
        this.doctorID = doctorID;
        this.medicalRecordID = medicalRecordID;
        this.diagnosisDate = diagnosisDate;
        this.TreatmentPlans = TreatmentPlans;
        this.diagnosisDescription = diagnosisDescription;
        this.prescription = prescription;
    }

	public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getDiagnosisID() {
        return diagnosisID;
    }

    public void setDiagnosisID(String diagnosisID) {
        this.diagnosisID = diagnosisID;
    }

    public LocalDateTime getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(LocalDateTime diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public model.TreatmentPlans getTreatmentPlans() {
        return TreatmentPlans;
    }

    public void setTreatmentPlans(model.TreatmentPlans treatmentPlans) {
        TreatmentPlans = treatmentPlans;
    }

    public String getDiagnosisDescription() {
        return diagnosisDescription;
    }

    public void setDiagnosisDescription(String diagnosisDescription) {
        this.diagnosisDescription = diagnosisDescription;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

	public String getDoctorID() {
		return doctorID;
	}



	public void setDoctorID(String doctorID) {
		this.doctorID = doctorID;
	}



	public String getMedicalRecordID() {
		return medicalRecordID;
	}



	public void setMedicalRecordID(String medicalRecordID) {
		this.medicalRecordID = medicalRecordID;
	}




}