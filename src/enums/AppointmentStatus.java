package enums;

/**
 * Represents the various statuses an appointment can have in the system.
 * The possible values are:
 * <ul>
 *     <li><b>CONFIRMED:</b> The appointment is confirmed.</li>
 *     <li><b>CANCELED:</b> The appointment has been canceled.</li>
 *     <li><b>COMPLETED:</b> The appointment has been completed.</li>
 *     <li><b>AVAILABLE:</b> The appointment slot is available for scheduling.</li>
 *     <li><b>PENDING:</b> The appointment is pending confirmation.</li>
 * </ul>
 */
public enum AppointmentStatus {
    CONFIRMED, CANCELED, COMPLETED, AVAILABLE, PENDING;

    /**
     * Returns a string representation of the current appointment status.
     *
     * @return A string representing the status, such as "PENDING", "CONFIRMED", "CANCELED", etc.
     */
    @Override
    public String toString() {
        return switch (this) {
            case PENDING -> "PENDING";
            case CONFIRMED -> "CONFIRMED";
            case CANCELED -> "CANCELED";
            case COMPLETED -> "COMPLETED";
            case AVAILABLE -> "AVAILABLE";
        };
    }

    /**
     * Converts a string representation of an appointment status to the corresponding enum value.
     *
     * @param status A string representing the appointment status (e.g., "PENDING", "CONFIRMED", "CANCELED").
     * @return The corresponding {@code AppointmentStatus} enum value, or {@code null} if the input string does not match any valid status.
     */
    public static AppointmentStatus toEnumAppointmentStatus(String status) {
        return switch (status) {
            case "PENDING" -> PENDING;
            case "CONFIRMED" -> CONFIRMED;
            case "CANCELED" -> CANCELED;
            case "COMPLETED" -> COMPLETED;
            case "AVAILABLE" -> AVAILABLE;
            default -> null;
        };
    }
}
