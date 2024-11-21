package controller;

import model.Patient;
import repository.UserRepository;

/**
 * The PatientController class that provides methods to access and retrieve patient-related information
 * from the UserRepository.
 */

public class PatientController {

    /**
     * Retrieves a Patient object based on the provided patient ID.
     *
     * @param patientId The unique identifier of the patient.
     * @return The Patient object if found in the repository, or null if not found.
     */

	public static Patient getPatientById(String patientId) {
		return UserRepository.PATIENTS.get(patientId);
	}

    /**
     * Retrieves the full name of a patient based on the provided patient ID.
     *
     * @param patientId The unique identifier of the patient.
     * @return The full name of the patient if found, or "Unknown patient" if the patient does not exist.
     */

	public static String getPatientNameById(String patientId) {
		Patient patient = getPatientById(patientId);
		return patient != null ? patient.getFullName() : "Unknown patient";
	}

    /**
     * Retrieves detailed information of a patient based on the provided patient ID.
     *
     * @param patientId The unique identifier of the patient.
     * @return A formatted string containing the patient's information if found, or "Unknown patient" if the patient does not exist.
     */

	public static String getPatientInfoById(String patientId) {
		Patient patient = getPatientById(patientId);

		if (patient != null) {
			// Using String concatenation instead of StringBuilder
			String info = "Patient Information:\n";
			info += "UID: " + patient.getUID() + "\n";
			info += "Full Name: " + patient.getFullName() + "\n";
			info += "Username: " + patient.getUsername() + "\n";
			info += "Email: " + patient.getEmail() + "\n";
			info += "Phone No: " + patient.getPhoneNo() + "\n";
			info += "Date of Birth: " + patient.getDoB() + "\n";
			info += "Gender: " + patient.getGender() + "\n";
			info += "Role: " + patient.getRole() + "\n";
			info += "Allergies: " + patient.getAllergies() + "\n";
			info += "Date of Admission: " + patient.getDateOfAdmission() + "\n";

			return info;
		} else {
			return "Unknown patient";
		}
	}


}
