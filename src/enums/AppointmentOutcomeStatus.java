package enums;
/**
 * Enum representing the status of an appointment outcome.
 * It has two possible values: INCOMPLETED and COMPLETED.
 */
public enum AppointmentOutcomeStatus {
    INCOMPLETED, COMPLETED;
    /**
     * Overrides the default toString() method to provide a string representation of
     * the AppointmentOutcomeStatus enum.
     *
     * @return A string representing the status (either "INCOMPLETED" or "COMPLETED").
     */
    @Override
    public String toString() {
        switch (this) {
            case INCOMPLETED:
                return "INCOMPLETED";
            case COMPLETED:
                return "COMPLETED";
            default:
                return "UNKNOWN";
        }
    }
    /**
     * Converts a string to the corresponding AppointmentOutcomeStatus enum value.
     *
     * @param status The string representation of the appointment outcome status.
     * @return The corresponding AppointmentOutcomeStatus enum value, or null if the input string is not valid.
     */
    public static AppointmentOutcomeStatus toEnumAppointmentOutcomeStatus(String status) {
        switch (status.toUpperCase()) {
            case "INCOMPLETED":
                return INCOMPLETED;
            case "COMPLETED":
                return COMPLETED;
            default:
                return null; // or throw an exception if you want to handle invalid inputs differently
        }
    }
}
