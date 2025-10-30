package ptit.edu.vn.ltw.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetail implements Serializable {
    private String field;
    private String issue;
}
