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
import repository.PersonnelRepository;
import repository.Repository;
import controller.*;
import enums.PersonnelFileType;
import enums.ReplenishStatus;
import helper.DateTimePicker;
import helper.Helper;

/**
 * This class represents the User Interface (UI) for the Admin in the Healthcare Management System (HMS).
 * It allows the Admin to manage hospital staff, view appointments, and manage the medication inventory.
 * It also supports the addition, update, removal of hospital staff and medicines.
 */

public class AdminUI extends MainUI {
	private Admin admin;
	/**
     * Constructs an AdminUI instance for the given Admin.
     * 
     * @param admin The admin for whom the UI is created.
     */
    public AdminUI(Admin admin) {
        this.admin = admin;
    }
    
    /**
     * Prints the available menu options for the Admin user.
     */
	@Override
    protected void printChoice() {
        System.out.printf("Welcome! Admin --- %s ---\n", admin.getFullName());
        printBreadCrumbs("HMS App UI > Admin Dashboard");
        System.out.println("Administrator Menu:");
        System.out.println("1. View and Manage Hospital Staff");
        System.out.println("2. View Appointments Details");
        System.out.println("3. View and Manage Medication Inventory ");
        System.out.println("4. Approve Replenishment Requests");
        System.out.println("5. Logout");
    }
    
	/**
     * Starts the Admin UI by showing the Admin Dashboard.
     */
	public void start() {
		showAdminDashboard();
	}
	
