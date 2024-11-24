package pro.sky.bank_star.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.bank_star.model.ProductData;


public interface RecommendationsRepository extends JpaRepository<ProductData, Long> {

}
