package boundary;

/**
 * Base Boundary Class where all the boundary classes inherit from
 */
public abstract class Boundary {

    /**
     * Print Choice method
     */
    protected abstract void printChoice();

    /**
     *  Print Start Menu method
     */

    public abstract void start();

    /**
     * Boundary Constructor
     */
    public Boundary() {
    }

    /**
     * To print
     * @param warning error
     */
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

    /**
     * Method to display a single line
     */
    protected static void singleline(){
        System.out.println("---------------------------------------");
    }

    /**
     * Method to print header lines
     */
    protected static void headerline(){
        System.out.println("--------------------------------------------------");
    }

}
