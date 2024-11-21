package enums;

/**
 * Represents the status of a payment.
 * The possible values are:
 * <ul>
 *     <li><b>OUTSTANDING:</b> Payment is yet to be made.</li>
 *     <li><b>CLEARED:</b> Payment has been processed and cleared.</li>
 * </ul>
 */
public enum PaymentStatus {
    OUTSTANDING, CLEARED;

    /**
     * Returns a string representation of the current payment status.
     *
     * @return A string representing the payment status, such as "OUTSTANDING" or "CLEARED".
     */
    @Override
    public String toString() {
        return switch (this) {
            case OUTSTANDING -> "OUTSTANDING";
            case CLEARED -> "CLEARED";
        };
    }

    /**
     * Converts a string to the corresponding {@code PaymentStatus} enum value.
     *
     * @param status A string representing the payment status (e.g., "OUTSTANDING", "CLEARED").
     * @return The corresponding {@code PaymentStatus} enum value, or {@code null} if the string does not match any valid status.
     */
    public static PaymentStatus toEnumRecordStatusType(String status) {
        return switch (status) {
            case "OUTSTANDING" -> OUTSTANDING;
            case "CLEARED" -> CLEARED;
            default -> null; // or throw an exception if you want to handle invalid inputs differently
        };
    }
}
