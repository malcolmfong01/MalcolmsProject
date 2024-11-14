package model;
import java.time.LocalDateTime;
import enums.PrescriptionStatus;
public class PrescribedMedication {
    private String diagnosisID;
    private String medicineID;
    private int medicineQuantity;
    private int periodDays;
    private PrescriptionStatus PrescriptionStatus;
    private String dosage;

    public PrescribedMedication(String diagnosisID, String medicineID, int medicineQuantity, int periodDays, enums.PrescriptionStatus prescriptionStatus, String dosage) {
        this.diagnosisID = diagnosisID;
        this.medicineID = medicineID;
        this.medicineQuantity = medicineQuantity;
        this.periodDays = periodDays;
        PrescriptionStatus = prescriptionStatus;
        this.dosage = dosage;
    }
    public String getDiagnosisID() {
        return diagnosisID;
    }

    public void setDiagnosisID(String diagnosisID) {
        this.diagnosisID = diagnosisID;
    }

    public String getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(String medicineID) {
        this.medicineID = medicineID;
    }

    public int getMedicineQuantity() {
        return medicineQuantity;
    }

    public void setMedicineQuantity(int medicineQuantity) {
        this.medicineQuantity = medicineQuantity;
    }

    public int getPeriodDays() {
        return periodDays;
    }

    public void setPeriodDays(int periodDays) {
        this.periodDays = periodDays;
    }

    public enums.PrescriptionStatus getPrescriptionStatus() {
        return PrescriptionStatus;
    }

    public void setPrescriptionStatus(enums.PrescriptionStatus prescriptionStatus) {
        PrescriptionStatus = prescriptionStatus;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

}

