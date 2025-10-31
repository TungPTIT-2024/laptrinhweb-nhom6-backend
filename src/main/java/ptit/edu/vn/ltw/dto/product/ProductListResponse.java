package ptit.edu.vn.ltw.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Accessors(chain=true)
public class ProductListResponse {
    private List<ProductDetailResponse> productList;
    private int page;
    private int size;
    private int totalPage;
}
