package ptit.edu.vn.ltw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ptit.edu.vn.ltw.dto.checkout.CheckoutItemRequest;
import ptit.edu.vn.ltw.dto.checkout.CheckoutRequest;
import ptit.edu.vn.ltw.dto.common.GenericResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CheckoutController {
    @PostMapping("/api/v1/checkout")
    public ResponseEntity<GenericResponse> applyDiscount(@Valid @RequestBody CheckoutRequest request) {
        StringBuilder itemList = new StringBuilder();
        for (CheckoutItemRequest item : request.getListItem()) {
            itemList.append("Item: ");
            itemList.append(item.getProductId()).append(", ");
        }
        log.info("List of items to checkout: {}", itemList);
        GenericResponse response = new GenericResponse().setHttpStatus(200).setMessage("Success");
        return ResponseEntity.ok(response);
    }

}
