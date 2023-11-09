Feature: Project
  Scenario: As a user I would like to create user and crud project
    Given send POST request "/api/user.json" with data
    When get token
    Then response code should be 200
    When send POST request "/api/projects.json" with body
    """
    {
      "Content": "sergio",
      "Icon": 5
    }
    """
    Then response code should be 200
    And the attribute "Content" should be "sergio"
    And save "Id" in the variable "$ID_PROJECT"
    When send PUT request "/api/projects/$ID_PROJECT.json" with body
    """
    {
      "Content": "sergioupdate"
    }
    """
    Then response code should be 200
    And the attribute "Content" should be "sergioupdate"
    When send DELETE request "/api/projects/$ID_PROJECT.json" with body
    """
    """
    Then response code should be 200
    And the attribute "Content" should be "sergioupdate"