package enums;
/**
 * Represents the status of a prescription in the system.
 * This enum defines two possible statuses for a prescription:
 * <ul>
 *     <li>PENDING - Prescription has been issued but not yet dispensed.</li>
 *     <li>DISPENSED - Prescription has been dispensed to the patient.</li>
 * </ul>
 */
public enum PrescriptionStatus {
	PENDING, DISPENSED;
	 /**
     * Returns a user-friendly string representation of the prescription status.
     * @return a string representing the prescription status (either "Pending" or "Dispensed").
     */
	@Override
    public String toString() {
        switch (this) {
        	case PENDING:
        		return "Pending";
            case DISPENSED:
                return "Dispensed";
            default:
                return "Unknown";
        }
    }
	   /**
     * Converts a string value to its corresponding enum.
     * @param status the string representation of the status (e.g., "Pending", "Dispensed").
     * @return the corresponding enum value, or null if the string does not match any status.
     */
    public static PrescriptionStatus toEnumPrescriptionStatus(String status) {
        switch (status) {
        	case "Pending":
        		return PENDING;
            case "Dispensed":
                return DISPENSED;
            
            default:
                return null; // or throw an exception if you want to handle invalid inputs differently
        }
    }


}
