package nectordiaz.clothesshop.pricing.domain.port.out;

import java.time.LocalDateTime;
import java.util.Optional;
import nectordiaz.clothesshop.pricing.domain.Price;

public interface PricePort {

  Optional<Price> findPrice(Long productId, Long brandId, LocalDateTime applicationDate);
}
