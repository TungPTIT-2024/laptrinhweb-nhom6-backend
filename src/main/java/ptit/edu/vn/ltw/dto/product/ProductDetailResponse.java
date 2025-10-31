package ptit.edu.vn.ltw.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Accessors(chain=true)
public class ProductDetailResponse {
    private String id;
    private String name;
    private BigDecimal price;
    private String image;
    private String description;
    private String sku;
    private BigDecimal discountPercentage;
    private String discountDescription;
}
