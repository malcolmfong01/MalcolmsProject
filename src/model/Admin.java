package model;

import java.time.LocalDateTime;

import controller.HMSPersonnelController;
import enums.PersonnelFileType;

public class Admin extends HMSPersonnel {
    private LocalDateTime dateOfCreation;

    // Constructor for creation of Admin
    public Admin(String fullName, String username, String email, String phoneNo,
                 String passwordHash, LocalDateTime DoB, String gender, String role, LocalDateTime dateOfCreation) {
        super(HMSPersonnelController.generateUID(PersonnelFileType.ADMINS), fullName, username, email, phoneNo, passwordHash, DoB, gender, "Admins");
        this.dateOfCreation = dateOfCreation;
    }
    
    // Constructor for creation of Admin from CSV
    public Admin(String UID, String fullName,  String username, String email, String phoneNo,
                 String passwordHash, LocalDateTime DoB, String gender, String role, LocalDateTime dateOfCreation) {
        super(UID, fullName, username, email, phoneNo, passwordHash, DoB, gender, role);
        this.dateOfCreation = dateOfCreation;
    }

    // Getter and Setter for dateOfCreation
    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
}