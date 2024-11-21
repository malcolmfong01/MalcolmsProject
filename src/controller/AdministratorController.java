package controller;

import model.Administrator;
import repository.UserRepository;

/**
 * The AdministratorController class that provides methods to access and retrieve staff-related information
 * from the UserRepository.
 */

public class AdministratorController extends UserController {

    /**
     * Retrieves a Doctor object based on the provided doctor ID.
     *
     * @param adminId The unique identifier of the doctor.
     * @return The Doctor object if found in the repository, or null if not found or if the repository is not loaded.
     */

    public static Administrator getAdminById(String adminId) {
        if (UserRepository.isRepoLoad())
            return UserRepository.ADMINS.get(adminId);
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
        Administrator administrator = getAdminById(adminId);
        return administrator != null ? administrator.getFullName() : "Unknown Administrator";
    }


}
