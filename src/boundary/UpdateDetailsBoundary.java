package boundary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import controller.UserController;
import utility.Validator;
import model.Patient;
/**
 * This class provides a user interface to view and update the particulars of a Patient.
 * It displays current information about the patient and offers options to modify specific fields.
 */
public class UpdateDetailsBoundary {
    
    private final Patient patient;
    /**
     * Constructor to initialize the UI with a specific patient object.
     *
     * @param patient The Patient object whose details are to be viewed and updated.
     */
    // Constructor to initialize with the current patient object
    public UpdateDetailsBoundary(Patient patient) {
        this.patient = patient;
    }
    /**
     * Displays the current particulars of the patient.
     */
    // Display current personal particulars
    public void displayCurrentParticulars() {
        System.out.println("\n--- Current Personal Particulars ---");
        System.out.println("Full Name        : " + patient.getFullName());
        System.out.println("Phone Number     : " + patient.getPhoneNo());
        System.out.println("Email            : " + patient.getEmail());
        System.out.println("Allergies        : " + patient.getAllergies());
        System.out.println("Date of Admission: " + (patient.getDateOfAdmission() != null 
                                ? patient.getDateOfAdmission().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) 
                                : "N/A"));
        System.out.println("Date of Birth    : " + (patient.getDoB() != null 
                                ? patient.getDoB().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) 
                                : "N/A"));
        System.out.println("-------------------------------------\n");
    }
    /**
     * Starts the UI for updating patient particulars, offering options to modify different fields.
     */
    // Start the UI for updating patient particulars
    public void start() {
        int choice;
        boolean keepUpdating = true;

        // Display current particulars at the start
        displayCurrentParticulars();

        while (keepUpdating) {
            System.out.println("--- Update Patient Particulars ---");
            System.out.println("1. Update Full Name");
            System.out.println("2. Update Phone Number");
            System.out.println("3. Update Email");
            System.out.println("4. Update Allergies");
            System.out.println("5. Update Date of Birth");
            System.out.println("6. Finish and Save");
            System.out.print("Enter your choice: ");

            choice = Validator.readInt("");

            switch (choice) {
                case 1:
                    String fullName = Validator.readString("Enter new full name:");
                    patient.setFullName(fullName);
                    System.out.println("Full name updated.");
                    break;
                case 2:
                    String phoneNumber = Validator.readValidPhoneNumber("Enter new phone number: ");
                    patient.setPhoneNo(phoneNumber);
                    System.out.println("Phone number updated.");
                    break;
                case 3:
                    String email = Validator.readEmail("Enter new email:");
                    patient.setEmail(email);
                    System.out.println("Email updated.");
                    break;
                case 4:
                    String allergies = Validator.readString("Enter new allergies (if any):");
                    patient.setAllergies(allergies);
                    System.out.println("Allergies updated.");
                    break;
                case 5:
                    LocalDateTime DoB = Validator.readDate("Enter new date of birth (yyyy-MM-dd):");
                    patient.setDoB(DoB);
                    System.out.println("Date of birth updated.");
                    break;
                case 6:
                    // Save all updates
                    if (UserController.updatePatientParticulars(patient.getUID(), patient)) {
                        System.out.println("All changes saved successfully.");
                    } else {
                        System.out.println("Failed to save changes.");
                    }
                    keepUpdating = false; // Exit loop
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }

    }
}
