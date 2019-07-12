package org.borodust.likeit.service;

/**
 * Service for keeping track of likes given to a named thing.
 */
public interface LikeService {
    /**
     * @param thing
     */
    void like(String thing);

    /**
     * @param thing
     * @return
     */
    long getLikes(String thing);
}