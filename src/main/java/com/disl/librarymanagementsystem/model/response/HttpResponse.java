package com.disl.librarymanagementsystem.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HttpResponse {
    private HttpStatus status;
    private String message;
    private Object payload;
    private boolean success;

    public HttpResponse(HttpStatus status, String message, boolean success) {
        this.status = status;
        this.message = message;
        this.success = success;
    }

    public static ResponseEntity<HttpResponse> getResponseEntity(HttpStatus httpStatus, String message, Object payload, boolean success) {
        return new ResponseEntity<>(new HttpResponse(httpStatus, message, payload, success), httpStatus);
    }
    public static ResponseEntity<HttpResponse> getResponseEntityForError(HttpStatus httpStatus, String message, Object payload) {
        return new ResponseEntity<>(new HttpResponse(httpStatus, message, payload, httpStatus.equals(HttpStatus.OK)), httpStatus);
    }


}
