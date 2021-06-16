Feature: Lead services
  Background:
    * configure driver = { type: 'chrome', showDriverLog: false }

  Scenario: testing the CRUD of Lead service
    Given url 'https://staging-crm.sabye-songkran.com'
    Given driver 'https://staging-crm.sabye-songkran.com/'
    And waitFor('#identifier').input('dung10@gmail.com')
    And waitFor('#password').input('Dungnguyen@')
    And click('button > span.MuiButton-label')
    And waitFor('div.MuiPaper-root > div')
    * cookies driver.cookies
    And path '/api/lead/v1alpha2/sources'
    And request read('SourceInfo.json')
    And method post
    Then status 200