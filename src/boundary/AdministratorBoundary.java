package boundary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import Main.Main;
import enums.*;
import enums.User;
import model.*;
import repository.RecordsRepository;
import repository.UserRepository;
import utility.Validator;
import repository.MedicineRepository;
import controller.*;
import utility.DateTime;



/**
 * AdministratorBoundary class represents the user interface for an administrator in the HMS
 * system.
 * This class handles administrator-specific interactions.
 */

public class AdministratorBoundary extends Boundary {
	private final Administrator administrator;

	/**
     * Constructs an AdministratorBoundary instance for the given Administrator.
     * 
     * @param administrator The administrator for whom the UI is created.
     */

    public AdministratorBoundary(Administrator administrator) {
        this.administrator = administrator;
    }
    /**
     * Displays the administrator menu options.
     */
	@Override
    protected void printChoice() {
        System.out.printf("Welcome! Administrator --- %s ---\n", administrator.getFullName());
        System.out.println("Administrator Menu:");
        System.out.println("1. View and Manage Hospital User");
        System.out.println("2. View Appointments Details");
        System.out.println("3. View and Manage Medication Inventory");
        System.out.println("4. Approve Replenishment Requests");
        System.out.println("5. View and Manage Billing Information");
        System.out.println("6. Logout");
    }
    
	/**
     * Starts the Administrator UI by showing the Administrator Dashboard.
     */
	public void start() {
		showAdminDashboard();
	}

	/**
     * Displays the Administrator Dashboard and handles the Administrator menu choices.
     */

