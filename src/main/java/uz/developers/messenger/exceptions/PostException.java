package uz.developers.messenger.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Exception thrown when a post is invalid.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PostException extends RuntimeException{

    public PostException(String message) {
        super(message);
    }



}
