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

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = Discount.DISCOUNT_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Discount {

    public static final String DISCOUNT_TABLE = "discount";
    public static final String ID_COL = "id";
    public static final String PRODUCT_ID_COL = "product_id";
    public static final String DESCRIPTION_COL = "description";
    public static final String PERCENTAGE_COL = "percentage";
    public static final String START_DATE_COL = "start_date";
    public static final String END_DATE_COL = "end_date";

    @Id
    @UuidGenerator
    @Column(name = ID_COL, nullable = false, updatable = false, length = 36)
    private String id;

    @Column(name = PRODUCT_ID_COL, length = 36)
    private String productId;

    @Column(name = DESCRIPTION_COL, length = 255)
    private String description;

    @Column(name = PERCENTAGE_COL, precision = 5, scale = 2)
    private BigDecimal percentage;

    @Column(name = START_DATE_COL)
    private Instant startDate;

    @Column(name = END_DATE_COL)
    private Instant endDate;
}
