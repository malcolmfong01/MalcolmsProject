package boundary;
import java.util.Scanner;
import HMSApp.HMSMain;
import model.Pharmacist;
import controller.*;
import enums.PersonnelFileType;
import helper.Validator;

/**
 * Represents the user interface for a pharmacist in the Health Management System (HMS).
 * This UI provides pharmacists with functionalities such as viewing appointment outcome records,
 * updating prescription statuses, viewing and managing inventory, and submitting replenishment requests.
 */
public class PharmacistBoundary extends MainUI{
	private Pharmacist pharmacist;
    /**
     * Constructs a new PharmacistBoundary instance and initializes the pharmacist object
     * using the UID of the currently authenticated user.
     */
    public PharmacistBoundary() {
    	this.pharmacist = (Pharmacist) HMSPersonnelController.getPersonnelByUID(AuthenticationController.cookie.getUid(), PersonnelFileType.PHARMACISTS);
    }
    /**
     * Prints the menu options available to the pharmacist.
     * Displays the pharmacist's name and the list of available actions.
     */
	@Override
	protected void printChoice() {
		System.out.printf("Welcome! Pharmacist --- %s ---\n", pharmacist.getFullName());
        System.out.println("Pharmacist Menu:");
        System.out.println("1. View Appointment Outcome Record");
        System.out.println("2. Update Prescription Status");
        System.out.println("3. View Medication Inventory");
        System.out.println("4. Submit Replenishment Request");
        System.out.println("5. Logout");
        System.out.print("Enter your choice: ");

		
	}
    /**
     * Starts the main interaction loop for the pharmacist UI.
     * Displays the menu and handles user input for each menu option.
     */
	@Override
	public void start() {
		showPharmacistDashboard();
	}
    /**
     * Displays the pharmacist's dashboard and handles user interaction.
     * Provides options to view appointment records, update prescriptions,
     * monitor inventory, submit replenishment requests, or log out.
     */
    public void showPharmacistDashboard() {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do {
        	printChoice();
            choice = Validator.readInt("");
            switch(choice) {
            case 1:
        	  // Call the method to view appointment outcome records
        	  PharmacistController.viewAppointmentOutcomeRecords();
          		break;
          	case 2:
          		// Call the method to update prescription status
          		PharmacistController.updatePrescriptionStatus();
          	break;
            case 3:
                // Call the method to view medication inventory
                PharmacistController.monitorInventory();
                break;
            case 4:
                // Call the method to submit replenishment request
                PharmacistController.submitReplenishmentRequests();
                break;
            case 5:
                System.out.println("Logging out...");
                HMSMain.main(null); // Return to the main application
                break;
            default:
                System.out.println("Invalid choice! Please try again.");
        }
    } while(choice != 5);
        sc.close(); // Close the Scanner
    }
}
