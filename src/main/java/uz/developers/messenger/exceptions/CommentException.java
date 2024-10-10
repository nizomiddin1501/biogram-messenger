package uz.developers.messenger.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Exception thrown when a comment is invalid.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CommentException extends RuntimeException{

    public CommentException(String message) {
        super(message);
    }





}
