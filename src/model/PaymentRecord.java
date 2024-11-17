package model;

import java.time.LocalDateTime;

import controller.RecordsController;
import enums.RecordFileType;

/**
 * Represents a payment record in the system. Inherits from the HMSRecords class to include
 * the basic record details and adds specific information related to the payment such as
 * the amount and the associated patient ID.
 */
public class PaymentRecord extends HMSRecords {
    private double paymentAmount;
    private String patientID;


    /**
     * Constructs a PaymentRecord object with the specified details.
     *
     * @param createdDate the date and time when the payment record was created
     * @param updatedDate the date and time when the payment record was last updated
     * @param recordStatus the status of the payment record (ACTIVE, INACTIVE, etc.)
     * @param patientID the ID of the patient associated with the payment
     * @param paymentAmount the amount paid by the patient
     */
    // Constructor
    public PaymentRecord(
            LocalDateTime createdDate,
            LocalDateTime updatedDate,
            RecordStatusType recordStatus,
            String patientID,
            double paymentAmount) {
        super(RecordsController.generateRecordID(RecordFileType.PAYMENT_RECORDS), createdDate, updatedDate,
                recordStatus);
        this.patientID = patientID;
        this.paymentAmount = paymentAmount;
    }

    
    /**
     * Constructs a PaymentRecord object from CSV data.
     *
     * @param recordID the unique ID of the payment record
     * @param createdDate the date and time when the payment record was created
     * @param updatedDate the date and time when the payment record was last updated
     * @param recordStatus the status of the payment record (ACTIVE, INACTIVE, etc.)
     * @param patientID the ID of the patient associated with the payment
     * @param paymentAmount the amount paid by the patient
     */
    // Constructor when convert CSV to object
    public PaymentRecord(
            String recordID,
            LocalDateTime createdDate,
            LocalDateTime updatedDate,
            RecordStatusType recordStatus,
            String patientID,
            double paymentAmount) {
        super(recordID, createdDate, updatedDate, recordStatus);
        this.patientID = patientID;
        this.paymentAmount = paymentAmount;
    }
    
    /**
     * Gets the payment amount associated with this payment record.
     *
     * @return the payment amount
     */
    // Getters and Setters
    public double getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * Sets the payment amount for this payment record.
     *
     * @param paymentAmount the payment amount to set
     */
    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    /**
     * Gets the patient ID associated with this payment record.
     *
     * @return the patient ID
     */
    public String getPatientID() {
        return patientID;
    }

    /**
     * Sets the patient ID for this payment record.
     *
     * @param patientID the patient ID to set
     */
    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }
}