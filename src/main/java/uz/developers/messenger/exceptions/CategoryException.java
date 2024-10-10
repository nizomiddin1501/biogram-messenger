package uz.developers.messenger.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Exception thrown when a category is invalid.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoryException extends RuntimeException{

    public CategoryException(String message) {
        super(message);
    }



}
