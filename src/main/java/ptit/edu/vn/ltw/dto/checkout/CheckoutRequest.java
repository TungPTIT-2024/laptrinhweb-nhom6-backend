package ptit.edu.vn.ltw.dto.checkout;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain=true)
public class CheckoutRequest {
    private List<CheckoutItemRequest> listItem;
}
