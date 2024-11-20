package model;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Represents a prescription for a patient, including the diagnosis ID, prescription date,
 * and the medications prescribed as part of the treatment.
 */

public class Prescription {
    private String diagnosisID;
    private LocalDateTime prescriptionDate;
    private ArrayList<PrescribedMedication> medications;

    /**
     * Constructs a new Prescription with the provided diagnosis ID, prescription date,
     * and list of prescribed medications.
     *
     * @param diagnosisID the ID of the diagnosis associated with the prescription
     * @param prescriptionDate the date when the prescription was issued
     * @param medications a list of medications prescribed to the patient
     */

    public Prescription(String diagnosisID, LocalDateTime prescriptionDate, ArrayList<PrescribedMedication> medications) {
        this.diagnosisID = diagnosisID;
        this.prescriptionDate = prescriptionDate;
        this.medications = medications;
    }

    /**
     * Gets the diagnosis ID associated with this prescription.
     *
     * @return the diagnosis ID
     */

    public String getDiagnosisID() {
        return diagnosisID;
    }

    /**
     * Sets the diagnosis ID for this prescription.
     *
     * @param diagnosisID the diagnosis ID to set
     */

    public void setDiagnosisID(String diagnosisID) {
        this.diagnosisID = diagnosisID;
    }

    /**
     * Gets the prescription date for this prescription.
     *
     * @return the prescription date
     */

    public LocalDateTime getPrescriptionDate() {
        return prescriptionDate;
    }

    /**
     * Sets the prescription date for this prescription.
     *
     * @param prescriptionDate the prescription date to set
     */

    public void setPrescriptionDate(LocalDateTime prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    /**
     * Gets the list of medications prescribed to the patient.
     *
     * @return the list of prescribed medications
     */

    public ArrayList<PrescribedMedication> getMedications() {
        return medications;
    }

    /**
     * Sets the list of medications prescribed to the patient.
     *
     * @param medications the list of prescribed medications to set
     */

    public void setMedications(ArrayList<PrescribedMedication> medications) {
        this.medications = medications;
    }

    /**
     * Adds a new prescribed medication to the prescription.
     *
     * @param newPrescribedMedication the medication to add to the prescription
     */

    public void addPrescribedMedication(model.PrescribedMedication newPrescribedMedication) {
        this.medications.add(newPrescribedMedication);
    }


}


