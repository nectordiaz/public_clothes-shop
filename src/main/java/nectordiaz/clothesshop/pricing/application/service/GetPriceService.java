package nectordiaz.clothesshop.pricing.application.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import nectordiaz.clothesshop.pricing.application.port.in.GetPriceUseCase;
import nectordiaz.clothesshop.pricing.application.port.out.PricePort;
import nectordiaz.clothesshop.pricing.domain.Price;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPriceService implements GetPriceUseCase {

  private final PricePort pricePort;

  @Override
  public Optional<Price> getPrice(Long productId, Long brandId, LocalDateTime applicationDate) {
    List<Price> prices = pricePort.findPrices(productId, brandId, applicationDate);
    if (prices.isEmpty()) {
      return Optional.empty();
    }

    // Aquí tengo un doble dilema sobre dónde poner esta lógica,
    // en primer lugar, si esto forma parte de negocio del caso de uso
    // (debería quedarse aquí, o si por el contrario es algo técnico y común en todas las tablas,
    // entonces debería de ir al adapter o incluso a la SQL).
    // Por otro lado, si fuese responsabilidad del adapter/SQL,
    // tengo también la duda de dejarlo que lo gestionara la App de Spring VS la query de BD.
    // Al final es una comparación u ordenación que requiere de poder de cómputo,
    // quizá que lo dejaría en la app de Spring porque es más fácilmente escalable horizontalmente
    // que la BD.
    return prices.stream().max(Comparator.comparing(Price::getPriority));
  }
}
