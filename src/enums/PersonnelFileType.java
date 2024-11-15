/**
 * Enum representing different types of personnel files, each associated with a
 * specific file name for personnel data (e.g., doctors, patients, pharmacists, admins).
 */
package enums;

public enum PersonnelFileType {
    DOCTORS("doctors"),
    PATIENTS("patients"),
    PHARMACISTS("pharmacists"),
    ADMINS("admins");

    /**
     * The file name associated with this personnel file type.
     */
    public final String fileName;

    /**
     * Constructs a PersonnelFileType with the specified file name.
     *
     * @param fileName the file name associated with the personnel type
     */
    PersonnelFileType(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Converts a file name string to the corresponding PersonnelFileType enum constant.
     *
     * @param fileName the file name to match with an enum constant
     * @return the matching PersonnelFileType enum constant
     * @throws IllegalArgumentException if no matching enum constant is found
     */
    public static PersonnelFileType toEnum(String fileName) {
        for (PersonnelFileType type : PersonnelFileType.values()) {
            if (type.fileName.equalsIgnoreCase(fileName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant for file name: " + fileName);
    }
}
