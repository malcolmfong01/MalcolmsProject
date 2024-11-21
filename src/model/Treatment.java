package model;

import java.time.LocalDateTime;

/**
 * Represents a Treatment Plan associated with a particular diagnosis.
 * Contains details about the treatment, such as the diagnosis ID, treatment date, and description.
 */

public class Treatment {

    /**
     * Constructor for creating a TreatmentPlan instance.
     *
     * @param diagnosisID the ID of the diagnosis associated with this treatment
     * @param treatmentDate the date when the treatment is scheduled
     * @param treatmentDescription a description of the treatment plan
     */
    public Treatment(String diagnosisID, LocalDateTime treatmentDate, String treatmentDescription) {
        this.diagnosisID = diagnosisID;
        this.treatmentDate = treatmentDate;
        this.treatmentDescription = treatmentDescription;
    }

    private String diagnosisID;
    private LocalDateTime treatmentDate;
    private String treatmentDescription;

    /**
     * Gets the diagnosis ID associated with this treatment plan.
     *
     * @return the diagnosis ID
     */
    public String getDiagnosisID() {
        return diagnosisID;
    }

    /**
     * Sets the diagnosis ID for this treatment plan.
     *
     * @param diagnosisID the new diagnosis ID
     */
    public void setDiagnosisID(String diagnosisID) {
        this.diagnosisID = diagnosisID;
    }

    /**
     * Gets the treatment date for this treatment plan.
     *
     * @return the treatment date
     */
    public LocalDateTime getTreatmentDate() {
        return treatmentDate;
    }

    /**
     * Sets the treatment date for this treatment plan.
     *
     * @param treatmentDate the new treatment date
     */
    public void setTreatmentDate(LocalDateTime treatmentDate) {
        this.treatmentDate = treatmentDate;
    }

    /**
     * Gets the description of the treatment.
     *
     * @return the treatment description
     */
    public String getTreatmentDescription() {
        return treatmentDescription;
    }

    /**
     * Sets the description for this treatment plan.
     *
     * @param treatmentDescription the new treatment description
     */
    public void setTreatmentDescription(String treatmentDescription) {
        this.treatmentDescription = treatmentDescription;
    }

}
