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
 * Non-blocking asynchronous REST interface to Like It! service
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

    /**
     * Likes a thing.
     *
     * @param name name of the thing to like
     * @return 200 with OK in the body for success or 400 with DO NO HARM for malformed requests
     */
    @RequestMapping("/like")
    public DeferredResult<ResponseEntity<String>> like(@RequestParam("name") String name) {
        DeferredResult<ResponseEntity<String>> deferred = new DeferredResult<>();

        likeService.like(name)
                .thenRun(() -> deferred.setResult(ok("OK")))
                .exceptionally(ex -> reportError(deferred, ex));

        return deferred;
    }

    /**
     * Retrieves number of likes for a specified name.
     *
     * @param name name of the thing to retrieve number of likes for
     * @return 200 with number of likes in the body for success
     * or 400 with DO NO HARM for malformed requests
     */
    @RequestMapping("/get-likes")
    public DeferredResult<ResponseEntity<String>> displayError(@RequestParam("name") String name) {
        DeferredResult<ResponseEntity<String>> deferred = new DeferredResult<>();
        likeService.getLikes(name)
                .thenAccept(likes -> deferred.setResult(ok(valueOf(likes))))
                .exceptionally(ex -> reportError(deferred, ex));
        return deferred;
    }

    /**
     * Reports uncaught exception as a malformed request.
     *
     * @param ex Uncaught exception
     * @return 404 DO NO HARM
     */
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
