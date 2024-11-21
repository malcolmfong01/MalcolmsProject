package controller;

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
     * Retrieves the full name of a doctor based on the provided doctor ID.
     *
     * @param pharmacistId The unique identifier of the doctor.
     * @return The full name of the doctor if found, or "Unknown Doctor" if the doctor does not exist in the repository.
     */

    public static String getPharmacistNameById(String pharmacistId) {
        Pharmacist pharmacist = getPharmacistById(pharmacistId);
        return pharmacist != null ? pharmacist.getFullName() : "Unknown Pharmacist";
    }

    /**
     * method to update the prescribed Medication Repository
     */
    public static void updatePrescribedMedicationRepository() {
        PrescribedMedicationRepository.saveAlltoCSV();
        AppointmentOutcomeRecordRepository.saveAppointmentOutcomeRecordRepository();
    }

}