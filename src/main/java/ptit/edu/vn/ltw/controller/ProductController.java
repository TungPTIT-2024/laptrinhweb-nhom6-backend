package ptit.edu.vn.ltw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {
    @GetMapping("/api/v1/products")
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.ok("List of products");
    }

    @GetMapping("/api/v1/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        return ResponseEntity.ok("Product details for ID: " + id);
    }

    @PostMapping("/internal/api/v1/product")
    public ResponseEntity<?> createProduct() {
        return ResponseEntity.ok("Product created");
    }

    @PatchMapping("/internal/api/v1/product/{id}")
    public ResponseEntity<?> patchProduct(@PathVariable String id) {
        return ResponseEntity.ok("Product created");
    }

    @DeleteMapping("/internal/api/v1/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        return ResponseEntity.ok("Product created");
    }

}
