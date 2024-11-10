## 🔧 기능 구현 목록

## TODO: StoreFactoryImpl을 줄일 계획을 세우자.


>Outputview도 많은 리펙토링이 필요하다. 시험은 application에서 테스트하자.
>
> 코드 public private 순서도 보고 무엇보다 테스트를 좀 봐야대
### 서비스 
- 주문 서비스 ( 최종 주문을 만들어주기까지 해주면됌) input output 다필요하지만.
- 영수증 서비스 ( 최종 주문을 받아서 영수증을 발행해줌) 여긴 output만 있어도 괜찮을거 같다.
- 영수증 발행하고 계속 쇼핑할지 말지 물어봐

### 주문

- 주문은 만들어진 시간을 담고 있어야 한다
- 주문의 요구들을 담고 있다.
- 주문은 최소 한개의 요구라도 존재해야 한다



### 요구

- 요구는 상품의 이름과 수량이 존재한다


### 편의점
- 편의점은 상품들의 모음이다.

### 상품

- 상품은 이름, 가격, 프로모션 , 프로모션 재고 수량, 일반 재고 수량을 가진다 
- 상품을 이름을 기반으로 같은 상품이라고 인식할 수 있다
- 상품은 프로모션 주문을 처리하여 재고를 줄일 수 있어야 한다
- 상품의 가격은 0일 수 없다.
- 상품은 반드시 이름.가격,재고 수량, Promotion이 존재해야 한다. 상품의 정체성은 이 값들의 존재다
- 상품은 재고와 관련 로직중 프로모션일때 재고 관리는 promotion에게 물어본다.
- 일반 재고관리를 수행한다. request에 대해서 Dto를 던진다
- ~~상품은 수량을 리필할수도 있어야 한다는 넣지말자~~

### 프로모션

- 프로 모션은 이름을 가지고 있어야 한다
- 프로 모션은 request에 맞게 dto를 던져야 한다.
- 프로 모션은 프로모션 check 기능이 있어야 한다.
- 근데 당연히 시작과 끝나는게 뒤틀리면 안되고 (개발자에러?)
- 하나사는데 두개 준다 이런건 하지말자
- 로직 테스트 빡세게 해봐야할듯

### checkSummaryDto

- 꼭 필요한 것들만들고 있자
- 승인여부
- 보너스 받을수 있는 것
- 프로모션 못받는거

객체 자체의 의미가 없는 값 객체는 getter를 열어준다.

## 회원 Customer를 만든다.

---
## VO (소비자의 입장에서의 VO를 다루도록 한다) 
### 주문 수량

- 수량은 0보다 작아질 수 없다
- 수량은 int이여야 한다
- getter가 있다
- 수량을 음수로 만들만큼 많은 숫자를 빼면 에러가 난다

### 구매 상품의 이름

- 상품의 이름은 반드시 존재해야한다 (null이 아니다)
- 상품의 이름이 공백만으로 존재할 수는 없다.
- 상품은 재고 안에 존재하고 있어야 한다. -> 재고 리스트를 input 과정에서 만들자
- getter가 있다

### 에러
- 구매할 상품과 수량 형식이 올바르지 않은 경우: `[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.`
- 존재하지 않는 상품을 입력한 경우: `[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.`
- 구매 수량이 재고 수량을 초과한 경우: `[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.`
- 기타 잘못된 입력의 경우: `[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.`

### 


### 맴버십에 대한 입력 부분을 추가해야할 것 같다 .

---

## ✅ FeedBack 체크 리스트

## 1️⃣ 가독성

- [x]  작명의 의미 전달력 + Number 같이 다른것과 겹치는거 사용 x
- [x]  코드 포맷팅 (IntelliJ IDEA: ⌥⌘L)
- [ ]  요약 설명은 Comment에, ReadMe는 꼼꼼히하고 상시 업데이트
- [ ]  구현 순서를 깔끔하게 하였는가

## 2️⃣ 코딩 습관

- [x]  필요하다면 싱글톤을 적용하기 -> Config의 관리 밖의 것들을 싱글톤으로 처리
- [x]  안 쓰는 Importation 삭제
- [x]  변수명에 자료형을 쓰지 않는다
- [x]  변수명에 불용어를 넣지 않는다
- [x]  if 안에 부정문을 줄여보자
- [x]  와일드 카드를 지양한다 -> 필요없는 경우 사용 x
- [x]  commit의 가독성을 놓치지 말자

## 3️⃣ 설계 중점 사항

- [x]  Config를 써보자
- [x]  참조로 추적되는 데이터형 getter 지양 → DTO 사용 (record 타입), 방어적 복사 대안, 수정 불가 객체 return
- [x]  DTO는 의미있게 사용한다(+ Dto 내부에서 생성 처리)
- [x]  구현 방식에 집중해서 과제 자체에 대한 예외들을 빼먹지 말 것
- [x]  상수가 연관성이 있다면 enum 사용
- [x]  메서드는 한 가지 일만 처리한다 (10 줄 이하 이건 메인 코드도 동일) -> 최대한 노력
- [x]  마지막 프로그래밍 요구 사항 지켰는지 check (예외를 잘보자)
- [x]  많이 의존 당한다면 interface의 근거가 충분하다 (+ 인터페이스화 할 근거가 필요 가령, 활용성이 높다) 
- [x]  객체는 객체에서의 역할에 충실하고 나머지는 service에 존재한다
- [x]  validation은 객체 자체의 정체성, 유효성 검사, 파싱, 그리고 정제를 분리해서 생각한다.
- [x]  view의 역할을 침범하지 말자 ex) 포메팅
- [x]  getter를 되도록 이면 지양한다 캡슐화에 조금 더 신경 써야 할 것 같다.
- [x]  필드를 줄이기 위해 노력하자

## 4️⃣ Test

- [ ]  test coverage check (comment에 첨부해보기?)
- [x]  테스트 자체에서 메서드를 만들거나 여러 기능을 사용해 중복 테스트 줄이기
- [x]  2주차 feedback 테스트 메서드 참고 자료 활용
- [x]  UI(System.out, System.in, Scanner) 로직은 제외한다 (굳이 사용할 필요가 없었다)
- [x]  Interface 적극 사용 → 테스트 대역이라면 따로 분리하자
- [x]  테스트를 하기 용이하게 코드를 작성한다
- [x]  TDD에 대한 시도를 해본다 -> 확정된 설계를 하면 유용하겠지만 너무 오래 걸렸다.
- [x]  공백 테스트는 \n \t 같은 것도 하자
- [x]  예외 테스트를 집중적으로 하자
- [ ]  테스트 코드도 가독성을 신경써야 한다

## 5️⃣ 기타

- [x]  stackOverflow를 고려한다
- [x]  enum Map을 고려한다 -> 사용할 필요가 없었다
- [x]  Java 8의 인터페이스 default 기능을 고려한다 (like 상속) ->미사용
- [x]  필요하다면 상속을 사용해보자 -> 미사용
- [x]  꼭 필요한 경우가 아니라면 상수 처리할 필요가 없을 것 같다
- [x]  숫자 구분자 1_000_000 를 사용하면 가독성이 좋다
- [x]  서비스가 필드를 가지지 않도록 만들어보자 -> 이건 static이 아니라면 불가능해 보인다. 대신 도메인을 필드로 가지지 않도록 하였다
- [x]  줄 바꿈이 필요하다면 `System.lineSeparator()` 을 써보자
- [x]  예외 처리를 흐름 제어용으로 사용하지 말 것


