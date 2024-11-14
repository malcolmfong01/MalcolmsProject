package controller;

import model.Doctor;
import model.Patient;
import repository.PersonnelRepository;
import repository.RecordsRepository;

public class DoctorController {

	public static Doctor getDoctorById(String doctorId) {
		if (PersonnelRepository.isRepoLoad())
			return PersonnelRepository.DOCTORS.get(doctorId);
		else
			return null;

	}

	public static String getDoctorNameById(String doctorId) {
		Doctor doctor = getDoctorById(doctorId);
		return doctor != null ? doctor.getFullName() : "Unknown Doctor";
	}

}
