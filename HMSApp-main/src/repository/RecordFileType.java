package repository;

public enum RecordFileType {
    MEDICAL_RECORDS("medicalRecords"),
    APPOINTMENT_RECORDS("appointmentRecords"),
    PAYMENT_RECORDS("paymentRecords"),
	APPOINTMENT_OUTCOME_RECORDS("appointmentOutcomeRecords"),
	DIAGNOSIS_RECORDS("diagnosisRecords"), 
	MEDICINE_RECORDS("medicineRecords");

    public final String fileName;

    RecordFileType(String fileName) {
        this.fileName = fileName;
    }
}