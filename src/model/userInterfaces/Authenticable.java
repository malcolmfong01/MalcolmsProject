package model.userInterfaces;

/**
 * Interface to represent entities that can be authenticated with a username and password.
 */
public interface Authenticable {
    /**
     * Gets the username of the entity.
     *
     * @return the username
     */
    String getUsername();

    /**
     * Sets the username of the entity.
     *
     * @param username the username to set
     */
    void setUsername(String username);

    /**
     * Gets the hashed password of the entity.
     *
     * @return the hashed password
     */
    String getPasswordHash();

    /**
     * Sets the hashed password of the entity.
     *
     * @param passwordHash the hashed password to set
     */
    void setPasswordHash(String passwordHash);
}
