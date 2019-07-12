package org.borodust.likeit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
}
