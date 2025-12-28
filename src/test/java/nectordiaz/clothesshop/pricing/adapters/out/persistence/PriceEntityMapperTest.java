package nectordiaz.clothesshop.pricing.adapters.out.persistence;

import static org.junit.jupiter.api.Assertions.*;

import nectordiaz.clothesshop.pricing.domain.Price;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class PriceEntityMapperTest {

  private final PriceEntityMapper mapper = Mappers.getMapper(PriceEntityMapper.class);

  @Test
  void toDomain_mapsAllFields() {

    PriceEntity entity = Instancio.of(PriceEntity.class).create();

    Price domain = mapper.toDomain(entity);

    assertNotNull(domain);
    assertEquals(entity.getBrandId(), domain.getBrandId());
    assertEquals(entity.getPriceList(), domain.getPriceList());
    assertEquals(entity.getProductId(), domain.getProductId());
    assertEquals(entity.getPriority(), domain.getPriority());
    assertEquals(entity.getPrice(), domain.getPrice());
    assertEquals(entity.getCurr(), domain.getCurr());
    assertEquals(entity.getStartDate(), domain.getStartDate());
    assertEquals(entity.getEndDate(), domain.getEndDate());
  }

  @Test
  void toDomain_null_returnsNull() {
    assertNull(mapper.toDomain(null));
  }
}
