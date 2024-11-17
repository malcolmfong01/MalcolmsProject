package model;

import java.time.LocalDateTime;

import enums.PersonnelFileType;
/**
 * Represents a session cookie for a user in the system.
 * Contains the user ID, role, and the start time of the session.
 */
public class SessionCookie {
	private String uid;
	private PersonnelFileType role;
	private LocalDateTime startSession;

    /**
     * Constructor for creating a SessionCookie instance.
     * The start time of the session is set to the current time.
     *
     * @param uid the unique identifier of the user
     * @param role the role of the user in the system (e.g., Doctor, Patient, Admin)
     */
	// Constructor with parameters
	public SessionCookie(String uid, PersonnelFileType role) {
		this.uid = uid;
		this.role = role;
		this.startSession = LocalDateTime.now(); // Sets startSession to the current time
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

    /**
     * Gets the start time of the session.
     * 
     * @return the start time of the session
     */
	public LocalDateTime getStartSession() {
		return startSession;
	}

    /**
     * Sets the start time of the session.
     * 
     * @param startSession the new start time of the session
     */
	public void setStartSession(LocalDateTime startSession) {
		this.startSession = startSession;
	}
}
