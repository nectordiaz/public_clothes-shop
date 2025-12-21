package nectordiaz.clothesshop.pricing.adapters.in.web;

import nectordiaz.clothesshop.pricing.adapters.in.web.dto.PriceResponseDto;
import nectordiaz.clothesshop.pricing.domain.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceMapper {

  @Mapping(source = "curr", target = "currency")
  PriceResponseDto toResponse(Price p);
}
