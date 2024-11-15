package model;

import java.time.LocalDateTime;

import controller.HMSPersonnelController;
import enums.PersonnelFileType;

public class Pharmacist extends HMSPersonnel {
    private LocalDateTime dateOfEmployment;

    // Constructor for creation of Pharmacist
    public Pharmacist( String fullName, String username, String email, String phoneNo,
                      String passwordHash, LocalDateTime DoB, String gender, LocalDateTime dateOfEmployment) {
        super(HMSPersonnelController.generateUID(PersonnelFileType.PHARMACISTS), fullName, username, email, phoneNo, passwordHash, DoB, gender, "PHARMACISTS");
        this.dateOfEmployment = dateOfEmployment;
    }
    
 // Constructor for creation of Pharmacist from csv
    public Pharmacist(String UID, String fullName, String username, String email, String phoneNo,
            String passwordHash, LocalDateTime DoB, String gender, String role, LocalDateTime dateOfEmployment) {
		super(UID, fullName, username, email, phoneNo, passwordHash, DoB, gender, role);

		this.dateOfEmployment = dateOfEmployment;
    }

    // Getters and Setters

    public LocalDateTime getDateOfEmployment() {
        return dateOfEmployment;
    }

    public void setDateOfEmployment(LocalDateTime dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }
}