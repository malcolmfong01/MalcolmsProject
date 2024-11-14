package model;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Prescription {
    private String diagnosisID;
    private LocalDateTime prescriptionDate;
    private ArrayList<PrescribedMedication> medications;
    public Prescription(String diagnosisID, LocalDateTime prescriptionDate, ArrayList<PrescribedMedication> medications) {
        this.diagnosisID = diagnosisID;
        this.prescriptionDate = prescriptionDate;
        this.medications = medications;
    }

    public String getDiagnosisID() {
        return diagnosisID;
    }

    public void setDiagnosisID(String diagnosisID) {
        this.diagnosisID = diagnosisID;
    }

    public LocalDateTime getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(LocalDateTime prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public ArrayList<PrescribedMedication> getMedications() {
        return medications;
    }

    public void setMedications(ArrayList<PrescribedMedication> medications) {
        this.medications = medications;
    }


    public void addPrescribedMedication(model.PrescribedMedication newPrescribedMedication) {
        this.medications.add(newPrescribedMedication);
    }


}


