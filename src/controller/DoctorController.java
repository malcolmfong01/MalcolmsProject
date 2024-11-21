package controller;

import model.Doctor;
import repository.UserRepository;

/**
 * The DoctorController class provides methods for retrieving doctor-related information
 * from the UserRepository.
 */

public class DoctorController {

    /**
     * Retrieves a Doctor object based on the provided doctor ID.
     *
     * @param doctorId The unique identifier of the doctor.
     * @return The Doctor object if found in the repository, or null if not found or if the repository is not loaded.
     */

	public static Doctor getDoctorById(String doctorId) {
		if (UserRepository.isRepoLoad())
			return UserRepository.DOCTORS.get(doctorId);
		else
			return null;

	}

    /**
     * Retrieves the full name of a doctor based on the provided doctor ID.
     *
     * @param doctorId The unique identifier of the doctor.
     * @return The full name of the doctor if found, or "Unknown Doctor" if the doctor does not exist in the repository.
     */

	public static String getDoctorNameById(String doctorId) {
		Doctor doctor = getDoctorById(doctorId);
		return doctor != null ? doctor.getFullName() : "Unknown Doctor";
	}

}
