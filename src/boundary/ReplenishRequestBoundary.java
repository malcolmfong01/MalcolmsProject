package boundary;

import utility.Validator;
import model.Medicine;
import repository.MedicineRepository;
import enums.ReplenishStatus;

import java.time.LocalDateTime;

/**
 * ReplenishRequestBoundary manages the user interface for submitting
 * replenishment requests for low-stock medicines and checking current
 * replenishment request statuses.
 */
public class ReplenishRequestBoundary extends Boundary {

    @Override
    public void start() {
        submitReplenishmentRequests();
    }

    @Override
    protected void printChoice() {
        System.out.println("1. Enter a Medicine ID to submit a replenishment request.");
        System.out.println("2. Check Replenishment Requests and Status.");
        System.out.println("3. Return to Main Menu.");
    }

    /**
     * Handles replenishment requests and displays current request statuses.
     */
    public void submitReplenishmentRequests() {
        int choice;
        do {
            printChoice();
            choice = Validator.readInt("Choose an option: ");

            switch (choice) {
                case 1 -> handleReplenishmentRequest();
                case 2 -> displayReplenishmentStatuses();
                case 3 -> returnToPharmacistMenu();
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 3);
    }

    /**
     * Handles the submission of a replenishment request for a specific medicine.
     * Prompts the user for a valid Medicine ID and requested quantity, then updates
     * the medicine replenishment status.
     */
    private void handleReplenishmentRequest() {
        Medicine medicine = null;

        // Keep asking for a valid Medicine ID until found
        while (medicine == null) {
            System.out.print("Enter Medicine ID for replenishment request: ");
            String medicineID = Validator.readString();

            medicine = MedicineRepository.MEDICINES.get(medicineID);
            if (medicine == null) {
                printWarning("Error: Medicine with ID " + medicineID + " not found. Please enter a valid ID.");
            }
        }

        // Proceed with replenishment request
        System.out.print("Enter Requested Quantity: ");
        int requestedQuantity = Validator.readInt("");
        medicine.setReplenishmentStock(requestedQuantity);
        medicine.setReplenishStatus(ReplenishStatus.REQUESTED);
        medicine.setReplenishRequestDate(LocalDateTime.now());
        MedicineRepository.saveAllMedicinesToCSV();  // Save changes

        System.out.println("Replenishment request submitted for " + medicine.getName() + " with quantity " + requestedQuantity);
    }

    /**
     * Displays all current replenishment requests and their statuses.
     */
    private void displayReplenishmentStatuses() {
        System.out.println("Replenishment Requests and Status:");

        boolean requestFound = false;
        for (Medicine med : MedicineRepository.MEDICINES.values()) {
            if (med.getReplenishStatus() != ReplenishStatus.NULL) {
                requestFound = true;
                System.out.println("Medicine ID: " + med.getMedicineID());
                System.out.println("Name: " + med.getName());
                System.out.println("Replenish Status: " + med.getReplenishStatus());
                System.out.println("Replenishment Stock: " + med.getReplenishmentStock());
                System.out.println("Replenishment Request Date: " + med.getReplenishRequestDate());
                System.out.println();
            }
        }

        if (!requestFound) {
            System.out.println("No replenishment requests found.");
        }
    }

    /**
     * Returns to the Pharmacist main menu.
     */
    private void returnToPharmacistMenu() {
        PharmacistBoundary pharmacistBoundary = new PharmacistBoundary();
        pharmacistBoundary.start();
    }
}