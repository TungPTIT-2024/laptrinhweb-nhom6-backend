package ptit.edu.vn.ltw.exception;

import io.jsonwebtoken.lang.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ErrorDetail> errorDetails = ex.getFieldErrors().stream()
                .map(error -> new ErrorDetail().setField(error.getField()).setIssue(error.getDefaultMessage()))
                .toList();
        ExceptionResponse exceptionResponse = new ExceptionResponse().setHttpStatus(ex.getStatusCode().value())
                .setMessage("Validation failed for one or more arguments.").setDetails(errorDetails);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);

    }

    @ExceptionHandler(HttpStatusException.class)
    public ResponseEntity<ExceptionResponse> handleResponseStatusException(HttpStatusException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse().setHttpStatus(ex.getStatusCode().value())
                .setMessage(ex.getReason());

        if (!Collections.isEmpty(ex.getErrorDetails())){
            exceptionResponse.setDetails(ex.getErrorDetails());
        }

        return ResponseEntity.status(ex.getStatusCode()).body(exceptionResponse);
    }
}
