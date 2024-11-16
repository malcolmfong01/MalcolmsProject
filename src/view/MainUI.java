package view;

import java.util.Scanner;

public abstract class MainUI {

    protected abstract void printChoice();

    public abstract void start();

    public MainUI() {
    }

    public void printWarning(String breadcrumb) {
        String spaces = String.format("%" + (105 - breadcrumb.length()) + "s", "");
        System.out.println("[ERROR !!! " + breadcrumb + spaces + "!!!]");
    }

    /**
     * Method to handle invalid inputs for all views.
     * Ensures user-friendly feedback for invalid entries.
     */
    protected void handleInvalidInput() {
        System.out.println("Invalid input. Please try again.");
    }

    /**
     * Method to display a simple horizontal separator between sections.
     * Adds structure to the console output for readability.
     */
    protected void printSeparator() {
        System.out
                .println("===========================================================================================");
    }

    /**
     * Method to exit the application.
     * Can be overridden if needed, but provides a basic exit confirmation.
     */
    protected void exitApp() {
        System.out.println("Exiting the Hospital Management System... Goodbye!");
        System.exit(0);
    }

    /**
     * Method to handle user input for navigation in the view.
     * This method can be used by child views to facilitate action selection.
     * 
     * @param maxChoice The maximum number of valid choices for the user input.
     * @return The user's choice as an integer.
     */
    protected int getUserChoice(int maxChoice) {
//        try {
//            int choice = Integer.parseInt(System.console().readLine());
//            if (choice < 1 || choice > maxChoice) {
//                handleInvalidInput();
//                return -1;
//            }
//            return choice;
//        } catch (NumberFormatException e) {
//            handleInvalidInput();
//            return -1;
//        }
        // Check if console is available
        if (System.console() != null) {
            try {
                int choice = Integer.parseInt(System.console().readLine());
                if (choice < 1 || choice > maxChoice) {
                    handleInvalidInput();
                    return -1;
                }
                return choice;
            } catch (NumberFormatException e) {
                handleInvalidInput();
                return -1;
            }
        } else {
            // Fallback to Scanner if console is null
            Scanner scanner = new Scanner(System.in);
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > maxChoice) {
                    handleInvalidInput();
                    return -1;
                }
                return choice;
            } catch (NumberFormatException e) {
                handleInvalidInput();
                return -1;
            }
        }
    }
}
