package model;

import java.time.LocalDateTime;

import controller.HMSPersonnelController;
import repository.PersonnelFileType;

public class Patient extends HMSPersonnel {


    private String insuranceInfo;
    private String allergies;
    private LocalDateTime dateOfAdmission;

    // Constructor for creation of Patient
    public Patient(String fullName, String idCard, String username, String email, String phoneNo,
                   String passwordHash, LocalDateTime DoB, String gender,
                    String insuranceInfo, String allergies, LocalDateTime dateOfAdmission) {
        super(HMSPersonnelController.generateUID(PersonnelFileType.PATIENTS), fullName, idCard, username, email, phoneNo, passwordHash, DoB, gender, "Patients");


        this.insuranceInfo = insuranceInfo;
        this.allergies = allergies;
        this.dateOfAdmission = dateOfAdmission;
    }
    
    // Constructor for creation of Patient from CSV
    public Patient(String UID, String fullName, String idCard, String username, String email, String phoneNo,
                   String passwordHash, LocalDateTime DoB, String gender, String role,
                    String insuranceInfo, String allergies, LocalDateTime dateOfAdmission) {
        super(UID, fullName, idCard, username, email, phoneNo, passwordHash, DoB, gender, role);


        this.insuranceInfo = insuranceInfo;
        this.allergies = allergies;
        this.dateOfAdmission = dateOfAdmission;
    }

    // Getters and Setters


    public String getInsuranceInfo() {
        return insuranceInfo;
    }

    public void setInsuranceInfo(String insuranceInfo) {
        this.insuranceInfo = insuranceInfo;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public LocalDateTime getDateOfAdmission() {
        return dateOfAdmission;
    }

    public void setDateOfAdmission(LocalDateTime dateOfAdmission) {
        this.dateOfAdmission = dateOfAdmission;
    }
}