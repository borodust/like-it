package org.borodust.likeit.service.impl;

import com.devskiller.jfairy.Fairy;
import org.borodust.likeit.data.LikeRepository;
import org.borodust.likeit.service.LikeService;
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
    private Fairy fairy = Fairy.create();

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

    @Test(expected = IllegalArgumentException.class)
    public void emptyName() {
        likeService.getLikes("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullName() {
        likeService.getLikes(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void bigName() {
        likeService.like(fairy.textProducer().randomString(DefaultLikeService.NAME_MAX_LENGTH + 1));
    }

}
