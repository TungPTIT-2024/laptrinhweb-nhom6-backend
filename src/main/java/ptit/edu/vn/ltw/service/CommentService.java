package ptit.edu.vn.ltw.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ptit.edu.vn.ltw.dto.comment.CommentListResponse;
import ptit.edu.vn.ltw.dto.comment.CommentRequest;
import ptit.edu.vn.ltw.dto.comment.CommentResponse;
import ptit.edu.vn.ltw.dto.common.GenericResponse;
import ptit.edu.vn.ltw.entity.Comment;
import ptit.edu.vn.ltw.exception.ErrorDetail;
import ptit.edu.vn.ltw.exception.HttpStatusException;
import ptit.edu.vn.ltw.repository.CommentRepository;
import ptit.edu.vn.ltw.utility.ContextUtility;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ProductService productService;

    @Transactional
    public CommentListResponse getCommentsByProductId(String productId, Integer page, Integer size) {
        PageRequest pageable = PageRequest.of(page - 1, size);

        Page<Object[]> commentPage = commentRepository.findCommentsByProductId(productId, pageable);

        List<CommentResponse> commentList = commentPage.stream().map(arr -> {
            Comment comment = (Comment) arr[0];
            String username = arr.length > 1 ? (String) arr[1] : null;
            String profileImage = arr.length > 2 ? (String) arr[2] : null;

            CommentResponse detail = new CommentResponse();
            BeanUtils.copyProperties(comment, detail);
            if (Objects.nonNull(username)) detail.setUserName(username);
            if (Objects.nonNull(profileImage)) detail.setProfileImage(profileImage);
            return detail;
        }).toList();

        CommentListResponse response = new CommentListResponse();
        response.setCommentList(commentList);
        response.setPage(page);
        response.setSize(size);
        response.setTotalPage(commentPage.getTotalPages());

        return response;
    }

    public GenericResponse createCommentForProduct(String productId, @Valid CommentRequest request) {
        String userId = ContextUtility.getUserId();
        productService.checkProductExistById(productId);

        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setProductId(productId);
        comment.setContent(request.getContent());

        commentRepository.save(comment);

        return new GenericResponse().setHttpStatus(200).setMessage("Comment created successfully");
    }

    public GenericResponse updateComment(String commentId, @Valid CommentRequest request) {
        String userId = ContextUtility.getUserId();

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> {
            ErrorDetail errorDetail = new ErrorDetail().setField("comment_id").setIssue(String.format("Comment with id %s not found", commentId));
            throw HttpStatusException.badRequest("Resources not found", List.of(errorDetail));
        });

        if (!comment.getUserId().equals(userId)) {
            ErrorDetail errorDetail = new ErrorDetail().setField("user_id").setIssue("You are not authorized to update this comment");
            throw HttpStatusException.forbidden("Forbidden", List.of(errorDetail));
        }

        comment.setContent(request.getContent());
        commentRepository.save(comment);

        return new GenericResponse().setHttpStatus(200).setMessage("Comment updated successfully");
    }

    public GenericResponse deleteComment(String commentId) {
        String userId = ContextUtility.getUserId();

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> {
            ErrorDetail errorDetail = new ErrorDetail().setField("comment_id").setIssue(String.format("Comment with id %s not found", commentId));
            throw HttpStatusException.badRequest("Resources not found", List.of(errorDetail));
        });

        if (!comment.getUserId().equals(userId)) {
            ErrorDetail errorDetail = new ErrorDetail().setField("user_id").setIssue("You are not authorized to update this comment");
            throw HttpStatusException.forbidden("Forbidden", List.of(errorDetail));
        }

        commentRepository.deleteById(commentId);
        return new GenericResponse().setHttpStatus(200).setMessage("Comment updated successfully");

    }
}
