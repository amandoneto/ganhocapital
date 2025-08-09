Feature: Read json from console
  User will provide the stock json via command line
  adding one json per line.

  Scenario: Parsing one single stock
    Given Read list from console:
    """
    [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
    {"operation":"sell", "unit-cost":20.00, "quantity": 5000}]
    """
    When Parsing the transaction
    Then we should have a single transaction list

  Scenario: Parsing multi list stock
    Given Read list from console:
    """
    [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
    {"operation":"sell", "unit-cost":20.00, "quantity": 5000}]
    [{"operation":"buy", "unit-cost":20.00, "quantity": 10000},
    {"operation":"sell", "unit-cost":10.00, "quantity": 5000}]
    """
    When Parsing the transaction
    Then we should have a several transaction list