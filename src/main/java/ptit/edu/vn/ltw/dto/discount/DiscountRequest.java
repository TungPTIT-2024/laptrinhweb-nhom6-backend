package ptit.edu.vn.ltw.dto.discount;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Accessors(chain=true)
public class DiscountRequest {
    @NotBlank(message = "productId is required")
    @Size(max = 36, message = "productId must be at most 36 characters")
    private String productId;

    @Size(max = 255, message = "description must be at most 255 characters")
    private String description;

    @NotNull(message = "percentage is required")
    @DecimalMin(value = "0.00", message = "percentage must be >= 0")
    @DecimalMax(value = "100.00", message = "percentage must be <= 100")
    private BigDecimal percentage;

    private Instant startDate;

    private Instant endDate;
}
