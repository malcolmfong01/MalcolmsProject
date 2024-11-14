package enums;

public enum PrescriptionStatus {
	PENDING, DISPENSED;
	
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
