package org.borodust.likeit.service.impl;

import org.borodust.likeit.service.LikeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/**
 * Asynchronous service for keeping likes, based on {@link LikeService}
 *
 * @author Pavel Korolev
 */
@Service
public class AsyncLikeService {

    private final LikeService likeService;
    private final Executor executor;

    public AsyncLikeService(LikeService likeService, @Qualifier("worker-pool") Executor executor) {
        this.likeService = likeService;
        this.executor = executor;
    }

    /**
     * See {@link LikeService#like(String)}
     */
    public CompletableFuture<Void> like(String name) {
        return execute(() -> likeService.like(name));
    }

    /**
     * See {@link LikeService#like(String)}
     */
    public CompletableFuture<Long> getLikes(String name) {
        return execute(() -> likeService.getLikes(name));
    }

    private <T> CompletableFuture<T> execute(Supplier<T> supplier) {
        CompletableFuture<T> result = new CompletableFuture<>();
        executor.execute(() -> {
            try {
                result.complete(supplier.get());
            } catch (Throwable ex) {
                result.completeExceptionally(ex);
            }
        });
        return result;
    }

    private CompletableFuture<Void> execute(Runnable runnable) {
        return execute(() -> {
            runnable.run();
            return null;
        });
    }

}
