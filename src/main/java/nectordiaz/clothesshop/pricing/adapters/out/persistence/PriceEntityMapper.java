package nectordiaz.clothesshop.pricing.adapters.out.persistence;

import nectordiaz.clothesshop.pricing.domain.Price;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceEntityMapper {

  Price toDomain(PriceEntity e);
}
