package model;
/**
 * Enum representing the different statuses of a record.
 * This is used to track the lifecycle state of records in the system.
 */
public enum RecordStatusType {
    ACTIVE,     // Record is actively in use
    INACTIVE,   // Record is not in use but available
    ARCHIVED,   // Record is archived for future reference
    DELETED;    // Record is marked as deleted (soft delete)

    /**
     * Converts the enum value to a string representation.
     * 
     * @return a string representing the status of the record
     */
    // Optional: You can define methods to return more information about the status
    @Override
    public String toString() {
        switch (this) {
            case ACTIVE:
                return "ACTIVE";
            case INACTIVE:
                return "INACTIVE";
            case ARCHIVED:
                return "ARCHIVED";
            case DELETED:
                return "DELETED";
            default:
                return "Unknown";
        }
    }

    /**
     * Converts a string to its corresponding RecordStatusType enum value.
     * 
     * @param status the string representation of the status
     * @return the corresponding RecordStatusType enum value, or null if the input is invalid
     */
    public static RecordStatusType toEnumRecordStatusType(String status) {
        switch (status.toUpperCase()) {
            case "ACTIVE":
                return ACTIVE;
            case "INACTIVE":
                return INACTIVE;
            case "ARCHIVED":
                return ARCHIVED;
            case "DELETED":
                return DELETED;
            default:
                return null;  // or throw an exception if you want to handle invalid inputs differently
        }
    }
}