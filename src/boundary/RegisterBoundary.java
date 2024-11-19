package boundary;

import utility.Validator;
import model.MedicalRecord;
import enums.RecordStatusType;
import repository.PersonnelRepository;
import repository.RecordsRepository;
import controller.AuthenticationController;
import controller.RecordsController;
import enums.RecordFileType;

import java.time.LocalDateTime;
import java.util.ArrayList;
/**
 * Handles the user interface for registering various types of users in the system.
 */
public class RegisterBoundary extends Boundary {
	
//  public static void main (String[]args) {
//  // Creating and starting AdminBoundary with the dummy admin
//	  RegisterBoundary testing = new RegisterBoundary();
//	  testing.start();
//}
    /**
     * Displays the menu options for user registration.
     */
    @Override
    protected void printChoice() {
        System.out.println("You would like to register as:");
        System.out.println("1. Patient");
        System.out.println("2. Doctor");
        System.out.println("3. Pharmacist");
        System.out.println("4. Administrator");
        System.out.println("5. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }
    /**
     * Starts the registration UI and handles the user input for registration choices.
     */
    @Override
    public void start() {
        while (true) {
            printChoice();
            int role = Validator.readInt("",1, 5);

            switch (role) {
                case 1 -> patientRegister();
                case 2 -> doctorRegister();
                case 3 -> pharmacistRegister();
                case 4 -> adminRegister();
                case 5 -> {
                    System.out.println("Returning to main menu...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
    /**
     * Handles patient registration input and logic, including creating a new patient record.
     */
 // Registration for Patient
    public void patientRegister() {
        String fullName = Validator.readString("Enter full name: ");
        String email = Validator.readEmail("Enter email:");
        String phoneNo = Validator.readValidPhoneNumber("Enter phone number: ");
        LocalDateTime DoB = Validator.readDate("Enter date of birth (yyyy-MM-dd): ");
        String gender = Validator.readGender("Enter gender (M/F): ");
        String allergies = Validator.readString("Enter allergies (if any): ");
        String bloodType = Validator.readValidBloodType("Enter BloodType: ");
        bloodType = (bloodType=="") ? null : bloodType;
        LocalDateTime dateOfAdmission = LocalDateTime.now();
        String username = Validator.readString("Enter desired username: ");
        
        // Ensure username is unique
        while (AuthenticationController.isUsernameTaken(username, PersonnelRepository.PATIENTS)) {
            System.out.println("The username '" + username + "' is already taken. ");
            username = Validator.readString("Please enter a new username:");
        }

        // Register the patient
        String patientUID = AuthenticationController.registerPatient(fullName, username, email, phoneNo, "password", DoB, gender, allergies, dateOfAdmission);
        if (patientUID != null) {
            System.out.println("Patient registered successfully!, your default password is: password ");

            // Generate a new medical record ID (assuming RecordsController has a method for this)
            String recordID = RecordsController.generateRecordID(RecordFileType.MEDICAL_RECORDS);

            // Create the MedicalRecord for the new patient
            MedicalRecord mr = new MedicalRecord(
                    fullName, // patient name
                    phoneNo, //  patient phone number
                    email,   // patient email
                    LocalDateTime.now(), // Created date
                    LocalDateTime.now(), // Updated date
                    RecordStatusType.ACTIVE,// Record status
                    patientUID,              // patientID
                    null,                // doctorID (can be assigned later)
                    bloodType,
                    new ArrayList<>(),    // Empty diagnosis list
                    allergies
            );

            // Add the new MedicalRecord to the repository
            RecordsRepository.MEDICAL_RECORDS.put(recordID, mr);
            RecordsRepository.saveAllRecordFiles();

            System.out.println("Medical record created successfully for patient " + fullName);
        } else {
            System.out.println("Registration failed. Username may already exist.");
        }
    }
    /**
     * Handles doctor registration input and logic, including creating a new doctor record.
     */
    // Registration for Doctor
    public void doctorRegister() {
        String fullName = Validator.readString("Enter full name: ");
        String email = Validator.readEmail("Enter email: ");
        String phoneNo = Validator.readValidPhoneNumber("Enter phone number: ");
        LocalDateTime DoB = Validator.readDate("Enter date of birth (yyyy-MM-dd): ");
        String gender = Validator.readGender("Enter gender (M/F): ");
        LocalDateTime dateJoin = LocalDateTime.now();
        String username = Validator.readString("Enter desired username: ");
        
		while (AuthenticationController.isUsernameTaken(username, PersonnelRepository.DOCTORS)) {
		System.out.println("The username '" + username + "' is already taken. ");
		username = Validator.readString("Please enter a new username: ");
		}

        boolean success = AuthenticationController.registerDoctor(fullName, username, email, phoneNo, "password", DoB, gender, dateJoin);
        System.out.println(success ? "Doctor registered successfully!, your default password is: password " : "Registration failed. Username may already exist.");
    }
    /**
     * Handles pharmacist registration input and logic.
     */
    // Registration for Pharmacist
    public void pharmacistRegister() {
        String fullName = Validator.readString("Enter full name: ");
        String email = Validator.readEmail("Enter email: ");
        String phoneNo = Validator.readValidPhoneNumber("Enter phone number: ");
        LocalDateTime DoB = Validator.readDate("Enter date of birth (yyyy-MM-dd): ");
        String gender = Validator.readGender("Enter gender (M/F): ");
        LocalDateTime dateOfEmployment = LocalDateTime.now();
        String username = Validator.readString("Enter desired username: ");
        
		while (AuthenticationController.isUsernameTaken(username, PersonnelRepository.PHARMACISTS)) {
		System.out.println("The username '" + username + "' is already taken.");
		username = Validator.readString("Please enter a new username: ");
		}

        boolean success = AuthenticationController.registerPharmacist(fullName, username, email, phoneNo, "password", DoB, gender, dateOfEmployment);
        System.out.println(success ? "Pharmacist registered successfully!, your default password is: password "  : "Registration failed. Username may already exist.");
    }
    /**
     * Handles administrator registration input and logic.
     */
    // Registration for Admin
    public void adminRegister() {
    	String fullName = Validator.readString("Enter full name:");
        String email = Validator.readEmail("Enter email:");
        String phoneNo = Validator.readValidPhoneNumber("Enter phone number:");
        LocalDateTime DoB = Validator.readDate("Enter date of birth (yyyy-MM-dd):");
        String gender = Validator.readGender("Enter gender (M/F):");
        LocalDateTime dateOfCreation = LocalDateTime.now();
        String username = Validator.readString("Enter desired username:");
		while (AuthenticationController.isUsernameTaken(username, PersonnelRepository.ADMINS)) {
		System.out.println("The username '" + username + "' is already taken. ");
		username = Validator.readString("Please enter a new username:");
		}

        boolean success = AuthenticationController.registerAdmin(fullName, username, email, phoneNo, "password", DoB, gender, dateOfCreation);
        System.out.println(success ? "Admin registered successfully!, your default password is: password " : "Registration failed. Username may already exist.");
    }
}
