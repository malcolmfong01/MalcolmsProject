package HMSApp;

import java.util.Scanner;

import model.*;
import repository.*;
import view.*;

public class HMSMain {
    public static void main(String[] args) {
        HMSAppUI hmsAppUI = new HMSAppUI();
        printHMSWelcomeTitle();
        hmsAppUI.start();
    }

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