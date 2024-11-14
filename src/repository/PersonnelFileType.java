package repository;

public enum PersonnelFileType {
    DOCTORS("doctors"),
    PATIENTS("patients"),
    PHARMACISTS("pharmacists"),
    ADMINS("admins");

    public final String fileName;

    PersonnelFileType(String fileName) {
        this.fileName = fileName;
    }

    // Static method to convert a string to the corresponding enum constant
    public static PersonnelFileType toEnum(String fileName) {
        for (PersonnelFileType type : PersonnelFileType.values()) {
            if (type.fileName.equalsIgnoreCase(fileName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant for file name: " + fileName);
    }
}
