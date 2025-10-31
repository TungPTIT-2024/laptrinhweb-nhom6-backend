package ptit.edu.vn.ltw.dto.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Accessors(chain=true)
public class CommentListResponse {
    private List<CommentResponse> commentList;
    private int page;
    private int size;
    private int totalPage;

}
