package nectordiaz.clothesshop.pricing.adapters.out.persistence;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PRICES")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PriceEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "BRAND_ID")
  private Long brandId;

  @Column(name = "PRODUCT_ID")
  private Long productId;

  @Column(name = "PRICE_LIST")
  private Integer priceList;

  @Column(name = "START_DATE")
  private LocalDateTime startDate;

  @Column(name = "END_DATE")
  private LocalDateTime endDate;

  @Column(name = "PRIORITY")
  private Integer priority;

  @Column(name = "PRICE")
  private BigDecimal price;

  @Column(name = "CURR")
  private String curr;
}
