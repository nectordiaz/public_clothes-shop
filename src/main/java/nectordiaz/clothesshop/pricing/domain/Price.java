package nectordiaz.clothesshop.pricing.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {
  Long brandId;
  Long productId;
  Integer priceList;
  LocalDateTime startDate;
  LocalDateTime endDate;
  Integer priority;
  BigDecimal price;
  String curr;
}
