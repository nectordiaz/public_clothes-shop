package nectordiaz.clothesshop.pricing.application.port.out;

import java.time.LocalDateTime;
import java.util.List;
import nectordiaz.clothesshop.pricing.domain.Price;

public interface PricePort {

  List<Price> findPrices(Long productId, Long brandId, LocalDateTime applicationDate);
}
