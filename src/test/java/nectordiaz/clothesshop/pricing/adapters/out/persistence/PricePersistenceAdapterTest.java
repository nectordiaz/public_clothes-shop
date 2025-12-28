package nectordiaz.clothesshop.pricing.adapters.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;
import nectordiaz.clothesshop.pricing.domain.Price;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PricePersistenceAdapterTest {

  @Mock private PriceJpaRepository repository;

  @Mock private PriceEntityMapper mapper;

  @InjectMocks private PricePersistenceAdapter adapter;

  private LocalDateTime applicationDate;

  @BeforeEach
  void setUp() {
    applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
  }

  @Test
  void shouldReturnMappedPrices_whenRepositoryReturnsEntity() {
    // given
    Long productId = 35455L;
    Long brandId = 1L;

    PriceEntity entity = Instancio.create(PriceEntity.class);

    Optional<Price> price = Optional.of(Instancio.create(Price.class));

    when(repository
            .findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDescPriceListDesc(
                productId, brandId, applicationDate, applicationDate))
        .thenReturn(Optional.of(entity));

    when(mapper.toDomain(entity)).thenReturn(price.get());

    // when
    Optional<Price> result = adapter.findPrice(productId, brandId, applicationDate);

    // then
    assertNotNull(result);
    assertEquals(price, result);

    verify(repository)
        .findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDescPriceListDesc(
            productId, brandId, applicationDate, applicationDate);
    verify(mapper).toDomain(entity);
    verifyNoMoreInteractions(repository, mapper);
  }

  @Test
  void shouldReturnEmptyList_whenRepositoryReturnsEmptyList() {
    // given
    Long productId = 35455L;
    Long brandId = 1L;

    when(repository
            .findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDescPriceListDesc(
                productId, brandId, applicationDate, applicationDate))
        .thenReturn(Optional.empty());

    // when
    Optional<Price> result = adapter.findPrice(productId, brandId, applicationDate);

    // then
    assertTrue(result.isEmpty());

    verify(repository)
        .findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDescPriceListDesc(
            productId, brandId, applicationDate, applicationDate);
    verifyNoInteractions(mapper);
  }
}
