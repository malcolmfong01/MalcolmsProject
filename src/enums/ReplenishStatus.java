package enums;
/**
 * Represents the status of a medication replenishment request.
 * This enum defines three possible states:
 * <ul>
 *     <li>NULL - No replenishment status is specified.</li>
 *     <li>REQUESTED - A replenishment request has been made.</li>
 *     <li>APPROVED - The replenishment request has been approved.</li>
 * </ul>
 */
public enum ReplenishStatus {
	 /**
     * Indicates that no replenishment status is specified.
     */
    NULL,
    /**
     * Indicates that a replenishment request has been made.
     */
    REQUESTED,
    /**
     * Indicates that a replenishment request has been approved.
     */
    APPROVED
}
