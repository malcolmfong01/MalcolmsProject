package model;

import java.time.LocalDateTime;

import controller.HMSPersonnelController;
import enums.PersonnelFileType;
/**
 * Represents a pharmacist in the system, extending the HMSPersonnel class to include specific details
 * such as the pharmacist's license number and date of employment.
 */
public class Pharmacist extends HMSPersonnel {
    private String pharmacistLicenseNumber;
    private LocalDateTime dateOfEmployment;

    /**
     * Constructs a Pharmacist object with the specified details.
     *
     * @param fullName the full name of the pharmacist
     * @param idCard the ID card number of the pharmacist
     * @param username the username for the pharmacist
     * @param email the email address of the pharmacist
     * @param phoneNo the phone number of the pharmacist
     * @param passwordHash the hashed password of the pharmacist
     * @param DoB the date of birth of the pharmacist
     * @param gender the gender of the pharmacist
     * @param pharmacistLicenseNumber the license number of the pharmacist
     * @param dateOfEmployment the date the pharmacist was employed
     */
    // Constructor for creation of Pharmacist
    public Pharmacist( String fullName, String idCard, String username, String email, String phoneNo,
                      String passwordHash, LocalDateTime DoB, String gender,
                      String pharmacistLicenseNumber, LocalDateTime dateOfEmployment) {
        super(HMSPersonnelController.generateUID(PersonnelFileType.PHARMACISTS), fullName, idCard, username, email, phoneNo, passwordHash, DoB, gender, "PHARMACISTS");
        this.pharmacistLicenseNumber = pharmacistLicenseNumber;
        this.dateOfEmployment = dateOfEmployment;
    }
    
    /**
     * Constructs a Pharmacist object from CSV data.
     *
     * @param UID the unique ID of the pharmacist
     * @param fullName the full name of the pharmacist
     * @param idCard the ID card number of the pharmacist
     * @param username the username for the pharmacist
     * @param email the email address of the pharmacist
     * @param phoneNo the phone number of the pharmacist
     * @param passwordHash the hashed password of the pharmacist
     * @param DoB the date of birth of the pharmacist
     * @param gender the gender of the pharmacist
     * @param role the role of the pharmacist
     * @param pharmacistLicenseNumber the license number of the pharmacist
     * @param dateOfEmployment the date the pharmacist was employed
     */
 // Constructor for creation of Pharmacist from csv
    public Pharmacist(String UID, String fullName, String idCard, String username, String email, String phoneNo,
            String passwordHash, LocalDateTime DoB, String gender, String role,
            String pharmacistLicenseNumber, LocalDateTime dateOfEmployment) {
		super(UID, fullName, idCard, username, email, phoneNo, passwordHash, DoB, gender, role);
		this.pharmacistLicenseNumber = pharmacistLicenseNumber;
		this.dateOfEmployment = dateOfEmployment;
    }

    /**
     * Gets the pharmacist's license number.
     *
     * @return the pharmacist's license number
     */
    // Getters and Setters
    public String getPharmacistLicenseNumber() {
        return pharmacistLicenseNumber;
    }

    /**
     * Sets the pharmacist's license number.
     *
     * @param pharmacistLicenseNumber the license number to set
     */
    public void setPharmacistLicenseNumber(String pharmacistLicenseNumber) {
        this.pharmacistLicenseNumber = pharmacistLicenseNumber;
    }

    /**
     * Gets the date of employment for the pharmacist.
     *
     * @return the date the pharmacist was employed
     */
    public LocalDateTime getDateOfEmployment() {
        return dateOfEmployment;
    }

    /**
     * Sets the date of employment for the pharmacist.
     *
     * @param dateOfEmployment the date of employment to set
     */
    public void setDateOfEmployment(LocalDateTime dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }
}