package model;

import java.time.LocalDateTime;

import controller.HMSPersonnelController;
import enums.PersonnelFileType;
/**
 * Represents an Admin in the HMS system, inheriting from the HMSPersonnel class.
 * Contains information specific to an Admin, including the date of creation.
 */
public class Admin extends HMSPersonnel {
    private LocalDateTime dateOfCreation;
    
    /**
     * Constructor for creating an Admin instance with the provided details.
     * Generates a unique UID for the Admin and initializes all attributes.
     * 
     * @param fullName the full name of the Admin
     * @param idCard the ID card number of the Admin
     * @param username the username for login
     * @param email the email address of the Admin
     * @param phoneNo the phone number of the Admin
     * @param passwordHash the hashed password of the Admin
     * @param DoB the date of birth of the Admin
     * @param gender the gender of the Admin
     * @param role the role of the Admin
     * @param dateOfCreation the date the Admin account was created
     */
    // Constructor for creation of Admin
    public Admin(String fullName, String idCard, String username, String email, String phoneNo,
                 String passwordHash, LocalDateTime DoB, String gender, String role, LocalDateTime dateOfCreation) {
        super(HMSPersonnelController.generateUID(PersonnelFileType.ADMINS), fullName, idCard, username, email, phoneNo, passwordHash, DoB, gender, "Admins");
        this.dateOfCreation = dateOfCreation;
    }
    
    /**
     * Constructor for creating an Admin instance from CSV data.
     * Initializes the Admin with the provided details, including the unique UID.
     * 
     * @param UID the unique UID of the Admin
     * @param fullName the full name of the Admin
     * @param idCard the ID card number of the Admin
     * @param username the username for login
     * @param email the email address of the Admin
     * @param phoneNo the phone number of the Admin
     * @param passwordHash the hashed password of the Admin
     * @param DoB the date of birth of the Admin
     * @param gender the gender of the Admin
     * @param role the role of the Admin
     * @param dateOfCreation the date the Admin account was created
     */
    // Constructor for creation of Admin from CSV
    public Admin(String UID, String fullName, String idCard, String username, String email, String phoneNo,
                 String passwordHash, LocalDateTime DoB, String gender, String role, LocalDateTime dateOfCreation) {
        super(UID, fullName, idCard, username, email, phoneNo, passwordHash, DoB, gender, role);
        this.dateOfCreation = dateOfCreation;
    }

    /**
     * Gets the date when the Admin account was created.
     * 
     * @return the date of creation of the Admin account
     */
    // Getter and Setter for dateOfCreation
    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }
    
    /**
     * Sets the date when the Admin account was created.
     * 
     * @param dateOfCreation the new date of creation for the Admin account
     */
    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
}