package enums;

public enum AppointmentOutcomeStatus {
    INCOMPLETED, COMPLETED;

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
