package enums;

/**
 * Enum representing the status of a payment.
 * It has two possible values: OUTSTANDING and CLEARED.
 */
public enum PaymentStatus {
    OUTSTANDING, CLEARED;

    /**
     * Overrides the default toString() method to provide a string representation of
     * the PaymentStatus enum.
     *
     * @return A string representing the status (e.g., "OUTSTANDING", "CLEARED").
     */
    @Override
    public String toString() {
        switch (this) {
            case OUTSTANDING:
                return "OUTSTANDING";
            case CLEARED:
                return "CLEARED";
            default:
                return "Unknown";
        }
    }

    /**
     * Converts a string to the corresponding PaymentStatus enum value.
     *
     * @param status The string representation of the payment status.
     * @return The corresponding PaymentStatus enum value, or null if the input string does not match any status.
     */
    public static PaymentStatus toEnumPaymentStatus(String status) {
        switch (status) {
            case "OUTSTANDING":
                return OUTSTANDING;
            case "CLEARED":
                return CLEARED;
            default:
                return null; // or throw an exception if you want to handle invalid inputs differently
        }
    }
}
