package model.userInterfaces;

/**
 * Interface to represent entities with an assignable role.
 */
public interface RoleAssignable {
    /**
     * Gets the role of the entity.
     *
     * @return the role
     */
    String getRole();

    /**
     * Sets the role of the entity.
     *
     * @param role the role to set
     */
    void setRole(String role);
}
