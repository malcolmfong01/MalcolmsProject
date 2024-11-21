package enums;

/**
 * Represents the different statuses a record can have in the system.
 * This enum defines the following statuses:
 * <ul>
 *     <li><b>ACTIVE:</b> The record is currently in use and operational.</li>
 *     <li><b>INACTIVE:</b> The record is not actively used but remains available for reference.</li>
 *     <li><b>ARCHIVED:</b> The record is stored for long-term reference and is not part of active operations.</li>
 *     <li><b>DELETED:</b> The record is marked as deleted (soft delete) and is no longer accessible in normal operations.</li>
 * </ul>
 */
public enum RecordStatus {
    ACTIVE,
    INACTIVE,
    ARCHIVED,
    DELETED;

    /**
     * Provides a user-friendly string representation of the record status.
     *
     * @return A string representation of the status, such as "ACTIVE" or "DELETED".
     */
    @Override
    public String toString() {
        return switch (this) {
            case ACTIVE -> "ACTIVE";
            case INACTIVE -> "INACTIVE";
            case ARCHIVED -> "ARCHIVED";
            case DELETED -> "DELETED";
        };
    }

    /**
     * Converts a string representation of a record status to its corresponding enum value.
     *
     * @param status A string representing the status, such as "ACTIVE" or "DELETED".
     * @return The corresponding {@code RecordStatus} enum value, or {@code null} if the input is invalid.
     */
    public static RecordStatus toEnumRecordStatusType (String status) {
        return switch (status.toUpperCase()) {
            case "ACTIVE" -> ACTIVE;
            case "INACTIVE" -> INACTIVE;
            case "ARCHIVED" -> ARCHIVED;
            case "DELETED" -> DELETED;
            default -> null; // Returns null for unrecognized statuses
        };
    }
}
