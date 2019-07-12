package org.borodust.likeit.controller;

import org.borodust.likeit.service.LikeService;
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
    private final LikeService likeService;

    @Autowired
    public ApiController(LikeService likeService) {
        this.likeService = likeService;
    }

    @RequestMapping("/like")
    public DeferredResult<String> like(@RequestParam("name") String thing) {
        DeferredResult<String> result = new DeferredResult<>();

        // FIXME Make async
        likeService.like(thing);
        result.setResult("OK");

        return result;
    }

    @RequestMapping("/get-likes")
    public DeferredResult<String> getLikes(@RequestParam("name") String thing) {
        DeferredResult<String> result = new DeferredResult<>();

        // FIXME Make async
        long likes = likeService.getLikes(thing);
        result.setResult(String.valueOf(likes));
        return result;
    }
}
