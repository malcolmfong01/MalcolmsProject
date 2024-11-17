package model;

import java.time.LocalDateTime;

import controller.HMSPersonnelController;
import enums.PersonnelFileType;

/**
 * Represents a Doctor in the system. Inherits from the HMSPersonnel class to include
 * basic personnel details such as name, contact information, etc., and adds specific
 * information related to the doctor's specialty, medical license, date of joining, and years of experience.
 */
public class Doctor extends HMSPersonnel {
    private String specialty;
    private String medicalLicenseNumber;
    private LocalDateTime dateJoin;
    private int yearsOfExperiences;

    
    /**
     * Constructs a Doctor object with the specified details.
     *
     * @param fullName the full name of the doctor
     * @param idCard the identification card number of the doctor
     * @param username the username of the doctor
     * @param email the email address of the doctor
     * @param phoneNo the phone number of the doctor
     * @param passwordHash the hashed password of the doctor
     * @param DoB the date of birth of the doctor
     * @param gender the gender of the doctor
     * @param specialty the specialty of the doctor (e.g., cardiology, dermatology)
     * @param medicalLicenseNumber the medical license number of the doctor
     * @param dateJoin the date the doctor joined the organization
     * @param yearsOfExperiences the number of years of experience the doctor has
     */
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
    
    /**
     * Constructs a Doctor object from CSV data.
     *
     * @param UID the unique ID of the doctor
     * @param fullName the full name of the doctor
     * @param idCard the identification card number of the doctor
     * @param username the username of the doctor
     * @param email the email address of the doctor
     * @param phoneNo the phone number of the doctor
     * @param passwordHash the hashed password of the doctor
     * @param DoB the date of birth of the doctor
     * @param gender the gender of the doctor
     * @param role the role of the doctor (e.g., "Doctors")
     * @param specialty the specialty of the doctor (e.g., cardiology, dermatology)
     * @param medicalLicenseNumber the medical license number of the doctor
     * @param dateJoin the date the doctor joined the organization
     * @param yearsOfExperiences the number of years of experience the doctor has
     */
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

    /**
     * Gets the specialty of the doctor.
     *
     * @return the specialty of the doctor
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Sets the specialty of the doctor.
     *
     * @param specialty the specialty to set
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * Gets the medical license number of the doctor.
     *
     * @return the medical license number of the doctor
     */
    public String getMedicalLicenseNumber() {
        return medicalLicenseNumber;
    }

    /**
     * Sets the medical license number of the doctor.
     *
     * @param medicalLicenseNumber the medical license number to set
     */
    public void setMedicalLicenseNumber(String medicalLicenseNumber) {
        this.medicalLicenseNumber = medicalLicenseNumber;
    }

    /**
     * Gets the date when the doctor joined the organization.
     *
     * @return the date the doctor joined
     */
    public LocalDateTime getDateJoin() {
        return dateJoin;
    }
    
    /**
     * Sets the date when the doctor joined the organization.
     *
     * @param dateJoin the date to set for when the doctor joined
     */
    public void setDateJoin(LocalDateTime dateJoin) {
        this.dateJoin = dateJoin;
    }

    /**
     * Gets the number of years of experience the doctor has.
     *
     * @return the number of years of experience
     */
    public int getYearsOfExperiences() {
        return yearsOfExperiences;
    }

    /**
     * Sets the number of years of experience the doctor has.
     *
     * @param yearsOfExperiences the years of experience to set
     */
    public void setYearsOfExperiences(int yearsOfExperiences) {
        this.yearsOfExperiences = yearsOfExperiences;
    }
}