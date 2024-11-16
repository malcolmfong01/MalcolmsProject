package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import controller.HMSPersonnelController;
import helper.Helper;
import model.Patient;

public class UpdatePatientParticularsUI {
    
    private Patient patient;

    // Constructor to initialize with the current patient object
    public UpdatePatientParticularsUI(Patient patient) {
        this.patient = patient;
    }

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

    // Start the UI for updating patient particulars
    public void start() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        boolean keepUpdating = true;

        // Display current particulars at the start
        displayCurrentParticulars();

        while (keepUpdating) {
            System.out.println("--- Update Patient Particulars ---");
            System.out.println("1. Update Full Name");
            System.out.println("2. Update Phone Number"); //Check this
            System.out.println("3. Update Email");
            System.out.println("4. Update Allergies");
            System.out.println("5. Update Date of Birth");
            System.out.println("6. Finish and Save");
            System.out.print("Enter your choice: ");

            choice = Helper.readInt("");
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter new full name: ");
                    String fullName = Helper.readString();
                    patient.setFullName(fullName);
                    System.out.println("Full name updated.");
                    break;
                case 2:
                    System.out.print("Enter new phone number: ");
                    String phoneNumber = Helper.readString();
                    patient.setPhoneNo(phoneNumber);
                    System.out.println("Phone number updated.");
                    break;
                case 3:
                    System.out.print("Enter new email: ");
                    String email = Helper.readEmail("Enter new email: ");
                    patient.setEmail(email);
                    System.out.println("Email updated.");
                    break;
                case 4:
                    System.out.print("Enter new allergies information: ");
                    String allergies = Helper.readString();
                    patient.setAllergies(allergies);
                    System.out.println("Allergies updated.");
                    break;
                case 5:
                    System.out.print("Enter new date of birth (yyyy-MM-dd HH:mm): ");
                    String dob = Helper.readString();
                    patient.setDoB(LocalDateTime.parse(dob, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                    System.out.println("Date of birth updated.");
                    break;
                case 6:
                    // Save all updates
                    if (HMSPersonnelController.updatePatientParticulars(patient.getUID(), patient)) {
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
