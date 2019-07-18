package org.borodust.likeit.service.impl;

import com.devskiller.jfairy.Fairy;
import org.borodust.likeit.data.LikeRepository;
import org.borodust.likeit.service.LikeService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Pavel Korolev
 */
public class LikeServiceTest {
    private static final String THING_TO_LIKE = "like-it-service";
    private LikeService likeService;
    private Fairy fairy = Fairy.create();
    private LikeRepository likeRepository;

    @Before
    public void cleanupFixture() {
        this.likeRepository = mock(LikeRepository.class);
        this.likeService = new DefaultLikeService(likeRepository);
    }

    @Test
    public void like() {
        likeService.like(THING_TO_LIKE);

        verify(likeRepository).increment(THING_TO_LIKE);
    }

    @Test
    public void getLikes() {
        likeService.getLikes(THING_TO_LIKE);

        verify(likeRepository).count(THING_TO_LIKE);
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
