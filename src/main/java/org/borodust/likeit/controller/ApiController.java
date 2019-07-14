package org.borodust.likeit.controller;

import org.borodust.likeit.service.impl.AsyncLikeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletionException;

import static java.lang.String.valueOf;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

/**
 * Non-blocking asynchronous Web interface to Like It! service
 *
 * @author Pavel Korolev
 */
@RestController
public class ApiController {
    private static final Logger log = LoggerFactory.getLogger(ApiController.class);
    private final AsyncLikeService likeService;

    @Autowired
    public ApiController(AsyncLikeService likeService) {
        this.likeService = likeService;
    }

    @RequestMapping("/like")
    public DeferredResult<ResponseEntity<String>> like(@RequestParam("name") String name) {
        DeferredResult<ResponseEntity<String>> deferred = new DeferredResult<>();

        likeService.like(name)
                .thenRun(() -> deferred.setResult(ok("OK")))
                .exceptionally(ex -> reportError(deferred, ex));

        return deferred;
    }

    @RequestMapping("/get-likes")
    public DeferredResult<ResponseEntity<String>> displayError(@RequestParam("name") String name) {
        DeferredResult<ResponseEntity<String>> deferred = new DeferredResult<>();
        likeService.getLikes(name)
                .thenAccept(likes -> deferred.setResult(ok(valueOf(likes))))
                .exceptionally(ex -> reportError(deferred, ex));
        return deferred;
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> displayError(Exception ex) {
        log.error("Invalid request", ex);
        return badRequestResponse();
    }

    private Void reportError(DeferredResult<ResponseEntity<String>> deferred, Throwable originalException) {
        try {
            if (originalException instanceof CompletionException) {
                throw originalException.getCause();
            } else {
                throw originalException;
            }
        } catch (IllegalArgumentException ex) {
            deferred.setResult(badRequestResponse());
        } catch (Throwable ex) {
            log.error("Unexpected server error", ex);
            deferred.setResult(status(INTERNAL_SERVER_ERROR)
                    .body("I'M NOT FEELING TOO WELL"));
        }
        return null;
    }

    private ResponseEntity<String> badRequestResponse() {
        return badRequest().body("DO NO HARM");
    }
}
