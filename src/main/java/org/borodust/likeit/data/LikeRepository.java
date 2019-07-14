package org.borodust.likeit.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LikeRepository {
    private final RedisTemplate<String, Long> redis;

    @Autowired
    public LikeRepository(RedisTemplate<String, Long> redisTemplate) {
        this.redis = redisTemplate;
    }

    public void increment(String name) {
        redis.opsForValue().increment(name);
    }

    public long count(String name) {
        Long count = redis.opsForValue().get(name);
        return count == null ? 0 : count;
    }
}
