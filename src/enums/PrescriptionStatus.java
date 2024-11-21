package enums;

/**
 * Represents the status of a prescription in the system.
 * This enum defines the following statuses:
 * <ul>
 *     <li><b>PENDING:</b> The prescription has been issued but not yet dispensed.</li>
 *     <li><b>DISPENSED:</b> The prescription has been dispensed to the patient.</li>
 * </ul>
 */
public enum PrescriptionStatus {
    PENDING, DISPENSED;

    /**
     * Provides a user-friendly string representation of the prescription status.
     *
     * @return A string representation of the status, either "Pending" or "Dispensed".
     */
    @Override
    public String toString() {
        return switch (this) {
            case PENDING -> "Pending";
            case DISPENSED -> "Dispensed";
        };
    }

    /**
     * Converts a string representation of a prescription status to its corresponding enum value.
     *
     * @param status A string representing the status, such as "Pending" or "Dispensed".
     * @return The corresponding {@code PrescriptionStatus} enum value, or {@code null} if the input does not match any status.
     */
    public static PrescriptionStatus toEnumPrescriptionStatus(String status) {
        return switch (status) {
            case "Pending" -> PENDING;
            case "Dispensed" -> DISPENSED;
            default -> null; // Returns null for unrecognized statuses
        };
    }
}

