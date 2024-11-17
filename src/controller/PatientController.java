package controller;

import model.Patient;
import repository.PersonnelRepository;
/**
 * The PatientController class provides methods to access and retrieve patient-related information
 * from the PersonnelRepository.
 */
public class PatientController {
    /**
     * Retrieves a Patient object based on the provided patient ID.
     *
     * @param patientId The unique identifier of the patient.
     * @return The Patient object if found in the repository, or null if not found.
     */
	public static Patient getPatientById(String patientId) {
		return PersonnelRepository.PATIENTS.get(patientId);
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
			StringBuilder info = new StringBuilder();
			info.append("Patient Information:\n");
			info.append("UID: ").append(patient.getUID()).append("\n");
			info.append("Full Name: ").append(patient.getFullName()).append("\n");
			info.append("Username: ").append(patient.getUsername()).append("\n");
			info.append("Email: ").append(patient.getEmail()).append("\n");
			info.append("Phone No: ").append(patient.getPhoneNo()).append("\n");
			info.append("Date of Birth: ").append(patient.getDoB()).append("\n");
			info.append("Gender: ").append(patient.getGender()).append("\n");
			info.append("Role: ").append(patient.getRole()).append("\n");
			info.append("Allergies: ").append(patient.getAllergies()).append("\n");
			info.append("Date of Admission: ").append(patient.getDateOfAdmission()).append("\n");

			return info.toString();
		} else {
			return "Unknown patient";
		}
	}

}
