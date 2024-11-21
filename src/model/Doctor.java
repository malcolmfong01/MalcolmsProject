package model;

import java.time.LocalDateTime;

import controller.UserController;
import enums.User;

/**
 * Doctor class that extends the User class
 */

public class Doctor extends model.User {
    private final LocalDateTime dateJoin;

    /**
     * Constructs a Doctor object with the specified details.
     *
     * @param fullName the full name of the doctor
     * @param username the username of the doctor
     * @param email the email address of the doctor
     * @param phoneNo the phone number of the doctor
     * @param passwordHash the hashed password of the doctor
     * @param DoB the date of birth of the doctor
     * @param gender the gender of the doctor
     * @param dateJoin the date the doctor joined the organization
     */

    public Doctor(
    			String fullName, String username, String email, String phoneNo,
                  String passwordHash, LocalDateTime DoB, String gender,
                   LocalDateTime dateJoin) {
        super(UserController.generateUID(User.DOCTORS), fullName, username, email, phoneNo, passwordHash, DoB, gender, "Doctors");
        this.dateJoin = dateJoin;

    }
    
    /**
     * Constructs a Doctor object from CSV data.
     *
     * @param UID the unique ID of the doctor
     * @param fullName the full name of the doctor
     * @param username the username of the doctor
     * @param email the email address of the doctor
     * @param phoneNo the phone number of the doctor
     * @param passwordHash the hashed password of the doctor
     * @param DoB the date of birth of the doctor
     * @param gender the gender of the doctor
     * @param role the role of the doctor (e.g., "Doctors")
     * @param dateJoin the date the doctor joined the organization
     */

    public Doctor(String UID, 
    			String fullName, String username, String email, String phoneNo,
                  String passwordHash, LocalDateTime DoB, String gender, String role, LocalDateTime dateJoin) {
        super(UID, fullName, username, email, phoneNo, passwordHash, DoB, gender, role);
        this.dateJoin = dateJoin;

    }
    /**
     * Gets the date when the doctor joined the organization.
     *
     * @return the date the doctor joined
     */

    public LocalDateTime getDateJoin() {
        return dateJoin;
    }

}