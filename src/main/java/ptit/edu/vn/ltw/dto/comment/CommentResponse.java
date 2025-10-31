package ptit.edu.vn.ltw.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

import static ptit.edu.vn.ltw.utility.TimeUtility.TIME_FORMAT;
import static ptit.edu.vn.ltw.utility.TimeUtility.ZONE_GMT7;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Accessors(chain=true)
public class CommentResponse {
    private String userId;
    private String userName;
    private String profileImage;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_FORMAT, timezone = ZONE_GMT7)
    private Instant createdAt;
}
