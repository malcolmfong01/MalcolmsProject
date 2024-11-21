package boundary;

import utility.Validator;
import model.Medicine;
import repository.MedicineRepository;

import java.time.LocalDateTime;

/**
 * MonitorInventoryBoundary manages the user interface for monitoring and managing
 * inventory levels of medicines, including checking for expired and low-stock items.
 * <p>
 * Provides options to remove expired medicines from stock and lists items below the
 * low-stock threshold.
 * </p>
 */

public class MonitorInventoryBoundary extends Boundary {

    @Override
    public void start() {
        monitorInventory();
    }

    @Override
    protected void printChoice() {
        System.out.println("1. Return to Pharmacist Menu");
    }

    /**
     * Monitors the full inventory, displaying stock levels, expired items,
     * and low-stock items with options for removal.
     */

    public void monitorInventory() {
        if (MedicineRepository.MEDICINES.isEmpty()) {
            System.out.println("No medicines available in the inventory.");
            returnToMenu();
            return;
        }

        System.out.println("Full Inventory - Monitoring stock levels:");
        for (Medicine medicine : MedicineRepository.MEDICINES.values()) {
            displayMedicineDetails(medicine);
        }

        printline();

        // Step 1: Check for expired medicines
        System.out.println("Expired Medicines:");
        LocalDateTime now = LocalDateTime.now();
        boolean expired = false;

        for (Medicine medicine : MedicineRepository.MEDICINES.values()) {
            if (medicine.getExpiryDate().isBefore(now)) {
                expired = true;
                displayMedicineDetails(medicine);
                System.out.println("Replenishment Status: " + medicine.getReplenishStatus() + " (Expired)");
                System.out.println();
            }
        }

        if (!expired) {
            System.out.println("No medicines are expired.");
        } else {
            promptToRemoveExpiredMedicines(now);
        }

        printline();

        // Step 4: Check for medicines below the low stock level
        System.out.println("Medicines Below Low Stock Level:");
        boolean lowStockFound = false;
        for (Medicine medicine : MedicineRepository.MEDICINES.values()) {
            if (medicine.getInventoryStock() < medicine.getLowStockLevel()) {
                lowStockFound = true;
                displayMedicineDetails(medicine);
                System.out.println("Stock Level: " + medicine.getInventoryStock() + " (Below threshold)");
                System.out.println("Replenishment Request Date: " + medicine.getReplenishRequestDate());
                System.out.println();
            }
        }

        if (!lowStockFound) {
            System.out.println("All medicines are above the low stock level.");
        }

        returnToMenu();
    }

    /**
     * Displays detailed information for a given medicine.
     * 
     * @param medicine details
     */

    private void displayMedicineDetails(Medicine medicine) {
        System.out.println("Medicine ID: " + medicine.getMedicineID());
        System.out.println("Name: " + medicine.getName());
        System.out.println("Stock Level: " + medicine.getInventoryStock());
        System.out.println("Low Stock Level: " + medicine.getLowStockLevel());
        System.out.println("Expiry Date: " + medicine.getExpiryDate());
    }

    /**
     * Prompts the user to remove expired medicines from the inventory.
     * 
     * @param now The current date and time used to compare expiry dates.
     */

    private void promptToRemoveExpiredMedicines(LocalDateTime now) {
        int response;
        do {
            System.out.println("Do you want to remove expired medicines from stock?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.print("Enter your choice: ");
            response = Validator.readInt("");

            switch (response) {
                case 1 -> removeExpiredMedicines(now);
                case 2 -> System.out.println("No expired medicines were removed.");
                default -> System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        } while (response != 1 && response != 2);
    }

    /**
     * Removes expired medicines by setting their inventory stock to zero.
     * 
     * @param now The current date and time used to compare expiry dates.
     */
    private void removeExpiredMedicines(LocalDateTime now) {
        for (Medicine medicine : MedicineRepository.MEDICINES.values()) {
            if (medicine.getExpiryDate().isBefore(now)) {
                medicine.setInventoryStock(0);  // Set stock to zero for expired medicines
                System.out.println("Removed " + medicine.getName() + " from stock due to expiration.");
            }
        }
        MedicineRepository.saveAllMedicinesToCSV();
    }

    /**
     * Returns to the main menu after completing inventory checks and updates.
     */
    private void returnToMenu() {
        printChoice();
        int choice = Validator.readInt("");
        if (choice == 1) {
            returntoPharmacistMenu();
        } else {
            handleInvalidInput();
        }
    }

    /**
     * Returns to the Pharmacist main menu.
     */

    private void returntoPharmacistMenu() {
        PharmacistBoundary pharmacistBoundary = new PharmacistBoundary();
        pharmacistBoundary.start();
    }
}