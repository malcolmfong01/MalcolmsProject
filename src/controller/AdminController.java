package controller;

import model.Admin;
import repository.PersonnelRepository;

/**
 * The AdministratorController class that provides methods to access and retrieve staff-related information
 * from the PersonnelRepository.
 */

public class AdminController extends HMSPersonnelController {

    /**
     * Retrieves a Doctor object based on the provided doctor ID.
     *
     * @param adminId The unique identifier of the doctor.
     * @return The Doctor object if found in the repository, or null if not found or if the repository is not loaded.
     */

    public static Admin getAdminById(String adminId) {
        if (PersonnelRepository.isRepoLoad())
            return PersonnelRepository.ADMINS.get(adminId);
        else
            return null;

    }

    /**
     * Retrieves the full name of a doctor based on the provided doctor ID.
     *
     * @param adminId The unique identifier of the doctor.
     * @return The full name of the doctor if found, or "Unknown Doctor" if the doctor does not exist in the repository.
     */

    public static String getAdminNameById(String adminId) {
        Admin admin = getAdminById(adminId);
        return admin != null ? admin.getFullName() : "Unknown Administrator";
    }


}
