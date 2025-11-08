package ptit.edu.vn.ltw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ptit.edu.vn.ltw.dto.common.GenericResponse;
import ptit.edu.vn.ltw.dto.product.ProductRequest;
import ptit.edu.vn.ltw.dto.product.ProductDetailResponse;
import ptit.edu.vn.ltw.dto.product.ProductListResponse;
import ptit.edu.vn.ltw.service.ProductService;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/api/v1/products")
    public ResponseEntity<ProductListResponse> getProducts(@RequestParam(defaultValue = "1") Integer page,
                                                           @RequestParam(defaultValue = "5") Integer size,
                                                           @RequestParam(required = false) String keyword,
                                                           @RequestParam(required = false) Integer minPrice,
                                                           @RequestParam(required = false) Integer maxPrice) {
        if (page < 1) page = 1;
        if (size < 1) size = 5;
        ProductListResponse response = productService.getProducts(page, size, keyword, minPrice, maxPrice);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/products/{id}")
    public ResponseEntity<ProductDetailResponse> getProductById(@PathVariable String id) {
        ProductDetailResponse response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/internal/api/v1/products")
    public ResponseEntity<ProductDetailResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductDetailResponse response = productService.createProduct(productRequest);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/internal/api/v1/products/{id}")
    public ResponseEntity<ProductDetailResponse> patchProduct(@PathVariable String id, @Valid @RequestBody ProductRequest productRequest) {
        ProductDetailResponse response = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/internal/api/v1/products/{id}")
    public ResponseEntity<GenericResponse> deleteProduct(@PathVariable String id) {
        GenericResponse response = productService.deleteProductById(id);
        return ResponseEntity.ok(response);
    }

}
