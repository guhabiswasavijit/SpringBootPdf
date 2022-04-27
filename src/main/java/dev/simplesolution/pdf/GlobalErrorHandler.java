package dev.simplesolution.pdf;

import java.time.LocalDateTime;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({OutOfMemoryError.class,PdfGenerationError.class})
    public ResponseEntity<Object> handleExceptions(OutOfMemoryError error, WebRequest webRequest) {
    	Logger logger = LoggerFactory.getLogger("Sytem-Error");
        String stacktrace = ExceptionUtils.getStackTrace(error);
        logger.error(stacktrace);
        return this.constructErrorResponse();
    }
    private ResponseEntity<Object> constructErrorResponse(){
        ErrorResponse response = new ErrorResponse();
        response.setDateTime(LocalDateTime.now());
        response.setMessage("Temporary outage");
        ResponseEntity<Object> entity = new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        return entity;
    }
}
