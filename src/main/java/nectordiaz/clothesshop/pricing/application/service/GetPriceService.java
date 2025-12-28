package nectordiaz.clothesshop.pricing.application.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import nectordiaz.clothesshop.pricing.adapters.in.web.exception.NotFoundException;
import nectordiaz.clothesshop.pricing.domain.Price;
import nectordiaz.clothesshop.pricing.domain.port.in.GetPriceUseCase;
import nectordiaz.clothesshop.pricing.domain.port.out.PricePort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPriceService implements GetPriceUseCase {

  private final PricePort pricePort;

  @Override
  public Price getPrice(Long productId, Long brandId, LocalDateTime applicationDate) {

    return pricePort
        .findPrice(productId, brandId, applicationDate)
        .orElseThrow(
            () ->
                new NotFoundException(
                    String.format(
                        "No price found for productId=%d, brandId=%d, applicationDate=%s",
                        productId, brandId, applicationDate)));
  }
}
