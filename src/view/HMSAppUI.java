/**
 * Centralized user interface for the Hospital Management System (HMS) application.
 * Provides options for users to log in, register an account, or exit the application.
 * Handles loading of all necessary repositories for data access.
 */
package view;

import java.util.Scanner;

import repository.AppointmentOutcomeRecordRepository;
import repository.DiagnosisRepository;
import repository.MedicineRepository;
import repository.PersonnelRepository;
import repository.PrescribedMedicationRepository;
import repository.PrescriptionRepository;
import repository.RecordsRepository;
import repository.Repository;
import repository.TreatmentPlansRepository;

public class HMSAppUI extends MainUI {

    /**
     * Constructs the HMSAppUI.
     */
	public HMSAppUI() {

	}

    /**
     * Prints the main menu options for the HMS application.
     */
	@Override
	protected void printChoice() {
		printBreadCrumbs("HMS App UI");
		System.out.println("Would you like to? :");
		System.out.println("1. Login");
		System.out.println("2. Register an account");
		System.out.println("3. Close App");
	}

    /**
     * Starts the HMS application interface, allowing the user to select options
     * for logging in, registering, or closing the application.
     * Also initializes and loads all required repositories.
     */
	@Override
	public void start() {
		loadHMSRepository();

		Scanner sc = new Scanner(System.in);

		while (true) {
			printChoice();

			int role = sc.nextInt();
			sc.nextLine(); // Clear the newline character

			switch (role) {
				case 1:
					LoginUI loginUI = new LoginUI();
					loginUI.start();
					break;
				case 2:
					RegisterUI registerUI = new RegisterUI();
					registerUI.start();
					break;
				case 3:
					System.exit(0);
					break;
				default:
					System.out.println("Invalid choice! Please select a valid option.");
					break;
			}
		}
	}

    /**
     * Starts the testing environment by loading all HMS repositories.
     */
	public void startTestingEnv() {
		loadHMSRepository();

	}

    /**
     * Loads all repositories necessary for the HMS application in a specified order.
     * Ensures that data is loaded correctly before use in the application.
     */
	public void loadHMSRepository() {
		// MUST BE LOADED IN THIS SEQUENCES RECORDS REPOSITORY LOADED LAST!
		Repository.loadRepository(new PersonnelRepository());
		Repository.loadRepository(new PrescribedMedicationRepository());
		Repository.loadRepository(new TreatmentPlansRepository());
		Repository.loadRepository(new PrescriptionRepository());
		Repository.loadRepository(new DiagnosisRepository());
		Repository.loadRepository(new AppointmentOutcomeRecordRepository());
		Repository.loadRepository(new RecordsRepository());
		Repository.loadRepository(new MedicineRepository());
	}

    /**
     * Checks if all repositories have been successfully loaded and displays the status.
     * Each repository's load status is printed along with a checkmark or X symbol.
     *
     * @return true if all repositories are loaded; false otherwise
     */
	public Boolean isAllRepoLoaded() {
		System.out.println("Repository Load Status:");

		// Define symbols for loaded and not loaded
		String loadedSymbol = "✓";
		String notLoadedSymbol = "✗";

		// Check and print each repository's load status
		System.out.println("Personnel Repository: " +
				(PersonnelRepository.isRepoLoaded() ? loadedSymbol : notLoadedSymbol));
		System.out.println("Records Repository: " +
				(RecordsRepository.isRepoLoaded() ? loadedSymbol : notLoadedSymbol));
		System.out.println("Prescribed Medication Repository: " +
				(PrescribedMedicationRepository.isRepoLoaded() ? loadedSymbol : notLoadedSymbol));
		System.out.println("Treatment Plans Repository: " +
				(TreatmentPlansRepository.isRepoLoaded() ? loadedSymbol : notLoadedSymbol));
		System.out.println("Prescription Repository: " +
				(PrescriptionRepository.isRepoLoaded() ? loadedSymbol : notLoadedSymbol));
		System.out.println("Diagnosis Repository: " +
				(DiagnosisRepository.isRepoLoaded() ? loadedSymbol : notLoadedSymbol));
		System.out.println("Appointment Outcome Record Repository: " +
				(AppointmentOutcomeRecordRepository.isRepoLoaded() ? loadedSymbol : notLoadedSymbol));
		System.out.println("Medicine Repository: " +
				(MedicineRepository.isRepoLoaded() ? loadedSymbol : notLoadedSymbol));

		// Return true if all repositories are loaded
		return PersonnelRepository.isRepoLoaded() &&
				RecordsRepository.isRepoLoaded() &&
				PrescribedMedicationRepository.isRepoLoaded() &&
				TreatmentPlansRepository.isRepoLoaded() &&
				PrescriptionRepository.isRepoLoaded() &&
				DiagnosisRepository.isRepoLoaded() &&
				AppointmentOutcomeRecordRepository.isRepoLoaded() &&
				MedicineRepository.isRepoLoaded();
	}

}