package org.borodust.likeit.service;

/**
 * Service for keeping track of likes given to a named thing.
 */
public interface LikeService {
    /**
     * Records a like for a thing.
     *
     * @throws IllegalArgumentException if name is null, empty or more than 280 characters
     */
    void like(String name);

    /**
     * Gets number of likes for a thing.
     *
     * @param name name of the thing
     * @return number of likes
     * @throws IllegalArgumentException if name is null, empty or more than 280 characters
     */
    long getLikes(String name);
}