package ptit.edu.vn.ltw.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = Comment.TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Comment extends Auditable {

    public static final String TABLE_NAME = "comment";
    public static final String ID_COL = "id";
    public static final String PRODUCT_ID_COL = "product_id";
    public static final String USER_ID_COL = "user_id";
    public static final String CONTENT_COL = "content";


    @Id
    @UuidGenerator
    @Column(name = ID_COL, nullable = false, updatable = false, length = 36)
    private String commentId;

    @Column(name = PRODUCT_ID_COL, length = 36)
    private String productId;

    @Column(name = USER_ID_COL, length = 36)
    private String userId;

    @Column(name = CONTENT_COL, length = 2000)
    private String content;
}
