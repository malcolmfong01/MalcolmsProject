package controller;

import boundary.*;
import repository.AppointmentOutcomeRecordRepository;
import repository.PrescribedMedicationRepository;
import boundary.ViewAppointmentOutcomeRecordBoundary;
import boundary.UpdatePrescriptionStatusBoundary;

/**
 * The PharmacistController class that provides methods to access and retrieve pharmacist-related information
 * from the AppointmentOutcomeRecord and PrescribedMedication Repositories.
 */

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
	    ViewAppointmentOutcomeRecordBoundary outcomeRecordUI = new ViewAppointmentOutcomeRecordBoundary();
	    outcomeRecordUI.viewAppointmentOutcomeRecords();
	}

    /**
     * Initiates the UI for updating the status of a specific prescription in an
     * appointment outcome record. This method creates an instance of
     * UpdatePrescriptionStatusBoundary and calls its method to manage the interaction
     * and update process.
     */
    public static void updatePrescriptionStatus() {
        UpdatePrescriptionStatusBoundary updateStatusUI = new UpdatePrescriptionStatusBoundary();
        updateStatusUI.start();
    }

    /**
     * Initiates the UI for monitoring inventory levels, checking expired medicines,
     * and displaying medicines below low stock levels.
     */
    public static void monitorInventory() {
        MonitorInventoryBoundary monitorInventoryUI = new MonitorInventoryBoundary();
        monitorInventoryUI.start();
	}

    /**
     * Initiates the UI for submitting replenishment requests and checking the
     * current status of all requests.
     */
    public static void submitReplenishmentRequests() {
        SubmitReplenishmentRequestBoundary submitRequestUI = new SubmitReplenishmentRequestBoundary();
        submitRequestUI.start();
    }
    
    public static void updatePrescribedMedicationRepository() {
        // Save changes to repositories
        PrescribedMedicationRepository.saveAlltoCSV();
        AppointmentOutcomeRecordRepository.saveAppointmentOutcomeRecordRepository();
    }

}