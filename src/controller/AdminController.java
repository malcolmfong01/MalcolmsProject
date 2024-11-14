package controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import enums.AppointmentStatus;
import enums.ReplenishStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import helper.Helper; // Ensure this import exists
import model.*; // Make sure these models are correctly imported
import repository.*;


/**
 * This class provides administrative functionalities related to medical records, personnel management,
 * medicine management, and appointment handling in a healthcare system.
 * <p>
 * It includes methods for listing, adding, updating, removing medicines, managing appointments, 
 * and displaying personnel details based on roles and gender.
 * </p>
 */
public class AdminController extends HMSPersonnelController {
	
	 /**
     * Lists all medicines currently available in the system.
     */
	public static void listAllMedicine() {
		MedicineController.listAllMedicines();
	}
	
	/**
     * Adds a new medicine to the system.
     * 
     * @param medicine The medicine to be added.
     */
	public static void addMedicine(Medicine medicine) {
		
		 MedicineController.addMedicine(medicine);
	}
	
	/**
     * Updates the details of an existing medicine in the system.
     * 
     * @param medicineID The ID of the medicine to be updated.
     * @param medicine The updated medicine object.
     */
	public static void updateMedicine(String medicineID, Medicine medicine) {
		 MedicineController.updateMedicine(medicineID, medicine);		
	}
	
	 /**
     * Removes a medicine from the system by its ID.
     * 
     * @param medicineID The ID of the medicine to be removed.
     */
	public static void removeMedicine(String medicineID) {
		 MedicineController.removeMedicine(medicineID);
	}
	
	/**
     * Approves a replenishment request for a medicine.
     * 
     * @param medicineID The ID of the medicine to be replenished.
     * @param medicine The updated medicine object after replenishment.
     */
	public static void approveReplenishRequest(String medicineID, Medicine medicine ) {
		
		 MedicineController.updateMedicine(medicineID, medicine);	
	}
    
	/**
     * Lists all appointment records in the system.
     */
	public static void listAllAppointments() {

	    System.out.println("\n--- All Appointment Records ---");

	    boolean found = false;

	    for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
	       
	        System.out.println("Appointment ID: " + appointment.getRecordID());
	        System.out.println("Patient ID: " + appointment.getPatientID());
	        System.out.println("Doctor ID: " + appointment.getDoctorID() );
	        System.out.println("Appointment Time: " + appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
	        System.out.println("Location: " + appointment.getLocation());
	        System.out.println("Status: " + appointment.getAppointmentStatus());
	        
	        AppointmentOutcomeRecord outcome = appointment.getAppointmentOutcomeRecord();
	        if (outcome == null) {
	        	System.out.println("Appointment Outcome Record Not Available");
	        	System.out.println("---------------------------------------");
	        	continue;
	        }
	        System.out.println("Appointment Outcome ID: " + outcome.getAppointmentOutcomeRecordID());
	        System.out.println("Consultation Notes: " + outcome.getConsultationNotes());
	        System.out.println("Service: " + outcome.getTypeOfService());
	        System.out.println("Appointment Outcome Status: " + outcome.getAppointmentOutcomeStatus());
	        
	        Prescription prescription = outcome.getPrescription();
	        if(prescription == null) {
	        	System.out.println("Prescription Not Available");
	        	System.out.println("---------------------------------------");
	        	continue;
	        }
	        System.out.println();
	        System.out.println("Prescription ");
	        System.out.println("Prescription Date: " + prescription.getPrescriptionDate() );
	        if(prescription.getMedications()== null) {
	        	System.out.println("Prescribed Medication Not Available");
	        	System.out.println("---------------------------------------");
	        	continue;
	        }
	        for(PrescribedMedication medication : prescription.getMedications()) {
	        	Medicine medicine = MedicineController.getMedicineByUID(medication.getMedicineID());
	        	System.out.println("Medicine name: " + medicine.getName());
	        	System.out.println("Period of Days: " + medication.getPeriodDays());
	        	System.out.println("Quantity: " + medication.getMedicineQuantity());
	        	System.out.println("Dosage: " + medication.getDosage());
	        	System.out.println("Prescription Status: " + medication.getPrescriptionStatus());
	        	System.out.println();
	        }
	        System.out.println("---------------------------------------");
	        found = true;
	    }

	    if (!found) {
	        System.out.println("No appointments found.");
	    }

	    System.out.println("---------------------------------------");
	}
	
    /**
     * Prints the details of a given personnel.
     * 
     * @param personnel The personnel whose details are to be printed.
     */
    public static void printPersonnelDetails(HMSPersonnel personnel) {
        System.out.println("--------------------------------------------------");
        System.out.println("Personnel Details:");
        System.out.println("--------------------------------------------------");
        System.out.printf("%-20s: %s%n", "UID", personnel.getUID());
        System.out.printf("%-20s: %s%n", "Full Name", personnel.getFullName());
        System.out.printf("%-20s: %s%n", "ID Card", personnel.getIdCard());
        System.out.printf("%-20s: %s%n", "Username", personnel.getUsername());
        System.out.printf("%-20s: %s%n", "Email", personnel.getEmail());
        System.out.printf("%-20s: %s%n", "Phone No", personnel.getPhoneNo());
        System.out.printf("%-20s: %s%n", "Password", personnel.getPasswordHash());
        System.out.printf("%-20s: %s%n", "DOB", personnel.getDoB());
        System.out.printf("%-20s: %s%n", "Gender", personnel.getGender());
        System.out.printf("%-20s: %s%n", "Role", personnel.getRole());
        System.out.println("--------------------------------------------------\n");
    }
    
