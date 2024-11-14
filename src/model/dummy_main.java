// package model;
//
// import repository.*;
// import view.HMSAppUI;
// import model.*;
// import enums.PrescriptionStatus;
//
// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;
//
// public class dummy_main {
// public static void main(String[] args) {
//
// HMSAppUI hms = new HMSAppUI();
// hms.startTestingEnv();
//
// System.out.println(hms.isAllRepoLoaded());
//
//
//
// // Create dummy patients
// Patient patient1 = new Patient(
// "Alice Smith", "ID123456", "alice_smith", "alice@example.com", "1234567890",
// "hashedpassword1", LocalDateTime.of(1990, 5, 15, 0, 0), "Female",
// "Insurance123", "Peanuts", LocalDateTime.now().minusDays(10)
// );
//
// Patient patient2 = new Patient(
// "Bob Johnson", "ID234567", "bob_johnson", "bob@example.com", "0987654321",
// "hashedpassword2", LocalDateTime.of(1985, 3, 22, 0, 0), "Male",
// "Insurance456", "Penicillin", LocalDateTime.now().minusDays(20)
// );
//
// Patient patient3 = new Patient(
// "Charlie Brown", "ID345678", "charlie_brown", "charlie@example.com",
// "1122334455",
// "hashedpassword3", LocalDateTime.of(1992, 12, 1, 0, 0), "Male",
// "Insurance789", "None", LocalDateTime.now().minusDays(5)
// );
//
//
//
// Doctor doctor1 = new Doctor(
// "Dr. John Smith", "ID001234", "john_smith", "john.smith@example.com",
// "1234567890",
// "hashedpassword1", LocalDateTime.of(1975, 4, 12, 0, 0), "Male",
// "Cardiology", "MLN12345", LocalDateTime.of(2010, 5, 20, 0, 0), 15
// );
//
// Doctor doctor2 = new Doctor(
// "Dr. Emily Johnson", "ID002345", "emily_johnson",
// "emily.johnson@example.com", "0987654321",
// "hashedpassword2", LocalDateTime.of(1980, 8, 22, 0, 0), "Female",
// "Neurology", "MLN23456", LocalDateTime.of(2012, 6, 15, 0, 0), 12
// );
//
// Doctor doctor3 = new Doctor(
// "Dr. Michael Lee", "ID003456", "michael_lee", "michael.lee@example.com",
// "1122334455",
// "hashedpassword3", LocalDateTime.of(1982, 1, 5, 0, 0), "Male",
// "Orthopedics", "MLN34567", LocalDateTime.of(2015, 7, 10, 0, 0), 9
// );
//
// // Prescribed Medication Example 1
// PrescribedMedication med1 = new PrescribedMedication(
// "D001", // diagnosisID
// "Med001", // medicineID
// "30 tablets", // medicineQuantity
// 30, // periodDays
// PrescriptionStatus.PENDING, // PrescriptionStatus
// "1 tablet daily" // dosage
// );
//
// // Prescribed Medication Example 2
// PrescribedMedication med2 = new PrescribedMedication(
// "D002",
// "Med002",
// "60 capsules",
// 60,
// PrescriptionStatus.DISPENSED,
// "2 capsules after meal"
// );
//
// // Prescribed Medication Example 3
// PrescribedMedication med3 = new PrescribedMedication(
// "D002",
// "Med003",
// "15 ml bottle",
// 7,
// PrescriptionStatus.PENDING,
// "5 ml twice daily"
// );
// TreatmentPlans treatmentPlan1 = new TreatmentPlans("D001",
// LocalDateTime.of(2023, 10, 20, 14, 30), "Wait to die");
// TreatmentPlans treatmentPlan2 = new TreatmentPlans("D002",
// LocalDateTime.of(2023, 8, 12, 10, 15), "Cannot Cure");
//
// // Creating Prescriptions with lists of medications
// ArrayList<PrescribedMedication> medsList1 = new ArrayList<>();
// medsList1.add(med1);
// medsList1.add(med2);
//
// ArrayList<PrescribedMedication> medsList2 = new ArrayList<>();
// medsList2.add(med3);
//
// Prescription prescription1 = new Prescription("D001", LocalDateTime.now(),
// medsList1);
// Prescription prescription2 = new Prescription("D002", LocalDateTime.now(),
// medsList2);
//
// // Diagnosis Example 1
// Diagnosis diagnosis1 = new Diagnosis(
// "PA-0b3d2ffd-433f-46ae-b091-1a07228d5b93", // patientID
// "D001", // diagnosisID
// "DO-8a3f2ca9-4b81-4e72-932b-730c5f537775", // doctorID
// "MR-cc335912-5c06-42c3-87b2-57234254a51b", // medicalRecordID
// LocalDateTime.of(2023, 10, 20, 14, 30), // diagnosisDate
// "Hypertension", // diagnosisDescription
// prescription1 // Prescription
// );
//
// // Diagnosis Example 2
// Diagnosis diagnosis2 = new Diagnosis(
// "PA-9d484130-7cae-4c59-9074-eadd4bab0487",
// "D002",
// "DO-8a3f2ca9-4b81-4e72-932b-730c5f537775",
// "MR002",
// LocalDateTime.of(2023, 8, 12, 10, 15),
// "Diabetes",
// prescription2
// );
//
// Diagnosis diagnosis3 = new Diagnosis(
// "PA-4f8cb69c-38c6-4462-9fdd-6680bf25eb61",
// "D002",
// "DO-8a3f2ca9-4b81-4e72-932b-730c5f537775",
// "MR002",
// LocalDateTime.of(2023, 8, 12, 10, 15),
// "Diabetes",
// prescription2
// );
//
// ArrayList<Diagnosis> diagnoses1 = new ArrayList<>();
// diagnoses1.add(diagnosis1);
// MedicalRecord record1 = new MedicalRecord(
// LocalDateTime.of(2023, 10, 1, 8, 30), // createdDate
// LocalDateTime.of(2023, 10, 1, 8, 30), // updatedDate
// RecordStatusType.ACTIVE, // recordStatus
// "PA-0b3d2ffd-433f-46ae-b091-1a07228d5b93", // patientID
// "DO-8a3f2ca9-4b81-4e72-932b-730c5f537775", // doctorID
// "A+", // bloodType
// diagnoses1 // ArrayList<Diagnosis>
// "money"
// );
//
// ArrayList<Diagnosis> diagnoses2 = new ArrayList<>();
// diagnoses2.add(diagnosis2);
// MedicalRecord record2 = new MedicalRecord(
// LocalDateTime.of(2023, 11, 1, 8, 30), // createdDate
// LocalDateTime.of(2023, 11, 3, 8, 30), // updatedDate
// RecordStatusType.ACTIVE, // recordStatus
// "PA-9d484130-7cae-4c59-9074-eadd4bab0487", // patientID
// "DO-8a3f2ca9-4b81-4e72-932b-730c5f537775", // doctorID
// "AB+", // bloodType
// diagnoses2 // ArrayList<Diagnosis>
// );
//
// ArrayList<Diagnosis> diagnoses3 = new ArrayList<>();
// diagnoses3.add(diagnosis3);
// MedicalRecord record3 = new MedicalRecord(
// LocalDateTime.of(2024, 11, 1, 8, 30), // createdDate
// LocalDateTime.of(2024, 11, 3, 8, 30), // updatedDate
// RecordStatusType.ACTIVE, // recordStatus
// "PA-9d484130-7cae-4c59-9074-eadd4bab0487", // patientID
// "DO-8a3f2ca9-4b81-4e72-932b-730c5f537775", // doctorID
// "O+", // bloodType
// diagnoses3 // ArrayList<Diagnosis>
// );
//
// // Add patients to storage
// PersonnelRepository.PATIENTS.put(patient1.getUID(), patient1);
// PersonnelRepository.PATIENTS.put(patient2.getUID(), patient2);
// PersonnelRepository.PATIENTS.put(patient3.getUID(), patient3);
// PersonnelRepository.DOCTORS.put(doctor1.getUID(), doctor1);
// PersonnelRepository.DOCTORS.put(doctor2.getUID(), doctor2);
// PersonnelRepository.DOCTORS.put(doctor3.getUID(), doctor3);
////
// PersonnelRepository.saveAllPersonnelFiles();
//// PrescribedMedicationRepository.diagnosisToMedicationsMap.put(medsList1.getFirst().getDiagnosisID(),
// medsList1);
//// PrescribedMedicationRepository.diagnosisToMedicationsMap.put(medsList2.getFirst().getDiagnosisID(),
// medsList2);
//// PrescriptionRepository.PRESCRIPTION_MAP.put(prescription1.getDiagnosisID(),prescription1);
//// PrescriptionRepository.PRESCRIPTION_MAP.put(prescription2.getDiagnosisID(),prescription2);
//// DiagnosisRepository.addDiagnosis(diagnosis1.getMedicalRecordID(),
// diagnosis1);
//// DiagnosisRepository.addDiagnosis(diagnosis2.getMedicalRecordID(),
// diagnosis2);
//// DiagnosisRepository.addDiagnosis(diagnosis3.getMedicalRecordID(),
// diagnosis3);
//// RecordsRepository.MEDICAL_RECORDS.put(record1.getRecordID(), record1);
//// RecordsRepository.MEDICAL_RECORDS.put(record2.getRecordID(), record2);
//// RecordsRepository.MEDICAL_RECORDS.put(record3.getRecordID(), record3);
////
//// PrescribedMedicationRepository.saveAlltoCSV();
//// PrescriptionRepository.saveAlltoCSV();
//// DiagnosisRepository.saveAlltoCSV();
//// RecordsRepository.saveAllRecordFiles();
//
//
//
//
//
////
//// // Create dummy medical records for each patient
//// MedicalRecord record1 = new MedicalRecord("P001", "D002"); // Doctor ID
// D002
//// MedicalRecord record2 = new MedicalRecord("P002", "D003"); // Doctor ID
// D003
//// MedicalRecord record3 = new MedicalRecord("P003", "D004"); // Doctor ID
// D004
////
//// // Add some diagnoses to each medical record
//// Diagnosis diagnosis1 = new Diagnosis("P001", "Common Cold",
// LocalDateTime.now().minusDays(5));
//// Diagnosis diagnosis2 = new Diagnosis("P002", "Seasonal Allergy",
// LocalDateTime.now().minusDays(10));
//// Diagnosis diagnosis3 = new Diagnosis("P003", "Back Pain",
// LocalDateTime.now().minusDays(3));
////
//// record1.addDiagnosis(diagnosis1);
//// record2.addDiagnosis(diagnosis2);
//// record3.addDiagnosis(diagnosis3);
////
//// // Create treatment plans for each medical record
//// TreatmentPlan treatmentPlan1 = new TreatmentPlan("Common Cold Treatment",
// LocalDateTime.now().minusDays(4));
//// TreatmentPlan treatmentPlan2 = new TreatmentPlan("Allergy Management",
// LocalDateTime.now().minusDays(9));
//// TreatmentPlan treatmentPlan3 = new TreatmentPlan("Physiotherapy Sessions",
// LocalDateTime.now().minusDays(2));
////
//// record1.setTreatmentPlan(treatmentPlan1);
//// record2.setTreatmentPlan(treatmentPlan2);
//// record3.setTreatmentPlan(treatmentPlan3);
////
//// // Create prescriptions for each medical record
//// List<PrescribedMedication> meds1 = new ArrayList<>();
//// meds1.add(new PrescribedMedication("Med1", "Paracetamol", "500mg"));
////
//// List<PrescribedMedication> meds2 = new ArrayList<>();
//// meds2.add(new PrescribedMedication("Med2", "Cetirizine", "10mg"));
////
//// List<PrescribedMedication> meds3 = new ArrayList<>();
//// meds3.add(new PrescribedMedication("Med3", "Ibuprofen", "200mg"));
////
//// Prescription prescription1 = new Prescription(meds1,
// LocalDateTime.now().minusDays(5));
//// Prescription prescription2 = new Prescription(meds2,
// LocalDateTime.now().minusDays(10));
//// Prescription prescription3 = new Prescription(meds3,
// LocalDateTime.now().minusDays(3));
////
//// record1.setPrescription(prescription1);
//// record2.setPrescription(prescription2);
//// record3.setPrescription(prescription3);
////
//// // Store medical records
//// medicalRecords.put(record1.getPatientID(), record1);
//// medicalRecords.put(record2.getPatientID(), record2);
//// medicalRecords.put(record3.getPatientID(), record3);
////
//// // Display the created dummy medical records
//// for (String patientID : medicalRecords.keySet()) {
//// MedicalRecord record = medicalRecords.get(patientID);
//// System.out.println("\n+------------------------------------------------------------+");
//// System.out.printf("| Medical Record for Patient ID: %-28s |\n", patientID);
//// System.out.println("+------------------------------------------------------------+");
//// System.out.printf("| Doctor ID : %-35s |\n", record.getDoctorID());
//// System.out.printf("| Blood Type : %-35s |\n",
// patients.get(patientID).getBloodType());
//// System.out.println("+------------------------------------------------------------+");
////
//// System.out.println("| Diagnoses:");
//// for (Diagnosis diagnosis : record.getDiagnosisList()) {
//// System.out.println("+------------------------------------------------------------+");
//// System.out.printf("| %-20s: %-35s |\n", "Description",
// diagnosis.getDiagnosisDescription());
//// System.out.printf("| %-20s: %-35s |\n", "Date",
// diagnosis.getDiagnosisDate());
//// System.out.println("+------------------------------------------------------------+");
//// }
////
//// System.out.println("| Treatment Plan:");
//// TreatmentPlans treatmentPlan = record.getTreatmentPlan();
//// System.out.println("+------------------------------------------------------------+");
//// System.out.printf("| %-20s: %-35s |\n", "Description",
// treatmentPlan.getTreatmentDescription());
//// System.out.printf("| %-20s: %-35s |\n", "Date",
// treatmentPlan.getTreatmentDate());
//// System.out.println("+------------------------------------------------------------+");
////
//// System.out.println("| Prescription:");
//// Prescription prescription = record.getPrescription();
//// System.out.println("+------------------------------------------------------------+");
//// System.out.printf("| %-20s: %-35s |\n", "Date",
// prescription.getPrescriptionDate());
//// for (PrescribedMedication med : prescription.getMedications()) {
//// System.out.printf("| %-20s: %-35s |\n", "Medication",
// med.getMedicationName());
//// System.out.printf("| %-20s: %-35s |\n", "Dosage", med.getDosage());
//// System.out.println("+------------------------------------------------------------+");
//// }
//// }
//// }
//
// }
// }
