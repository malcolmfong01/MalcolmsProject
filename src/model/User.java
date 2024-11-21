package model;

import model.userInterfaces.*;

import java.time.LocalDateTime;

/**
 * User Super class in the Hospital Management System (HMS).
 * This class holds personal and contact details for a personnel, as well as their role in the system.
 */

public class User implements Authenticable, Contactable, Identification, PersonalDetails, RoleAssignable {

    private String UID;
    private String fullName;
    private String username;
    private String email;
    private String phoneNo;
    private String passwordHash;
    private LocalDateTime DoB;
    private String gender;
    private String role;

    /**
     * Constructs a new User object with the specified details.
     *
     * @param UID the unique ID for the personnel
     * @param fullName the full name of the personnel
     * @param username the username of the personnel for system login
     * @param email the email address of the personnel
     * @param phoneNo the phone number of the personnel
     * @param passwordHash the hashed password for the personnel's account
     * @param DoB the date of birth of the personnel
     * @param gender the gender of the personnel
     * @param role the role or position of the personnel in the system
     */

    public User(String UID, String fullName, String username, String email, String phoneNo,
                String passwordHash, LocalDateTime DoB, String gender, String role) {
        this.UID = UID;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.passwordHash = passwordHash;
        this.DoB = DoB;
        this.gender = gender;
        this.role = role;
    }

    /**
     * Gets the unique ID of the personnel.
     *
     * @return the UID of the personnel
     */
    // Getters and Setters
    public String getUID() {
        return UID;
    }

    /**
     * Sets the unique ID for the personnel.
     *
     * @param UID the UID to set
     */
    public void setUID(String UID) {
        this.UID = UID;
    }

    /**
     * Gets the full name of the personnel.
     *
     * @return the full name of the personnel
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the full name of the personnel.
     *
     * @param fullName the full name to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    /**
     * Gets the username of the personnel for login.
     *
     * @return the username of the personnel
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for the personnel.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email address of the personnel.
     *
     * @return the email address of the personnel
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address for the personnel.
     *
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number of the personnel.
     *
     * @return the phone number of the personnel
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * Sets the phone number for the personnel.
     *
     * @param phoneNo the phone number to set
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * Gets the hashed password of the personnel.
     *
     * @return the hashed password of the personnel
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets the hashed password for the personnel.
     *
     * @param passwordHash the hashed password to set
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Gets the date of birth of the personnel.
     *
     * @return the date of birth of the personnel
     */
    public LocalDateTime getDoB() {
        return DoB;
    }

    /**
     * Sets the date of birth for the personnel.
     *
     * @param DoB the date of birth to set
     */
    public void setDoB(LocalDateTime DoB) {
        this.DoB = DoB;
    }

    /**
     * Gets the gender of the personnel.
     *
     * @return the gender of the personnel
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender for the personnel.
     *
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets the role of the personnel within the system.
     *
     * @return the role of the personnel
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role for the personnel.
     *
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }
}