    /**
     * Prints the details of a given doctor.
     * 
     * @param doctor The doctor whose details are to be printed.
     */
    public static void printDoctorDetails(Doctor doctor) {
        System.out.println("============== Doctor Details ==============");
        printPersonnelDetails(doctor);
        System.out.printf("%-20s: %s%n", "Specialty", doctor.getSpecialty());
        System.out.printf("%-20s: %s%n", "Medical License", doctor.getMedicalLicenseNumber());
        System.out.printf("%-20s: %s%n", "Date Joined", doctor.getDateJoin());
        System.out.printf("%-20s: %s years%n", "Experience", doctor.getYearsOfExperiences());
        System.out.println("===========================================\n");
        System.out.println();
    }
    
    /**
     * Prints the details of a given pharmacist.
     * 
     * @param pharmacist The pharmacist whose details are to be printed.
     */
    public static void printPharmacistDetails(Pharmacist pharmacist) {
        System.out.println("========== Pharmacist Details ===========");
        printPersonnelDetails(pharmacist);
        System.out.printf("%-20s: %s%n", "License Number", pharmacist.getPharmacistLicenseNumber());
        System.out.printf("%-20s: %s%n", "Date of Employment", pharmacist.getDateOfEmployment());
        System.out.println("=========================================\n");
        System.out.println();
    }
    
    /**
     * Lists all personnel filtered by role (Doctors or Pharmacists).
     * 
     * @param type The type of personnel to list (Doctors or Pharmacists).
     */
    public static void listPersonnelByRole(PersonnelFileType type) {
        Map<String, ? extends HMSPersonnel> personnelMap = null;

        switch (type) {
            case DOCTORS:
                personnelMap = PersonnelRepository.DOCTORS;
                break;
            case PHARMACISTS:
                personnelMap = PersonnelRepository.PHARMACISTS;
                break;
            default:
                System.out.println("Error: Unsupported personnel type.");
                return;
        }

        if (personnelMap != null && !personnelMap.isEmpty()) {
            System.out.println("\nListing all personnel of type: " + type);
            System.out.println("===========================================");
            for (HMSPersonnel personnel : personnelMap.values()) {
                if (type == PersonnelFileType.DOCTORS) {
                    printDoctorDetails((Doctor) personnel);
                } else if (type == PersonnelFileType.PHARMACISTS) {
                    printPharmacistDetails((Pharmacist) personnel);
                }
            }
            System.out.println("===========================================\n");
        } else {
            System.out.println("No personnel found for type: " + type);
        }
    }
    
    /**
     * Lists all personnel filtered by gender.
     * 
     * @param gender The gender of personnel to list.
     */
    public static void listPersonnelByGender(String gender) {
        System.out.println("\nListing staff filtered by gender: " + gender);
        System.out.println("===========================================");
        
        for (Doctor doctor : PersonnelRepository.DOCTORS.values()) {
            if (doctor.getGender().equalsIgnoreCase(gender)) {
                printDoctorDetails(doctor);
            }
        }
        
        for (Pharmacist pharmacist : PersonnelRepository.PHARMACISTS.values()) {
            if (pharmacist.getGender().equalsIgnoreCase(gender)) {
                printPharmacistDetails(pharmacist);
            }
        }
        System.out.println("===========================================\n");
    }
    
   
    /**
     * Lists all personnel sorted by age from oldest to youngest.
     */
    public static void listPersonnelByAge() {
        List<HMSPersonnel> combinedList = new ArrayList<>();
        
        combinedList.addAll(PersonnelRepository.DOCTORS.values());
        combinedList.addAll(PersonnelRepository.PHARMACISTS.values());
        
        // Sort the combined list by age from oldest to youngest
        combinedList.sort(Comparator.comparingInt(personnel -> calculateAge((HMSPersonnel) personnel)).reversed());

        System.out.println("\nListing all personnel sorted by age (oldest to youngest):");
        System.out.println("===========================================");
        for (HMSPersonnel personnel : combinedList) {
            if (personnel instanceof Doctor) {
                printDoctorDetails((Doctor) personnel);
            } else if (personnel instanceof Pharmacist) {
                printPharmacistDetails((Pharmacist) personnel);
            }
        }
        System.out.println("===========================================\n");
    }

    /**
     * Calculates the age of a personnel based on their date of birth.
     * 
     * @param personnel The personnel whose age is to be calculated.
     * @return The age in years.
     */
    public static int calculateAge(HMSPersonnel personnel) {
        LocalDate birthDate = personnel.getDoB().toLocalDate(); // Ensure this returns LocalDate
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

}

	    
	

	
	

	