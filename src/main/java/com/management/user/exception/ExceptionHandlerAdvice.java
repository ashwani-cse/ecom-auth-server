package com.management.user.exception;

import com.management.user.dto.ErrorPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientResponseException;

import static com.management.user.exception.ErrorResponse.handlerException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(RestClientResponseException.class)
    public ResponseEntity<ErrorPayload> handleBadRequestException(RestClientResponseException exception) {
        return handlerException(exception.getStatusCode().value(), exception.getMessage());
    }


    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ErrorPayload> handleObjectNotFoundException(ObjectNotFoundException exception) {
        log.error("handleObjectNotFoundException: {}", exception.getMessage());
        return handlerException(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

}
