package utility;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Utility class to facilitate user selection of a date and time.
 * This class enables a user to input year, month, day, hour, and minute
 * to generate a LocalDateTime instance.
 */
public class DateTime {

    /**
     * Requests a date and time input from the user. This method sequentially
     * prompts for year, month, day, hour, and minute, ensuring that all inputs
     * are validated within their respective ranges.
     *
     * @param message the message displayed before starting the input prompts
     * @return a LocalDateTime object reflecting the user's input
     */
    public static LocalDateTime pickDateTime(String message) {
        Scanner scanner = Validator.sc;
        System.out.println(message);

        // Variables to hold date and time components
        int year = getValidInput(scanner, "Enter year (e.g., 2024): ",
                input -> input > 0,
                "Please input a valid year (e.g., 2024).");
        int month = getValidInput(scanner, "Enter month (1-12): ",
                input -> input >= 1 && input <= 12,
                "Month must be between 1 and 12.");
        int day = getValidInput(scanner, "Enter day of the month: ",
                input -> input >= 1 && input <= 31,
                "Day must be between 1 and 31.");
        int hour = getValidInput(scanner, "Enter hour (0-23): ",
                input -> input >= 0 && input <= 23,
                "Hour must be between 0 and 23.");
        int minute = getValidInput(scanner, "Enter minute (0-59): ",
                input -> input >= 0 && input <= 59,
                "Minute must be between 0 and 59.");

        // Combine inputs into a LocalDateTime object
        return LocalDateTime.of(year, month, day, hour, minute);
    }

    /**
     * Method to help handle input validation.
     *
     * @param scanner Scanner object for user input
     * @param prompt Prompt message for the user
     * @param validator Lambda to validate the input
     * @param errorMessage Error message for invalid input
     * @return a valid integer input
     */
    private static int getValidInput(Scanner scanner, String prompt,
                                     java.util.function.Predicate<Integer> validator,
                                     String errorMessage) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = scanner.nextInt();
                if (!validator.test(input)) {
                    throw new InputMismatchException(errorMessage);
                }
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. " + errorMessage);
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
}
