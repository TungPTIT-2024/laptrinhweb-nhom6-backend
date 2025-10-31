package ptit.edu.vn.ltw.dto.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.Instant;

import static ptit.edu.vn.ltw.utility.TimeUtility.TIME_FORMAT;
import static ptit.edu.vn.ltw.utility.TimeUtility.ZONE_GMT7;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_FORMAT, timezone = ZONE_GMT7)
    private Instant createdAt;

}
