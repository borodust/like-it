package org.borodust.likeit.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LikeServiceTest {
    private static final String THING_TO_LIKE = "like-it-service";

    @Autowired
    private LikeService likeService;

    @Test
    public void like() {
        long currentLikes = likeService.getLikes(THING_TO_LIKE);

        likeService.like(THING_TO_LIKE);

        assertEquals(currentLikes + 1, likeService.getLikes(THING_TO_LIKE));
    }
}
