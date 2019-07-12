package org.borodust.likeit.data;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LikeRepository {
    private final RedissonClient redis;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public LikeRepository(RedissonClient redissonClient) {
        this.redis = redissonClient;
    }

    public void increment(String name) {
        redis.getAtomicLong(name).incrementAndGet();
    }

    public long count(String name) {
        return redis.getAtomicLong(name).get();
    }
}
