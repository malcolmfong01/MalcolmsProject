/**
 * Centralized user interface for the Hospital Management System (HMS) application.
 * Provides options for users to log in, register an account, or exit the application.
 * Handles loading of all necessary repositories for data access.
 */
package boundary;

import repository.*;
import utility.Validator;
import repository.UserRepository;

/**
 * The Main User Interface Menu
 */
public class MainBoundary extends Boundary {

    /**
     * Constructs the HMSAppMainPage
     */
	public MainBoundary() {

	}

    /**
     * Prints the main menu options for the HMS application.
     */
	@Override
	protected void printChoice() {
		printHeader("Welcome to SCSJ Group 3's HMS App ");
		System.out.println("| What would you like to do? :      |");
		System.out.println("| 1. Login                          |");
		System.out.println("| 2. Register an account            |");
		System.out.println("| 3. Close App                      |");
		System.out.println("=====================================");
		System.out.print("Enter your choice: ");
	}

    /**
     * Starts the HMS application interface, allowing the user to select options
     * for logging in, registering, or closing the application.
     * Also initializes and loads all required repositories.
     */
	@Override
	public void start() {
		loadHMSRepository();

		while (true) {
			printChoice();

			int role = Validator.readInt("");

			switch (role) {
				case 1 -> {
					LoginBoundary loginBoundary = new LoginBoundary();
					loginBoundary.start();
				}
				case 2 -> {
					RegisterBoundary registerBoundary = new RegisterBoundary();
					registerBoundary.start();
				}
				case 3 -> {
					System.out.println("Exiting the Hospital Management System... Hope to see you again!");
					System.exit(0);
				}
				default -> System.out.println("Invalid choice! Please select a valid option.");
			}
		}
	}

	/**
	 * Method to
	 * @param title print title of the app
	 */
	private static void printHeader(String title) {
		System.out.println("=".repeat(37));
		System.out.printf("  %s%n", title);
		System.out.println("=".repeat(37));
	}

    /**
     * Loads all repositories necessary for the HMS application in a specified order.
     * Ensures that data is loaded correctly before use in the application.
     */
	public static void loadHMSRepository() {
		// MUST BE LOADED IN THIS SEQUENCE, RECORDS REPOSITORY LOADED LAST!
		Repository.loadRepository(new UserRepository());
		Repository.loadRepository(new PrescribedMedicationRepository());
		Repository.loadRepository(new TreatmentRepository());
		Repository.loadRepository(new PrescriptionRepository());
		Repository.loadRepository(new DiagnosisRepository());
		Repository.loadRepository(new AppointmentOutcomeRecordRepository());
		Repository.loadRepository(new RecordsRepository());
		Repository.loadRepository(new MedicineRepository());
	}

}