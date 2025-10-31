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
public class CommentController {

    @GetMapping("/api/v1/products/{id}/comments")
    public ResponseEntity<?> getProductComments(@PathVariable(value = "id") String productId) {
        return ResponseEntity.ok("List of products");
    }

    @PostMapping("/api/v1/products/{id}/comment")
    public ResponseEntity<?> postProductComment(@PathVariable(value = "id") String productId) {
        return ResponseEntity.ok("List of products");
    }

    @PatchMapping("/api/v1/comments/{id} ")
    public ResponseEntity<?> editComment(@PathVariable(value = "id") String commentId) {
        return ResponseEntity.ok("List of products");
    }

    @DeleteMapping("/api/v1/comments/{id} ")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "id") String commentId) {
        return ResponseEntity.ok("List of products");
    }
}
