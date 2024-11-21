package model;
import enums.PrescriptionStatus;

/**
 * Prescribed Medication class for a patient, including information about the diagnosis,
 * medicine, dosage, and treatment duration.
 */

public class PrescribedMedication {
    private String diagnosisID;
    private String medicineID;
    private int medicineQuantity;
    private int periodDays;
    private PrescriptionStatus PrescriptionStatus;
    private String dosage;
    private String prescribedMedID;

    /**
     * Constructs a new PrescribedMedication with the provided details.
     *
     * @param diagnosisID the diagnosis ID associated with the prescribed medication
     * @param medicineID the ID of the prescribed medication
     * @param medicineQuantity the quantity of the prescribed medication
     * @param periodDays the number of days for which the medication is prescribed
     * @param prescriptionStatus the current status of the prescription (e.g., active, completed)
     * @param dosage the dosage instructions for the prescribed medication
     */
    public PrescribedMedication(String prescribedMedID,String diagnosisID, String medicineID, int medicineQuantity, int periodDays, enums.PrescriptionStatus prescriptionStatus, String dosage) {
        this.diagnosisID = diagnosisID;
        this.medicineID = medicineID;
        this.medicineQuantity = medicineQuantity;
        this.periodDays = periodDays;
        PrescriptionStatus = prescriptionStatus;
        this.dosage = dosage;
        this.prescribedMedID = prescribedMedID;
    }


    /**
     * Gets the diagnosis ID associated with the prescribed medication.
     *
     * @return the diagnosis ID
     */
    public String getDiagnosisID() {
        return diagnosisID;
    }

    /**
     * Sets the diagnosis ID for the prescribed medication.
     *
     * @param diagnosisID the diagnosis ID to set
     */
    public void setDiagnosisID(String diagnosisID) {
        this.diagnosisID = diagnosisID;
    }

    /**
     * Gets the medicine ID for the prescribed medication.
     *
     * @return the medicine ID
     */
    public String getMedicineID() {
        return medicineID;
    }

    /**
     * Sets the medicine ID for the prescribed medication.
     *
     * @param medicineID the medicine ID to set
     */
    public void setMedicineID(String medicineID) {
        this.medicineID = medicineID;
    }

    /**
     * Gets the quantity of the prescribed medication.
     *
     * @return the medicine quantity
     */
    public int getMedicineQuantity() {
        return medicineQuantity;
    }

    /**
     * Sets the quantity of the prescribed medication.
     *
     * @param medicineQuantity the quantity of medicine to set
     */
    public void setMedicineQuantity(int medicineQuantity) {
        this.medicineQuantity = medicineQuantity;
    }

    /**
     * Gets the period (in days) for which the prescribed medication is to be taken.
     *
     * @return the period in days
     */
    public int getPeriodDays() {
        return periodDays;
    }

    /**
     * Sets the period (in days) for which the prescribed medication is to be taken.
     *
     * @param periodDays the period in days to set
     */
    public void setPeriodDays(int periodDays) {
        this.periodDays = periodDays;
    }

    /**
     * Gets the current status of the prescribed medication (e.g., active, completed).
     *
     * @return the prescription status
     */
    public enums.PrescriptionStatus getPrescriptionStatus() {
        return PrescriptionStatus;
    }

    /**
     * Sets the status of the prescribed medication.
     *
     * @param prescriptionStatus the prescription status to set
     */
    public void setPrescriptionStatus(enums.PrescriptionStatus prescriptionStatus) {
        PrescriptionStatus = prescriptionStatus;
    }

    /**
     * Gets the dosage instructions for the prescribed medication.
     *
     * @return the dosage instructions
     */

    public String getDosage() {
        return dosage;
    }

    /**
     * Sets the dosage instructions for the prescribed medication.
     *
     * @param dosage the dosage instructions to set
     */

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    /**
     * Gets the prescribed medication ID.
     *
     * @return the dosage instructions
     */

    public String getPrescribedMedID() {
        return prescribedMedID;
    }

    public void setPrescribedMedID(String prescribedMedID) {
        this.prescribedMedID = prescribedMedID;
    }
}

