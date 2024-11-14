package helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DateTimePicker {

    // main only here for testing purpose
    public static void main(String[] args) {
        LocalDateTime selectedDateTime = pickDateTime("Please select a date and time");
        System.out.println("You selected: " + selectedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    public static LocalDateTime pickDateTime(String title) {
        Scanner scanner = Helper.sc;
        System.out.println(title);

        int year = 0, month = 0, day = 0, hour = 0, minute = 0;

        while (true) {
            try {
                System.out.print("Enter year (e.g., 2023): ");
                year = scanner.nextInt();
                if (year < 0)
                    throw new InputMismatchException("Year cannot be negative.");
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid year.");
                scanner.nextLine(); // Clear the invalid input
            }
        }

        while (true) {
            try {
                System.out.print("Enter month (1-12): ");
                month = scanner.nextInt();
                if (month < 1 || month > 12)
                    throw new InputMismatchException("Month must be between 1 and 12.");
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid month (1-12).");
                scanner.nextLine();
            }
        }

        while (true) {
            try {
                System.out.print("Enter day of the month: ");
                day = scanner.nextInt();
                if (day < 1 || day > 31)
                    throw new InputMismatchException("Day must be between 1 and 31.");
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid day (1-31).");
                scanner.nextLine();
            }
        }

        while (true) {
            try {
                System.out.print("Enter hour (0-23): ");
                hour = scanner.nextInt();
                if (hour < 0 || hour > 23)
                    throw new InputMismatchException("Hour must be between 0 and 23.");
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid hour (0-23).");
                scanner.nextLine();
            }
        }

        while (true) {
            try {
                System.out.print("Enter minute (0-59): ");
                minute = scanner.nextInt();
                scanner.nextLine();
                if (minute < 0 || minute > 59)
                    throw new InputMismatchException("Minute must be between 0 and 59.");
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid minute (0-59).");
                scanner.nextLine();
            }
        }

        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);
        return dateTime;
    }
}
