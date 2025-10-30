package ptit.edu.vn.ltw.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

public class HttpStatusException extends ResponseStatusException{
    @Getter
    private final transient List<ErrorDetail> errorDetails;

    protected HttpStatusException(HttpStatusCode status, String reason, List<ErrorDetail> errorDetails) {
        super(status, reason);
        this.errorDetails = errorDetails;
    }

    public static HttpStatusException badRequest(String message, List<ErrorDetail> errorDetails) {
        return new HttpStatusException(HttpStatus.BAD_REQUEST, message, errorDetails);
    }

    public static HttpStatusException forbidden(String message, List<ErrorDetail> errorDetails) {
        return new HttpStatusException(HttpStatus.FORBIDDEN, message, errorDetails);
    }

    public static HttpStatusException notFound(String message, List<ErrorDetail> errorDetails) {
        return new HttpStatusException(HttpStatus.NOT_FOUND, message, errorDetails);
    }

    public static HttpStatusException internalServerError(String message) {
        return new HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR, message, Collections.emptyList());
    }

    public static HttpStatusException badGateway(String message) {
        return new HttpStatusException(HttpStatus.BAD_GATEWAY, message, Collections.emptyList());
    }

    public static HttpStatusException custom(HttpStatus status, String message, List<ErrorDetail> errorDetails) {
        return new HttpStatusException(status, message, errorDetails);
    }

}
