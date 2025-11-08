package ptit.edu.vn.ltw.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ptit.edu.vn.ltw.entity.Product;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("""
        SELECT p FROM Product p
        WHERE (:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
          AND (:minPrice IS NULL OR p.price >= :minPrice)
          AND (:maxPrice IS NULL OR p.price <= :maxPrice)
    """)
    Page<Product> searchProducts(
            @Param("keyword") String keyword,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable
    );
}
