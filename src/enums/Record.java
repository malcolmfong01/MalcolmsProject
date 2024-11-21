package enums;

/**
 * Enum representing different types of record files within the system.
 * Each enum value is associated with a specific file name for record management.
 */

public enum Record {
    MEDICAL_RECORDS("medicalRecords"),
    APPOINTMENT_RECORDS("appointmentRecords"),
    PAYMENT_RECORDS("paymentRecords"),
	APPOINTMENT_OUTCOME_RECORDS("appointmentOutcomeRecords"),
	DIAGNOSIS_RECORDS("diagnosisRecords"),
    PRESCRIBED_RECORDS("prescribedRecords"),
	MEDICINE_RECORDS("medicineRecords");

    /**
     * The file name associated with the record type.
     */

    public final String fileName;

    /**
     * Constructor to associate each Record with a corresponding file name.
     *
     * @param fileName The file name associated with the record type.
     */

    Record(String fileName) {
        this.fileName = fileName;
    }
}