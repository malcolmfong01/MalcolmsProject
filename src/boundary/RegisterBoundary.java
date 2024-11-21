package boundary;

import enums.Record;
import repository.UserRepository;
import utility.Validator;
import model.MedicalRecord;
import enums.RecordStatus;
import repository.RecordsRepository;
import controller.RegisterController;
import controller.RecordsController;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * User Registration handling Menu
 */

public class RegisterBoundary extends Boundary {

    /**
     * Displays the menu options for user registration
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
     * Starts the registration menu and handles the user input for different user registrations
     */

    @Override
    public void start() {
        while (true) {
            printChoice();
            int role = Validator.readInt("",1, 5);

            switch (role) {
                case 1 -> patientRegister();
                case 2 -> registerDoctor();
                case 3 -> registerPharmacist();
                case 4 -> registerAdministrator();
                case 5 -> {
                    System.out.println("Returning to main menu...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    /**
     * Registration for a new patient
     */

    public void patientRegister() {
        String fullName = Validator.readString("Enter full name: ");
        String email = Validator.readEmail("Enter email:");
        String phoneNo = Validator.readValidPhoneNumber("Enter phone number: ");
        LocalDateTime DoB = Validator.readDate("Enter date of birth (yyyy-MM-dd): ");
        String gender = Validator.readGender("Enter gender (M/F): ");
        String allergies = Validator.readString("Enter allergies (if any): ");
        String bloodType = Validator.readValidBloodType("Enter BloodType: ");
        bloodType = (bloodType.isEmpty()) ? null : bloodType;
        LocalDateTime dateOfAdmission = LocalDateTime.now();
        String username = Validator.readString("Enter desired username: ");

        while (RegisterController.isUsernameTaken(username, UserRepository.PATIENTS)) {
            System.out.println("The username '" + username + "' is already taken. ");
            username = Validator.readString("Please enter a new username:");
        }

        String patientUID = RegisterController.registerPatient(fullName, username, email, phoneNo, "password", DoB, gender, allergies, dateOfAdmission);
        if (patientUID != null) {
            System.out.println("Patient registered successfully!, your default password is: password ");

            // Additional logic to add a new medical record for a new patient
            String recordID = RecordsController.generateRecordID(Record.MEDICAL_RECORDS);
            MedicalRecord mr = new MedicalRecord(
                    fullName, // patient name
                    phoneNo, //  patient phone number
                    email,   // patient email
                    LocalDateTime.now(), // Created date
                    LocalDateTime.now(), // Updated date
                    RecordStatus.ACTIVE,// Record status
                    patientUID,              // patientID
                    null,                // doctorID (can be assigned later)
                    bloodType,
                    new ArrayList<>(),    // Empty diagnosis list
                    allergies
            );
            RecordsRepository.MEDICAL_RECORDS.put(recordID, mr);
            RecordsRepository.saveAllRecordFiles();

            System.out.println("Medical record created successfully for patient " + fullName);
        } else {
            System.out.println("Registration failed. Please try again.");
        }
    }

    /**
     * Registration for a new doctor
     */

    public void registerDoctor() {
        String fullName = Validator.readString("Enter full name: ");
        String email = Validator.readEmail("Enter email: ");
        String phoneNo = Validator.readValidPhoneNumber("Enter phone number: ");
        LocalDateTime DoB = Validator.readDate("Enter date of birth (yyyy-MM-dd): ");
        String gender = Validator.readGender("Enter gender (M/F): ");
        LocalDateTime dateJoin = LocalDateTime.now();
        String username = Validator.readString("Enter desired username: ");
        
		while (RegisterController.isUsernameTaken(username, UserRepository.DOCTORS)) {
		System.out.println("The username '" + username + "' is already taken. ");
		username = Validator.readString("Please enter a new username: ");
		}

        boolean success = RegisterController.registerDoctor(fullName, username, email, phoneNo, "password", DoB, gender, dateJoin);
        System.out.println(success ? "Doctor registered successfully!, your default password is: password " : "Registration failed. Username may already exist.");
    }

    /**
     * Registration for a new pharmacist
     */

    public void registerPharmacist() {
        String fullName = Validator.readString("Enter full name: ");
        String email = Validator.readEmail("Enter email: ");
        String phoneNo = Validator.readValidPhoneNumber("Enter phone number: ");
        LocalDateTime DoB = Validator.readDate("Enter date of birth (yyyy-MM-dd): ");
        String gender = Validator.readGender("Enter gender (M/F): ");
        LocalDateTime dateOfEmployment = LocalDateTime.now();
        String username = Validator.readString("Enter desired username: ");
        
		while (RegisterController.isUsernameTaken(username, UserRepository.PHARMACISTS)) {
		System.out.println("The username '" + username + "' is already taken.");
		username = Validator.readString("Please enter a new username: ");
		}

        boolean success = RegisterController.registerPharmacist(fullName, username, email, phoneNo, "password", DoB, gender, dateOfEmployment);
        System.out.println(success ? "Pharmacist registered successfully!, your default password is: password "  : "Registration failed. Username may already exist.");
    }

    /**
     * Registration for a new administrator
     */

    public void registerAdministrator() {
    	String fullName = Validator.readString("Enter full name:");
        String email = Validator.readEmail("Enter email:");
        String phoneNo = Validator.readValidPhoneNumber("Enter phone number:");
        LocalDateTime DoB = Validator.readDate("Enter date of birth (yyyy-MM-dd):");
        String gender = Validator.readGender("Enter gender (M/F):");
        LocalDateTime dateOfCreation = LocalDateTime.now();
        String username = Validator.readString("Enter desired username:");
		while (RegisterController.isUsernameTaken(username, UserRepository.ADMINS)) {
		System.out.println("The username '" + username + "' is already taken. ");
		username = Validator.readString("Please enter a new username:");
		}

        boolean success = RegisterController.registerAdmin(fullName, username, email, phoneNo, "password", DoB, gender, dateOfCreation);
        System.out.println(success ? "Administrator registered successfully!, your default password is: password " : "Registration failed. Username may already exist.");
    }
}
