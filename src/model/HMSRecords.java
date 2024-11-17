package model;

import java.time.LocalDateTime;

import controller.RecordsController;
import enums.RecordFileType;
/**
 * Abstract class representing a generic record in the Hospital Management System (HMS).
 * This class contains common fields such as record ID, creation date, update date, and status
 * for all types of records in the system.
 */
public abstract class HMSRecords {
    private String recordID;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private RecordStatusType recordStatus;

    /**
     * Constructs an HMSRecords object with the specified details.
     *
     * @param recordID the unique identifier for the record
     * @param createdDate the date and time when the record was created
     * @param updatedDate the date and time when the record was last updated
     * @param recordStatus the status of the record (e.g., active, inactive)
     */
    public HMSRecords(String recordID, LocalDateTime createdDate, LocalDateTime updatedDate,
            RecordStatusType recordStatus) {
        this.recordID = recordID;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.recordStatus = recordStatus;
    }

    /**
     * Gets the unique identifier for the record.
     *
     * @return the record ID
     */
    public String getRecordID() {
        return recordID;
    }

    /**
     * Sets the unique identifier for the record.
     *
     * @param recordID the record ID to set
     */
    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    /**
     * Gets the date and time when the record was created.
     *
     * @return the creation date
     */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the date and time when the record was created.
     *
     * @param createdDate the creation date to set
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Gets the date and time when the record was last updated.
     *
     * @return the updated date
     */
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    /**
     * Sets the date and time when the record was last updated.
     *
     * @param updatedDate the updated date to set
     */
    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * Gets the status of the record.
     *
     * @return the record status
     */
    public RecordStatusType getRecordStatus() {
        return recordStatus;
    }

    /**
     * Sets the status of the record.
     *
     * @param recordStatus the status to set
     */
    public void setRecordStatus(RecordStatusType recordStatus) {
        this.recordStatus = recordStatus;
    }

}
