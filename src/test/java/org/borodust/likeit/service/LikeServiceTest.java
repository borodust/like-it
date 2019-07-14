package org.borodust.likeit.service;

import org.borodust.likeit.data.LikeRepository;
import org.borodust.likeit.service.impl.DefaultLikeService;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Pavel Korolev
 */
public class LikeServiceTest {
    private static final String THING_TO_LIKE = "like-it-service";
    private LikeService likeService;

    @Before
    public void cleanupFixture() {
        this.likeService = new DefaultLikeService(setupLikeRepositoryMock());
    }

    private LikeRepository setupLikeRepositoryMock() {
        LikeRepository mock = mock(LikeRepository.class);
        AtomicLong counter = new AtomicLong(0);
        doAnswer((invocation) -> counter.incrementAndGet())
                .when(mock).increment(THING_TO_LIKE);
        when(mock.count(THING_TO_LIKE))
                .thenAnswer((invocation) -> counter.get());
        return mock;
    }

    @Test
    public void like() {
        long currentLikes = likeService.getLikes(THING_TO_LIKE);

        likeService.like(THING_TO_LIKE);

        assertEquals(currentLikes + 1, likeService.getLikes(THING_TO_LIKE));
    }
}
