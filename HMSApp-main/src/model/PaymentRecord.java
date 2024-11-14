package model;

import java.time.LocalDateTime;

import controller.RecordsController;
import repository.RecordFileType;

public class PaymentRecord extends HMSRecords {
    private double paymentAmount;
    private String patientID;

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

    // Getters and Setters
    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }
}