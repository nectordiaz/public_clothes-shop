package nectordiaz.clothesshop.pricing.e2e;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = nectordiaz.clothesshop.pricing.PricesServiceApplication.class)
@AutoConfigureMockMvc
public class CucumberSpringConfiguration {}
