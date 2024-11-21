package enums;

/**
 * Enum representing different types of personnel files, each associated with a
 * specific file name for personnel data (e.g., doctors, patients, pharmacists, admins).
 */

public enum UserType {
    DOCTORS("doctors"),
    PATIENTS("patients"),
    PHARMACISTS("pharmacists"),
    ADMINS("admins");

    /**
     * The file name associated with this personnel file type.
     */
    public final String fileName;

    /**
     * Constructs a UserType with the specified file name.
     *
     * @param fileName the file name associated with the personnel type
     */
    UserType(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Converts a file name string to the corresponding UserType enum constant.
     *
     * @param fileName the file name to match with an enum constant
     * @return the matching UserType enum constant
     * @throws IllegalArgumentException if no matching enum constant is found
     */

    public static UserType toEnum(String fileName) {
        for (UserType type : UserType.values()) {
            if (type.fileName.equalsIgnoreCase(fileName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant for file name: " + fileName);
    }
}
