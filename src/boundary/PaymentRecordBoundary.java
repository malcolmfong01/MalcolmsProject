/**
 * User interface for displaying a patient's medical record in a formatted view.
 * Allows the user to view detailed information about a patient's diagnoses,
 * prescriptions, and prescribed medications.
 */
package boundary;

import controller.UserController;
import model.*;
import repository.RecordsRepository;
import utility.Validator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A user interface class for displaying a patient's medical record in a formatted view.
 * This class allows the user to view detailed information about a patient's diagnoses,
 * prescriptions, medications, and treatment plans.
 */
public class PaymentRecordBoundary extends Boundary {

    /**
     * The medical record to be displayed.
     */
    private final PaymentRecord paymentRecord;
    private final Patient patient;

    /**
     * Constructs a MedicalRecordBoundary with the specified medical record.
     *
     * @param paymentRecord the medical record to be displayed
     */
    public PaymentRecordBoundary(PaymentRecord paymentRecord) {
        this.paymentRecord = paymentRecord;
        this.patient = UserController.getPatientById(PaymentRecord.getPatientID());
    }

    /**
     * Displays the Payment record in a formatted box structure.
     */
    public void displayPaymentRecordInBox() {
        String border = "+------------------------------------------+";
        System.out.println(border);
        int index = 1;
        ArrayList<PaymentRecord> records = new ArrayList<>();
        for (PaymentRecord paymentRecord : RecordsRepository.PAYMENT_RECORDS
                .values()) {
            if (PaymentRecord.getPatientID().equals(patient.getUID())) {
                    records.add(paymentRecord);
                    System.out.printf("%d. Payment Record %s for Patient ID: %s%n",
                            index,
                            paymentRecord.getRecordID(),
                            patient.getUID());

                    index++;
                // Print basic information
                System.out.printf("| %-20s: %-20s |\n", "Patient Name", patient.getFullName());
                System.out.printf("| %-20s: %-20s |\n", "Patient DOB", patient.getDoB());
                System.out.printf("| %-20s: %-20s |\n", "Phone Number", patient.getPhoneNo());
                System.out.printf("| %-20s: %-20s |\n", "Email", patient.getEmail());
                System.out.printf("| %-20s: %-20s |\n", "Payment Amount", paymentRecord.getPaymentAmount());
                System.out.printf("| %-20s: %-20s |\n", "Payment Status", paymentRecord.getPaymentStatus());
                System.out.println(border);
                System.out.println(border);
            }

        }
        if (records.isEmpty()) {
            printWarning("Error: No Payment records found for patient ID " + patient.getUID());
            return;
        }
        int selectedIndex = Validator.readInt("\nEnter Payment Record ID to update or '-1' to exit: ");
        if (selectedIndex == -1) {
            return;
        }
        if (selectedIndex < 1 || selectedIndex > records.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        if (records.get(selectedIndex - 1) == null) {
            printWarning("Error: Payment Record ID ");
        }
        // Display the selected payment record
        PaymentRecord selectedRecord = records.get(selectedIndex - 1);
        try (FileWriter writer = new FileWriter("./src/repository/invoices/SelectedPaymentRecord_" + patient.getUID() + ".txt")) {
            writer.write(border + "\n");
            writer.write("Selected Payment Record for Patient ID: " +  patient.getUID() + "\n");
            writer.write(border + "\n");
            writer.write(String.format("| %-20s: %-20s |\n", "Patient Name", patient.getFullName()));
            writer.write(String.format("| %-20s: %-20s |\n", "Patient DOB", patient.getDoB()));
            writer.write(String.format("| %-20s: %-20s |\n", "Phone Number", patient.getPhoneNo()));
            writer.write(String.format("| %-20s: %-20s |\n", "Email", patient.getEmail()));
            writer.write(String.format("| %-20s: %-20s |\n", "Payment Amount", selectedRecord.getPaymentAmount()));
            writer.write(String.format("| %-20s: %-20s |\n", "Payment Status", selectedRecord.getPaymentStatus()));
            writer.write(border + "\n");
            System.out.println("Selected payment record saved to file: SelectedPaymentRecord_" +  patient.getUID() + ".txt");
        } catch (IOException e) {
            System.out.println("Error generating payment record file: " + e.getMessage());
        }
    }


    /**
     * Prints the available options for the MedicalRecordBoundary.
     */
    @Override
    protected void printChoice() {
        System.out.println("Options:");
        System.out.println("1. Make Payment");
        System.out.println("2. Back to Previous Menu");
        System.out.println("Enter your choice: ");
    }

    /**
     * Starts the MedicalRecordBoundary, displaying the medical record and allowing
     * the user to navigate back to the previous menu.
     */
    @Override
    public void start() {
        displayPaymentRecordInBox(); // Display the medical record

        int choice; // Initialize choice to ensure the loop runs at least once
        while (true) {
            printChoice();
            choice = Validator.readInt("");
            switch (choice) {
                case 1 -> {
                    System.out.println("1.View and Print Payment");
                    return; // Exits the method and returns to the previous menu
                }
                case 2 -> {
                    System.out.println("Returning to previous menu...");
                    return; // Exits the method and returns to the previous menu
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
