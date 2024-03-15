Feature: Trainer CRUD operations

  Scenario: Create a trainer
    When The Client Saves trainer using url "/api/v2/trainer/save"
    Then The Client for saving trainer receives a response status 200