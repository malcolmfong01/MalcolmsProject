package controller;

import java.util.Map;

import enums.User;
import model.*;
import repository.*;

/**
 * The UserController class is a super class that provides methods to access and retrieve user-related information
 * from the UserRepository.
 */
public class UserController {
    /**
     * Generates a unique ID (UID) for the personnel based on their type.
     * @param user the type of personnel (Administrator, Doctor, Patient, Pharmacist)
     * @return a unique UID string for the personnel
     */
    public static String generateUID(User user) {
        String prefix = "";
        int nextId = 0;
        Map<String, ? extends model.User> repository = null;

        switch (user) {
            case ADMINS:
                prefix = "A";
                repository = UserRepository.ADMINS;
                break;
            case DOCTORS:
                prefix = "D";
                repository = UserRepository.DOCTORS;
                break;
            case PATIENTS:
                prefix = "P";
                repository = UserRepository.PATIENTS;
                break;
            case PHARMACISTS:
                prefix = "PH";
                repository = UserRepository.PHARMACISTS;
                break;
            default:
                return "";
        }
        // Find the highest ID currently in the repository
        if (repository != null && !repository.isEmpty()) {
            for (String idCard : repository.keySet()) {
                if (idCard.startsWith(prefix)) {
                    try {
                        // Extract the numeric part after the prefix and parse it
                        int currentId = Integer.parseInt(idCard.substring(prefix.length()));
                        nextId = Math.max(nextId, currentId + 1);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID format: " + idCard);
                        // Handle the error or skip the invalid entry
                    }
                }
            }
        }

        // Format the next ID with leading zeros (e.g., "A001")
        return String.format("%s%03d", prefix, nextId);
    }
    /**
     * Adds a new user (e.g., Doctor, Patient, etc.) to the system.
     * @param user the user to be added
     * @return true if user is successfully added, false if the data is invalid
     */
    public static boolean addUser(model.User user) {
        if (user == null) {
            System.out.println("Error: Invalid user data.");
            return false;
        }
        // Automatically generate UID if not provided
        if (user.getUID() == null || user.getUID().isEmpty()) {
            user.setUID(generateUID(determinePersonnelType(user)));
        }

        // Determine the type of user and add it to the appropriate collection using a switch statement
        switch (user) {
            case Doctor doctor -> {
                UserRepository.DOCTORS.put(doctor.getUID(), doctor);
                System.out.println("Doctor added: " + doctor.getFullName());
            }
            case Patient patient -> {
                UserRepository.PATIENTS.put(patient.getUID(), patient);
                System.out.println("Patient added: " + patient.getFullName());
            }
            case Pharmacist pharmacist -> {
                UserRepository.PHARMACISTS.put(pharmacist.getUID(), pharmacist);
                System.out.println("Pharmacist added: " + pharmacist.getFullName());
            }
            case Administrator administrator -> {
                UserRepository.ADMINS.put(administrator.getUID(), administrator);
                System.out.println("Administrator added: " + administrator.getFullName());
            }
            default -> {
                System.out.println("Error: Unsupported user type.");
                return false;
            }
        }
        // Save the updated user to the file
        UserRepository.saveAllPersonnelFiles();
        return true;
    }


    // Determine personnel type based on the class of personnel
    private static User determinePersonnelType(model.User personnel) {
        if (personnel instanceof Doctor) {
            return User.DOCTORS;
        } else if (personnel instanceof Patient) {
            return User.PATIENTS;
        } else if (personnel instanceof Pharmacist) {
            return User.PHARMACISTS;
        } else if (personnel instanceof Administrator) {
            return User.ADMINS;
        }
        return null;
    }

    /**
     * Removes a user from the system based on their UID and type.
     * @param UID the UID of the user to be removed
     * @param type the type of user (Administrator, Doctor, Patient, Pharmacist)
     * @return true if user is successfully removed, false if UID is invalid or user not found
     */
    // Remove personnel by UID
    public static boolean removeUser(String UID, User type) {
        if (UID == null || UID.isEmpty()) {
            System.out.println("Error: Invalid ID Card.");
            return false;
        }

        switch (type) {
            case DOCTORS:
                Doctor removedDoctor = UserRepository.DOCTORS.remove(UID);
                if (removedDoctor != null) {
                    System.out.println("Doctor removed: " + removedDoctor.getFullName());
                    UserRepository.saveAllPersonnelFiles();
                    return true;
                }
                break;
            case PATIENTS:
                Patient removedPatient = UserRepository.PATIENTS.remove(UID);
                if (removedPatient != null) {
                    System.out.println("Patient removed: " + removedPatient.getFullName());
                    UserRepository.saveAllPersonnelFiles();
                    return true;
                }
                break;
            case PHARMACISTS:
                Pharmacist removedPharmacist = UserRepository.PHARMACISTS.remove(UID);
                if (removedPharmacist != null) {
                    System.out.println("Pharmacist removed: " + removedPharmacist.getFullName());
                    UserRepository.saveAllPersonnelFiles();
                    return true;
                }
                break;
            case ADMINS:
                Administrator removedAdministrator = UserRepository.ADMINS.remove(UID);
                if (removedAdministrator != null) {
                    System.out.println("Administrator removed: " + removedAdministrator.getFullName());
                    UserRepository.saveAllPersonnelFiles();
                    return true;
                }
                break;
            default:
                System.out.println("Error: Unsupported personnel type.");
                return false;
        }

        System.out.println("Error: Personnel not found with ID Card: " + UID);
        return false;
    }

