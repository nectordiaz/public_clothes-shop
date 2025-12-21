package nectordiaz.clothesshop.pricing.adapters.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
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

  @Mock
  private PriceJpaRepository repository;

  @Mock
  private PriceEntityMapper mapper;

  @InjectMocks
  private PricePersistenceAdapter adapter;

  private LocalDateTime applicationDate;

  @BeforeEach
  void setUp() {
    applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
  }

  @Test
  void shouldReturnMappedPrices_whenRepositoryReturnsEntities() {
    // given
    Long productId = 35455L;
    Long brandId = 1L;

    PriceEntity entity1 = Instancio.create(PriceEntity.class);
    PriceEntity entity2 = Instancio.create(PriceEntity.class);

    Price price1 = Instancio.create(Price.class);
    Price price2 = Instancio.create(Price.class);

    when(repository.findApplicablePrices(productId, brandId, applicationDate))
        .thenReturn(List.of(entity1, entity2));

    when(mapper.toDomain(entity1)).thenReturn(price1);
    when(mapper.toDomain(entity2)).thenReturn(price2);

    // when
    List<Price> result = adapter.findPrices(productId, brandId, applicationDate);

    // then
    assertEquals(2, result.size());
    assertEquals(List.of(price1, price2), result);

    verify(repository).findApplicablePrices(productId, brandId, applicationDate);
    verify(mapper).toDomain(entity1);
    verify(mapper).toDomain(entity2);
    verifyNoMoreInteractions(repository, mapper);
  }

  @Test
  void shouldReturnEmptyList_whenRepositoryReturnsEmptyList() {
    // given
    Long productId = 35455L;
    Long brandId = 1L;

    when(repository.findApplicablePrices(productId, brandId, applicationDate))
        .thenReturn(List.of());

    // when
    List<Price> result = adapter.findPrices(productId, brandId, applicationDate);

    // then
    assertEquals(0, result.size());

    verify(repository).findApplicablePrices(productId, brandId, applicationDate);
    verifyNoInteractions(mapper);
  }
}