	/**
     * Displays the Admin Dashboard and handles the Admin's menu choices.
     */
    public void showAdminDashboard() {
        int choice = 0;
        do {
            printChoice();
            choice = Helper.readInt("");
            switch (choice) {
                case 1:
                    printBreadCrumbs("HMS App UI > Admin Dashboard > View and Manage Staff");
                    viewAndManageStaff();
                    break;
                case 2: 
                	printBreadCrumbs("HMS App UI > Admin Dashboard > View Appointments");
                	AdminController.listAllAppointments();
                    break;
                case 3:
                    printBreadCrumbs("HMS App UI > Admin Dashboard > View And Manage Medication Inventory");
                    viewAndManageMedicationInventory();
                    break;
                case 4:
                    printBreadCrumbs("HMS App UI > Admin Dashboard > Approve Replenish Request");
                    approveReplenishRequest();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    HMSMain.main(null);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while(choice != 8);
        
    }
    
    /**
     * Displays the options for managing hospital staff (Doctors and Pharmacists).
     */
    public static void viewAndManageStaff() {
    	System.out.println("Enter your choice");
	    System.out.println("1. View Staff By Role");
	    System.out.println("2. View Staff By Gender");
	    System.out.println("3. View Staff By Age");
	    System.out.println("4. Add Doctor");
	    System.out.println("5. Add Pharmacist");
	    System.out.println("6. Update Doctor");
	    System.out.println("7. Update Pharmacist");
	    System.out.println("8. Remove Doctor");
	    System.out.println("9. Remove Pharmacist");
	    
	    AdminUI adminUI = new AdminUI(null);
	    int choice = adminUI.getUserChoice(9);

	    switch (choice) {
	        case 1:
	            AdminController.listPersonnelByRole(PersonnelFileType.DOCTORS);
	            AdminController.listPersonnelByRole(PersonnelFileType.PHARMACISTS);
	            break;
	        case 2:
	            AdminController.listPersonnelByGender("Male");
	            AdminController.listPersonnelByGender("Female");
	            break;
	        case 3:
	            AdminController.listPersonnelByAge();
	            break;
	        case 4:
	        	addPersonnel("Doctor");
	            break;
	        case 5:
	        	addPersonnel("Pharmacist");
	            break;
	        case 6:
	            updatePersonnel("Doctor");
	            break;
	        case 7:
	            updatePersonnel("Pharmacist");
	            break;
	        case 8:
	        	removePersonnel("Doctor");
	            break;
	        case 9:
	        	removePersonnel("Pharmacist");
	            break;
	        default:
	            System.out.println("Error: Invalid choice. Please select a valid option.");
	            break;
	    }
	}
    
    /**
     * Prompts the user to enter details for adding a new staff (Doctor or Pharmacist).
     * 
     * @param role The role of the personnel to be added (Doctor or Pharmacist).
     */
    public static void addPersonnel(String role) {
        System.out.println("Enter Full Name: " );
        String fullName = Helper.readString();
        System.out.println("Enter ID Card: " );
        String idCard = Helper.readString();
        System.out.println("Enter Username: " );
        String username = Helper.readString();
        System.out.println("Enter Email: "  );
        String email = Helper.readString();
        System.out.println("Enter Phone No: " );
        String phoneNo = Helper.readString();
        System.out.println("Enter DOB (YYYY-MM-DD HH:MM):" );
        LocalDateTime DoB = readDate();
        System.out.println("Enter Gender: " );
        String gender = Helper.readString();
        if (role == "Doctor" ) {
        	 System.out.println("Enter Speciality: " );
             String specialty = Helper.readString();
             System.out.println("Enter Medical License Number: " );
             String medicalLicenseNumber = Helper.readString();
             System.out.println("Enter Date Join (YYYY-MM-DD HH:MM): " );
             LocalDateTime dateJoin = readDate();
             System.out.println("Enter Years of Experiences: " );
             int yearsOfExperiences = Helper.readInt("");
             Doctor doctor = new Doctor(fullName,idCard, username, email, phoneNo, "default", 
            		 DoB, gender, specialty, medicalLicenseNumber, dateJoin, yearsOfExperiences);
             HMSPersonnel personnel = (HMSPersonnel) doctor;
             AdminController.addPersonnel(personnel);
        }      
        
        else {
        	System.out.println("Enter Pharmacist License Number: " );
        	String pharmacistLicenseNumber = Helper.readString();
        	System.out.println("Enter Date Of Employment (YYYY-MM-DD HH:MM) : " );
        	LocalDateTime dateOfEmployment = readDate();
        	Pharmacist pharmacist = new Pharmacist(fullName,idCard, username, email, phoneNo, "defaultPassword", DoB, gender,pharmacistLicenseNumber , dateOfEmployment);
        	HMSPersonnel personnel = (HMSPersonnel) pharmacist;
        	AdminController.addPersonnel(personnel);
        }
    }
    
    /**
     * Prompts the user to enter details for updating an existing staff (Doctor or Pharmacist).
     * 
     * @param role The role of the personnel to be updated (Doctor or Pharmacist).
     */
    public static void updatePersonnel(String role) {
        String UID = Helper.readString("Enter UID: ");
        Doctor doctor = (Doctor) HMSPersonnelController.getPersonnelByUID(UID, PersonnelFileType.DOCTORS);
        Pharmacist pharmacist = (Pharmacist) HMSPersonnelController.getPersonnelByUID(UID,
                PersonnelFileType.PHARMACISTS);

        if (doctor == null && pharmacist == null) {
            System.out.println("Personnel does not exist!");
            return;
        }

        String username = Helper.readString("Enter New Username: ");
        String email = Helper.readString("Enter New Email: ");
        String phoneNo = Helper.readString("Enter New Phone No: ");
        String hashedPassword = Helper.readString("Enter New Password: ");

        if (role.equals("Doctor")) {
            String specialty = Helper.readString("Enter New Specialty: ");
            String medicalLicenseNumber = Helper.readString("Enter New Medical License Number: ");
            int yearsOfExperiences = Helper.readInt("Enter New Years of Experience: ");

            doctor.setUsername(username);
            doctor.setEmail(email);
            doctor.setPhoneNo(phoneNo);
            doctor.setPasswordHash(hashedPassword);
            doctor.setSpecialty(specialty);
            doctor.setMedicalLicenseNumber(medicalLicenseNumber);
            doctor.setYearsOfExperiences(yearsOfExperiences);

            AdminController.updatePersonnel(doctor.getUID(), doctor);
        } else {
            String pharmacistLicenseNumber = Helper.readString("Enter New Pharmacist License Number: ");

            pharmacist.setUsername(username);
            pharmacist.setEmail(email);
            pharmacist.setPhoneNo(phoneNo);
            pharmacist.setPasswordHash(hashedPassword);
            pharmacist.setPharmacistLicenseNumber(pharmacistLicenseNumber);

            AdminController.updatePersonnel(pharmacist.getUID(), pharmacist);
        }
    }
    
    /**
     * Prompts the user to enter the UID of a staff member to remove (Doctor or Pharmacist).
     * 
     * @param role The role of the personnel to be removed (Doctor or Pharmacist).
     */
    public static void removePersonnel(String role) {
    	System.out.println("Enter Staff UID: ");
    	if(role == "Doctor") {
    		String uidDoctor = Helper.readString();
            AdminController.removePersonnel(uidDoctor, PersonnelFileType.DOCTORS);
    	}
    	else {
    		String uidPharmacist = Helper.readString();
            AdminController.removePersonnel(uidPharmacist, PersonnelFileType.PHARMACISTS);
    	}
    }  
    
    /**
     * Displays options for managing the medication inventory.
     */
    public static void viewAndManageMedicationInventory() {
    	System.out.println("Enter your choice");
		System.out.println("1. View All Medicine");
	    System.out.println("2. Add Medicine");
	    System.out.println("3. Update Medicine");
	    System.out.println("4. Remove Medicine");
	    int choice = Helper.readInt("");
	    switch(choice) {
	    	case 1: MedicineController.listAllMedicines();
	    	break;
	    	case 2: addMedicine();
	    	break;
	    	case 3: updateMedicine();
	    	break;
	    	case 4: removeMedicine();
	    	break;
	    	default: System.out.println("Error: Invalid choice. Please select a valid option.");
	    	break;
	    }
	}
    
    /**
     * Prompts the user to enter details for adding a new medicine to the inventory.
     */
    public static void addMedicine() {
        String medicineID = Helper.readString("Enter Medicine ID: ");
        String name = Helper.readString("Enter Medicine Name: ");
        String manufacturer = Helper.readString("Enter Manufacturer: ");
        LocalDateTime expiryDate = DateTimePicker.pickDateTime("Enter Expiry Date: ");
        int inventoryStock = Helper.readInt("Enter Inventory Stock: ");
        int lowStockLevel = Helper.readInt("Enter Low Stock Level: ");
        System.out.print("Enter Replenish Status: ");
        ReplenishStatus status = ReplenishStatus.NULL;
        String dateTimeString = "0001-01-01 00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime date = LocalDateTime.parse(dateTimeString, formatter);
        Medicine medicine = new Medicine(medicineID, name, manufacturer, expiryDate,
                inventoryStock, lowStockLevel, status, date, date);
        MedicineController.addMedicine(medicine);
    }
    
    /**
     * Prompts the user to update the details of an existing medicine in the inventory.
     */
    public static void updateMedicine() {
        String medicineID = Helper.readString("Enter Medicine ID: ");
        Medicine medicine = MedicineController.getMedicineByUID(medicineID);
        String manufacturer = Helper.readString("Enter New Manufacturer: ");
        medicine.setManufacturer(manufacturer);
        LocalDateTime expiryDate = DateTimePicker.pickDateTime("Enter New Expiry Date (YYYY-MM-DD HH:MM:): ");
        medicine.setExpiryDate(expiryDate);
        int inventoryStock = Helper.readInt("Enter New Inventory Stock: ");
        medicine.setInventoryStock(inventoryStock);
        int lowStockLevel = Helper.readInt("Enter New Low Stock Level: ");
        medicine.setLowStockLevel(lowStockLevel);
        MedicineController.updateMedicine(medicineID, medicine);
    }
    
    /**
     * Prompts the user to remove a medicine from the inventory.
     */
    public static void removeMedicine() {
        String medicineID = Helper.readString("Enter Medicine ID: ");
        MedicineController.removeMedicine(medicineID);
    }
	
    /**
     * Approves a replenish request for a particular medicine.
     */
    public static void approveReplenishRequest() {

        String medicineID = Helper.readString("Enter Medicine ID: ");
        Medicine medicine = MedicineController.getMedicineByUID(medicineID);
        if(medicine == null) {
        	return;
        }
        if(medicine.getReplenishStatus()!= ReplenishStatus.REQUESTED) {
        	System.out.println("No Replenish Request For This Medicine!");
        	return;
        }
        String manufacturer = Helper.readString("Enter New Manufacturer: ");
        medicine.setManufacturer(manufacturer);

        LocalDateTime expiryDate = DateTimePicker.pickDateTime("Enter New Expiry Date (YYYY-MM-DD HH:MM:): ");
        medicine.setExpiryDate(expiryDate);

        int inventoryStock = Helper.readInt("Enter New Inventory Stock: ");
        medicine.setInventoryStock(inventoryStock);

        int lowStockLevel = Helper.readInt("Enter New Low Stock Level: ");
        medicine.setLowStockLevel(lowStockLevel);

        medicine.setReplenishStatus(ReplenishStatus.APPROVED);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedDateTime = currentDateTime.format(formatter);
        LocalDateTime approvedDate = LocalDateTime.parse(formattedDateTime, formatter);
        medicine.setApprovedDate(approvedDate);
        AdminController.approveReplenishRequest(medicineID, medicine);
    }

    
    /**
     * Reads a valid date from the user input.
     * 
     * @return The parsed date as a LocalDateTime object.
     */
    public static LocalDateTime readDate() {
    	LocalDateTime date = null;
        boolean valid = false;
        while (!valid) {
            String input = Helper.readString();

            try {
                // Parse the input into LocalDateTime
                date = LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                valid = true; // Input is valid, exit the loop
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD HH:MM.");
            }
        }
        return date;
    }
    

   
    
   
    

}
