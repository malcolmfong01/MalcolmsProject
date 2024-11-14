//package view;
//
//import java.time.DayOfWeek;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.time.temporal.TemporalAdjusters;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Scanner;
//import java.util.UUID;
//
//import HMSApp.HMSMain;
//import controller.*;
//import enums.AppointmentStatus;
//import enums.PrescriptionStatus;
//import model.*;
//import repository.AppointmentOutcomeRecordRepository;
//import repository.DiagnosisRepository;
//import repository.PersonnelRepository;
//import repository.PrescribedMedicationRepository;
//import repository.PrescriptionRepository;
//import repository.RecordFileType;
//import repository.RecordsRepository;
//import repository.TreatmentPlansRepository;
//
//public class DoctorUI extends MainUI {
//	private Doctor doctor;
//
//	public DoctorUI(Doctor doctor) {
//		this.doctor = doctor;
//
//	}
//
//	@Override
//	protected void printChoice() {
//		System.out.printf("Welcome! Dr. --- %s ---\n", doctor.getFullName());
//		printBreadCrumbs("HMS App UI > Doctor Dashboard");
//		System.out.println("Doctor Menu:");
//		System.out.println("1. View Patient Medical Records");  //done
//		System.out.println("2. Update Patient Medical Records");  //done
//		System.out.println("3. View Personal Schedule");
//		System.out.println("4. Set Availability for Appointments");
//		System.out.println("5. Accept or Decline Appointment Requests");
//		System.out.println("6. View Upcoming Appointments");
//		System.out.println("7. Record Appointment Outcome");
//		System.out.println("8. Logout");
//	}
//
//	public void start() {
//		showDoctorDashboard();
//	}
//
//	public void showDoctorDashboard() {
//		Scanner sc = new Scanner(System.in);
//		int choice = 0;
//		do {
//			printChoice();
//			choice = sc.nextInt();
//			switch (choice) {
//			case 1:
//				// Code for viewing patient medical records
//				viewPatientMedicalRecord(doctor.getUID());
//				break;
//			case 2:
//				// Code for updating patient medical records
//				selectAndUpdateMedicalRecord();
//				break;
//			case 3:
//				// Code for viewing personal schedule
//				viewPersonalSchedule();
//				break;
//			case 4:
//				// Code for setting availability for appointments
//				availabilityForAppointments();
//				break;
//			case 5:
//				// Code for accepting or declining appointment requests
//				acceptOrDeclineAppointmentRequests();
//				break;
//			case 6:
//				viewUpcomingAppointments();
//				// Code for viewing upcoming appointments
//				break;
//			case 7:
//				// Code for recording appointment outcome
//				recordAppointmentOutcome();
//				break;
//			case 8:
//				System.out.println("Logging out...");
//				HMSMain.main(null);
//				break;
//			default:
//				System.out.println("Invalid choice!");
//			}
//
//		} while (choice != 8);
//
//		sc.close(); // Close the Scanner
//	}
//
//	//1. viewPatientMedicalRecordUI
//	public void viewPatientMedicalRecord(String doctorId) {
//		System.out.println("\n--- All Patients' Medical Records for Doctor ID: " + doctorId + " ---");
//
//		boolean recordsFound = false;
//
//		for (MedicalRecord record : RecordsRepository.MEDICAL_RECORDS_RECORDID.values()) {
//			if (record.getDoctorID().equals(doctorId)) {
//				recordsFound = true;
//
//				Patient patient = PatientController.getPatientById(record.getPatientID());
//				if (patient != null) {
//					System.out.println("\nPatient Information:");
//					System.out.println("UID: " + patient.getUID());
//					System.out.println("Full Name: " + patient.getFullName());
//					System.out.println("Email: " + patient.getEmail());
//					System.out.println("Phone No: " + patient.getPhoneNo());
//					System.out.println("Date of Birth: " + patient.getDoB());
//					System.out.println("Gender: " + patient.getGender());
//					System.out.println("---------------------------------------");
//
//					System.out.println("Medical Record:");
//					System.out.println("Blood Type: " + record.getBloodType());
//					System.out.println("---------------------------------------");
//
//					ArrayList<Diagnosis> diagnoses = DiagnosisRepository.getDiagnosesByPatientID(patient.getUID());
//					if (!diagnoses.isEmpty()) {
//						System.out.println("\n--- Diagnosis Records ---");
//						for (Diagnosis diagnosis : diagnoses) {
//							if (diagnosis.getDoctorID().equals(doctorId)) {
//								System.out.println("Diagnosis Date: " + diagnosis.getDiagnosisDate());
//								System.out.println("Diagnosis Description: " + diagnosis.getDiagnosisDescription());
//
//								String diagnosisID = diagnosis.getDiagnosisID();
//								TreatmentPlans patientTreatmentPlan = TreatmentPlansRepository.getTreatmentPlansByDiagnosisID(diagnosisID);
//
//								if (patientTreatmentPlan != null) {
//									System.out.println("------------------------------------------------");
//									//System.out.println("Diagnosis ID: " + patientTreatmentPlan.getDiagnosisID());
//									System.out.println("Treatment Date: " + patientTreatmentPlan.getTreatmentDate());
//									System.out.println("Treatment Description: " + patientTreatmentPlan.getTreatmentDescription());
//									System.out.println("------------------------------------------------");
//								} else {
//									System.out.println("No treatment plan found for Diagnosis ID: " + diagnosisID);
//								}
//
//								ArrayList<PrescribedMedication> prescribedMedications = PrescribedMedicationRepository.diagnosisToMedicationsMap.getOrDefault(diagnosisID, new ArrayList<>());
//								if (prescribedMedications.isEmpty()) {
//									System.out.println("No prescribed medications found for Diagnosis ID: " + diagnosisID);
//								} else {
//									System.out.println("--- Prescribed Medications ---");
//									for (PrescribedMedication medication : prescribedMedications) {
//										System.out.println("Quantity: " + medication.getMedicineQuantity());
//										System.out.println("Period (Days): " + medication.getPeriodDays());
//										System.out.println("Dosage: " + medication.getDosage());
//										System.out.println("Status: " + medication.getPrescriptionStatus());
//										System.out.println("---------------------------------------");
//									}
//								}
//							}
//						}
//					} else {
//						System.out.println("No diagnosis records found for Patient ID: " + patient.getUID());
//					}
//				} else {
//					System.out.println("Unknown patient with ID: " + record.getPatientID());
//				}
//			}
//		}
//
//		if (!recordsFound) {
//			System.out.println("No medical records found for Doctor ID: " + doctorId);
//		}
//
//		System.out.println("---------------------------------------");
//	}
//
//
//	//2. selectAndUpdateMedicalRecordUI;
//	public void selectAndUpdateMedicalRecord() {
//		Scanner sc = new Scanner(System.in);
//
//		System.out.println("Enter Patient ID to select medical record:");
//		String patientId = sc.nextLine();
//
//		// Retrieve the patient's medical record by the doctor and patient ID
//		String medicalRecordID = retrieveMedicalRecordID(doctor.getUID(), patientId);
//		MedicalRecord medicalRecord = RecordsRepository.MEDICAL_RECORDS_RECORDID.get(medicalRecordID);
//
//		if (medicalRecord == null) {
//			System.out.println("No medical record found for the specified patient.");
//			return;
//		}
//
//		// Add a new diagnosis
//		System.out.println("Adding New Diagnosis");
//		System.out.println("Enter Diagnosis Description:");
//		String diagnosisDescription = sc.nextLine();
//		Diagnosis newDiagnosis = updateDiagnosis(patientId,
//				diagnosisDescription,
//				doctor.getUID(),
//				medicalRecordID,
//				null,
//				null,
//				medicalRecord);
//		System.out.println("New diagnosis added successfully with autogenerated ID: " + newDiagnosis.getDiagnosisID());
//
//		// Prompt to add treatment plan and prescription to the new diagnosis
//		System.out.println("\nChoose an option to add for the new diagnosis:");
//		System.out.println("1. Add Treatment Plan");
//		System.out.println("2. Add Prescription");
//		System.out.println("3. Add Both Treatment Plan and Prescription");
//
//		int updateChoice = sc.nextInt();
//		sc.nextLine(); // Consume newline left-over
//
//		String treatmentDescription = null;
//		boolean addMore;
//		TreatmentPlans newTreatmentPlan;
//		String medicineID;
//		Medicine medicine;
//		ArrayList<PrescribedMedication> newPrescribedMedicationsList;
//		switch (updateChoice) {
//			case 1:
//				// Add Treatment Plan only
//				System.out.println("Enter Treatment Description:");
//				treatmentDescription = sc.nextLine();
//				newTreatmentPlan = addTreatmentPlans(newDiagnosis, treatmentDescription);
//				System.out.println("Treatment plan added successfully for the new diagnosis with ID: " + newTreatmentPlan.getDiagnosisID());
//				break;
//
//			case 2:
//				// Add Prescription only
//				addMore = true;
//				newPrescribedMedicationsList = new ArrayList<>();
//				while (addMore) {
//					System.out.println("\n--- Add Prescribed Medication ---");
//					System.out.println("Enter Medication Name:");
//					String medicationName = sc.nextLine();
//					medicine = MedicineController.getMedicineByName(medicationName);
//
//					if (medicine == null) {
//						System.out.println("\n--- Invalid Prescribed Medication ---");
//						continue;
//					}
//
//					System.out.println("Enter Quantity:");
//					int quantity = sc.nextInt();
//					System.out.println("Enter Period (Days):");
//					int periodDays = sc.nextInt();
//					sc.nextLine(); // Consume newline left-over
//					System.out.println("Enter Dosage:");
//					String dosage = sc.nextLine();
//
//					PrescribedMedication newPrescribedMedication = new PrescribedMedication(newDiagnosis.getDiagnosisID(), medicine.getMedicineID(), quantity, periodDays, PrescriptionStatus.PENDING, dosage);
//					newPrescribedMedicationsList.add(newPrescribedMedication);
//
//					// Map diagnosis to medications
//					PrescribedMedicationRepository.diagnosisToMedicationsMap.put(newDiagnosis.getDiagnosisID(), newPrescribedMedicationsList);
//
//					Prescription newPrescription = new Prescription(newDiagnosis.getDiagnosisID(), LocalDateTime.now(), newPrescribedMedicationsList);
//					PrescriptionRepository.PRESCRIPTION_MAP.put(newDiagnosis.getDiagnosisID(), newPrescription);
//
//					System.out.println("Medication prescribed successfully for Diagnosis ID: " + newDiagnosis.getDiagnosisID());
//
//					System.out.print("Would you like to add another prescribed medication? (yes/no): ");
//					String response = sc.nextLine().trim().toLowerCase();
//					addMore = response.equals("yes");
//				}
//				System.out.println("Finished adding prescribed medications for Diagnosis ID: " + newDiagnosis.getDiagnosisID());
//				break;
//
//			case 3:
//				// Add both Treatment Plan and Prescription
//				System.out.println("Enter Treatment Description:");
//				treatmentDescription = sc.nextLine();
//				newTreatmentPlan = addTreatmentPlans(newDiagnosis, treatmentDescription);
//				System.out.println("Treatment plan added successfully with ID: " + newTreatmentPlan.getDiagnosisID());
//
//				// Initialize list for prescribed medications
//				newPrescribedMedicationsList = new ArrayList<>();
//				addMore = true;
//
//				// Loop to add multiple prescribed medications
//				while (addMore) {
//					System.out.println("\n--- Add Prescribed Medication ---");
//					System.out.print("Enter Medication Name: ");
//					String medicationName = sc.nextLine();
//					medicine = MedicineController.getMedicineByName(medicationName);
//
//					if (medicine == null) {
//						System.out.println("\n--- Invalid Prescribed Medication ---");
//						continue;
//					}
//
//					System.out.print("Enter Quantity: ");
//					int quantity = sc.nextInt();
//					System.out.print("Enter Period (Days): ");
//					int periodDays = sc.nextInt();
//					sc.nextLine(); // Consume newline left-over
//					System.out.print("Enter Dosage: ");
//					String dosage = sc.nextLine();
//
//					PrescribedMedication newPrescribedMedication = new PrescribedMedication(newDiagnosis.getDiagnosisID(), medicine.getMedicineID(), quantity, periodDays, PrescriptionStatus.PENDING, dosage);
//					newPrescribedMedicationsList.add(newPrescribedMedication);
//
//					// Map diagnosis to medications
//					PrescribedMedicationRepository.diagnosisToMedicationsMap.put(newDiagnosis.getDiagnosisID(), newPrescribedMedicationsList);
//
//					Prescription newPrescription = new Prescription(newDiagnosis.getDiagnosisID(), LocalDateTime.now(), newPrescribedMedicationsList);
//					PrescriptionRepository.PRESCRIPTION_MAP.put(newDiagnosis.getDiagnosisID(), newPrescription);
//
//					System.out.println("Medication prescribed successfully for Diagnosis ID: " + newDiagnosis.getDiagnosisID());
//
//					System.out.print("Would you like to add another prescribed medication? (yes/no): ");
//					String response = sc.nextLine().trim().toLowerCase();
//					addMore = response.equals("yes");
//				}
//				System.out.println("Finished adding both treatment plan and prescribed medications for Diagnosis ID: " + newDiagnosis.getDiagnosisID());
//				break;
//
//			default:
//				System.out.println("Invalid choice.");
//				break;
//		}
//	}
//
//
//	//3. viewPersonalSchedule
//	public void viewPersonalSchedule() {
//		System.out.println("\n--- Appointments for Dr. " + doctor.getFullName() + " (ID: " + doctor.getUID() + ") ---");
//
//		boolean found = false;
//		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//
//		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS_RECORDID.values()) {
//			if (doctor.getUID().equals(appointment.getDoctorID())) {
//				found = true;
//				System.out.println("Appointment Record:");
//				System.out.println("  - Appointment ID: " + appointment.getRecordID());
//				System.out.println("  - Date & Time: " + appointment.getAppointmentTime().format(dateTimeFormatter));
//				System.out.println("  - Location: " + appointment.getLocation());
//				System.out.println("  - Status: " + appointment.getAppointmentStatus());
//				System.out.println(
//						"  - Patient ID: " + (appointment.getPatientID() != null ? appointment.getPatientID() : "N/A"));
//				System.out.println("---------------------------------------");
//			}
//		}
//
//		if (!found) {
//			System.out.println("No appointments found for this doctor.");
//		}
//		System.out.println("---------------------------------------");
//	}
//	//4. availabilityForAppointments
//	public void availabilityForAppointments() {
//		Scanner scanner = new Scanner(System.in);
//		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//		AppointmentStatus status = AppointmentStatus.AVAILABLE;
//
//		List<AppointmentRecord> availableAppointments = new ArrayList<>();
//
//		System.out.println("Select your available days (type 'done' when finished):");
//
//		while (true) {
//			System.out.print("Enter a day (e.g., Monday) or type 'done' to finish: ");
//			String day = scanner.nextLine().trim().toLowerCase();
//			if (day.equals("done"))
//				break;
//
//			try {
//				System.out.print("Enter the date for " + day + " (format: yyyy-MM-dd): ");
//				String dateInput = scanner.nextLine();
//				LocalDate date = LocalDate.parse(dateInput);
//
//				System.out.print("Enter your preferred time slot for " + day + " (format: HH-HH, e.g., 10-16): ");
//				String timeRange = scanner.nextLine();
//				String[] times = timeRange.split("-");
//				int startHour = Integer.parseInt(times[0]);
//				int endHour = Integer.parseInt(times[1]);
//
//				for (int hour = startHour; hour < endHour; hour++) {
//					LocalDateTime appointmentTime = LocalDateTime.of(date, LocalTime.of(hour, 0));
//
//					AppointmentRecord appointment = new AppointmentRecord(
//							RecordsController.generateRecordID(RecordFileType.APPOINTMENT_RECORDS),
//							LocalDateTime.now(),
//							LocalDateTime.now(),
//							RecordStatusType.ACTIVE,
//							null,
//							null,
//							this.doctor.getUID(),
//							appointmentTime,
//							"level 2 - heart clinic",
//							status,
//							null
//					);
//
//					availableAppointments.add(appointment);
//					RecordsRepository.APPOINTMENT_RECORDS_RECORDID.put(appointment.getRecordID(), appointment);
//
//					System.out.println("Created and saved appointment for " + day + " at "
//							+ appointmentTime.format(dateTimeFormatter));
//				}
//			} catch (Exception e) {
//				System.out.println("Invalid input. Please ensure the date and time format is correct.");
//			}
//		}
//
//		System.out.println("\n--- Appointment Availability Summary ---");
//		for (AppointmentRecord appointment : availableAppointments) {
//			System.out.println("Day: " + appointment.getAppointmentTime().getDayOfWeek() + ", Time: "
//					+ appointment.getAppointmentTime().format(dateTimeFormatter) + ", Location: "
//					+ appointment.getLocation() + ", Record ID: " + appointment.getRecordID() + ", Doctor ID: "
//					+ appointment.getDoctorID());
//		}
//		System.out.println("---------------------------------------");
//
//		RecordsRepository.saveAllRecordFiles();
//	}
//	//5.acceptOrDeclineAppointmentRequests
//	public void acceptOrDeclineAppointmentRequests() {
//		System.out.println(
//				"\n--- Request Appointments for: " + doctor.getFullName() + " (UID: " + doctor.getUID() + ") ---");
//		Scanner sc = new Scanner(System.in);
//
//		boolean found = false;
//
//		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS_RECORDID.values()) {
//			if (doctor.getUID().equals(appointment.getDoctorID())
//					&& appointment.getAppointmentStatus().equals(AppointmentStatus.PENDING)) {
//				found = true;
//				System.out.println("Day: " + appointment.getAppointmentTime().getDayOfWeek() + ", Time: "
//						+ appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
//						+ ", Location: " + appointment.getLocation() + ", Patient ID: " + appointment.getPatientID()
//						+ ", Patient Name: " + PatientController.getPatientNameById(appointment.getPatientID()));
//
//				System.out.println("Do you want to accept or decline this appointment? (Type 'accept' or 'decline'): ");
//				String choice = sc.nextLine().trim().toLowerCase();
//
//				if ("accept".equals(choice)) {
//					appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
//					System.out.println(
//							"Appointment with Patient ID: " + appointment.getPatientID() + " has been confirmed.");
//				} else if ("decline".equals(choice)) {
//					appointment.setAppointmentStatus(AppointmentStatus.CANCELED);
//					System.out.println("Appointment with Patient ID: " + appointment.getPatientID()
//							+ " has been declined and is waiting for patient to acknowledge.");
//				} else {
//					System.out.println("Invalid choice. Please enter 'accept' or 'decline'.");
//				}
//			}
//		}
//
//		if (!found) {
//			System.out.println("No pending appointments found for this doctor.");
//		}
//
//		System.out.println("---------------------------------------");
//
//		RecordsRepository.saveAllRecordFiles();
//
//	}
//	//6. viewUpcomingAppointments
//	public void viewUpcomingAppointments() {
//		System.out.println(
//				"\n--- Upcoming Appointments for: " + doctor.getFullName() + " (UID: " + doctor.getUID() + ") ---");
//
//		boolean found = false;
//
//		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS_RECORDID.values()) {
//			if (doctor.getUID().equals(appointment.getDoctorID())
//					&& appointment.getAppointmentStatus().equals(AppointmentStatus.CONFIRMED)) {
//				found = true;
//				System.out.println("Day: " + appointment.getAppointmentTime().getDayOfWeek() + ", Time: "
//						+ appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
//						+ ", Location: " + appointment.getLocation() + ", Patient ID: " + appointment.getPatientID()
//						+ "\n" + PatientController.getPatientInfoById(appointment.getPatientID())
//
//				);
//			}
//		}
//
//		if (!found) {
//			System.out.println("No upcoming appointments found for this doctor.");
//		}
//
//		System.out.println("---------------------------------------");
//	}
//
//
//
//	public Diagnosis updateDiagnosis( String patientId,
//									  String diagnosisDescription,
//									  String doctorId,
//									  String medicalRecordID,
//									  TreatmentPlans treatmentPlans,
//									  Prescription prescription,
//									  MedicalRecord medicalRecord){
//		String newDiagnosisID = AppointmentController.generateRecordID(RecordFileType.DIAGNOSIS_RECORDS); // Method to generate unique IDs
//		Diagnosis newDiagnosis = new Diagnosis(patientId,newDiagnosisID,doctorId,medicalRecordID , LocalDateTime.now(), null, diagnosisDescription, null);
//		DiagnosisRepository.addDiagnosis(newDiagnosisID, newDiagnosis); // Add to repository
//		//input into hashmap
//		medicalRecord.addDiagnosis(newDiagnosis);
//		//savecsv
//		//RecordsRepository.saveAllRecordFiles();
//		return newDiagnosis;
//
//	}
//
//	public TreatmentPlans addTreatmentPlans(Diagnosis newDiagnosis, String treatmentDescription){
//		// String treatmentDescription = scanner.nextLine();
//		TreatmentPlans newTreatmentPlan = new TreatmentPlans(newDiagnosis.getDiagnosisID(), LocalDateTime.now(), treatmentDescription);
//		newDiagnosis.setTreatmentPlans(newTreatmentPlan);
//		//TreatmentPlansRepository.saveAlltoCSV(); // Add to repository
//		//RecordsRepository.saveAllRecordFiles();
//		TreatmentPlansRepository.addTreatmentPlansRecord(newTreatmentPlan);
//		return newTreatmentPlan;
//
//	}
//
//
//
//
//	public String retrieveMedicalRecordID(String doctorID, String patientID){
//		String medicalRecordID = getMedicalRecordID(doctorID, patientID);
//		MedicalRecord medicalRecord = RecordsRepository.MEDICAL_RECORDS_RECORDID.get(medicalRecordID);
//		return medicalRecord.getRecordID();
//	}
//
//
//	public boolean setAppointmentRecordStatus(String AppointmentRecordID, String status){
//		boolean flag = false;
//		AppointmentRecord appointmentRecord = RecordsRepository.APPOINTMENT_RECORDS_RECORDID.get(AppointmentRecordID);
//		if (appointmentRecord!=null)
//		{
//			appointmentRecord.setAppointmentStatus(AppointmentStatus.toEnumAppointmentStatus(status));
//
//		}
//		return flag;
//
//	}
//	public AppointmentOutcomeRecord generateAppointmentOutcomeRecord(MedicalRecord medicalRecord,String diagnosisID , String typeOfService, String consultationNotes){
//
//		AppointmentOutcomeRecord outcomeRecord = new AppointmentOutcomeRecord(
//				medicalRecord.getPatientID(),
//				medicalRecord.getDoctorID(),
//				diagnosisID,
//				medicalRecord.getRecordID(),
//				LocalDateTime.now(),
//				PrescriptionRepository.PRESCRIPTION_MAP.get(diagnosisID),
//				typeOfService,
//				consultationNotes);
//
//		AppointmentOutcomeRecordRepository.addAppointmentOutcomeRecord(medicalRecord.getPatientID(), outcomeRecord);
//		AppointmentOutcomeRecordRepository.saveAppointmentOutcomeRecordRepository();
//		return outcomeRecord;
//
//	}
//
//
//
//
//
//
//
//	/////////////////////////////CKHAI
//
////	public void selectAndUpdateMedicalRecord() {
////		Scanner scanner = new Scanner(System.in);
////	    System.out.println("\n--- Confirmed Appointment Records ---");
////	    List<AppointmentRecord> confirmedAppointments = new ArrayList<>();
////
////	    for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS_RECORDID.values()) {
////	        if (appointment.getAppointmentStatus().equals(AppointmentStatus.CONFIRMED)) {
////	            confirmedAppointments.add(appointment);
////	            System.out.println("Appointment Outcome Record ID: " + appointment.getAppointmentOutcomeRecordID());
////	            System.out.println("Patient ID: " + appointment.getPatientID());
////	            System.out.println("Doctor ID: " + appointment.getDoctorID());
////	            System.out.println("Date & Time: " + appointment.getAppointmentTime());
////	            System.out.println("---------------------------------------");
////	        }
////	    }
////
////	    if (confirmedAppointments.isEmpty()) {
////	        System.out.println("No confirmed appointments found.");
////	        return;
////	    }
////
////	    System.out.println("Enter the Appointment ID of the record you want to update:");
////	    String selectedAppointmentID = scanner.nextLine(); // Assume this method handles user input
////
////	    AppointmentRecord selectedAppointment = null;
////	    for (AppointmentRecord appointment : confirmedAppointments) {
////	        if (appointment.getAppointmentOutcomeRecordID().equals(selectedAppointmentID)) {
////	            selectedAppointment = appointment;
////	            break;
////	        }
////	    }
////
////	    if (selectedAppointment == null) {
////	        System.out.println("Invalid Appointment ID. No matching record found.");
////	        return;
////	    }
////
////
////
////
////
////
////	}
//
//	public static String getMedicalRecordID(String doctorId, String patientId) {
//	    //  through the medical records in the repository
//	    for (MedicalRecord record : RecordsRepository.MEDICAL_RECORDS_RECORDID.values()) {
//	        // Check if the record matches the provided doctorId and patientId
//	        if (record.getDoctorID().equals(doctorId) && record.getPatientID().equals(patientId)) {
//	            return record.getRecordID(); // Return the matching record's ID
//	        }
//	    }
//	    // If no record is found, return null or an appropriate message
//	    return null; // Or throw an exception if preferred
//	}
//
//
////	public void updateMedicalRecord(String doctorId, String patientId) {
////		Scanner scanner = new Scanner(System.in);
////
////	    // Step 1: Retrieve the patient record
////	    Patient patient = PatientController.getPatientById(patientId);
////	    if (patient == null) {
////	        System.out.println("Patient not found with ID: " + patientId);
////	        return;
////	    }
////
////	    // Step 2: Check if doctor has access to update this patient's record
////	    boolean hasAccess = false;
////	    MedicalRecord recordRetrieved = null;
////	    for (MedicalRecord record : RecordsRepository.MEDICAL_RECORDS_RECORDID.values()) {
////	        if (record.getPatientID().equals(patientId) && record.getDoctorID().equals(doctorId)) {
////	            hasAccess = true;
////	            recordRetrieved = record;
////	            break;
////	        }
////	    }
////	    if (!hasAccess) {
////	        System.out.println("Doctor does not have permission to update records for Patient ID: " + patientId);
////	        return;
////	    }
////
////
////
////	    // Step 3: Add new diagnosis
////	    System.out.println("Enter new diagnosis description:");
////	    String diagnosisDescription = scanner.nextLine();
////	    String medid = getMedicalRecordID(doctor.getUID(), patient.getUID());
////	    String newDiagnosisID = AppointmentController.generateRecordID(RecordFileType.DIAGNOSIS_RECORDS); // Method to generate unique IDs
//////	    Diagnosis newDiagnosis = new Diagnosis(patientId,newDiagnosisID,doctorId,medid , LocalDateTime.now(), diagnosisDescription, null);
//////	    DiagnosisRepository.addDiagnosis(newDiagnosisID, newDiagnosis); // Add to repository
//////	    recordRetrieved.addDiagnosis(newDiagnosis);
////
////
////	    // Step 4: Add treatment plan for the diagnosis
////	    System.out.println("Enter treatment description:");
////	    String treatmentDescription = scanner.nextLine();
////	    TreatmentPlans newTreatmentPlan = new TreatmentPlans(newDiagnosisID, LocalDateTime.now(), treatmentDescription);
////	    TreatmentPlansRepository.saveAlltoCSV(); // Add to repository
////
////
////
////
////	    // Step 5: Add prescribed medications (optional)
////
////	    System.out.println("Do you want to prescribe medication? (yes/no)");
////	    String response = scanner.nextLine();
////	    if (response.equalsIgnoreCase("yes")) {
////	        while (true) {
////	            String medicineID =  AppointmentController.generateRecordID(RecordFileType.MEDICINE_RECORDS);
////	            System.out.println("Enter Quantity:");
////	            int quantity = Integer.parseInt(scanner.nextLine());
////	            System.out.println("Enter Period (Days):");
////	            int periodDays = Integer.parseInt( scanner.nextLine());
////	            System.out.println("Enter Dosage:");
////	            String dosage =  scanner.nextLine();
////
////	            PrescribedMedication newMedication = new PrescribedMedication(newDiagnosisID, medicineID, quantity, periodDays,null , dosage);
////	            PrescriptionRepository.PRESCRIPTION_MAP.get(newDiagnosisID);
////
////
////	            PrescribedMedicationRepository.saveAlltoCSV(); // Add to repository
////
////	            System.out.println("Do you want to add another medication? (yes/no)");
////	            response =scanner.nextLine();
////	            if (!response.equalsIgnoreCase("yes")) {
////	                break;
////	            }
////	        }
////	    }
////
////
////	    //if (RecordsRepository.MEDICAL_RECORDS_PATIENTID.get(selectedAppointment.getPatientID()) != null){
//////    	medicalRecordID = RecordsController.generateRecordID(RecordFileType.MEDICAL_RECORDS);
//////
//////	    MedicalRecord medicalRecord = new MedicalRecord(
//////	    								medicalRecordID,
//////	    								LocalDateTime.now(),
//////	    								LocalDateTime.now(),
//////	    								RecordStatusType.ACTIVE,
//////	    								selectedAppointment.getPatientID(),
//////	    					            doctor.getUID(),
//////	    					            "O",
//////	    					            DiagnosisRepository.getDiagnosesByPatientID(selectedAppointment.getPatientID())
//////	    								);
//////	    RecordsRepository.MEDICAL_RECORDS_RECORDID.put(recordIDRetrieved, medicalRecord);
////
////
////
////	    Diagnosis newDiagnosis = new Diagnosis(patientId,newDiagnosisID,
////				doctorId,
////				medid ,
////				LocalDateTime.now(),
////				newTreatmentPlan,diagnosisDescription, P);
////		DiagnosisRepository.addDiagnosis(newDiagnosisID, newDiagnosis); // Add to repository
////		recordRetrieved.addDiagnosis(newDiagnosis);
////	    // Step 6: Display confirmation
////	    System.out.println("Medical record for Patient ID: " + patientId + " updated successfully.");
////	}
//
//
//
//
//
//
//
//
//
//
//
////
////	public void recordAppointmentOutcome() {
////	    Scanner scanner = new Scanner(System.in);
////
////	    System.out.println(
////	            "\n--- Confirmed Appointments for: " + doctor.getFullName() + " (UID: " + doctor.getUID() + ") ---");
////
////	    ArrayList<AppointmentRecord> confirmedAppointments = new ArrayList<>();
////	    int index = 1;
////
////	    for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS_RECORDID.values()) {
////	        if (doctor.getUID().equals(appointment.getDoctorID())
////	                && appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
////	            confirmedAppointments.add(appointment);
////	            System.out.println(index + ". Day: " + appointment.getAppointmentTime().getDayOfWeek()
////	                    + ", Time: " + appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
////	                    + ", Location: " + appointment.getLocation()
////	                    + ", Patient ID: " + appointment.getPatientID()
////	                    + ", Patient Name: " + PatientController.getPatientNameById(appointment.getPatientID()));
////	            index++;
////	        }
////	    }
////
////	    if (confirmedAppointments.isEmpty()) {
////	        System.out.println("No confirmed appointments found for this doctor.");
////	        return;
////	    }
////
////	    System.out.print("\nEnter the number of the appointment you want to record the outcome for: ");
////	    int selectedIndex = scanner.nextInt();
////	    scanner.nextLine();
////
////	    if (selectedIndex < 1 || selectedIndex > confirmedAppointments.size()) {
////	        System.out.println("Invalid selection. No outcome recorded.");
////	        return;
////	    }
////
////	    AppointmentRecord selectedAppointment = confirmedAppointments.get(selectedIndex - 1);
////
////	    System.out.print("The appointment is confirmed. Do you want to record the outcome? (yes/no): ");
////	    String response = scanner.nextLine().trim().toLowerCase();
////
////	    if (!"yes".equals(response)) {
////	        System.out.println("No changes made.");
////	        return;
////	    }
////
////	    selectedAppointment.setAppointmentStatus(AppointmentStatus.COMPLETED);
////
////	    // Generate IDs
////	    String outcomeRecordID = AppointmentController.generateRecordID(RecordFileType.APPOINTMENT_OUTCOME_RECORDS);
////	    String diagnosisID = AppointmentController.generateRecordID(RecordFileType.DIAGNOSIS_RECORDS);
////
////	    System.out.print("Enter diagnosis description: ");
////	    String diagnosisDescription = scanner.nextLine();
////	    String medicalRecordID = getMedicalRecordID(doctor.getUID(), selectedAppointment.getPatientID());
////	    Diagnosis diagnosis = new Diagnosis(selectedAppointment.getPatientID(), diagnosisID, doctor.getUID(), medicalRecordID,
////	            LocalDateTime.now(),null, diagnosisDescription, null);
////	    DiagnosisRepository.addDiagnosis(diagnosisID, diagnosis);
////
////
////	    System.out.print("Enter treatment description: ");
////	    String treatmentDescription = scanner.nextLine();
////	    TreatmentPlans treatmentPlan = new TreatmentPlans(diagnosisID, LocalDateTime.now(), treatmentDescription);
////	    TreatmentPlansRepository.addTreatmentPlansRecord(treatmentPlan);
////
////	    ArrayList<PrescribedMedication> medications = new ArrayList<>();
////	    Prescription prescription = new Prescription(diagnosisID, LocalDateTime.now(),medications);
////	    PrescriptionRepository.addPrescriptionRecord(prescription);
////	    System.out.print("Do you want to prescribe medication? (yes/no): ");
////	    if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
////	        while (true) {
////	            String medicineID = AppointmentController.generateRecordID(RecordFileType.MEDICINE_RECORDS);
////	            System.out.print("Enter quantity: ");
////	            int quantity = Integer.parseInt(scanner.nextLine());
////	            System.out.print("Enter period (days): ");
////	            int periodDays = Integer.parseInt(scanner.nextLine());
////	            System.out.print("Enter dosage: ");
////	            String dosage = scanner.nextLine();
////	            PrescribedMedication medication = new PrescribedMedication(diagnosisID,quantity, periodDays, enums.PrescriptionStatus.PENDING, dosage);
////	            medications.add(medication);
////	            PrescribedMedicationRepository.addMedication(diagnosisID, medication);
////
////	            System.out.print("Add another medication? (yes/no): ");
////	            if (!scanner.nextLine().trim().equalsIgnoreCase("yes")) {
////	                break;
////	            }
////	        }
////
////	    }
////
////	    System.out.print("Enter the type of service: ");
////	    String typeOfService = scanner.nextLine();
////	    System.out.print("Enter your consultation notes: ");
////	    String consultationNotes = scanner.nextLine();
////
////	    AppointmentOutcomeRecord outcomeRecord = new AppointmentOutcomeRecord(
////	            selectedAppointment.getPatientID(),
////	            doctor.getUID(),
////	            diagnosisID,
////	            outcomeRecordID,
////	            LocalDateTime.now(),
////	            prescription,
////	            typeOfService,
////	            consultationNotes);
////
////	    AppointmentOutcomeRecordRepository.addOutcomeRecord(outcomeRecord);
////	    //RecordsRepository.MEDICAL_RECORDS.set
////
////	    selectedAppointment.setAppointmentOutcomeRecordID(outcomeRecordID);
////
////
////
////
//////
//////
//////	    }
//////
////
////
////	    RecordsRepository.saveAllRecordFiles();
////	    AppointmentOutcomeRecordRepository.saveAppointmentOutcomeRecordRepository();
////	    DiagnosisRepository.saveAlltoCSV();
////	    TreatmentPlansRepository.saveAlltoCSV();
////	    PrescribedMedicationRepository.saveAlltoCSV();
////
////	    System.out.println("Appointment outcome recorded and saved successfully.");
////	}
//
//	public void recordAppointmentOutcome() {
//		Scanner scanner = new Scanner(System.in);
//
//		System.out.println(
//				"\n--- Confirmed Appointments for: " + doctor.getFullName() + " (UID: " + doctor.getUID() + ") ---");
//
//		ArrayList<AppointmentRecord> confirmedAppointments = new ArrayList<>();
//		int index = 1;
//
//		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS_RECORDID.values()) {
//			if (doctor.getUID().equals(appointment.getDoctorID())
//					&& appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
//				confirmedAppointments.add(appointment);
//				System.out.println(index + ". Day: " + appointment.getAppointmentTime().getDayOfWeek()
//						+ ", Time: " + appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
//						+ ", Location: " + appointment.getLocation()
//						+ ", Patient ID: " + appointment.getPatientID()
//						+ ", Patient Name: " + PatientController.getPatientNameById(appointment.getPatientID()));
//				index++;
//			}
//		}
//
//		if (confirmedAppointments.isEmpty()) {
//			System.out.println("No confirmed appointments found for this doctor.");
//			return;
//		}
//
//		System.out.print("\nEnter the number of the appointment you want to record the outcome for: ");
//		int selectedIndex = scanner.nextInt();
//		scanner.nextLine();
//
//		if (selectedIndex < 1 || selectedIndex > confirmedAppointments.size()) {
//			System.out.println("Invalid selection. No outcome recorded.");
//			return;
//		}
//
//		AppointmentRecord selectedAppointment = confirmedAppointments.get(selectedIndex - 1);
//		System.out.println("\n--- Select Diagnosis for Patient (ID: " + selectedAppointment.getPatientID() + ") ---");
//
//		// Fetch diagnoses for the selected patient
//		ArrayList<Diagnosis> diagnoses = DiagnosisRepository.getDiagnosesByPatientID(selectedAppointment.getPatientID());
//		if (diagnoses.isEmpty()) {
//			System.out.println("No diagnoses found for this patient.");
//			return;
//		}
//
//		for (int i = 0; i < diagnoses.size(); i++) {
//			Diagnosis diagnosis = diagnoses.get(i);
//			System.out.println((i + 1) + ". Diagnosis ID: " + diagnosis.getDiagnosisID()
//					+ ", Condition: " + diagnosis.getDiagnosisDescription());
//		}
//
//		System.out.print("\nEnter the number of the diagnosis to use: ");
//		int diagnosisIndex = scanner.nextInt();
//		scanner.nextLine();
//
//		if (diagnosisIndex < 1 || diagnosisIndex > diagnoses.size()) {
//			System.out.println("Invalid selection. No outcome recorded.");
//			return;
//		}
//
//		Diagnosis selectedDiagnosis = diagnoses.get(diagnosisIndex - 1);
//		String diagnosisID = selectedDiagnosis.getDiagnosisID();
//
//		System.out.print("The appointment is confirmed. Do you want to record the outcome? (yes/no): ");
//		String response = scanner.nextLine().trim().toLowerCase();
//
//		if (!"yes".equals(response)) {
//			System.out.println("No changes made.");
//			return;
//		}
//
//		selectedAppointment.setAppointmentStatus(AppointmentStatus.COMPLETED);
//
//		// appointment outcome record has the same ID as the appointmentRecord
//		String outcomeRecordID = selectedAppointment.getRecordID();
//
//		// update prescribedMedication to medical record and appointment outcome record
//		ArrayList<PrescribedMedication> medications = new ArrayList<>();
//	    Prescription prescription = new Prescription(diagnosisID, LocalDateTime.now(),medications);
//	    PrescriptionRepository.addPrescriptionRecord(prescription);
//	    System.out.print("Do you want to prescribe medication? (yes/no): ");
//	    if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
//	        while (true) {
//	            System.out.print("Enter quantity: ");
//	            int quantity = Integer.parseInt(scanner.nextLine());
//	            System.out.print("Enter period (days): ");
//	            int periodDays = Integer.parseInt(scanner.nextLine());
//	            System.out.print("Enter dosage: ");
//	            String dosage = scanner.nextLine();
//	            PrescribedMedication medication = new PrescribedMedication(diagnosisID,null, quantity, periodDays, enums.PrescriptionStatus.PENDING, dosage);
//	            medications.add(medication);
//	            PrescribedMedicationRepository.addMedication(diagnosisID, medication);
//
//	            System.out.print("Add another medication? (yes/no): ");
//	            if (!scanner.nextLine().trim().equalsIgnoreCase("yes")) {
//	                break;
//	            }
//	        }
//
//	    }
//
//
//		System.out.print("Enter the type of service: ");
//		String typeOfService = scanner.nextLine();
//		System.out.print("Enter your consultation notes: ");
//		String consultationNotes = scanner.nextLine();
//
//		AppointmentOutcomeRecord outcomeRecord = new AppointmentOutcomeRecord(
//				selectedAppointment.getPatientID(),
//				doctor.getUID(),
//				diagnosisID,
//				outcomeRecordID,
//				LocalDateTime.now(),
//				prescription,
//				typeOfService,
//				consultationNotes);
//
//		AppointmentOutcomeRecordRepository.addAppointmentOutcomeRecord(selectedAppointment.getPatientID(),outcomeRecord);
//		selectedAppointment.setAppointmentOutcomeRecordID(outcomeRecordID);
//
////		RecordsRepository.saveAllRecordFiles();
////		AppointmentOutcomeRecordRepository.saveAppointmentOutcomeRecordRepository();
////		DiagnosisRepository.saveAlltoCSV();
////		TreatmentPlansRepository.saveAlltoCSV();
////		PrescribedMedicationRepository.saveAlltoCSV();
//
//		System.out.println("Appointment outcome recorded and saved successfully.");
//	}
//
//}
////KC CODE
////	private void viewPatientRecords() {
////        printBreadCrumbs("HMS App UI > Doctor Dashboard > View Patient Records");
////        RecordsController rc = new RecordsController();
////        ArrayList<MedicalRecord> records = rc.getMedicalRecordsByDoctorID(AuthenticationController.cookie.getUid());
////
////        if (records.isEmpty()) {
////            System.out.println("No medical records found for this doctor.");
////            return;
////        }
////        for (MedicalRecord record : records) {
////            MedicalRecordUI recordUI = new MedicalRecordUI(record); // Instantiate MedicalRecordUI
////            recordUI.displayMedicalRecordInBox(); // Display the medical record
////        }
////    }