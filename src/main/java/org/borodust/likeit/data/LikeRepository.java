package org.borodust.likeit.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * Repository to save and retrieve like numbers.
 */
@Repository
public class LikeRepository {
    private final RedisTemplate<String, Long> redis;

    @Autowired
    public LikeRepository(RedisTemplate<String, Long> redisTemplate) {
        this.redis = redisTemplate;
    }

    /**
     * Increments number of likes
     *
     * @param name name of the thing
     */
    public void increment(String name) {
        redis.opsForValue().increment(name);
    }

    /**
     * Loads number of likes
     * @param name name of the thing
     * @return number of likes recorded
     */
    public long count(String name) {
        Long count = redis.opsForValue().get(name);
        return count == null ? 0 : count;
    }
}
