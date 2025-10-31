package ptit.edu.vn.ltw.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ptit.edu.vn.ltw.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    @Query("""
        SELECT c, u.username, u.profileImage
        FROM Comment c
        LEFT JOIN UserInfo u ON u.id = c.userId
        WHERE c.productId = :productId
        ORDER BY c.createdAt DESC
        """)
    Page<Object[]> findCommentsByProductId(String productId, Pageable pageable);


}
