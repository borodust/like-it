package org.borodust.likeit;

import org.redisson.api.RedissonClient;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.Executor;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * Like It application configuration.
 *
 * @author Pavel Korolev
 */
@SpringBootApplication(scanBasePackages = "org.borodust.likeit")
public class LikeItApplication {
    private static final Logger log = LoggerFactory.getLogger(LikeItApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LikeItApplication.class, args);
    }

    /**
     * Creates an executor for processing requests.
     *
     * @param workerCount number of worker threads
     * @return executor for processing requests
     */
    @Bean("worker-pool")
    Executor workerPool(@Value("${like.worker.count}") int workerCount) {
        log.info("Worker count: {}", workerCount);
        return newFixedThreadPool(workerCount);
    }

    /**
     * Creates a redis template for handling string-long pairs.
     *
     * @param redisson redisson client setup via spring boot
     * @return redis template mapping between thing names and like counts
     */
    @Bean
    RedisTemplate<String, Long> redisTemplate(RedissonClient redisson) {
        final RedisTemplate<String, Long> template = new RedisTemplate<>();
        template.setConnectionFactory(new RedissonConnectionFactory(redisson));
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericToStringSerializer<>(Long.class));
        template.setValueSerializer(new GenericToStringSerializer<>(Long.class));
        return template;
    }
}
