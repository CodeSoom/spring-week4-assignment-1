Feature: ProductApplicationService
  Product를 관리하는 서비스

  Scenario: 모든 product를 불러오지만 생성된 product가 존재하지 않는다.
    Given product를 생성하지 않았을 떄
    When 모든 product를 가져오는 경우
    Then 빈 리스트가 반환된다

  Scenario: 생성된 모든 product를 불러온다.
    Given product를 1개 생성했을 때
    When 모든 product를 가져오는 경우
    Then 이미 생성된 1개의 product를 얻어올 수 있다
