Feature: ProductApplicationService
  Scenario: 모든 product를 불러오지만 생성된 product가 존재하지 않는다.
    Given product를 0개 생성했을 때
    When 모든 product를 가져오는 경우
    Then 0개의 product를 얻어올 수 있다

  Scenario: 생성된 모든 product를 불러온다.
    Given product를 1개 생성했을 때
    When 모든 product를 가져오는 경우
    Then 1개의 product를 얻어올 수 있다
    And 생성된 product들이 포함되어있다

  Scenario: 새로운 product를 생성한다.
    Given product를 만드는데 필요한 데이터가 제공되었을 때
    When product를 생성하면
    Then 생성된 product를 찾을 수 있다
    And 생성된 product만 가져올 수 있다

  Scenario: 생성된 product를 삭제한다.
    Given product를 1개 생성했을 때
    When 생성된 product를 삭제하면
    Then 생성된 product를 찾을 수 없다

  Scenario: 생성되지 않은 product를 삭제한다.
    Given product를 0개 생성했을 때
    When 생성되지않은 product를 삭제하면 에러가 발생한다

  Scenario: 생성된 product를 변경한다.
    Given product를 1개 생성했을 때
    When 생성된 product를 변경하면
    When 변경된 product는 변경된 값을 갖고있다