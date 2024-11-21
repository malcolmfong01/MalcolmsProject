package controller;

import boundary.UpdatePrescriptionBoundary;
import model.Pharmacist;
import repository.AppointmentOutcomeRecordRepository;
import repository.UserRepository;
import repository.PrescribedMedicationRepository;

/**
 * The PharmacistController class that provides methods to access and retrieve pharmacist-related information
 * from the AppointmentOutcomeRecord and PrescribedMedication Repositories.
 */

public class PharmacistController{

    /**
     * Retrieves a Pharmacist object based on the provided pharmacist ID.
     *
     * @param pharmacistId The unique identifier of the pharmacist.
     * @return The Pharmacist object if found in the repository, or null if not found or if the repository is not loaded.
     */

    public static Pharmacist getPharmacistById(String pharmacistId) {
        if (UserRepository.isRepoLoad())
            return UserRepository.PHARMACISTS.get(pharmacistId);
        else
            return null;

    }

    /**
     * Retrieves the full name of a doctor based on the provided pharmacist ID.
     *
     * @param pharmacistId The unique identifier of the pharmacist.
     * @return The full name of the pharmacist if found, or "Unknown Pharmacist" if the pharmacist does not exist in the repository.
     */

    public static String getPharmacistNameById(String pharmacistId) {
        Pharmacist pharmacist = getPharmacistById(pharmacistId);
        return pharmacist != null ? pharmacist.getFullName() : "Unknown Pharmacist";
    }

    /**
     * Initiates the UI for updating the status of a specific prescription in an
     * appointment outcome record. This method creates an instance of
     * UpdatePrescriptionBoundary and calls its method to manage the interaction
     * and update process.
     */

    public static void updatePrescriptionStatus() {
        UpdatePrescriptionBoundary updateStatusUI = new UpdatePrescriptionBoundary();
        updateStatusUI.start();
    }

    public static void updatePrescribedMedicationRepository() {
        PrescribedMedicationRepository.saveAlltoCSV();
        AppointmentOutcomeRecordRepository.saveAppointmentOutcomeRecordRepository();
    }

}