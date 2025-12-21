package nectordiaz.clothesshop.pricing.adapters.out.persistence;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {

  @Query(
      "select p from PriceEntity p where p.productId = :productId and p.brandId = :brandId and :applicationDate between p.startDate and p.endDate")
  List<PriceEntity> findApplicablePrices(
      @Param("productId") Long productId,
      @Param("brandId") Long brandId,
      @Param("applicationDate") LocalDateTime applicationDate);
}
