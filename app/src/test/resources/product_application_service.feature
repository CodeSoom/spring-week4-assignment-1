Feature: ProductApplicationService
  Product를 관리하는 서비스

  Scenario: 모든 product를 불러오지만 생성된 product가 존재하지 않는다.
    Given product를 0개 생성했을 때
    When 모든 product를 가져오는 경우
    Then 0개의 product를 얻어올 수 있다

  Scenario: 생성된 모든 product를 불러온다.
    Given product를 1개 생성했을 때
    When 모든 product를 가져오는 경우
    Then 1개의 product를 얻어올 수 있다
    And 생성된 product들이 포함되어있다
