package org.borodust.likeit.controller;

import org.borodust.likeit.service.impl.AsyncLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * Non-blocking asynchronous Web interface to Like It! service
 *
 * @author Pavel Korolev
 */
@RestController
public class ApiController {
    private final AsyncLikeService likeService;

    @Autowired
    public ApiController(AsyncLikeService likeService) {
        this.likeService = likeService;
    }

    @RequestMapping("/like")
    public DeferredResult<String> like(@RequestParam("name") String thing) {
        DeferredResult<String> deferred = new DeferredResult<>();

        likeService.like(thing)
                .thenRun(() -> deferred.setResult("OK"));

        return deferred;
    }

    @RequestMapping("/get-likes")
    public DeferredResult<String> getLikes(@RequestParam("name") String thing) {
        DeferredResult<String> deferred = new DeferredResult<>();
        likeService.getLikes(thing)
                .thenAccept(likes -> deferred.setResult(String.valueOf(likes)));
        return deferred;
    }
}
