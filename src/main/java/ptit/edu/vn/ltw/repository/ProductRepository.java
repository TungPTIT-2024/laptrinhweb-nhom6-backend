package ptit.edu.vn.ltw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptit.edu.vn.ltw.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}
