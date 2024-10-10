package uz.developers.messenger.exceptions.handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.developers.messenger.exceptions.ResourceNotFoundException;
import uz.developers.messenger.payload.CustomApiResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        String message = ex.getMessage();
        CustomApiResponse apiResponse = new CustomApiResponse(message,false,null);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);

    }








}
