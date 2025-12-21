package nectordiaz.clothesshop.pricing.application.port.in;

import java.time.LocalDateTime;
import java.util.Optional;
import nectordiaz.clothesshop.pricing.domain.Price;

public interface GetPriceUseCase {
  Optional<Price> getPrice(Long productId, Long brandId, LocalDateTime applicationDate);
}
