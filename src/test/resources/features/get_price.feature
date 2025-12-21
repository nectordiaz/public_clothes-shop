Feature: Get applicable price

  Scenario Outline: Get applicable price for a product and brand at a given date
    Given the product id is <productId>
    And the brand id is <brandId>
    And the application date is "<applicationDate>"
    When the client requests the applicable price
    Then the response status should be 200
    And the response price should be <price>
    And the response price list should be <priceList>

    Examples:
      | productId | brandId | applicationDate     | price | priceList |
      | 35455     | 1       | 2020-06-14T10:00:00 | 35.50 | 1         |
      | 35455     | 1       | 2020-06-14T16:00:00 | 25.45 | 2         |
      | 35455     | 1       | 2020-06-14T21:00:00 | 35.50 | 1         |
      | 35455     | 1       | 2020-06-15T10:00:00 | 30.50 | 3         |
      | 35455     | 1       | 2020-06-16T21:00:00 | 38.95 | 4         |
