package boundary;

import java.util.Scanner;

public abstract class Boundary {

    protected abstract void printChoice();

    public abstract void start();

    public Boundary() {
    }

    public void printWarning(String warning) {
        String spaces = String.format("%" + (105 - warning.length()) + "s", "");
        System.out.println( warning + spaces );
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
    protected void printline() {
        System.out.println("===========================================================================================");
    }

    protected static void singleline(){
        System.out.println("---------------------------------------");
    }

    protected static void headerline(){
        System.out.println("--------------------------------------------------");
    }

}
