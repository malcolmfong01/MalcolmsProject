package HMSApp;

import boundary.*;

/**
 * Main entry point for our Hospital Management System application.
 */
public class HMSMain {
    public static void main(String[] args) {
        MainPageBoundary mainPageBoundary = new MainPageBoundary();
        mainPageBoundary.start();
    }

}