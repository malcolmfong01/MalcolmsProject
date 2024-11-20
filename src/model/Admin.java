package model;

import java.time.LocalDateTime;

import controller.StaffController;
import enums.PersonnelFileType;

/**
 * Administrator class that extends the User class
 */

public class Admin extends User {
    private final LocalDateTime dateOfCreation;

    /**
     * Constructor for creating an Admin instance with the provided details.
     * Generates a unique UID for the Admin and initializes all attributes.
     *
     * @param fullName the full name of the Admin
     * @param username the username for login
     * @param email the email address of the Admin
     * @param phoneNo the phone number of the Admin
     * @param passwordHash the hashed password of the Admin
     * @param DoB the date of birth of the Admin
     * @param gender the gender of the Admin
     * @param role the role of the Admin
     * @param dateOfCreation the date the Admin account was created
     */

    public Admin(String fullName, String username, String email, String phoneNo,
                 String passwordHash, LocalDateTime DoB, String gender, String role, LocalDateTime dateOfCreation) {
        super(StaffController.generateUID(PersonnelFileType.ADMINS), fullName, username, email, phoneNo, passwordHash, DoB, gender, "Admins");
        this.dateOfCreation = dateOfCreation;
    }
    
    /**
     * Constructor for creating an Admin instance from CSV data.
     * Initializes the Admin with the provided details, including the unique UID.
     *
     * @param UID the unique UID of the Admin
     * @param fullName the full name of the Admin
     * @param username the username for login
     * @param email the email address of the Admin
     * @param phoneNo the phone number of the Admin
     * @param passwordHash the hashed password of the Admin
     * @param DoB the date of birth of the Admin
     * @param gender the gender of the Admin
     * @param role the role of the Admin
     * @param dateOfCreation the date the Admin account was created
     */

    public Admin(String UID, String fullName,  String username, String email, String phoneNo,
                 String passwordHash, LocalDateTime DoB, String gender, String role, LocalDateTime dateOfCreation) {
        super(UID, fullName, username, email, phoneNo, passwordHash, DoB, gender, role);
        this.dateOfCreation = dateOfCreation;
    }

    /**
     * Gets the date when the Admin account was created.
     *
     * @return the date of creation of the Admin account
     */

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

}