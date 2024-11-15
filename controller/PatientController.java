package controller;

import model.Patient;
import repository.PersonnelRepository;

public class PatientController {

	public static Patient getPatientById(String patientId) {
		return PersonnelRepository.PATIENTS.get(patientId);
	}

	public static String getPatientNameById(String patientId) {
		Patient patient = getPatientById(patientId);
		return patient != null ? patient.getFullName() : "Unknown patient";
	}

	public static String getPatientInfoById(String patientId) {
		Patient patient = getPatientById(patientId);

		if (patient != null) {
			StringBuilder info = new StringBuilder();
			info.append("Patient Information:\n");
			info.append("UID: ").append(patient.getUID()).append("\n");
			info.append("Full Name: ").append(patient.getFullName()).append("\n");
			info.append("ID Card: ").append(patient.getIdCard()).append("\n");
			info.append("Username: ").append(patient.getUsername()).append("\n");
			info.append("Email: ").append(patient.getEmail()).append("\n");
			info.append("Phone No: ").append(patient.getPhoneNo()).append("\n");
			info.append("Date of Birth: ").append(patient.getDoB()).append("\n");
			info.append("Gender: ").append(patient.getGender()).append("\n");
			info.append("Role: ").append(patient.getRole()).append("\n");
			info.append("Insurance Info: ").append(patient.getInsuranceInfo()).append("\n");
			info.append("Allergies: ").append(patient.getAllergies()).append("\n");
			info.append("Date of Admission: ").append(patient.getDateOfAdmission()).append("\n");

			return info.toString();
		} else {
			return "Unknown patient";
		}
	}

}
