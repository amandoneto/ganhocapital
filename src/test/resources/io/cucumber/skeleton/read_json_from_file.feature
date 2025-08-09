Feature: Read json from file
  Via prompt, user will provide the full path
  and file name.

  Scenario: Read a single list from file
    Given Read list from "src/test/resources/files/single_list.txt"
    When Transform data into list of transaction
    Then Should have a single transaction list

  Scenario: Read a multi list from file
    Given Read list from "src/test/resources/files/multi_list.txt"
    When Transform data into list of transaction
    Then Should have multi transaction list