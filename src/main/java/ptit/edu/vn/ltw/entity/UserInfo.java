package ptit.edu.vn.ltw.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = UserInfo.USER_INFO_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserInfo extends Auditable{
    public static final String USER_INFO_TABLE = "user_info";
    public static final String ID_COL = "id";
    public static final String USERNAME_COL = "username";
    public static final String PASSWORD_COL = "password";
    public static final String FULL_NAME_COL = "full_name";

    @Id
    @UuidGenerator
    @Column(name = ID_COL, updatable = false, nullable = false, length = 36)
    private String id;

    @Column(name = USERNAME_COL, length = 200)
    private String username;

    @Column(name = PASSWORD_COL, length = 2000)
    private String password;

    @Column(name = FULL_NAME_COL, length = 255)
    private String fullName;
}
