package nectordiaz.clothesshop.pricing.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;
import nectordiaz.clothesshop.pricing.adapters.in.web.exception.NotFoundException;
import nectordiaz.clothesshop.pricing.domain.Price;
import nectordiaz.clothesshop.pricing.domain.port.out.PricePort;
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
  void shouldThrowNotFoundException_whenNoPricesFound() {
    // given
    Long productId = 35455L;
    Long brandId = 1L;

    // when
    when(pricePort.findPrice(productId, brandId, applicationDate)).thenReturn(Optional.empty());

    // then
    NotFoundException exception =
        assertThrows(
            NotFoundException.class,
            () -> getPriceService.getPrice(productId, brandId, applicationDate));

    assertTrue(
        exception
            .getMessage()
            .contains(
                String.format(
                    "No price found for productId=%d, brandId=%d, applicationDate=%s",
                    productId, brandId, applicationDate)));

    verify(pricePort).findPrice(productId, brandId, applicationDate);
    verifyNoMoreInteractions(pricePort);
  }

  @Test
  void shouldReturnSinglePrice_whenOnlyOnePriceFound() {
    // given
    Long productId = 35455L;
    Long brandId = 1L;

    Price singlePrice = Instancio.create(Price.class);
    singlePrice.setPriority(5);

    when(pricePort.findPrice(productId, brandId, applicationDate))
        .thenReturn(Optional.of(singlePrice));

    // when
    Price result = getPriceService.getPrice(productId, brandId, applicationDate);

    // then
    assertNotNull(result);
    assertEquals(5, result.getPriority());
    verify(pricePort).findPrice(productId, brandId, applicationDate);
    verifyNoMoreInteractions(pricePort);
  }
}
