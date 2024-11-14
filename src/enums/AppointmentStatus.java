package enums;

import model.RecordStatusType;

public enum AppointmentStatus {
    CONFIRMED, CANCELED, COMPLETED, AVAILABLE, PENDING;

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
