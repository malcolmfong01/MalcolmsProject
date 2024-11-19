package enums;

/**
 * Enum representing the status of an appointment.
 * It has multiple possible values: CONFIRMED, CANCELED, COMPLETED, AVAILABLE, and PENDING.
 */
public enum AppointmentStatus {
    CONFIRMED, CANCELED, COMPLETED, AVAILABLE, PENDING;
    /**
     * Overrides the default toString() method to provide a string representation of
     * the AppointmentStatus enum.
     *
     * @return A string representing the status (e.g., "PENDING", "CONFIRMED", "CANCELED", etc.).
     */
    @Override
    public String toString() {
        switch (this) {
            case PENDING:
                return "PENDING";
            case CONFIRMED:
                return "CONFIRMED";
            case CANCELED:
                return "CANCELED";
            case COMPLETED:
                return "COMPLETED";
            case AVAILABLE:
                return "AVAILABLE";
            default:
                return "Unknown";
        }
    }
    /**
     * Converts a string to the corresponding AppointmentStatus enum value.
     *
     * @param status The string representation of the appointment status.
     * @return The corresponding AppointmentStatus enum value, or null if the input string does not match any status.
     */
    public static AppointmentStatus toEnumAppointmentStatus(String status) {
        switch (status) {
            case "PENDING":
                return PENDING;
            case "CONFIRMED":
                return CONFIRMED;
            case "CANCELED":
                return CANCELED;
            case "COMPLETED":
                return COMPLETED;
            case "AVAILABLE":
                return AVAILABLE;
            default:
                return null; // or throw an exception if you want to handle invalid inputs differently
        }
    }

}
