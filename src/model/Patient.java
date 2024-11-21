package model;

import java.time.LocalDateTime;

import controller.UserController;

/**
 * Patient class that extends the User class
 */

public class Patient extends model.User {
    private String allergies;
    private LocalDateTime dateOfAdmission;

    /**
     * Constructs a new Patient with the specified details.
     *
     * @param fullName the full name of the patient
     * @param username the username for the patient account
     * @param email the email address of the patient
     * @param phoneNo the phone number of the patient
     * @param passwordHash the hashed password for the patient account
     * @param DoB the date of birth of the patient
     * @param gender the gender of the patient
     * @param allergies any known allergies of the patient
     * @param dateOfAdmission the date when the patient was admitted to the healthcare system
     */

    public Patient(String fullName, String username, String email, String phoneNo,
                   String passwordHash, LocalDateTime DoB, String gender, String allergies, LocalDateTime dateOfAdmission) {
        super(UserController.generateUID(enums.User.PATIENTS), fullName, username, email, phoneNo, passwordHash, DoB, gender, "Patients");



        this.allergies = allergies;
        this.dateOfAdmission = dateOfAdmission;
    }
    
    /**
     * Constructs a new Patient with the specified details, primarily used when creating a Patient from a CSV file.
     *
     * @param UID the unique identifier for the patient
     * @param fullName the full name of the patient
     * @param username the username for the patient account
     * @param email the email address of the patient
     * @param phoneNo the phone number of the patient
     * @param passwordHash the hashed password for the patient account
     * @param DoB the date of birth of the patient
     * @param gender the gender of the patient
     * @param role the role of the patient (used for identifying patient records)
     * @param allergies any known allergies of the patient
     * @param dateOfAdmission the date when the patient was admitted to the healthcare system
     */

    public Patient(String UID, String fullName, String username, String email, String phoneNo,
                   String passwordHash, LocalDateTime DoB, String gender, String role, String allergies, LocalDateTime dateOfAdmission) {
        super(UID, fullName, username, email, phoneNo, passwordHash, DoB, gender, role);



        this.allergies = allergies;
        this.dateOfAdmission = dateOfAdmission;
    }

    /**
     * Gets the allergies of the patient.
     *
     * @return the allergies
     */
    public String getAllergies() {
        return allergies;
    }

    /**
     * Sets the allergies for the patient.
     *
     * @param allergies the allergies to set
     */
    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    /**
     * Gets the date of admission for the patient.
     *
     * @return the date of admission
     */
    public LocalDateTime getDateOfAdmission() {
        return dateOfAdmission;
    }

    /**
     * Sets the date of admission for the patient.
     *
     * @param dateOfAdmission the date of admission to set
     */
    public void setDateOfAdmission(LocalDateTime dateOfAdmission) {
        this.dateOfAdmission = dateOfAdmission;
    }
}