package nectordiaz.clothesshop.pricing.adapters.out.persistence;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import nectordiaz.clothesshop.pricing.domain.Price;
import nectordiaz.clothesshop.pricing.domain.port.out.PricePort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PricePersistenceAdapter implements PricePort {

  private final PriceJpaRepository repository;
  private final PriceEntityMapper mapper;

  @Override
  public Optional<Price> findPrice(Long productId, Long brandId, LocalDateTime applicationDate) {
    return repository
        .findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDescPriceListDesc(
            productId, brandId, applicationDate, applicationDate)
        .map(mapper::toDomain);
  }
}
