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

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = Product.PRODUCT_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Product extends Auditable {

    public static final String PRODUCT_TABLE = "product";
    public static final String ID_COL = "id";
    public static final String NAME_COL = "name";
    public static final String PRICE_COL = "price";
    public static final String IMAGE_COL = "image";
    public static final String DESCRIPTION_COL = "description";
    public static final String SKU_COL = "sku";

    @Id
    @UuidGenerator
    @Column(name = ID_COL, nullable = false, updatable = false, length = 36)
    private String id;

    @Column(name = NAME_COL, length = 255)
    private String name;

    @Column(name = PRICE_COL, precision = 19, scale = 2)
    private BigDecimal price;

    @Column(name = IMAGE_COL, length = 500)
    private String image;

    @Column(name = DESCRIPTION_COL, length = 4000)
    private String description;

    @Column(name = SKU_COL, length = 255)
    private String sku;
}

