package nectordiaz.clothesshop.pricing.domain.port.in;

import java.time.LocalDateTime;
import nectordiaz.clothesshop.pricing.domain.Price;

public interface GetPriceUseCase {
  Price getPrice(Long productId, Long brandId, LocalDateTime applicationDate);
}