    public void showAdminDashboard() {
        while (true) {
            printChoice();
            int choice = Validator.readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> viewAndManageStaff();
                case 2 -> listAllAppointments();
                case 3 -> viewAndManageMedicationInventory();
                case 4 -> approveReplenishRequest();
                case 5 -> viewAndManageBilling();
                case 6 -> {
                    System.out.println("Logging out...");
                    Main.main(null); // Restart application
                    return; // Exit after logging out
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    /**
     * Administrator Menu Option 1
     * Displays the options for managing hospital staff (Doctors and Pharmacists).
     */

    private static void viewAndManageStaff() {
        System.out.println("1. View User By Role");
        System.out.println("2. View User By Gender");
        System.out.println("3. View User By Age");
        System.out.println("4. Add Doctor");
        System.out.println("5. Add Pharmacist");
        System.out.println("6. Update Doctor");
        System.out.println("7. Update Pharmacist");
        System.out.println("8. Remove Doctor");
        System.out.println("9. Remove Pharmacist");
        System.out.println("Enter your choice");
        int choice = Validator.readInt("");
        switch (choice) {
            case 1 -> {
                listStaffByRole(User.DOCTORS);
                listStaffByRole(User.PHARMACISTS);
            }
            case 2 -> {
                listStaffByGender("M");
                listStaffByGender("F");
            }
            case 3 -> listStaffByAge();
            case 4 -> addStaff("Doctor");
            case 5 -> addStaff("Pharmacist");
            case 6 -> updateStaff("Doctor");
            case 7 -> updateStaff("Pharmacist");
            case 8 -> removeStaff("Doctor");
            case 9 -> removeStaff("Pharmacist");
            default -> System.out.println("Error: Invalid choice. Please select a valid option.");
        }
    }


    /**
     * Prompts the user to enter details for adding a new staff (Doctor or Pharmacist).
     * 
     * @param role The role of the staff to be added (Doctor or Pharmacist).
     */

    private static void addStaff(String role) {
        System.out.println("Enter Full Name: " );
        String fullName = Validator.readString();
        System.out.println("Enter Username: " );
        String username = Validator.readString();
        String email = Validator.readEmail("Enter Email: ");
        String phoneNo = Validator.readValidPhoneNumber("Enter phone number:" );
        LocalDateTime DoB = Validator.readDate("Enter date of birth (yyyy-MM-dd):");
        String gender = Validator.readGender("Enter gender (M/F):");
        if (role.equals("Doctor")) {
             LocalDateTime dateJoin = LocalDateTime.now();
             Doctor doctor = new Doctor(fullName, username, email, phoneNo, "password",
            		 DoB, gender, dateJoin);
             AdministratorController.addUser(doctor);
        }      
        
        else {
        	LocalDateTime dateOfEmployment = LocalDateTime.now();
        	Pharmacist pharmacist = new Pharmacist(fullName, username, email, phoneNo, "password", DoB, gender , dateOfEmployment);
        	AdministratorController.addUser(pharmacist);
        }
    }
    
    /**
     * Prompts the user to enter details for updating an existing staff (Doctor or Pharmacist).
     * 
     * @param role The role of the staff to be updated (Doctor or Pharmacist).
     */

    private static void updateStaff(String role) {
        String UID = Validator.readString("Enter ID: ");
        model.User personnel = UserController.getUserbyUID(UID,
                role.equals("Doctor") ? User.DOCTORS : User.PHARMACISTS);

        if (personnel == null) {
            System.out.println("Staff does not exist!");
            return;
        }

        String username = Validator.readString("Enter New Username: ");
        while (RegisterController.isUsernameTaken(username,
                role.equals("Doctor") ? UserRepository.DOCTORS : UserRepository.PHARMACISTS)) {
            System.out.println("The username '" + username + "' is already taken. Please enter a new username:");
            username = Validator.readString("Enter a new username: ");
        }

        String email = Validator.readEmail("Enter New Email: ");
        String phoneNo = Validator.readValidPhoneNumber("Enter New Phone No: ");
        String hashedPassword = Validator.readString("Enter New Password: ");

        while (!RegisterController.isValidPassword(hashedPassword)) {
            System.out.println("Password must be at least 8 characters long, contain an uppercase letter, a number, and a special character.");
            hashedPassword = Validator.readString("Enter New Password: ");
        }

        // Update the common attributes
        personnel.setUsername(username);
        personnel.setEmail(email);
        personnel.setPhoneNo(phoneNo);
        personnel.setPasswordHash(hashedPassword);

        // Update the personnel in the repository
        AdministratorController.updatePersonnel(personnel.getUID(), personnel);

        System.out.println(role + " updated successfully.");
    }


    /**
     * Prompts the user to enter the UID of a staff member to remove (Doctor or Pharmacist).
     * 
     * @param role The role of the personnel to be removed (Doctor or Pharmacist).
     */

    private static void removeStaff(String role) {
    	System.out.println("Enter User ID: ");
    	if(role.equals("Doctor")) {
            String uidDoctor = Validator.readID("Doctor", "D\\d{3}");
            System.out.println("Validated Doctor ID: " + uidDoctor);
            AdministratorController.removeUser(uidDoctor, User.DOCTORS);
    	}
    	else {
            String uidPharmacist = Validator.readID("Pharmacist", "PH\\d{3}");
            System.out.println("Validated Pharmacist ID: " + uidPharmacist);
            AdministratorController.removeUser(uidPharmacist, User.PHARMACISTS);
    	}
    }

    /**
     * Administrator Menu Option 2
     * Lists all appointment records in the system.
     */

    private static void listAllAppointments() {

        System.out.println("\n--- All Appointment Records ---");

        boolean found = false;

        for (Appointment appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {

            System.out.println("Appointment ID: " + appointment.getRecordID());
            System.out.println("Patient ID: " + appointment.getPatientID());
            System.out.println("Doctor ID: " + appointment.getDoctorID());
            System.out.println("Appointment Time: "
                    + appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            System.out.println("Location: " + appointment.getLocation());
            System.out.println("Status: " + appointment.getAppointmentStatus());

            AppointmentOutcomeRecord outcome = appointment.getAppointmentOutcomeRecord();
            if (outcome == null) {
                System.out.println("Appointment Outcome Record Not Available");
                singleline();
                continue;
            }
            System.out.println("Appointment Outcome ID: " + outcome.getUID());
            System.out.println("Consultation Notes: " + outcome.getConsultationNotes());
            System.out.println("Service: " + outcome.getTypeOfService());
            System.out.println("Appointment Outcome Status: " + outcome.getAppointmentOutcomeStatus());

            Prescription prescription = outcome.getPrescription();
            if (prescription == null) {
                System.out.println("Prescription Not Available");
                singleline();
                continue;
            }
            System.out.println();
            System.out.println("Prescription ");
            System.out.println("Prescription Date: " + prescription.getPrescriptionDate());
            if (prescription.getMedications() == null) {
                System.out.println("Prescribed Medication Not Available");
                singleline();
                continue;
            }
            for (PrescribedMedication medication : prescription.getMedications()) {
                Medicine medicine = MedicineController.getMedicineByUID(medication.getMedicineID());

                // Null check for medicine before calling getName()
                if (medicine != null) {
                    System.out.println("Medicine name: " + medicine.getName());
                } else {
                    System.out.println("Medicine not found.");
                }

                System.out.println("Period of Days: " + medication.getPeriodDays());
                System.out.println("Quantity: " + medication.getMedicineQuantity());
                System.out.println("Dosage: " + medication.getDosage());
                System.out.println("Prescription Status: " + medication.getPrescriptionStatus());
                System.out.println();
            }
            singleline();
            found = true;
        }

        if (!found) {
            System.out.println("No other appointments found.");
        }

        singleline();
    }


    /**
     * Prints the details of a given staff
     *
     * @param personnel The staff whose details are to be printed.
     */

    private static void printStaffDetails(model.User personnel) {
        headerline();
        System.out.println("Personnel Details:");
        headerline();
        System.out.printf("%-20s: %s%n", "UID", personnel.getUID());
        System.out.printf("%-20s: %s%n", "Full Name", personnel.getFullName());
        System.out.printf("%-20s: %s%n", "Username", personnel.getUsername());
        System.out.printf("%-20s: %s%n", "Email", personnel.getEmail());
        System.out.printf("%-20s: %s%n", "Phone No", personnel.getPhoneNo());
        System.out.printf("%-20s: %s%n", "Password", personnel.getPasswordHash());
        System.out.printf("%-20s: %s%n", "DOB", personnel.getDoB());
        System.out.printf("%-20s: %s%n", "Gender", personnel.getGender());
        System.out.printf("%-20s: %s%n", "Role", personnel.getRole());
        headerline();
    }

    /**
     * Prints the details of a given doctor.
     *
     * @param doctor The doctor whose details are to be printed.
     */
    private static void printDoctorDetails(Doctor doctor) {
        System.out.println("============= Doctor Details ==============");
        printStaffDetails(doctor);
        System.out.printf("%-20s: %s%n", "Date Joined", doctor.getDateJoin());
        System.out.println("===========================================");
        System.out.println();
    }

    /**
     * Prints the details of a given pharmacist.
     *
     * @param pharmacist The pharmacist whose details are to be printed.
     */
    public static void printPharmacistDetails(Pharmacist pharmacist) {
        System.out.println("=========== Pharmacist Details ============");
        printStaffDetails(pharmacist);
        System.out.printf("%-20s: %s%n", "Date of Employment", pharmacist.getDateOfEmployment());
        System.out.println("===========================================");
        System.out.println();
    }

    /**
     * Lists all personnel filtered by role (Doctors or Pharmacists).
     *
     * @param type The type of personnel to list (Doctors or Pharmacists).
     */

    private static void listStaffByRole(User type) {
        Map<String, ? extends model.User> personnelMap;

        switch (type) {
            case DOCTORS:
                personnelMap = UserRepository.DOCTORS;
                break;
            case PHARMACISTS:
                personnelMap = UserRepository.PHARMACISTS;
                break;
            default:
                System.out.println("Error: No staff found.");
                return;
        }

        if (personnelMap != null && !personnelMap.isEmpty()) {
            System.out.println("\nListing all staff of type: " + type);
            System.out.println("==================================================");
            for (model.User staff : personnelMap.values()) {
                if (type == User.DOCTORS) {
                    printDoctorDetails((Doctor) staff);
                } else  {
                    // No check for Pharmacists since it's already assigned
                    printPharmacistDetails((Pharmacist) staff);
                }
            }
            System.out.println("==================================================\n");
        } else {
            System.out.println("No personnel found for type: " + type);
        }
    }

    /**
     * Lists all personnel filtered by gender.
     *
     * @param gender The gender of personnel to list.
     */
    private static void listStaffByGender(String gender) {
        System.out.println("\nListing staff filtered by gender: " + gender);
        System.out.println("===========================================");

        for (Doctor doctor : UserRepository.DOCTORS.values()) {
            if (doctor.getGender().trim().equalsIgnoreCase(gender)) {
                printDoctorDetails(doctor);
            }
        }

        for (Pharmacist pharmacist : UserRepository.PHARMACISTS.values()) {
            if (pharmacist.getGender().trim().equalsIgnoreCase(gender)) {
                printPharmacistDetails(pharmacist);
            }
        }
        System.out.println("===========================================\n");
    }

    /**
     * Lists all personnel sorted by age from oldest to youngest.
     */
    private static void listStaffByAge() {
        List<model.User> combinedList = new ArrayList<>();

        combinedList.addAll(UserRepository.DOCTORS.values());
        combinedList.addAll(UserRepository.PHARMACISTS.values());

        // Sort the combined list by age from oldest to youngest
        combinedList.sort(Comparator.comparingInt(personnel -> calculateAge((model.User) personnel)).reversed());

        System.out.println("\nListing all personnel sorted by age (oldest to youngest):");
        System.out.println("==================================================");
        for (model.User personnel : combinedList) {
            if (personnel instanceof Doctor) {
                printDoctorDetails((Doctor) personnel);
            } else if (personnel instanceof Pharmacist) {
                printPharmacistDetails((Pharmacist) personnel);
            }
        }
        System.out.println("==================================================\n");
    }

    /**
     * Calculates the age of a staff based on their date of birth.
     *
     * @param staff The staff whose age is to be calculated.
     * @return The age in years.
     */

    private static int calculateAge(model.User staff) {
        LocalDate birthDate = staff.getDoB().toLocalDate(); // Ensure this returns LocalDate
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    /**
     * Administrator Menu Option 3
     * Displays options for managing the medication inventory.
     */

    private static void viewAndManageMedicationInventory() {
        System.out.println("1. View All Medicine");
        System.out.println("2. Add Medicine");
        System.out.println("3. Update Medicine");
        System.out.println("4. Remove Medicine");
        System.out.print("Enter your choice: ");

        int choice = Validator.readInt("");

        switch (choice) {
            case 1 -> MedicineController.listAllMedicines(); // View all medicines
            case 2 -> addMedicine(); // Add new medicine
            case 3 -> updateMedicine(); // Update existing medicine
            case 4 -> removeMedicine(); // Remove a medicine
            default -> System.out.println("Error: Invalid choice. Please select a valid option.");
        }
    }


    /**
     * Prompts the user to enter details for adding a new medicine to the inventory.
     */

    private static void addMedicine() {
        String medicineID = MedicineController.getNextMedicineID();
        String name = Validator.readString("Enter Medicine Name: ");
        String manufacturer = Validator.readString("Enter Manufacturer: ");
        LocalDateTime expiryDate = DateTime.pickDateTime("Enter Expiry Date: ");
        int inventoryStock = Validator.readInt("Enter Inventory Stock: ");
        int lowStockLevel = Validator.readInt("Enter Low Stock Level: ");
        int replenishStock = 0;
        System.out.print("Enter Replenish Status: ");
        ReplenishStatus status = ReplenishStatus.NULL;
        String dateTimeString = "0001-01-01 00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime date = LocalDateTime.parse(dateTimeString, formatter);

        // Generate the next medicine ID
        Medicine medicine = new Medicine(medicineID,name, manufacturer, expiryDate,
                inventoryStock, lowStockLevel,replenishStock, status, date, date);
        MedicineController.addMedicine(medicine);
    }
    
    /**
     * Prompts the user to update the details of an existing medicine in the inventory.
     */

    private static void updateMedicine() {
        String medicineID = Validator.readID("Medicine", "M\\d{3}");
        System.out.println("Validated Medicine ID: " + medicineID);
        Medicine medicine = MedicineController.getMedicineByUID(medicineID);
        if (medicine == null) {
            updateMedicine();
            return ;
        }
        String manufacturer = Validator.readString("Enter New Manufacturer: ");
        medicine.setManufacturer(manufacturer);
        LocalDateTime expiryDate = DateTime.pickDateTime("Enter New Expiry Date (YYYY-MM-DD HH:MM:): ");
        medicine.setExpiryDate(expiryDate);
        int inventoryStock = Validator.readInt("Enter New Inventory Stock: ");
        medicine.setInventoryStock(inventoryStock);
        int lowStockLevel = Validator.readInt("Enter New Low Stock Level: ");
        medicine.setLowStockLevel(lowStockLevel);
        MedicineController.updateMedicine(medicineID, medicine);
    }
    
    /**
     * Prompts the user to remove a medicine from the inventory.
     */

    private static void removeMedicine() {
        String medicineID = Validator.readID("Medicine", "M\\d{3}");
        System.out.println("Validated Medicine ID: " + medicineID);
        MedicineController.removeMedicine(medicineID);
    }
	
    /**
     * Administrator Menu Option 4
     * Approves a replenish request for a particular medicine.
     */
    private static void approveReplenishRequest() {

        System.out.println("Replenishment Requests and Status:");

        boolean requestFound = false;
        for (Medicine med : MedicineRepository.MEDICINES.values()) {
            if (med.getReplenishStatus() == ReplenishStatus.REQUESTED) {
                requestFound = true;
                System.out.println("Medicine ID: " + med.getMedicineID());
                System.out.println("Name: " + med.getName());
                System.out.println("Replenish Status: " + med.getReplenishStatus());
                System.out.println("Replenishment Request Date: " + med.getReplenishRequestDate());
                System.out.println();
            }
        }

        if (!requestFound) {
            System.out.println("No replenishment requests found.");
            return ;
        }

        String medicineID = Validator.readString("Enter Medicine ID: ");
        Medicine medicine = MedicineController.getMedicineByUID(medicineID);
        if(medicine == null) {
        	return;
        }
        if(medicine.getReplenishStatus()!= ReplenishStatus.REQUESTED) {
        	System.out.println("No Replenish Request For This Medicine!");
        	return;
        }
        LocalDateTime expiryDate = DateTime.pickDateTime("Enter New Expiry Date (YYYY-MM-DD HH:MM:): ");
        medicine.setExpiryDate(expiryDate);

        medicine.setInventoryStock(medicine.getInventoryStock()+medicine.getReplenishmentStock());

        int lowStockLevel = Validator.readInt("Enter New Low Stock Level: ");
        medicine.setLowStockLevel(lowStockLevel);

        medicine.setReplenishStatus(ReplenishStatus.APPROVED);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedDateTime = currentDateTime.format(formatter);
        LocalDateTime approvedDate = LocalDateTime.parse(formattedDateTime, formatter);
        medicine.setApprovedDate(approvedDate);
        MedicineController.updateMedicine(medicineID, medicine);
    }

    /**
     * Administrator Menu Option 5
     * Displays the options for viewing and managing billing.
     */

    private static void viewAndManageBilling() {
        System.out.println("Enter your choice");
        System.out.println("1. View Billing Information By Patient ID");
        System.out.println("2. Manage Billing Information By Patient ID");
        System.out.println("Enter your choice");
        int choice = Validator.readInt("");

        switch (choice) {
            case 1 -> viewBillingByPatientID();
            case 2 -> manageBilling();
            default -> System.out.println("Error: Invalid choice. Please select a valid option.");
        }
    }

    /**
     * Administrator Menu Option 5
     * View billing by patient ID.
     */

    private static ArrayList <PaymentRecord> viewBillingByPatientID() {

        // Create a list to store matching records
        ArrayList<PaymentRecord> matchingRecords = new ArrayList<>();
        System.out.println("Enter Patient ID");
        // Read the patient ID
        String patientID = Validator.readID("Patient", "P\\d{3}");

        // Check if the repository is loaded
        if (RecordsRepository.isRepoLoad()) {
            for (PaymentRecord record : RecordsRepository.PAYMENT_RECORDS.values()) {
                // If patient ID matches, add the record to the list
                if (record.getPatientID().equals(patientID)) {
                    matchingRecords.add(record);
                }
            }
            // Display the results if records are found
            if (!matchingRecords.isEmpty()) {
                System.out.println("===========================================");
                System.out.println("Payment Records for Patient ID: " + patientID);
                for (PaymentRecord record : matchingRecords) {
                    System.out.println("Payment Amount: " + record.getPaymentAmount());
                    System.out.println("Payment Status: " + record.getRecordStatus());
                    System.out.println("Created At: " + record.getCreatedDate());
                    System.out.println("Updated At: " + record.getUpdatedDate());
                }
                System.out.println("===========================================");
            } else {
                System.out.println("No payment records found for Patient ID: " + patientID);
            }
        } else {
            System.out.println("Repository is not loaded. Cannot fetch records.");
        }

        // Return the list of matching records (empty if no matches found)
        return matchingRecords;
    }

    /**
     * Prompts the user to enter details for adding a new medicine to the inventory.
     */

    private static void manageBilling() {
        // Read the patient ID
        String patientID = Validator.readID("Patient", "P\\d{3}");

        List<PaymentRecord> patientPaymentRecords = RecordsRepository.PAYMENT_RECORDS.values().stream()
                .filter(record -> record.getPatientID().equals(patientID) && record.getPaymentStatus().equals(PaymentStatus.OUTSTANDING))
                .toList();
        if (patientPaymentRecords.isEmpty()) {
            System.out.println("No outstanding payment records found for Patient ID: " + patientID);
        return;
        }
        LocalDateTime updateDate = LocalDateTime.now();
        System.out.print("Enter New Status (e.g., CLEARED): ");
        PaymentStatus paymentStatus;
        paymentStatus = PaymentStatus.valueOf(Validator.readString().toUpperCase());

        // Check if the repository is loaded
        if (RecordsRepository.isRepoLoad()) {
            boolean recordFound = false;
            for (PaymentRecord record : RecordsRepository.PAYMENT_RECORDS.values()) {
                // If patient ID matches, add the record to the list
                if (PaymentRecord.getPatientID().equals(patientID) && record.getPaymentStatus().equals(PaymentStatus.OUTSTANDING)) {
                    record.setPaymentStatus(paymentStatus);
                    record.setUpdatedDate(updateDate);
                    record.setRecordStatus(RecordStatus.ARCHIVED);
                    recordFound = true; // Set flag to true as record was updated
                    break; // Exit the loop since we found and updated the record

                }
            }
            // Save changes to the repository if a record was updated
            if (recordFound) {
                RecordsRepository.saveAllRecordFiles();
                System.out.println("Payment record updated successfully for Patient ID: " + patientID);
            } else {
                System.out.println("No payment records found for Patient ID: " + patientID);
            }
        } else {
            System.out.println("Error: Payment records repository is not loaded.");
        }



    }
}
