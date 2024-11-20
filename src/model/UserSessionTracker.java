package model;
import enums.PersonnelFileType;

/**
 * UserSessionTracker Class
 * Tracks the User currently in the system
 * Contains the user ID, role, and the start time of the session.
 */

public class UserSessionTracker {
	private String uid;
	private PersonnelFileType role;

    /**
     * Constructor for creating a UserSessionTracker instance.
     * The start time of the session is set to the current time.
     *
     * @param uid the unique identifier of the user
     * @param role the role of the user in the system (e.g., Doctor, Patient, Admin)
     */
	// Constructor with parameters
	public UserSessionTracker(String uid, PersonnelFileType role) {
		this.uid = uid;
		this.role = role;
	}

    /**
     * Gets the unique identifier (UID) of the user associated with this session.
     *
     * @return the UID of the user
     */
	public String getUid() {
		return uid;
	}

    /**
     * Sets the unique identifier (UID) of the user associated with this session.
     *
     * @param uid the new UID of the user
     */
	public void setUid(String uid) {
		this.uid = uid;
	}

    /**
     * Gets the role of the user in the system.
     *
     * @return the role of the user
     */
	public PersonnelFileType getRole() {
		return role;
	}

    /**
     * Sets the role of the user in the system.
     *
     * @param role the new role of the user
     */
	public void setRole(PersonnelFileType role) {
		this.role = role;
	}
}
