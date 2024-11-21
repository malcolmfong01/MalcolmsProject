package model.userInterfaces;

/**
 * Interface to represent identifiable entities with a unique ID.
 */
public interface Identification {
    /**
     * Gets the unique ID of the entity.
     *
     * @return the unique ID
     */
    String getUID();

    /**
     * Sets the unique ID of the entity.
     *
     * @param UID the unique ID to set
     */
    void setUID(String UID);
}
