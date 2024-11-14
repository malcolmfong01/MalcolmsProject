package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HMSPersonnel {
    private static final long serialVersionUID = 1L;  // Optional but recommended for Serializable classes
    
    private String UID;
    private String fullName;
    private String idCard;
    private String username;
    private String email;
    private String phoneNo;
    private String passwordHash;
    private LocalDateTime DoB;
    private String gender;
    private String role;

    // Constructor
    public HMSPersonnel(String UID, String fullName, String idCard, String username, String email, String phoneNo,
                        String passwordHash, LocalDateTime DoB, String gender, String role) {
        this.UID = UID;
        this.fullName = fullName;
        this.idCard = idCard;
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.passwordHash = passwordHash;
        this.DoB = DoB;
        this.gender = gender;
        this.role = role;
    }

    // Getters and Setters
    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDateTime getDoB() {
        return DoB;
    }

    public void setDoB(LocalDateTime DoB) {
        this.DoB = DoB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
