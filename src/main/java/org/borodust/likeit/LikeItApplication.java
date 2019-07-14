package org.borodust.likeit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executor;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * Like It application configuration.
 *
 * @author Pavel Korolev
 */
@SpringBootApplication(scanBasePackages = "org.borodust.likeit")
public class LikeItApplication {

    public static void main(String[] args) {
        SpringApplication.run(LikeItApplication.class, args);
    }

    @Bean("worker-pool")
    public Executor workerPool(@Value("${like.worker.count}") int workerCount) {
        return newFixedThreadPool(workerCount);
    }
}
