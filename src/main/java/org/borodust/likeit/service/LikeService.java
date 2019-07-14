package org.borodust.likeit.service;

/**
 * Service for keeping track of likes given to a named thing.
 */
public interface LikeService {
    /**
     * @param name
     */
    void like(String name);

    /**
     * @param name
     * @return
     */
    long getLikes(String name);
}