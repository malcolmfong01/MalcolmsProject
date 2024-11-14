package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import controller.MedicineController;
import controller.PharmacistController;
import enums.PrescriptionStatus;
import enums.ReplenishStatus;
import enums.AppointmentOutcomeStatus;
import repository.AppointmentOutcomeRecordRepository;
import repository.DiagnosisRepository;
import repository.MedicineRepository;
import repository.PersonnelRepository;
import repository.PrescribedMedicationRepository;
import repository.PrescriptionRepository;
import repository.Repository;

public class Dummymain {

    public static void main(String[] args) {
        // 1. Create and add new Medicine objects to the repository
        Medicine med1 = new Medicine("M001", "Paracetamol", "ABC Pharmaceuticals",
                LocalDateTime.of(2025, 6, 30, 0, 0), 100, 20, ReplenishStatus.NULL, LocalDateTime.of(2023, 12, 1, 0, 0), LocalDateTime.of(1, 1, 1, 0, 0));
        
        Medicine med2 = new Medicine("M002", "Ibuprofen", "XYZ Pharma",
                LocalDateTime.of(2024, 8, 15, 0, 0), 50, 10, ReplenishStatus.NULL, LocalDateTime.of(2023, 11, 20, 0, 0), LocalDateTime.of(1, 1, 1, 0, 0));
        
        // Adding these medicines to the repository's in-memory collection
        MedicineRepository.MEDICINES.put(med1.getMedicineID(), med1);
        MedicineRepository.MEDICINES.put(med2.getMedicineID(), med2);

        // 2. Save all medicines to CSV
        System.out.println("Saving medicines to CSV...");
        MedicineRepository.saveAllMedicinesToCSV();

        // 3. Clear in-memory data to simulate fresh load
        System.out.println("Clearing in-memory medicine data...");
        MedicineRepository.MEDICINES.clear();

        // 4. Load medicines from CSV to verify persistence
        System.out.println("Loading medicines from CSV...");
        Repository.loadRepository(new MedicineRepository());

        // 5. Display loaded medicines to verify correct loading
        System.out.println("\nLoaded Medicines:");
        MedicineController.listAllMedicines();
        System.out.println("---- Medicine Repository Test Complete ----");

        // 1. Create and add new Pharmacist objects to the repository
        Pharmacist pharm1 = new Pharmacist("P001", "Alice Tan", "S1234567A", "alice.tan",
                "alice@example.com", "12345678", "passwordHash1", LocalDateTime.of(1985, 5, 15, 0, 0),
                "Female", "Pharmacists", "PLN123", LocalDateTime.of(2010, 6, 1, 0, 0));
        Pharmacist pharm2 = new Pharmacist("P002", "Bob Lim", "S2345678B", "bob.lim",
                "bob@example.com", "23456789", "passwordHash2", LocalDateTime.of(1982, 8, 20, 0, 0),
                "Male", "Pharmacists", "PLN456", LocalDateTime.of(2012, 3, 12, 0, 0));
        Pharmacist pharm3 = new Pharmacist("P003", "Charlie Wong", "S3456789C", "charlie.wong",
                "charlie@example.com", "34567890", "passwordHash3", LocalDateTime.of(1990, 11, 25, 0, 0),
                "Male", "Pharmacists", "PLN789", LocalDateTime.of(2015, 11, 5, 0, 0));

        // Add these pharmacists to the repository's in-memory collection
        PersonnelRepository.PHARMACISTS.put(pharm1.getUID(), pharm1);
        PersonnelRepository.PHARMACISTS.put(pharm2.getUID(), pharm2);
        PersonnelRepository.PHARMACISTS.put(pharm3.getUID(), pharm3);

        // Step 2: Save all personnel data, including pharmacists, to CSV
        System.out.println("Saving pharmacists to CSV...");
        PersonnelRepository.saveAllPersonnelFiles();

        // Step 3: Clear in-memory pharmacist data to simulate a fresh load
        System.out.println("Clearing in-memory pharmacist data...");
        PersonnelRepository.PHARMACISTS.clear();

        // Step 4: Load pharmacists from CSV to verify persistence
        System.out.println("Loading pharmacists from CSV...");
        PersonnelRepository personnelRepository = new PersonnelRepository();
        personnelRepository.loadFromCSV();

        System.out.println("---- Pharmacist Repository Test Complete ----");

        // 2. Create sample prescribed medications
        ArrayList<PrescribedMedication> medications1 = new ArrayList<>();
        medications1.add(new PrescribedMedication("D001", "M001", 10, 7, PrescriptionStatus.PENDING, "1 tablet daily"));
        medications1.add(new PrescribedMedication("D001", "M002", 5, 5, PrescriptionStatus.PENDING, "2 tablets daily"));

        ArrayList<PrescribedMedication> medications2 = new ArrayList<>();
        medications2.add(new PrescribedMedication("D002", "M003", 15, 10, PrescriptionStatus.PENDING, "1 tablet twice daily"));

        // 3. Save prescribed medications to repository
        PrescribedMedicationRepository.diagnosisToMedicationsMap.put("D001", medications1);
        PrescribedMedicationRepository.diagnosisToMedicationsMap.put("D002", medications2);
        PrescribedMedicationRepository.saveAlltoCSV();

        // 4. Create prescriptions associated with each diagnosis ID
        Prescription prescription1 = new Prescription("D001", LocalDateTime.now().minusDays(2), medications1);
        Prescription prescription2 = new Prescription("D002", LocalDateTime.now().minusDays(1), medications2);
        PrescriptionRepository.PRESCRIPTION_MAP.put("D001", prescription1);
        PrescriptionRepository.PRESCRIPTION_MAP.put("D002", prescription2);
        PrescriptionRepository.saveAlltoCSV();

        // 5. Create sample diagnosis records with prescriptions
        Diagnosis diagnosis1 = new Diagnosis(
                "P001", "D001", "Dr001", "MR001", LocalDateTime.now().minusDays(2), null, 
                "General illness diagnosis", prescription1);
        Diagnosis diagnosis2 = new Diagnosis(
                "P001", "D002", "Dr002", "MR002", LocalDateTime.now().minusDays(1), null, 
                "Follow-up diagnosis for illness", prescription2);

        // 6. Save diagnosis records to repository
        DiagnosisRepository.addDiagnosis("P001", diagnosis1);
        DiagnosisRepository.addDiagnosis("P001", diagnosis2);
        DiagnosisRepository.saveAlltoCSV();

        // 7. Create and add appointment outcome records with prescriptions
        AppointmentOutcomeRecord outcomeRecord1 = new AppointmentOutcomeRecord(
                "P001", "Dr001", "D001", "AOR001", LocalDateTime.now().minusDays(2), 
                prescription1, "General Consultation", "Patient advised on medication usage.", 
                AppointmentOutcomeStatus.COMPLETED
        );

        AppointmentOutcomeRecord outcomeRecord2 = new AppointmentOutcomeRecord(
                "P001", "Dr002", "D002", "AOR002", LocalDateTime.now().minusDays(1), 
                prescription2, "Follow-up Consultation", "Patient's condition improving.",
                AppointmentOutcomeStatus.COMPLETED
        );

        AppointmentOutcomeRecordRepository.addAppointmentOutcomeRecord("P001", outcomeRecord1);
        AppointmentOutcomeRecordRepository.addAppointmentOutcomeRecord("P001", outcomeRecord2);
        AppointmentOutcomeRecordRepository.saveAppointmentOutcomeRecordRepository();

        // Reload from CSV to simulate persistence
        System.out.println("Clearing in-memory diagnosis data...");
        DiagnosisRepository.patientDiagnosisRecords.clear();

        System.out.println("Loading diagnosis records from CSV...");
        Repository.loadRepository(new DiagnosisRepository());

        System.out.println("Clearing in-memory appointment outcome record data...");
        AppointmentOutcomeRecordRepository.patientOutcomeRecords.clear();

        System.out.println("Loading appointment outcome records from CSV...");
        Repository.loadRepository(new AppointmentOutcomeRecordRepository());

//        // Test viewAppointmentOutcomeRecords in PharmacistController
//        System.out.println("\nTesting viewAppointmentOutcomeRecords:");
//        PharmacistController.viewAppointmentOutcomeRecords();

        // Test updatePrescriptionStatus in PharmacistController
        System.out.println("\nTesting updatePrescriptionStatus:");
        PharmacistController.updatePrescriptionStatus();
    }
}