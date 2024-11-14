package model;

import java.time.LocalDateTime;

public class TreatmentPlans {
    public TreatmentPlans(String diagnosisID, LocalDateTime treatmentDate, String treatmentDescription) {
        this.diagnosisID = diagnosisID;
        this.treatmentDate = treatmentDate;
        this.treatmentDescription = treatmentDescription;
    }

    private String diagnosisID;
    private LocalDateTime treatmentDate;
    private String treatmentDescription;

    public String getDiagnosisID() {
        return diagnosisID;
    }

    public void setDiagnosisID(String diagnosisID) {
        this.diagnosisID = diagnosisID;
    }

    public LocalDateTime getTreatmentDate() {
        return treatmentDate;
    }

    public void setTreatmentDate(LocalDateTime treatmentDate) {
        this.treatmentDate = treatmentDate;
    }

    public String getTreatmentDescription() {
        return treatmentDescription;
    }

    public void setTreatmentDescription(String treatmentDescription) {
        this.treatmentDescription = treatmentDescription;
    }

}
