package model;

import java.time.LocalDateTime;

import controller.HMSPersonnelController;
import enums.PersonnelFileType;

public class Doctor extends HMSPersonnel {
    private LocalDateTime dateJoin;


    // Constructor for creation of Doctors
    public Doctor(
    			String fullName, String username, String email, String phoneNo,
                  String passwordHash, LocalDateTime DoB, String gender,
                   LocalDateTime dateJoin) {
        super(HMSPersonnelController.generateUID(PersonnelFileType.DOCTORS), fullName, username, email, phoneNo, passwordHash, DoB, gender, "Doctors");
        this.dateJoin = dateJoin;

    }
    
    // Constructor for creation of Doctors from CSV
    public Doctor(String UID, 
    			String fullName, String username, String email, String phoneNo,
                  String passwordHash, LocalDateTime DoB, String gender, String role, LocalDateTime dateJoin) {
        super(UID, fullName, username, email, phoneNo, passwordHash, DoB, gender, role);
        this.dateJoin = dateJoin;

    }

    public LocalDateTime getDateJoin() {
        return dateJoin;
    }

    public void setDateJoin(LocalDateTime dateJoin) {
        this.dateJoin = dateJoin;
    }


}