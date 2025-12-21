package nectordiaz.clothesshop.pricing.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import nectordiaz.clothesshop.pricing.application.port.out.PricePort;
import nectordiaz.clothesshop.pricing.domain.Price;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetPriceServiceTest {

  @Mock private PricePort pricePort;

  @InjectMocks private GetPriceService getPriceService;

  private LocalDateTime applicationDate;

  @BeforeEach
  void setUp() {
    applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
  }

  @Test
  void shouldReturnEmptyOptional_whenNoPricesFound() {
    // given
    Long productId = 35455L;
    Long brandId = 1L;

    when(pricePort.findPrices(productId, brandId, applicationDate)).thenReturn(List.of());

    // when
    Optional<Price> result = getPriceService.getPrice(productId, brandId, applicationDate);

    // then
    assertTrue(result.isEmpty());
    verify(pricePort).findPrices(productId, brandId, applicationDate);
    verifyNoMoreInteractions(pricePort);
  }

  @Test
  void shouldReturnPriceWithHighestPriority_whenMultiplePricesFound() {
    // given
    Long productId = 35455L;
    Long brandId = 1L;

    Price price1 = Instancio.create(Price.class);
    Price price2 = Instancio.create(Price.class);
    Price price3 = Instancio.create(Price.class);

    price1.setPriority(0);
    price2.setPriority(1);
    price3.setPriority(1); // same level of priority as price2

    when(pricePort.findPrices(productId, brandId, applicationDate))
        .thenReturn(List.of(price1, price2, price3));

    // when
    Optional<Price> result = getPriceService.getPrice(productId, brandId, applicationDate);

    // then
    assertTrue(result.isPresent());
    assertEquals(1, result.get().getPriority());
    // Podría ser price2 o price3 ya que tienen misma prioridad
    verify(pricePort).findPrices(productId, brandId, applicationDate);
    verifyNoMoreInteractions(pricePort);
  }

  @Test
  void shouldReturnSinglePrice_whenOnlyOnePriceFound() {
    // given
    Long productId = 35455L;
    Long brandId = 1L;

    Price singlePrice = Instancio.create(Price.class);
    singlePrice.setPriority(5);

    when(pricePort.findPrices(productId, brandId, applicationDate))
        .thenReturn(List.of(singlePrice));

    // when
    Optional<Price> result = getPriceService.getPrice(productId, brandId, applicationDate);

    // then
    assertTrue(result.isPresent());
    assertEquals(5, result.get().getPriority());
    verify(pricePort).findPrices(productId, brandId, applicationDate);
    verifyNoMoreInteractions(pricePort);
  }
}
