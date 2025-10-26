package com.disl.librarymanagementsystem.exceptionHandler;

import com.disl.librarymanagementsystem.model.response.HttpResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<HttpResponse> handleAccountAlreadyExists(AlreadyExistsException ex, HttpServletRequest request) {

        return HttpResponse.getResponseEntityForError(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                null);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<HttpResponse> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {

        return HttpResponse.getResponseEntityForError(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                null);
    }

}
