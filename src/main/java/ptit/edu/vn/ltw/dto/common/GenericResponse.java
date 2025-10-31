package ptit.edu.vn.ltw.dto.common;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class GenericResponse {
    private Integer httpStatus;
    private String message;
}
