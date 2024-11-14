package model;

import java.time.LocalDateTime;

import controller.RecordsController;
import repository.RecordFileType;

public abstract class HMSRecords {
    private String recordID;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private RecordStatusType recordStatus;

    public HMSRecords(String recordID, LocalDateTime createdDate, LocalDateTime updatedDate,
            RecordStatusType recordStatus) {
        this.recordID = recordID;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.recordStatus = recordStatus;
    }

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public RecordStatusType getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(RecordStatusType recordStatus) {
        this.recordStatus = recordStatus;
    }

}
