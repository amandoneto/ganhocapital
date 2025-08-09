Feature: Calculate Weighted Average Cost
  @wip
  Scenario: Calculate Weighted Average Cost for a single buy
    Given the following data
      | totalQuantity | quantity | unitCost | weightedAverageCost | expectedResult |
      | 100           | 50       | 12.00    | 0.00                | 4.00           |
      | 100           | 50       | 12.00    | 10.00               | 10.67          |
      | 10000         | 5000     | 25.00    | 10.00               | 15.00          |
    When average cost is calculated
    Then Average cost should return a value
