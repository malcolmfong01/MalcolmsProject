package model;

import java.time.LocalDateTime;

import enums.PersonnelFileType;

public class SessionCookie {
	private String uid;
	private PersonnelFileType role;
	private LocalDateTime startSession;

	// Constructor with parameters
	public SessionCookie(String uid, PersonnelFileType role) {
		this.uid = uid;
		this.role = role;
		this.startSession = LocalDateTime.now(); // Sets startSession to the current time
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public PersonnelFileType getRole() {
		return role;
	}

	public void setRole(PersonnelFileType role) {
		this.role = role;
	}

	public LocalDateTime getStartSession() {
		return startSession;
	}

	public void setStartSession(LocalDateTime startSession) {
		this.startSession = startSession;
	}
}
