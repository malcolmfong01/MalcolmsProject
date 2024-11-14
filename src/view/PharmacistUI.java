package view;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import HMSApp.HMSMain;
import model.Admin;
import model.Doctor;
import model.HMSPersonnel;
import model.Medicine;
import model.Pharmacist;
import repository.MedicineRepository;
import repository.PersonnelFileType;
import repository.PersonnelRepository;
import repository.Repository;
import controller.*;
import enums.ReplenishStatus;
import helper.Helper;


public class PharmacistUI extends MainUI{
	private Pharmacist pharmacist;
	
    public PharmacistUI() {
    	this.pharmacist = (Pharmacist) HMSPersonnelController.getPersonnelByUID(AuthenticationController.cookie.getUid(), PersonnelFileType.PHARMACISTS);
    }
    
	@Override
	protected void printChoice() {
		System.out.printf("Welcome! Pharmacist --- %s ---\n", pharmacist.getFullName());
		printBreadCrumbs("HMS App UI > Pharmacist Dashboard");
        System.out.println("Pharmacist Menu:");
        System.out.println("1. View Appointment Outcome Record");
        System.out.println("2. Update Prescription Status");
        System.out.println("3. View Medication Inventory");
        System.out.println("4. Submit Replenishment Request");
        System.out.println("5. Logout");

		
	}

	@Override
	public void start() {
		showPharmacistDashboard();
	}
    public void showPharmacistDashboard() {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do {
        	printChoice();
            choice = Helper.readInt("");
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

