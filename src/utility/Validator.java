package utility;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * This Validator class provides various helper methods for input reading, validation,
 * formatting, and general utilities for console applications.
 * Includes methods for reading and validating user input, formatting dates, and more.
 */
public class Validator {
    /**
     * Scanner instance for user input.
     */
    public static final Scanner sc = new Scanner(System.in);

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Validator() {}

    /**
     * Reads an integer from the user with a given prompt.
     * Continues to prompt until a valid integer is entered.
     *
     * @param prompt The prompt message to display to the user.
     * @return The integer entered by the user.
     */
    public static int readInt(String prompt) {
        while (true) {
            try {
                if (!prompt.isEmpty()) // Use isEmpty() for clarity and efficiency
                    System.out.print(prompt);
                int userInput = sc.nextInt();
                sc.nextLine(); // Consume newline left-over
                return userInput;
            } catch (InputMismatchException e) {
                sc.nextLine(); // Clear the invalid input
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
    }


    /**
     * Reads and validates an ID entered by the user based on a given type and format.
     *
     * @param idType A descriptive name for the ID type (e.g., "Medicine", "Patient").
     * @param regex The regular expression to validate the ID format.
     * @return The validated ID entered by the user.
     */
    public static String readID(String idType, String regex) {
        Scanner scanner = new Scanner(System.in);
        char prefix = idType.charAt(0); // Use charAt(0) to get the first character of idType
        System.out.printf("Enter %s ID (e.g., %c001, %c002): ", idType, prefix, prefix);

        String id = scanner.nextLine();

        // Validate input
        while (!id.matches(regex)) {
            System.out.printf("Invalid %s ID format! Please enter in the format %c001, %c002, etc.%n", idType, prefix, prefix);
            System.out.printf("Enter %s ID: ", idType);
            id = scanner.nextLine();
        }
        return id;
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
            if (!prompt.isEmpty())
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

    /**
     * Reads a string input from the user with a given prompt.
     *
     * @param prompt The prompt message to display to the user.
     * @return The string entered by the user.
     */
    public static String readString(String prompt) {

        while (true) {
            System.out.println(prompt);
            // Read the user input
            String input = sc.nextLine();

            // Check if the input is empty
            if (input.trim().isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            } else {
                // If input is not empty, return it
                return input;
            }
        }
    }

    /**
     * Reads a string input from the user without a prompt.
     *
     * @return The string entered by the user.
     */
    public static String readString() {
        return sc.nextLine();
    }

    /**
     * Reads a valid email address from the user with a prompt.
     * Continues to prompt until a valid email format is entered.
     *
     * @param prompt The prompt message to display to the user.
     * @return A valid email address entered by the user.
     */
    public static String readEmail(String prompt) {
        System.out.println(prompt);
        String email = sc.nextLine();
        while (!Validator.isValidEmail(email)) {
            System.out.println("The email '" + email + "' is invalid. Please enter a new email:");
            email = Validator.readString("Enter a new email: ");
        }
        return email;
    }

    /**
     * Reads and validates a gender input from the user with a prompt.
     * Continues to prompt until a valid gender ("M" or "F") is entered.
     *
     * @param prompt The prompt message to display to the user.
     * @return The valid gender entered by the user.
     */
    public static String readGender(String prompt) {
        System.out.println(prompt);
        String gender = sc.nextLine();
        while (!Validator.isValidGender(gender)) {
            System.out.println(gender + " is invalid genderType. Please enter again");
            gender = Validator.readString("Enter your gender again: ");
        }
        return gender;
    }

    /**
     * Prompts the user to enter a valid blood type until the input is valid.
     * Acceptable blood types are: "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-".
     *
     * @param prompt The prompt message to display to the user.
     * @return The valid blood type entered by the user.
     */
    public static String readValidBloodType(String prompt) {
        String[] validBloodTypes = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().toUpperCase();
            for (String validType : validBloodTypes) {
                if (input.equals(validType)) {
                    return input;
                }
            }
            System.out.println("Invalid blood type. Please enter a valid blood type (e.g., A+, O-, etc.).");
        }
    }

    /**
     * Prompts the user to enter a valid phone number until the input is valid.
     * A valid phone number must have exactly 8 digits and start with either 8 or 9.
     *
     * @param prompt The prompt message to display to the user.
     * @return The valid phone number entered by the user.
     */
    public static String readValidPhoneNumber(String prompt) {
        String phoneRegex = "^[89]\\d{7}$";
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            if (input.matches(phoneRegex)) {
                return input;
            }
            System.out.println("Invalid phone number. Please enter an 8-digit phone number starting with 8 or 9.");
        }
    }

    /**
     * Prompts the user for a confirmation with a yes/no question.
     *
     * @param message The confirmation message to display.
     * @return true if the user confirms with "yes"; false otherwise.
     */
    public static boolean promptConfirmation(String message) {
        while (true) {
            System.out.println("Are you sure you want to " + message + "? (yes/no)");
            String userInput = sc.nextLine().trim().toLowerCase();

            switch (userInput) {
                case "yes":
                    return true;
                case "no":
                    return false;
                default:
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
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
        while (true) {
            System.out.println(prompt);
            String userInput = sc.nextLine();
            try {
                return LocalDateTime.parse(userInput + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
            }
        }
    }

    /**
     * Checks if the provided email matches a valid email format.
     *
     * @param email The email address to validate.
     * @return True if the email is valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$";
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
        return gender.toUpperCase().matches(genderRegex);
    }


}
