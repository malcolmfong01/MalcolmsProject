package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

import enums.PrescriptionStatus;
import enums.ReplenishStatus;
import helper.Helper;
import model.AppointmentOutcomeRecord;
import model.Prescription;
import model.PrescribedMedication;
import model.Medicine;
import repository.AppointmentOutcomeRecordRepository;
import repository.MedicineRepository;
import repository.PrescribedMedicationRepository;
import view.ViewAppointmentOutcomeRecordUI;
import view.MonitorInventoryUI;
import view.SubmitReplenishmentRequestUI;
import view.UpdatePrescriptionStatusUI;

public class PharmacistController{

	 /**
	  * Initiates the view for displaying appointment outcome records.
	  * This method creates an instance of AppointmentOutcomeRecordUI and calls
	  * its method to display appointment outcome records based on patient ID input.
	  * 
	  * <p>Usage: Allows pharmacists to retrieve and view detailed appointment outcomes
	  * for specified patient records, including prescription details if available.</p>
	  */
	public static void viewAppointmentOutcomeRecords() {
	    ViewAppointmentOutcomeRecordUI outcomeRecordUI = new ViewAppointmentOutcomeRecordUI();
	    outcomeRecordUI.viewAppointmentOutcomeRecords();
	}

    /**
     * Initiates the UI for updating the status of a specific prescription in an
     * appointment outcome record. This method creates an instance of
     * UpdatePrescriptionStatusUI and calls its method to manage the interaction
     * and update process.
     */
    public static void updatePrescriptionStatus() {
        UpdatePrescriptionStatusUI updateStatusUI = new UpdatePrescriptionStatusUI();
        updateStatusUI.start();
    }

    /**
     * Initiates the UI for monitoring inventory levels, checking expired medicines,
     * and displaying medicines below low stock levels.
     */
    public static void monitorInventory() {
        MonitorInventoryUI monitorInventoryUI = new MonitorInventoryUI();
        monitorInventoryUI.start();
	}

    /**
     * Initiates the UI for submitting replenishment requests and checking the
     * current status of all requests.
     */
    public static void submitReplenishmentRequests() {
        SubmitReplenishmentRequestUI submitRequestUI = new SubmitReplenishmentRequestUI();
        submitRequestUI.start();
    }
    
    public static void updatePrescribedMedicationRepository() {
        // Save changes to repositories
        PrescribedMedicationRepository.saveAlltoCSV();
        AppointmentOutcomeRecordRepository.saveAppointmentOutcomeRecordRepository();
    }

//    /**
//     * Main method to test the PharmacistController functionality.
//     */
//    public static void main(String[] args) {
//        MedicineRepository medicineRepository = new MedicineRepository();
//		medicineRepository.loadFromCSV();
//        // AppointmentRepository.loadAllAppointments();
//        
//        int choice = -1;
//        
//        while (choice != 0) { // 0 to exit
//            System.out.println("Pharmacist Controller - Select an option:");
//            System.out.println("1. View Appointment Outcome Records");
//            System.out.println("2. Update Prescription Status");
//            System.out.println("3. Monitor Inventory");
//            System.out.println("4. Submit Replenishment Request");
//            System.out.println("0. Exit");
//            
//            choice = Helper.readInt("");
//            
//            switch (choice) {
//                case 1:
//                    viewAppointmentOutcomeRecords();
//                    break;
//                case 2:
//                    updatePrescriptionStatus();
//                    break;
//                case 3:
//                    monitorInventory();
//                    break;
//                case 4:
//                	submitReplenishmentRequests();
//                    break;
//                case 0:
//                    System.out.println("Exiting Pharmacist Controller.");
//                    break;
//                default:
//                    System.out.println("Error: Invalid choice. Please select a valid option.");
//                    break;
//            }
//        }
//    }
}