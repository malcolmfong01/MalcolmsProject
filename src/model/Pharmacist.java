package model;

import java.time.LocalDateTime;

import controller.UserController;
import enums.User;

/**
 * Pharmacist class that extends the User class
 */

public class Pharmacist extends model.User {
    private final LocalDateTime dateOfEmployment;

    /**
     * Constructs a Pharmacist object with the specified details.
     *
     * @param fullName the full name of the pharmacist
     * @param username the username for the pharmacist
     * @param email the email address of the pharmacist
     * @param phoneNo the phone number of the pharmacist
     * @param passwordHash the hashed password of the pharmacist
     * @param DoB the date of birth of the pharmacist
     * @param gender the gender of the pharmacist
     * @param dateOfEmployment the date the pharmacist was employed
     */

    public Pharmacist( String fullName, String username, String email, String phoneNo,
                      String passwordHash, LocalDateTime DoB, String gender, LocalDateTime dateOfEmployment) {
        super(UserController.generateUID(User.PHARMACISTS), fullName, username, email, phoneNo, passwordHash, DoB, gender, "PHARMACISTS");
        this.dateOfEmployment = dateOfEmployment;
    }
    
    /**
     * Constructs a Pharmacist object from CSV data.
     *
     * @param UID the unique ID of the pharmacist
     * @param fullName the full name of the pharmacist
     * @param username the username for the pharmacist
     * @param email the email address of the pharmacist
     * @param phoneNo the phone number of the pharmacist
     * @param passwordHash the hashed password of the pharmacist
     * @param DoB the date of birth of the pharmacist
     * @param gender the gender of the pharmacist
     * @param role the role of the pharmacist
     * @param dateOfEmployment the date the pharmacist was employed
     */

    public Pharmacist(String UID, String fullName, String username, String email, String phoneNo,
            String passwordHash, LocalDateTime DoB, String gender, String role, LocalDateTime dateOfEmployment) {
		super(UID, fullName, username, email, phoneNo, passwordHash, DoB, gender, role);

		this.dateOfEmployment = dateOfEmployment;
    }

    /**
     * Gets the date of employment for the pharmacist.
     *
     * @return the date the pharmacist was employed
     */
    public LocalDateTime getDateOfEmployment() {
        return dateOfEmployment;
    }

}