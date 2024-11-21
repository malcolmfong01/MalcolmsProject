package model;

import java.time.LocalDateTime;

import controller.UserController;

/**
 * Administrator class that extends the User class
 */

public class Administrator extends model.User {
    private final LocalDateTime dateOfCreation;

    /**
     * Constructor for creating an Administrator instance with the provided details.
     * Generates a unique UID for the Administrator and initializes all attributes.
     *
     * @param fullName the full name of the Administrator
     * @param username the username for login
     * @param email the email address of the Administrator
     * @param phoneNo the phone number of the Administrator
     * @param passwordHash the hashed password of the Administrator
     * @param DoB the date of birth of the Administrator
     * @param gender the gender of the Administrator
     * @param role the role of the Administrator
     * @param dateOfCreation the date the Administrator account was created
     */

    public Administrator(String fullName, String username, String email, String phoneNo,
                         String passwordHash, LocalDateTime DoB, String gender, String role, LocalDateTime dateOfCreation) {
        super(UserController.generateUID(enums.User.ADMINS), fullName, username, email, phoneNo, passwordHash, DoB, gender, "Admins");
        this.dateOfCreation = dateOfCreation;
    }
    
    /**
     * Constructor for creating an Administrator instance from CSV data.
     * Initializes the Administrator with the provided details, including the unique UID.
     *
     * @param UID the unique UID of the Administrator
     * @param fullName the full name of the Administrator
     * @param username the username for login
     * @param email the email address of the Administrator
     * @param phoneNo the phone number of the Administrator
     * @param passwordHash the hashed password of the Administrator
     * @param DoB the date of birth of the Administrator
     * @param gender the gender of the Administrator
     * @param role the role of the Administrator
     * @param dateOfCreation the date the Administrator account was created
     */

    public Administrator(String UID, String fullName, String username, String email, String phoneNo,
                         String passwordHash, LocalDateTime DoB, String gender, String role, LocalDateTime dateOfCreation) {
        super(UID, fullName, username, email, phoneNo, passwordHash, DoB, gender, role);
        this.dateOfCreation = dateOfCreation;
    }

    /**
     * Gets the date when the Administrator account was created.
     *
     * @return the date of creation of the Administrator account
     */

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

}