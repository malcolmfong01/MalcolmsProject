package model;

import java.time.LocalDateTime;

import controller.HMSPersonnelController;
import enums.PersonnelFileType;

public class Patient extends HMSPersonnel {



    private String allergies;
    private LocalDateTime dateOfAdmission;

    // Constructor for creation of Patient
    public Patient(String fullName, String username, String email, String phoneNo,
                   String passwordHash, LocalDateTime DoB, String gender, String allergies, LocalDateTime dateOfAdmission) {
        super(HMSPersonnelController.generateUID(PersonnelFileType.PATIENTS), fullName, username, email, phoneNo, passwordHash, DoB, gender, "Patients");



        this.allergies = allergies;
        this.dateOfAdmission = dateOfAdmission;
    }
    
    // Constructor for creation of Patient from CSV
    public Patient(String UID, String fullName, String username, String email, String phoneNo,
                   String passwordHash, LocalDateTime DoB, String gender, String role, String allergies, LocalDateTime dateOfAdmission) {
        super(UID, fullName, username, email, phoneNo, passwordHash, DoB, gender, role);



        this.allergies = allergies;
        this.dateOfAdmission = dateOfAdmission;
    }

    // Getters and Setters



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