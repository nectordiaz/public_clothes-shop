package nectordiaz.clothesshop.pricing.adapters.out.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nectordiaz.clothesshop.pricing.application.port.out.PricePort;
import nectordiaz.clothesshop.pricing.domain.Price;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PricePersistenceAdapter implements PricePort {

  private final PriceJpaRepository repository;
  private final PriceEntityMapper mapper;

  @Override
  public List<Price> findPrices(Long productId, Long brandId, LocalDateTime applicationDate) {
    return repository.findApplicablePrices(productId, brandId, applicationDate).stream()
        .map(mapper::toDomain)
        .collect(Collectors.toList());
  }
}
