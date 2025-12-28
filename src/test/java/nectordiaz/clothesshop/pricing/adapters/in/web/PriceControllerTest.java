package nectordiaz.clothesshop.pricing.adapters.in.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import nectordiaz.clothesshop.pricing.adapters.in.web.dto.PriceResponseDto;
import nectordiaz.clothesshop.pricing.domain.Price;
import nectordiaz.clothesshop.pricing.domain.port.in.GetPriceUseCase;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class PriceControllerTest {

  @Mock private GetPriceUseCase getPriceUseCase;

  @Mock private PriceMapper priceMapper;

  @InjectMocks private PriceController controller;

  @Test
  void shouldReturnOkWhenPriceFound() {
    Long productId = Instancio.create(Long.class);
    Long brandId = Instancio.create(Long.class);
    LocalDateTime applicationDate = Instancio.create(LocalDateTime.class);

    Price domainPrice = Instancio.create(Price.class);
    PriceResponseDto dto = Instancio.create(PriceResponseDto.class);

    when(getPriceUseCase.getPrice(productId, brandId, applicationDate)).thenReturn(domainPrice);
    when(priceMapper.toResponse(domainPrice)).thenReturn(dto);

    ResponseEntity<?> response = controller.getPrice(productId, brandId, applicationDate);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertSame(dto, response.getBody());
    verify(getPriceUseCase).getPrice(productId, brandId, applicationDate);
    verify(priceMapper).toResponse(domainPrice);
  }
}
