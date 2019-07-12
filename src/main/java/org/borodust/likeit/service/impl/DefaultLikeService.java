package org.borodust.likeit.service.impl;

import org.borodust.likeit.service.LikeService;
import org.springframework.stereotype.Service;

/**
 * Scalable and realtime {@link LikeService} implementation.
 *
 * @author Pavel Korolev
 */
@Service
public class DefaultLikeService implements LikeService {
    @Override
    public void like(String thing) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getLikes(String thing) {
        throw new UnsupportedOperationException();
    }
}
