package nectordiaz.clothesshop.pricing;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

// Suelo meter más cobertura pero por simplificar he metido solo lo que pedía el ejercicio

@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerTest {

  @Autowired MockMvc mockMvc;

  @Test
  void test1_2020_06_14_10_00_should_return_price_35_50() throws Exception {
    mockMvc
        .perform(
            get("/api/v1/prices")
                .param("productId", "35455")
                .param("brandId", "1")
                .param("applicationDate", "2020-06-14T10:00:00"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.price").value(35.50))
        .andExpect(jsonPath("$.priceList").value(1));
  }

  @Test
  void test2_2020_06_14_16_00_should_return_price_25_45() throws Exception {
    mockMvc
        .perform(
            get("/api/v1/prices")
                .param("productId", "35455")
                .param("brandId", "1")
                .param("applicationDate", "2020-06-14T16:00:00"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.price").value(25.45))
        .andExpect(jsonPath("$.priceList").value(2));
  }

  @Test
  void test3_2020_06_14_21_00_should_return_price_35_50() throws Exception {
    mockMvc
        .perform(
            get("/api/v1/prices")
                .param("productId", "35455")
                .param("brandId", "1")
                .param("applicationDate", "2020-06-14T21:00:00"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.price").value(35.50))
        .andExpect(jsonPath("$.priceList").value(1));
  }

  @Test
  void test4_2020_06_15_10_00_should_return_price_30_50() throws Exception {
    mockMvc
        .perform(
            get("/api/v1/prices")
                .param("productId", "35455")
                .param("brandId", "1")
                .param("applicationDate", "2020-06-15T10:00:00"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.price").value(30.50))
        .andExpect(jsonPath("$.priceList").value(3));
  }

  @Test
  void test5_2020_06_16_21_00_should_return_price_38_95() throws Exception {
    mockMvc
        .perform(
            get("/api/v1/prices")
                .param("productId", "35455")
                .param("brandId", "1")
                .param("applicationDate", "2020-06-16T21:00:00"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.price").value(38.95))
        .andExpect(jsonPath("$.priceList").value(4));
  }
}
