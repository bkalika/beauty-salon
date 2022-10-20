package com.bsalon.daos;

import java.util.List;

/**
 * @author @bkalika
 */
public interface IDao<Entity> {

    /**
     * Returns the entity from the database matching the given ID, otherwise null.
     * @param id The ID of the entity to be returned.
     * @return The entity from the database matching the given ID, otherwise null.
     * @throws DAOException If something fails at database level.
     */
    Entity find(Long id) throws DAOException;

    /**
     * Returns a list of all given entities from the database.
     * @return A list of all provided entities from the database.
     * @throws DAOException If something fails at database level.
     */
    List<Entity> list() throws DAOException;

    /**
     * Create the given entity in the database. The entity ID must be null, otherwise it will throw
     * IllegalArgumentException. After creating, the DAO will set the obtained ID in the given entity.
     * @param t The entity to be created in the database.
     * @throws IllegalArgumentException If the entity ID is not null.
     * @throws DAOException If something fails at database level.
     */
    boolean create(Entity t) throws IllegalArgumentException, DAOException;

    /**
     * Update the given entity in the database. The entity ID must not be null, otherwise it will throw
     * IllegalArgumentException. Note: the password will NOT be updated. Use changePassword() instead.
     * @param entity The entity to be updated in the database.
     * @throws IllegalArgumentException If the entity ID is null.
     * @throws DAOException If something fails at database level.
     */
    void update(Entity entity) throws IllegalArgumentException, DAOException;

    /**
     * Delete the given entity from the database. After deleting, the DAO will set the ID of the given
     * entity to null.
     * @param entity The entity to be deleted from the database.
     * @throws DAOException If something fails at database level.
     */
    void delete(Entity entity) throws DAOException;
}
