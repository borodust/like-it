package org.borodust.likeit.service.impl;

import org.borodust.likeit.data.LikeRepository;
import org.borodust.likeit.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Scalable and realtime {@link LikeService} implementation.
 *
 * @author Pavel Korolev
 */
@Service
public class DefaultLikeService implements LikeService {
    private final LikeRepository likeRepository;

    @Autowired
    public DefaultLikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public void like(String name) {
        likeRepository.increment(name);
    }

    @Override
    public long getLikes(String name) {
        return likeRepository.count(name);
    }
}
