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
import ptit.edu.vn.ltw.dto.comment.CommentListResponse;
import ptit.edu.vn.ltw.dto.comment.CommentRequest;
import ptit.edu.vn.ltw.dto.common.GenericResponse;
import ptit.edu.vn.ltw.service.CommentService;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/api/v1/product/comments")
    public ResponseEntity<CommentListResponse> getProductComments(@RequestParam(value = "id") String productId,
                                                                  @RequestParam(defaultValue = "0") Integer page,
                                                                  @RequestParam(defaultValue = "5") Integer size) {
        if (page < 1) page = 1;
        if (size < 1) size = 5;
        CommentListResponse response = commentService.getCommentsByProductId(productId, page, size);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/v1/product/comment")
    public ResponseEntity<GenericResponse> postProductComment(@RequestParam(value = "id") String productId,
                                                              @Valid @RequestBody CommentRequest request) {
        GenericResponse response = commentService.createCommentForProduct(productId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/api/v1/comment")
    public ResponseEntity<GenericResponse> editComment(@RequestParam(value = "id") String commentId,
                                                       @Valid @RequestBody CommentRequest request) {
        GenericResponse response = commentService.updateComment(commentId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/v1/comment")
    public ResponseEntity<GenericResponse> deleteComment(@RequestParam(value = "id") String commentId) {
        GenericResponse response = commentService.deleteComment(commentId);
        return ResponseEntity.ok(response);
    }
}
