package ptit.edu.vn.ltw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ptit.edu.vn.ltw.entity.Discount;

import java.time.Instant;
import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, String> {
    @Query("""
        SELECT d
        FROM Discount d
        WHERE d.productId IN :productIds
          AND d.startDate <= :now
          AND (d.endDate IS NULL OR d.endDate >= :now)
    """)
    List<Discount> findActiveByProductIds(@Param("productIds") List<String> productIds,
                                          @Param("now") Instant now);

    @Query("""
        SELECT d
        FROM Discount d
        WHERE d.productId = :productId
          AND d.startDate <= :now
          AND (d.endDate IS NULL OR d.endDate >= :now)
    """)
    List<Discount> findActiveDiscountByProductId(@Param("productId") String productId, @Param("now") Instant now);
}
