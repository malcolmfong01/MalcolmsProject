package model.userInterfaces;

/**
 * Interface to represent contactable entities with email and phone details.
 */
public interface Contactable {
    /**
     * Gets the email address of the entity.
     *
     * @return the email address
     */
    String getEmail();

    /**
     * Sets the email address of the entity.
     *
     * @param email the email address to set
     */
    void setEmail(String email);

    /**
     * Gets the phone number of the entity.
     *
     * @return the phone number
     */
    String getPhoneNo();

    /**
     * Sets the phone number of the entity.
     *
     * @param phoneNo the phone number to set
     */
    void setPhoneNo(String phoneNo);
}
