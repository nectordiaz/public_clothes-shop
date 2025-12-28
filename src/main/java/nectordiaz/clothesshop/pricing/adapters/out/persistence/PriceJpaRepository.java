package nectordiaz.clothesshop.pricing.adapters.out.persistence;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {

  Optional<PriceEntity> findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDescPriceListDesc(
      Long productId,
      Long brandId,
      LocalDateTime applicationDate1,
      LocalDateTime applicationDate2
  );
}
