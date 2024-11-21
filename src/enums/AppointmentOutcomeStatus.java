package enums;

/**
 * Represents the status of an appointment outcome in the system.
 * This enum defines the following statuses:
 * <ul>
 *     <li><b>INCOMPLETED:</b> The appointment outcome has not been fully completed.</li>
 *     <li><b>COMPLETED:</b> The appointment outcome has been successfully completed.</li>
 * </ul>
 */
public enum AppointmentOutcomeStatus {
    INCOMPLETED, COMPLETED;

    /**
     * Provides a string representation of the appointment outcome status.
     *
     * @return A string representing the status, either "INCOMPLETED" or "COMPLETED".
     */
    @Override
    public String toString() {
        return switch (this) {
            case INCOMPLETED -> "INCOMPLETED";
            case COMPLETED -> "COMPLETED";
        };
    }

    /**
     * Converts a string representation of an appointment outcome status to its corresponding enum value.
     *
     * @param status A string representing the status, such as "INCOMPLETED" or "COMPLETED".
     * @return The corresponding {@code AppointmentOutcomeStatus} enum value, or {@code null} if the input does not match any status.
     */
    public static AppointmentOutcomeStatus toEnumAppointmentOutcomeStatus(String status) {
        return switch (status.toUpperCase()) {
            case "INCOMPLETED" -> INCOMPLETED;
            case "COMPLETED" -> COMPLETED;
            default -> null; // Returns null for unrecognized statuses
        };
    }
}

