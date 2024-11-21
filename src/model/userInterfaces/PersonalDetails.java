package model.userInterfaces;

import java.time.LocalDateTime;

/**
 * Interface to represent entities with personal details such as name, date of birth, and gender.
 */
public interface PersonalDetails {
    /**
     * Gets the full name of the entity.
     *
     * @return the full name
     */
    String getFullName();

    /**
     * Sets the full name of the entity.
     *
     * @param fullName the full name to set
     */
    void setFullName(String fullName);

    /**
     * Gets the date of birth of the entity.
     *
     * @return the date of birth
     */
    LocalDateTime getDoB();

    /**
     * Sets the date of birth of the entity.
     *
     * @param DoB the date of birth to set
     */
    void setDoB(LocalDateTime DoB);

    /**
     * Gets the gender of the entity.
     *
     * @return the gender
     */
    String getGender();

    /**
     * Sets the gender of the entity.
     *
     * @param gender the gender to set
     */
    void setGender(String gender);
}
