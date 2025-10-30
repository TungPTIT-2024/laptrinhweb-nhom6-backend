package ptit.edu.vn.ltw.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse{
    private Integer httpStatus;
    private String message;
    private List<ErrorDetail> details;
}

