package helper;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class Helper {
    public static final Scanner sc = new Scanner(System.in);

    private Helper() {
        // Private constructor to prevent instantiation
    }

    public static int readInt(String prompt) {
        while (true) {
            try {
                if (prompt != "")
                    System.out.print(prompt);
                int userInput = sc.nextInt();
                sc.nextLine(); // Consume newline left-over
                return userInput;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
    }

    public static LocalDateTime readDate() {
        LocalDateTime date = null;
        boolean valid = false;
        while (!valid) {
            String input = Helper.readString();

            try {
                // Parse the input into LocalDateTime
                date = LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                valid = true; // Input is valid, exit the loop
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD HH:MM.");
            }
        }
        return date;
    }

    /**
     * Reads an integer from the user within a specified range, with a prompt
     * message.
     * Continues to prompt until a valid integer within the range is entered.
     *
     * @param prompt The prompt message to display to the user.
     * @param min    The minimum allowed value (inclusive).
     * @param max    The maximum allowed value (inclusive).
     * @return The integer entered by the user within the specified range.
     */
    public static int readInt(String prompt, int min, int max) {
        while (true) {
            if (prompt != "")
                System.out.print(prompt);
            try {
                int userInput = sc.nextInt();
                sc.nextLine(); // Consume newline left-over
                if (userInput >= min && userInput <= max) {
                    return userInput;
                } else {
                    System.out.println("Input out of allowed range (" + min + " to " + max + "). Try again.");
                }
            } catch (InputMismatchException e) {
                sc.nextLine(); // Clear the invalid input
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    public static double readDouble() {
        while (true) {
            try {
                System.out.print("Enter a double: ");
                double userInput = sc.nextDouble();
                sc.nextLine(); // Consume newline left-over
                return userInput;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid input. Please enter a decimal number.");
            }
        }
    }

    public static String readString(String prompt) {
        System.out.println(prompt);
        return sc.nextLine();
    }

    public static String readString() {
        return sc.nextLine();
    }

    public static String readEmail(String prompt) {
        System.out.println(prompt);
        String email = sc.nextLine();
        while (!Helper.isValidEmail(email)) {
            System.out.println("The email '" + email + "' is invalid. Please enter a new email:");
            email = Helper.readString("Enter a new email: ");
        }
        return email;
    }

    public static String readGender(String prompt) {
        System.out.println(prompt);
        String gender = sc.nextLine();
        while (!Helper.isValidGender(gender)) {
            System.out.println(gender + " is invalid genderType. Please enter again");
            gender = Helper.readString("Enter your gender again: ");
        }
        return gender;
    }

    public static boolean promptConfirmation(String message) {
        System.out.println("Are you sure you want to " + message + "? (yes/no)");
        String userInput = sc.nextLine().trim().toLowerCase();
        return userInput.equals("yes");
    }

    public static <K, V> int generateUniqueId(HashMap<K, V> database) {
        return database.isEmpty() ? 1 : database.size() + 1;
    }

    public static String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public static boolean isWeekend(String date) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, format);
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    public static void pressEnterToContinue() {
        System.out.println("Press Enter to continue...");
        sc.nextLine();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Reads a date input from the user in the specified format and returns a
     * LocalDateTime object.
     * Continues to prompt until a valid date is entered.
     * 
     * @param prompt The prompt message to display to the user.
     * @return LocalDateTime object representing the entered date and time.
     */
    public static LocalDateTime readDate(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.println(prompt);
            String userInput = sc.nextLine();
            try {
                return LocalDateTime.parse(userInput + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME); // Time set
                                                                                                            // to
                                                                                                            // midnight
                                                                                                            // by
                                                                                                            // default
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
            }
        }
    }

    public static String getFieldOrNull(String[] fields, int index) {
        return index < fields.length && !fields[index].isEmpty() ? fields[index] : null;
    }

    public static LocalDateTime parseDateTimeOrNull(String[] fields, int index) {
        try {
            return index < fields.length && !fields[index].isEmpty() ? LocalDateTime.parse(fields[index]) : null;
        } catch (Exception e) {
            return null; // return null if parsing fails or field is empty
        }
    }

    public static Double parseDoubleOrNull(String[] fields, int index) {
        try {
            return index < fields.length && !fields[index].isEmpty() ? Double.parseDouble(fields[index]) : null;
        } catch (Exception e) {
            return null; // return null if parsing fails or field is empty
        }
    }

    /**
     * Checks if the provided email matches a valid email format.
     *
     * @param email The email address to validate.
     * @return True if the email is valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(emailRegex);
    }

    /**
     * Checks if the provided gender input is valid (either "M" or "F").
     *
     * @param gender The gender input to validate.
     * @return True if the gender is "M" or "F", false otherwise.
     */
    public static boolean isValidGender(String gender) {
        String genderRegex = "^[MF]$";
        return gender.matches(genderRegex);
    }
}
