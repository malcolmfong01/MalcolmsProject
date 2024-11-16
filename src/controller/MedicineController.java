package controller;

import java.time.LocalDateTime;
import model.Medicine;
import repository.MedicineRepository;
import enums.ReplenishStatus;

public class MedicineController {

    private static String generateNextMedicineID() {
        int maxID = -1;

        // Loop through existing medicines to find the largest current ID number
        for (String id : MedicineRepository.MEDICINES.keySet()) {
            if (id.startsWith("M")) {
                try {
                    // Extract the numeric part of the ID (e.g., "M003" => 3)
                    int currentID = Integer.parseInt(id.substring(1));
                    maxID = Math.max(maxID, currentID);
                } catch (NumberFormatException e) {
                    // If ID format is incorrect, ignore it
                }
            }
        }

        // Increment the max ID by 1 to generate the next ID
        maxID++;

        // Format the new ID as M001, M002, etc.
        return String.format("M%03d", maxID);
    }
    // Public method to expose the ID generation functionality
    public static String getNextMedicineID() {
        return generateNextMedicineID(); // Call the private method
    }
    /**
     * Add a new medicine to the repository.
     */
    public static boolean addMedicine(Medicine medicine) {
        if (medicine == null || medicine.getName() == null) {
            System.out.println("Error: Invalid medicine data.");
            return false;
        }
        // Generate next medicine ID
        String nextMedicineID = generateNextMedicineID();

        // Set the new generated medicine ID
        medicine.setMedicineID(nextMedicineID);

        MedicineRepository.MEDICINES.put(medicine.getMedicineID(), medicine);
        MedicineRepository.saveAllMedicinesToCSV();
        System.out.println("Medicine added: " + medicine.getName());
        return true;
    }

    /**
     * Update an existing medicine's details in the repository.
     */
    public static boolean updateMedicine(String medicineID, Medicine updatedMedicine) {
        if (medicineID == null || medicineID.isEmpty() || updatedMedicine == null) {
            System.out.println("Error: Invalid update request.");
            return false;
        }

        if (MedicineRepository.MEDICINES.containsKey(medicineID)) {
            MedicineRepository.MEDICINES.put(medicineID, updatedMedicine);
            MedicineRepository.saveAllMedicinesToCSV();
            System.out.println("Medicine updated: " + updatedMedicine.getName());
            return true;
        } else {
            System.out.println("Error: Medicine not found for update.");
            return false;
        }
    }

    /**
     * Remove a medicine from the repository by its ID.
     */
    public static boolean removeMedicine(String medicineID) {
        if (medicineID == null || medicineID.isEmpty()) {
            System.out.println("Error: Invalid medicine ID.");
            return false;
        }

        Medicine removedMedicine = MedicineRepository.MEDICINES.remove(medicineID);
        if (removedMedicine != null) {
            MedicineRepository.saveAllMedicinesToCSV();
            System.out.println("Medicine removed: " + removedMedicine.getName());
            return true;
        } else {
            System.out.println("Error: Medicine not found with ID: " + medicineID);
            return false;
        }
    }

    /**
     * Retrieve a medicine from the repository by its ID.
     */
    public static Medicine getMedicineByUID(String medicineID) {
        if (medicineID == null || medicineID.isEmpty()) {
            System.out.println("Error: Invalid medicine ID.");
            return null;
        }

        Medicine medicine = MedicineRepository.MEDICINES.get(medicineID);
        if (medicine != null) {
            System.out.println("Medicine found: " + medicine.getName());
        } else {
            System.out.println("Error: Medicine not found with ID: " + medicineID);
        }
        return medicine;
    }

    /**
     * List all medicines in the repository.
     */
    public static void listAllMedicines() {
        if (MedicineRepository.MEDICINES.isEmpty()) {
            System.out.println("No medicines available.");
            return;
        }

        System.out.println("Listing all medicines:");
        for (Medicine medicine : MedicineRepository.MEDICINES.values()) {
            System.out.println("UID: " + medicine.getMedicineID());
            System.out.println("Name: " + medicine.getName());
            System.out.println("Manufacturer: " + medicine.getManufacturer());
            System.out.println("Expiry Date: " + medicine.getExpiryDate());
            System.out.println("Inventory Stock: " + medicine.getInventoryStock());
            System.out.println("Low Stock Level: " + medicine.getLowStockLevel());
            System.out.println("Replenish Status: " + medicine.getReplenishStatus());
            System.out.println("Replenishment Request Date: " + medicine.getReplenishRequestDate());
            System.out.println("Approved Date: " + medicine.getApprovedDate());
            System.out.println();
        }
    }
    /**
     * Retrieve a medicine from the repository by its name.
     */
    //for doctor purpose ,ck
    public static Medicine getMedicineByName(String medicineName) {
        if (medicineName == null || medicineName.isEmpty()) {
            System.out.println("Error: Invalid medicine name.");
            return null;
        }

        for (Medicine medicine : MedicineRepository.MEDICINES.values()) {
            if (medicineName.equalsIgnoreCase(medicine.getName())) {
                System.out.println("Medicine found: " + medicine.getName());
                return medicine;
            }
        }

        System.out.println("Error: Medicine not found with name: " + medicineName);
        return null;
    }

}
