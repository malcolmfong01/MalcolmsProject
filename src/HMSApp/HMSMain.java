/**
 * Main class for the Hospital Management System (HMS) application.
 * Initializes the application user interface and displays a welcome title.
 */
package HMSApp;

import java.util.Scanner;

import model.*;
import repository.*;
import view.*;

/**
 * Main entry point for the Hospital Management System (HMS) application.
 * Initializes the application interface and displays a welcome message.
 *
 * @param args command-line arguments (not used)
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
        System.out.println(
                "╔═════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println(
                "║                                                                                                     ║");
        System.out.println(
                "║  ____   ____      ______  _______            ______              _____        _____        _____    ║");
        System.out.println(
                "║ |    | |    |    |      \\/       \\       ___|\\     \\         ___|\\    \\   ___|\\    \\   ___|\\    \\   ║");
        System.out.println(
                "║ |    | |    |   /          /\\     \\     |    |\\     \\       /    /\\    \\ |    |\\    \\ |    |\\    \\  ║");
        System.out.println(
                "║ |    |_|    |  /     /\\   / /\\     |    |    |/____/|      |    |  |    ||    | |    ||    | |    | ║");
        System.out.println(
                "║ |    .-.    | /     /\\ \\_/ / /    /| ___|    \\|   | |      |    |__|    ||    |/____/||    |/____/| ║");
        System.out.println(
                "║ |    | |    ||     |  \\|_|/ /    / ||    \\    \\___|/       |    .--.    ||    ||    |||    ||    || ║");
        System.out.println(
                "║ |    | |    ||     |       |    |  ||    |\\     \\          |    |  |    ||    ||____|/|    ||____|/ ║");
        System.out.println(
                "║ |____| |____||\\____\\       |____|  /|\\ ___\\|_____|         |____|  |____||____|       |____|        ║");
        System.out.println(
                "║ |    | |    || |    |      |    | / | |    |     |         |    |  |    ||    |       |    |        ║");
        System.out.println(
                "║ |____| |____| \\|____|      |____|/   \\|____|_____|         |____|  |____||____|       |____|        ║");
        System.out.println(
                "║   \\(     )/      \\(          )/         \\(    )/             \\(      )/    \\(           \\(          ║");
        System.out.println(
                "║    '     '        '          '           '    '               '      '      '            '          ║");
        System.out.println(
                "║                                                                                                     ║");
        System.out.println(
                "║                           Welcome to Hospital Management System                                     ║");
        System.out.println(
                "║                                                                                                     ║");
        System.out.println(
                "╚═════════════════════════════════════════════════════════════════════════════════════════════════════╝");
    }
}