    /**
     * Generates an ID for User
     * @param UID
     * @param type
     * @return
     */
    public static model.User getUserbyUID(String UID, User type) {
        if (UID == null || UID.isEmpty()) {
            System.out.println("Error: Invalid ID Card.");
            return null;
        }

        return switch (type) {
            case DOCTORS -> UserRepository.DOCTORS.get(UID);
            case PATIENTS -> UserRepository.PATIENTS.get(UID);
            case PHARMACISTS -> UserRepository.PHARMACISTS.get(UID);
            case ADMINS -> UserRepository.ADMINS.get(UID);
            default -> {
                System.out.println("Error: Unsupported personnel type.");
                yield null; // 'yield' is used to return values from a block
            }
        };
    }

    /**
     * Updates the details of a personnel member based on their UID.
     * @param UID the UID of the personnel to update
     * @param updatedPersonnel the new personnel data
     * @return true if the personnel is successfully updated, false otherwise
     */
    // Update personnel details
    public static boolean updatePersonnel(String UID, model.User updatedPersonnel) {
        if (UID == null || UID.isEmpty() || updatedPersonnel == null) {
            System.out.println("Error: Invalid update request.");
            return false;
        }

        // Determine the type of personnel and update the record
        if (updatedPersonnel instanceof Doctor && UserRepository.DOCTORS.containsKey(UID)) {
            UserRepository.DOCTORS.put(UID, (Doctor) updatedPersonnel);
            System.out.println("Doctor updated: " + updatedPersonnel.getFullName());
        } else if (updatedPersonnel instanceof Patient && UserRepository.PATIENTS.containsKey(UID)) {
            UserRepository.PATIENTS.put(UID, (Patient) updatedPersonnel);
            System.out.println("Patient updated: " + updatedPersonnel.getFullName());
        } else if (updatedPersonnel instanceof Pharmacist && UserRepository.PHARMACISTS.containsKey(UID)) {
            UserRepository.PHARMACISTS.put(UID, (Pharmacist) updatedPersonnel);
            System.out.println("Pharmacist updated: " + updatedPersonnel.getFullName());
        } else if (updatedPersonnel instanceof Administrator && UserRepository.ADMINS.containsKey(UID)) {
            UserRepository.ADMINS.put(UID, (Administrator) updatedPersonnel);
            System.out.println("Administrator updated: " + updatedPersonnel.getFullName());
        } else {
            System.out.println("Error: Personnel not found for update.");
            return false;
        }

        // Save the updated data
        UserRepository.saveAllPersonnelFiles();
        return true;
    }

    /**
     * Retrieves a patient by their UID.
     * @param UID the UID of the patient to retrieve
     * @return the Patient object if found, or null if not found
     */
    // Retrieve a patient by UID
    public static Patient getPatientById(String UID) {
        if (UID == null || UID.isEmpty()) {
            System.out.println("Error: Invalid ID Card.");
            return null;
        }

        Patient patient = UserRepository.PATIENTS.get(UID);
        if (patient == null) {
            System.out.println("Error: Patient not found with ID Card: " + UID);
        }
        return patient;
    }
    /**
     * Updates a patient's particulars by UID.
     * @param UID the UID of the patient to update
     * @param updatedPatient the updated patient details
     * @return true if the update was successful, false otherwise
     */
    // Update a patient's particulars by UID
    // Update a patient's particulars by UID
    public static boolean updatePatientParticulars(String UID, Patient updatedPatient) {
        if (UID == null || UID.isEmpty() || updatedPatient == null) {
            System.out.println("Error: Invalid ID Card or patient data.");
            return false;
        }

        // Retrieve the existing patient
        Patient existingPatient = UserRepository.PATIENTS.get(UID);

        if (existingPatient == null) {
            System.out.println("Error: Patient not found with ID Card: " + UID);
            return false;
        }

        // Update fields from User class
        existingPatient.setFullName(updatedPatient.getFullName());
        existingPatient.setUsername(updatedPatient.getUsername());
        existingPatient.setEmail(updatedPatient.getEmail());
        existingPatient.setPhoneNo(updatedPatient.getPhoneNo());
        existingPatient.setPasswordHash(updatedPatient.getPasswordHash());
        existingPatient.setDoB(updatedPatient.getDoB());
        existingPatient.setGender(updatedPatient.getGender());

        // Update fields specific to Patient class
        existingPatient.setAllergies(updatedPatient.getAllergies());
        existingPatient.setDateOfAdmission(updatedPatient.getDateOfAdmission());

        // Save changes to repository
        UserRepository.PATIENTS.put(UID, existingPatient);
        UserRepository.saveAllPersonnelFiles();

        System.out.println("Patient details updated successfully for ID Card: " + UID);
        return true;
    }
}
