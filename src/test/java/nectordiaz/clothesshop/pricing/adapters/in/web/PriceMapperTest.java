package nectordiaz.clothesshop.pricing.adapters.in.web;

import static org.junit.jupiter.api.Assertions.*;

import nectordiaz.clothesshop.pricing.adapters.in.web.dto.PriceResponseDto;
import nectordiaz.clothesshop.pricing.domain.Price;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class PriceMapperTest {

  private final PriceMapper mapper = Mappers.getMapper(PriceMapper.class);

  @Test
  void toResponse_mapsAllFields_withoutUsingSetField() {
    Price price = Instancio.create(Price.class);

    PriceResponseDto dto = mapper.toResponse(price);

    assertNotNull(dto);
    assertEquals(price.getBrandId(), dto.getBrandId());
    assertEquals(price.getPriceList(), dto.getPriceList());
    assertEquals(price.getProductId(), dto.getProductId());
    assertEquals(0, price.getPrice().compareTo(dto.getPrice()));
    assertEquals(price.getCurr(), dto.getCurrency());
    assertEquals(price.getStartDate(), dto.getStartDate());
    assertEquals(price.getEndDate(), dto.getEndDate());
  }

  @Test
  void toDomain_null_returnsNull() {
    assertNull(mapper.toResponse(null));
  }
}
