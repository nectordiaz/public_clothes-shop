package nectordiaz.clothesshop.pricing.adapters.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import nectordiaz.clothesshop.pricing.adapters.in.web.dto.PriceResponseDto;
import nectordiaz.clothesshop.pricing.application.port.in.GetPriceUseCase;
import nectordiaz.clothesshop.pricing.domain.Price;
import nectordiaz.clothesshop.pricing.exception.ErrorResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/prices")
@RequiredArgsConstructor
@Validated
public class PriceController {

  private final GetPriceUseCase getPriceUseCase;
  private final PriceMapper priceMapper;

  // Lo he codificado entendiendo que lo correcto es que siempre devolvamos un único precio
  // aplicable,
  // por lo que los 3 parametros de entrada deberían ser obligatorios.
  // Aquí no es mala práctica usar clases que engloben los parámetros de entrada en vez de usar
  // @RequestParam de forma individual
  // pero por simplificar lo he dejado así.
  @GetMapping
  @Operation(
      summary = "Get applicable price",
      description = "Devuelve el precio aplicable para un producto y marca en una fecha dada",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Price found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PriceResponseDto.class),
                    examples = {
                      @ExampleObject(
                          name = "success",
                          value =
                              "{\"productId\":35455,\"brandId\":1,\"priceList\":1,\"startDate\":\"2020-06-14T00:00:00\",\"endDate\":\"2020-12-31T23:59:59\",\"price\":35.50,\"currency\":\"EUR\"}")
                    })),
        @ApiResponse(responseCode = "404", description = "No price found for given parameters"),
        @ApiResponse(
            responseCode = "default",
            description = "Unexpected error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  public ResponseEntity<?> getPrice(
      @RequestParam @NotNull Long productId,
      @RequestParam @NotNull Long brandId,
      @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime applicationDate) {
    Optional<Price> res = getPriceUseCase.getPrice(productId, brandId, applicationDate);
    if (res.isEmpty()) {
      return ResponseEntity.status(404)
          .body(new ErrorResponse("No price found for given parameters"));
    }
    Price p = res.get();
    PriceResponseDto response = priceMapper.toResponse(p);
    return ResponseEntity.ok(response);
  }
}
