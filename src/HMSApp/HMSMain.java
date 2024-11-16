/**
 * Main class for the Hospital Management System (HMS) application.
 * Initializes the application user interface and displays a welcome title.
 */
package HMSApp;

import view.HMSAppUI;

/**
 * Main entry point for the Hospital Management System (HMS) application.
 * Initializes the application interface and displays a welcome message.
 *
 * @param // args command-line arguments (not used)
 */
public class HMSMain {
    public static void main(String[] args) {
        HMSAppUI hmsAppUI = new HMSAppUI();
        printHMSWelcomeTitle();
        hmsAppUI.start();
    }

    /**
     * Prints the welcome title in ASCII art format for the Hospital Management System.
     * Displays a stylized title block and welcome message to greet users.
     */
    private static void printHMSWelcomeTitle() {
        System.out.println("=========================================");
        System.out.println("    WELCOME TO THE HOSPITAL MANAGEMENT    ");
        System.out.println("                 SYSTEM                   ");
        System.out.println("=========================================");
    }

}