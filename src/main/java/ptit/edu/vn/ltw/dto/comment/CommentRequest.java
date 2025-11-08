package ptit.edu.vn.ltw.dto.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Accessors(chain=true)
public class CommentRequest {
    @Length(min = 1, max = 5000, message = "Comment must be between 10 and 5000 characters")
    @NotBlank(message = "Comment is required")
    private String content;

    private String productId;
    private String commentId;

}
