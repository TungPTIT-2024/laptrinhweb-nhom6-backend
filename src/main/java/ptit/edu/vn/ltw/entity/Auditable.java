package ptit.edu.vn.ltw.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.EntityListeners;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class Auditable {

    public static final String CREATED_AT_COL = "createdAt";
    public static final String MODIFIED_AT_COL = "modifiedAt";

    @CreatedDate
    @Column(name = CREATED_AT_COL, nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = MODIFIED_AT_COL)
    private Instant modifiedAt;
}

