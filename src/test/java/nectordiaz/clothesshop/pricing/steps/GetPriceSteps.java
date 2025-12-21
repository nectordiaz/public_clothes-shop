package nectordiaz.clothesshop.pricing.steps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

public class GetPriceSteps {

  @Autowired
  private MockMvc mockMvc;

  private Long productId;
  private Long brandId;
  private LocalDateTime applicationDate;
  private MvcResult mvcResult;

  @Given("the product id is {long}")
  public void the_product_id_is(Long productId) {
    this.productId = productId;
  }

  @And("the brand id is {long}")
  public void the_brand_id_is(Long brandId) {
    this.brandId = brandId;
  }

  @And("the application date is {string}")
  public void the_application_date_is(String date) {
    this.applicationDate = LocalDateTime.parse(date);
  }

  @When("the client requests the applicable price")
  public void the_client_requests_the_applicable_price() throws Exception {
    mvcResult =
        mockMvc
            .perform(
                get("/api/v1/prices")
                    .param("productId", productId.toString())
                    .param("brandId", brandId.toString())
                    .param("applicationDate", applicationDate.toString())
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
  }

  @Then("the response status should be {int}")
  public void the_response_status_should_be(Integer status) throws Exception {
    mockMvc
        .perform(
            get("/api/v1/prices")
                .param("productId", productId.toString())
                .param("brandId", brandId.toString())
                .param("applicationDate", applicationDate.toString()))
        .andExpect(status().is(status));
  }

  @And("the response price should be {double}")
  public void the_response_price_should_be(Double price) throws Exception {
    String response = mvcResult.getResponse().getContentAsString();
    Double responsePrice = JsonPath.read(response, "$.price");
    org.junit.jupiter.api.Assertions.assertEquals(price, responsePrice);
  }

  @And("the response price list should be {int}")
  public void the_response_price_list_should_be(Integer priceList) throws Exception {
    String response = mvcResult.getResponse().getContentAsString();
    Integer responsePriceList = JsonPath.read(response, "$.priceList");
    org.junit.jupiter.api.Assertions.assertEquals(priceList, responsePriceList);
  }
}
