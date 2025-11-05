package ptit.edu.vn.ltw.dto.checkout;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class CheckoutItemRequest {
    private String productId;
    private Integer quantity;
}
