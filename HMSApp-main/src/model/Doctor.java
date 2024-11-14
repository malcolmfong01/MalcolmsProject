package model;

import java.time.LocalDateTime;

import controller.HMSPersonnelController;
import repository.PersonnelFileType;

public class Doctor extends HMSPersonnel {
    private String specialty;
    private String medicalLicenseNumber;
    private LocalDateTime dateJoin;
    private int yearsOfExperiences;

    // Constructor for creation of Doctors
    public Doctor(
    			String fullName, String idCard, String username, String email, String phoneNo,
                  String passwordHash, LocalDateTime DoB, String gender,
                  String specialty, String medicalLicenseNumber, LocalDateTime dateJoin, int yearsOfExperiences) {
        super(HMSPersonnelController.generateUID(PersonnelFileType.DOCTORS), fullName, idCard, username, email, phoneNo, passwordHash, DoB, gender, "Doctors");
        this.specialty = specialty;
        this.medicalLicenseNumber = medicalLicenseNumber;
        this.dateJoin = dateJoin;
        this.yearsOfExperiences = yearsOfExperiences;
    }
    
    // Constructor for creation of Doctors from CSV
    public Doctor(String UID, 
    			String fullName, String idCard, String username, String email, String phoneNo,
                  String passwordHash, LocalDateTime DoB, String gender, String role,
                  String specialty, String medicalLicenseNumber, LocalDateTime dateJoin, int yearsOfExperiences) {
        super(UID, fullName, idCard, username, email, phoneNo, passwordHash, DoB, gender, role);
        this.specialty = specialty;
        this.medicalLicenseNumber = medicalLicenseNumber;
        this.dateJoin = dateJoin;
        this.yearsOfExperiences = yearsOfExperiences;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getMedicalLicenseNumber() {
        return medicalLicenseNumber;
    }

    public void setMedicalLicenseNumber(String medicalLicenseNumber) {
        this.medicalLicenseNumber = medicalLicenseNumber;
    }

    public LocalDateTime getDateJoin() {
        return dateJoin;
    }

    public void setDateJoin(LocalDateTime dateJoin) {
        this.dateJoin = dateJoin;
    }

    public int getYearsOfExperiences() {
        return yearsOfExperiences;
    }

    public void setYearsOfExperiences(int yearsOfExperiences) {
        this.yearsOfExperiences = yearsOfExperiences;
    }
}