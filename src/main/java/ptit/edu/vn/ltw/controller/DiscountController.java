package ptit.edu.vn.ltw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ptit.edu.vn.ltw.dto.common.GenericResponse;
import ptit.edu.vn.ltw.dto.discount.DiscountRequest;
import ptit.edu.vn.ltw.service.DiscountService;

@RestController
@RequiredArgsConstructor
public class DiscountController {
    private final DiscountService discountService;

    @PostMapping("/internal/product/{productId}/discount")
    public ResponseEntity<GenericResponse> applyDiscount(@PathVariable String productId, @Valid @RequestBody DiscountRequest request) {
        GenericResponse response = discountService.applyDiscountToProduct(productId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/internal/discount/{id}")
    public ResponseEntity<GenericResponse> patchDiscount(@PathVariable String id, @Valid @RequestBody DiscountRequest request) {
        GenericResponse response = discountService.updateDiscount(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/internal/discount/{id}")
    public ResponseEntity<GenericResponse> removeDiscount(@PathVariable String id) {
        GenericResponse response = discountService.removeDiscount(id);
        return ResponseEntity.ok(response);
    }
}
